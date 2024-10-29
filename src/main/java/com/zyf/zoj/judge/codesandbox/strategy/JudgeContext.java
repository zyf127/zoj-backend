package com.zyf.zoj.judge.codesandbox.strategy;

import com.zyf.zoj.model.dto.question.JudgeCase;
import com.zyf.zoj.model.dto.question.JudgeConfig;
import com.zyf.zoj.model.dto.questionsumbit.JudgeInfo;
import lombok.Data;

import java.util.List;

/**
 * 判题上下文（定义在判题策略中传递的参数）
 */
@Data
public class JudgeContext {

    private JudgeInfo judgeInfo;

    private List<String> actualOutputList;

    private JudgeConfig judgeConfig;

    private List<JudgeCase> judgeCaseList;

    private String language;
}
