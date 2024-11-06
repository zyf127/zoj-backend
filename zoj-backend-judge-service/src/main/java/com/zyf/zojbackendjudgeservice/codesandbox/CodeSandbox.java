package com.zyf.zojbackendjudgeservice.codesandbox;

import com.zyf.zojbackendmodel.codesandbox.ExecuteCodeRequest;
import com.zyf.zojbackendmodel.codesandbox.ExecuteCodeResponse;

/**
 * 代码沙箱接口
 */
public interface CodeSandbox {
    /**
     * 在代码沙箱中执行代码
     *
     * @param executeCodeRequest 执行代码请求信息
     * @return 执行代码响应信息
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
