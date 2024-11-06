package com.zyf.zojbackendjudgeservice.codesandbox.impl;

import com.zyf.zojbackendjudgeservice.codesandbox.CodeSandbox;
import com.zyf.zojbackendmodel.codesandbox.ExecuteCodeRequest;
import com.zyf.zojbackendmodel.codesandbox.ExecuteCodeResponse;
import com.zyf.zojbackendmodel.codesandbox.JudgeInfo;
import com.zyf.zojbackendmodel.enums.JudgeInfoMessageEnum;
import com.zyf.zojbackendmodel.enums.QuestionSubmitStatusEnum;

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
        judgeInfo.setMemory(10.0);
        executeCodeResponse.setJudgeInfo(judgeInfo);
        return executeCodeResponse;
    }
}
