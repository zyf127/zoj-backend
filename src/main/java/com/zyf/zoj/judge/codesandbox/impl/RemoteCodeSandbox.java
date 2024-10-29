package com.zyf.zoj.judge.codesandbox.impl;

import com.zyf.zoj.judge.codesandbox.CodeSandbox;
import com.zyf.zoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.zyf.zoj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 远程代码沙箱
 */
public class RemoteCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程代码沙箱执行代码。。。");
        return null;
    }
}
