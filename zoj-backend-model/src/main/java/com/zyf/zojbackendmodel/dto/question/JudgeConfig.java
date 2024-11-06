package com.zyf.zojbackendmodel.dto.question;

import lombok.Data;

/**
 * 题目配置
 *
 */
@Data
public class JudgeConfig {

    /**
     * 时间限制（ms）
     */
    private Long timeLimit;

    /**
     * 内存限制（MB）
     *
     */
    private Double memoryLimit;

    /**
     * 堆栈限制（MB）
     */
    private Double stackLimit;
}
