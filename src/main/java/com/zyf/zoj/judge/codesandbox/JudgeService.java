package com.zyf.zoj.judge.codesandbox;

import com.zyf.zoj.model.entity.QuestionSubmit;

public interface JudgeService {
    QuestionSubmit doJudge(Long questionSubmitId);
}
