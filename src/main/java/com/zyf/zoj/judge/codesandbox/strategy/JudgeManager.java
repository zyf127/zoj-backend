package com.zyf.zoj.judge.codesandbox.strategy;

import com.zyf.zoj.model.dto.questionsumbit.JudgeInfo;
import com.zyf.zoj.model.enums.QuestionSubmitLanguageEnum;

public class JudgeManager {
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        String language = judgeContext.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if (QuestionSubmitLanguageEnum.JAVA.getValue().equals(language)) {
            judgeStrategy = new JavaJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
