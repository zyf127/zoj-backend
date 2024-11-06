package com.zyf.zojbackendquestionservice.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyf.zojbackendcommon.common.ErrorCode;
import com.zyf.zojbackendcommon.constant.CommonConstant;
import com.zyf.zojbackendcommon.exception.BusinessException;
import com.zyf.zojbackendcommon.utils.SqlUtils;
import com.zyf.zojbackendmodel.dto.questionsumbit.QuestionSubmitAddRequest;
import com.zyf.zojbackendmodel.dto.questionsumbit.QuestionSubmitQueryRequest;
import com.zyf.zojbackendmodel.entity.Question;
import com.zyf.zojbackendmodel.entity.QuestionSubmit;
import com.zyf.zojbackendmodel.entity.User;
import com.zyf.zojbackendmodel.enums.QuestionSubmitLanguageEnum;
import com.zyf.zojbackendmodel.enums.QuestionSubmitStatusEnum;
import com.zyf.zojbackendmodel.vo.QuestionSubmitVO;
import com.zyf.zojbackendquestionservice.mapper.QuestionSubmitMapper;
import com.zyf.zojbackendquestionservice.service.QuestionService;
import com.zyf.zojbackendquestionservice.service.QuestionSubmitService;
import com.zyf.zojbackendserviceclient.service.JudgeFeignClient;
import com.zyf.zojbackendserviceclient.service.UserFeignClient;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
* @author zyf
* @description 针对表【question_submit(题目提交)】的数据库操作Service实现
* 
*/
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
    implements QuestionSubmitService {

    @Resource
    private QuestionService questionService;

    @Resource
    private UserFeignClient userFeignClient;

    @Resource
    @Lazy
    private JudgeFeignClient judgeFeignClient;

    @Override
    public Long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {
        // 检验编程语言是否合法
        String language = questionSubmitAddRequest.getLanguage();
        QuestionSubmitLanguageEnum languageEnum = QuestionSubmitLanguageEnum.getEnumByValue(language);
        if (languageEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "编程语言错误");
        }
        // 判断实体是否存在，根据类别获取实体
        Long questionId = questionSubmitAddRequest.getQuestionId();
        Question question = questionService.getById(questionSubmitAddRequest.getQuestionId());
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        Long userId = loginUser.getId();
        // 提交题目
        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setLanguage(questionSubmitAddRequest.getLanguage());
        questionSubmit.setCode(questionSubmitAddRequest.getCode());
        questionSubmit.setQuestionId(questionId);
        questionSubmit.setUserId(userId);
        // 设置初始状态
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        questionSubmit.setJudgeInfo("{}");
        boolean save = this.save(questionSubmit);
        if (!save) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "提交题目失败");
        }
        // 进行判题
        Long questionSubmitId = questionSubmit.getId();
        CompletableFuture.runAsync(() -> {
            judgeFeignClient.doJudge(questionSubmitId);
        });
        return questionSubmitId;
    }

    @Override
    public QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest) {
        QueryWrapper<QuestionSubmit> queryWrapper = new QueryWrapper<>();
        if (questionSubmitQueryRequest == null) {
            return queryWrapper;
        }

        String language = questionSubmitQueryRequest.getLanguage();
        Integer status = questionSubmitQueryRequest.getStatus();
        Long questionId = questionSubmitQueryRequest.getQuestionId();
        String questionTitle = questionSubmitQueryRequest.getQuestionTitle();
        Long userId = questionSubmitQueryRequest.getUserId();
        String sortField = questionSubmitQueryRequest.getSortField();
        String sortOrder = questionSubmitQueryRequest.getSortOrder();

        // 拼接查询条件
        queryWrapper.eq(StringUtils.isNotEmpty(language), "language", language);
        queryWrapper.eq(QuestionSubmitStatusEnum.getEnumByValue(status) != null, "status", status);
        queryWrapper.eq(ObjectUtils.isNotEmpty(questionId), "question_id", questionId);
        if (StringUtils.isNotBlank(questionTitle)) {
            List<Question> questionList = questionService.list(new QueryWrapper<Question>().like("title", questionTitle));
            if (!CollectionUtils.isEmpty(questionList)) {
                List<Long> questionIdList = questionList.stream().map(Question::getId).collect(Collectors.toList());
                queryWrapper.in(!CollectionUtils.isEmpty(questionIdList), "question_id", questionIdList);
            }
        }
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "user_id", userId);
        queryWrapper.eq("is_delete", false);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Override
    public QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser) {
        QuestionSubmitVO questionSubmitVO = QuestionSubmitVO.objToVo(questionSubmit);
        // 脱敏：仅本人和管理员能看见自己提交的代码
        long userId = loginUser.getId();
        if (userId != questionSubmitVO.getUserId() && !userFeignClient.isAdmin(loginUser)) {
            questionSubmitVO.setCode(null);
        }
        return questionSubmitVO;
    }

    @Override
    public Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser) {
        List<QuestionSubmit> questionSubmitList = questionSubmitPage.getRecords();
        Page<QuestionSubmitVO> questionSubmitVOPage = new Page<>(questionSubmitPage.getCurrent(), questionSubmitPage.getSize(), questionSubmitPage.getTotal());
        if (CollUtil.isEmpty(questionSubmitList)) {
            return questionSubmitVOPage;
        }
        List<QuestionSubmitVO> questionSubmitVOList = questionSubmitList.stream()
                .map(questionSubmit -> getQuestionSubmitVO(questionSubmit, loginUser))
                .collect(Collectors.toList());
        List<Long> userIdList = questionSubmitList.stream().map(QuestionSubmit::getUserId).collect(Collectors.toList());
        Map<Long, String> userIdNameMap = userFeignClient.listByIds(userIdList).stream().collect(Collectors.toMap(User::getId, User::getUserName));
        List<Long> questionIdList = questionSubmitList.stream().map(QuestionSubmit::getQuestionId).collect(Collectors.toList());
        Map<Long, String> questionIdTitleMap = questionService.listByIds(questionIdList).stream().collect(Collectors.toMap(Question::getId, Question::getTitle));
        for (QuestionSubmitVO questionSubmitVO : questionSubmitVOList) {
            questionSubmitVO.setUserName(userIdNameMap.get(questionSubmitVO.getUserId()));
            questionSubmitVO.setQuestionTitle(questionIdTitleMap.get(questionSubmitVO.getQuestionId()));
        }
        questionSubmitVOPage.setRecords(questionSubmitVOList);
        return questionSubmitVOPage;
    }
}




