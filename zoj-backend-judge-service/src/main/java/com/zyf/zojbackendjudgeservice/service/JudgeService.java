package com.zyf.zojbackendjudgeservice.service;

import com.zyf.zojbackendmodel.entity.QuestionSubmit;

public interface JudgeService {
    QuestionSubmit doJudge(Long questionSubmitId);
}
