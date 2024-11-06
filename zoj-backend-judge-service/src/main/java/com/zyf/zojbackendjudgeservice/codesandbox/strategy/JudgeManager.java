package com.zyf.zojbackendjudgeservice.codesandbox.strategy;

import com.zyf.zojbackendmodel.codesandbox.JudgeInfo;
import com.zyf.zojbackendmodel.enums.QuestionSubmitLanguageEnum;

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
