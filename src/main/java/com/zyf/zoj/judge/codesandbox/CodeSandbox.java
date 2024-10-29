package com.zyf.zoj.judge.codesandbox;

import com.zyf.zoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.zyf.zoj.judge.codesandbox.model.ExecuteCodeResponse;

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
