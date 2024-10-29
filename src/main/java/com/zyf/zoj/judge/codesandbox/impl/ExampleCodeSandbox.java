package com.zyf.zoj.judge.codesandbox.impl;

import com.zyf.zoj.judge.codesandbox.CodeSandbox;
import com.zyf.zoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.zyf.zoj.judge.codesandbox.model.ExecuteCodeResponse;
import com.zyf.zoj.model.dto.questionsumbit.JudgeInfo;
import com.zyf.zoj.model.enums.JudgeInfoMessageEnum;
import com.zyf.zoj.model.enums.QuestionSubmitStatusEnum;

import java.util.Arrays;
import java.util.List;

/**
 * 示例代码沙箱（仅仅是为了跑通整个业务流程）
 */
public class ExampleCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        executeCodeResponse.setMessage("代码执行成功");
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getValue());
        judgeInfo.setTime(1000L);
        judgeInfo.setMemory(1000L);
        executeCodeResponse.setJudgeInfo(judgeInfo);
        return executeCodeResponse;
    }
}
