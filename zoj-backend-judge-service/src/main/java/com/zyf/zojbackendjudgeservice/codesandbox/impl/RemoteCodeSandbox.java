package com.zyf.zojbackendjudgeservice.codesandbox.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.zyf.zojbackendcommon.common.ErrorCode;
import com.zyf.zojbackendcommon.exception.BusinessException;
import com.zyf.zojbackendjudgeservice.codesandbox.CodeSandbox;
import com.zyf.zojbackendmodel.codesandbox.ExecuteCodeRequest;
import com.zyf.zojbackendmodel.codesandbox.ExecuteCodeResponse;
import org.springframework.stereotype.Component;

/**
 * 远程代码沙箱
 */
@Component
public class RemoteCodeSandbox implements CodeSandbox {

    // 定义鉴权请求头和密钥
    private static final String AUTH_REQUEST_HEADER = "auth";

    private static final String AUTH_REQUEST_SECRET_KEY = "hvIbPRZYipMSREJ/ZlCMTx9/+sOe3igjz9d0XqhcPOA=";

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        String url = "http://192.168.126.128:8700/executeCode";
        String jsonStr = JSONUtil.toJsonStr(executeCodeRequest);
        String responseStr = HttpUtil.createPost(url)
                .header(AUTH_REQUEST_HEADER, AUTH_REQUEST_SECRET_KEY)
                .body(jsonStr)
                .execute()
                .body();
        if (StrUtil.isBlank(responseStr)) {
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR, "executeCode remoteSandbox error, message = " + responseStr);
        }
        return JSONUtil.toBean(responseStr, ExecuteCodeResponse.class);
    }
}
