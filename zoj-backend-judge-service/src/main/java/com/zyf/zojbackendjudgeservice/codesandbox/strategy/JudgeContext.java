package com.zyf.zojbackendjudgeservice.codesandbox.strategy;

import com.zyf.zojbackendmodel.codesandbox.JudgeInfo;
import com.zyf.zojbackendmodel.dto.question.JudgeCase;
import com.zyf.zojbackendmodel.dto.question.JudgeConfig;
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
