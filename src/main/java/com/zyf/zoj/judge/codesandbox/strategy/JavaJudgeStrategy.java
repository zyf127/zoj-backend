package com.zyf.zoj.judge.codesandbox.strategy;

import com.zyf.zoj.model.dto.question.JudgeCase;
import com.zyf.zoj.model.dto.question.JudgeConfig;
import com.zyf.zoj.model.dto.questionsumbit.JudgeInfo;
import com.zyf.zoj.model.enums.JudgeInfoMessageEnum;

import java.util.List;
import java.util.stream.Collectors;

public class JavaJudgeStrategy implements JudgeStrategy {
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        List<String> actualOutputList = judgeContext.getActualOutputList();
        JudgeConfig judgeConfig = judgeContext.getJudgeConfig();
        List<JudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();
        JudgeInfoMessageEnum judgeInfoMessageEnum = JudgeInfoMessageEnum.ACCEPTED;
        List<String> standardOutputList = judgeCaseList.stream().map(JudgeCase::getOutput).collect(Collectors.toList());
        JudgeInfo judgeInfoResponse = new JudgeInfo();
        judgeInfoResponse.setTime(judgeInfo.getTime());
        judgeInfoResponse.setMemory(judgeInfo.getMemory());
        // 判题实际输出用例数量与标准输出用例数量是否一致
        if (actualOutputList.size() != standardOutputList.size()) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        // 判断用例是否全部通过
        for (int i = 0; i < actualOutputList.size(); i++) {
            if (!actualOutputList.get(i).equals(standardOutputList.get(i))) {
                judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
                judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
                return judgeInfoResponse;
            }
        }
        Long time = judgeInfo.getTime();
        Long memory = judgeInfo.getMemory();
        // 判断是否超时
        if (time / 2 > judgeConfig.getTimeLimit()) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        // 判断是否超出内存限制
        if (memory > judgeConfig.getMemoryLimit()) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
        return judgeInfoResponse;
    }
}
