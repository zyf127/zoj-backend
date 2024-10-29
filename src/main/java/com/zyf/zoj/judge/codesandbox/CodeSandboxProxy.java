package com.zyf.zoj.judge.codesandbox;

import com.zyf.zoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.zyf.zoj.judge.codesandbox.model.ExecuteCodeResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 代码沙箱代理
 */
@Slf4j
@AllArgsConstructor
public class CodeSandboxProxy implements CodeSandbox {

    private final CodeSandbox codeSandbox;

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("代码沙箱请求信息：" + executeCodeRequest.toString());
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        log.info("代码沙箱响应信息：" + executeCodeResponse.toString());
        return executeCodeResponse;
    }
}
