package com.zyf.zojbackendmodel.codesandbox;

import lombok.Data;

/**
 * 题目信息
 */
@Data
public class JudgeInfo {
    /**
     * 程序执行信息
     */
    private String message;

    /**
     * 消耗内存（MB）
     */
    private Double memory;

    /**
     * 消耗时间（ms）
     */
    private Long time;
}

