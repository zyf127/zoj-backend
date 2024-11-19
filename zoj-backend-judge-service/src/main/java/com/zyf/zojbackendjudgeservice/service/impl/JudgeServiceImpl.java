package com.zyf.zojbackendjudgeservice.service.impl;

import cn.hutool.json.JSONUtil;
import com.zyf.zojbackendcommon.common.ErrorCode;
import com.zyf.zojbackendcommon.exception.BusinessException;
import com.zyf.zojbackendjudgeservice.codesandbox.CodeSandbox;
import com.zyf.zojbackendjudgeservice.codesandbox.factory.CodeSandboxFactory;
import com.zyf.zojbackendjudgeservice.codesandbox.proxy.CodeSandboxProxy;
import com.zyf.zojbackendjudgeservice.codesandbox.strategy.JudgeContext;
import com.zyf.zojbackendjudgeservice.codesandbox.strategy.JudgeManager;
import com.zyf.zojbackendjudgeservice.service.JudgeService;
import com.zyf.zojbackendmodel.codesandbox.ExecuteCodeRequest;
import com.zyf.zojbackendmodel.codesandbox.ExecuteCodeResponse;
import com.zyf.zojbackendmodel.codesandbox.JudgeInfo;
import com.zyf.zojbackendmodel.dto.question.JudgeCase;
import com.zyf.zojbackendmodel.dto.question.JudgeConfig;
import com.zyf.zojbackendmodel.entity.Question;
import com.zyf.zojbackendmodel.entity.QuestionSubmit;
import com.zyf.zojbackendmodel.enums.JudgeInfoMessageEnum;
import com.zyf.zojbackendmodel.enums.QuestionSubmitStatusEnum;
import com.zyf.zojbackendserviceclient.service.QuestionFeignClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService {

    @Value("${codesandbox.type:example}")
    private String type;

    @Resource
    private QuestionFeignClient questionFeignClient;

    @Override
    public QuestionSubmit doJudge(Long questionSubmitId) {
        // 1.根据题目提交id获取提交信息和题目信息
        QuestionSubmit questionSubmit = questionFeignClient.getQuestionSubmitById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目提交信息不存在");
        }
        Question question = questionFeignClient.getQuestionById(questionSubmit.getQuestionId());
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }
        // 2.判断提交信息的状态是否为等待中，等待中的提交信息才可以开始判题
        Integer status = questionSubmit.getStatus();
        if (!status.equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目正在判题中");
        }
        // 3.更新提交信息的状态，防止重复执行
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionFeignClient.updateQuestionSubmitById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目提交信息状态更新错误");
        }
        // 4.使用代码沙箱进行判题
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(question.getJudgeCase(), JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        CodeSandboxProxy codeSandboxProxy = new CodeSandboxProxy(codeSandbox);
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .inputList(inputList)
                .language(language)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandboxProxy.executeCode(executeCodeRequest);
        // 5.获取判题的结果，分析其中的信息（是否通过全部用例、是否超出时间限制、是否超出内存限制）
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setActualOutputList(executeCodeResponse.getOutputList());
        judgeContext.setJudgeConfig(JSONUtil.toBean(question.getJudgeConfig(), JudgeConfig.class));
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setLanguage(questionSubmit.getLanguage());
        JudgeManager judgeManager = new JudgeManager();
        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);
        // 6.返回更新判题后的状态
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        update = questionFeignClient.updateQuestionSubmitById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目提交信息状态更新错误");
        }
        // 更新题目通过数
        if (JudgeInfoMessageEnum.ACCEPTED.getValue().equals(judgeInfo.getMessage())) {
            update = questionFeignClient.incrementQuestionAcceptedNum(question.getId());
            if (!update) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "更新题目通过数失败");
            }
        }
        return questionFeignClient.getQuestionSubmitById(questionSubmitId);
    }
}
