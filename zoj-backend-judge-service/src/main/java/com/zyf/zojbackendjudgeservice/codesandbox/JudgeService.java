package com.zyf.zojbackendjudgeservice.codesandbox;

import com.zyf.zojbackendmodel.entity.QuestionSubmit;

public interface JudgeService {
    QuestionSubmit doJudge(Long questionSubmitId);
}
