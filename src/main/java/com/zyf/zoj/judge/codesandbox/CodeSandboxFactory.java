package com.zyf.zoj.judge.codesandbox;

import com.zyf.zoj.judge.codesandbox.impl.ExampleCodeSandbox;
import com.zyf.zoj.judge.codesandbox.impl.RemoteCodeSandbox;
import com.zyf.zoj.judge.codesandbox.impl.ThirdPartyCodeSandbox;

/**
 * 代码沙箱创建工厂
 */
public class CodeSandboxFactory {

    /**
     *根据传入的类型创建对应的代码沙箱
     *
     * @param type
     * @return
     */
    public static CodeSandbox newInstance(String type) {
        switch (type) {
            case "example":
                return new ExampleCodeSandbox();
            case "remote":
                return new RemoteCodeSandbox();
            case "thirdParty":
                return new ThirdPartyCodeSandbox();
            default:
                return new ExampleCodeSandbox();
        }
    }
}
