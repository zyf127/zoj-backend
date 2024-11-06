package com.zyf.zojbackendjudgeservice.codesandbox.impl;

import com.zyf.zojbackendjudgeservice.codesandbox.CodeSandbox;
import com.zyf.zojbackendmodel.codesandbox.ExecuteCodeRequest;
import com.zyf.zojbackendmodel.codesandbox.ExecuteCodeResponse;

/**
 * 第三方代码沙箱
 */
public class ThirdPartyCodeSandbox implements CodeSandbox {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("第三方代码沙箱执行代码。。。");
        return null;
    }
}
