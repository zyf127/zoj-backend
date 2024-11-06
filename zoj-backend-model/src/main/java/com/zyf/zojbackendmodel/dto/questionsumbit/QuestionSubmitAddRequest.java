package com.zyf.zojbackendmodel.dto.questionsumbit;

import lombok.Data;

/**
 * 创建请求
 *
 * @author zyf
 *
 */
@Data
public class QuestionSubmitAddRequest {

    /**
     * 编程语言
     */
    private String language;

    /**
     * 用户代码
     */
    private String code;

    /**
     * 题目 id
     */
    private Long questionId;

    private static final long serialVersionUID = 1L;
}
