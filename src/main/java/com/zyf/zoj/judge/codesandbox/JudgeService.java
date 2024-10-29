package com.zyf.zoj.judge.codesandbox;

import com.zyf.zoj.model.entity.QuestionSubmit;
import com.zyf.zoj.model.vo.QuestionSubmitVO;

public interface JudgeService {
    QuestionSubmit doJudge(Long questionSubmitId);
}
