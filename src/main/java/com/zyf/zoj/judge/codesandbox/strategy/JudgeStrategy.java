package com.zyf.zoj.judge.codesandbox.strategy;

import com.zyf.zoj.model.dto.questionsumbit.JudgeInfo;

/**
 * 判题策略
 */
public interface JudgeStrategy {

    /**
     * 判题
     *
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext);
}
