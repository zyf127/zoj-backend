package com.zyf.zojbackendjudgeservice.codesandbox.factory;

import com.zyf.zojbackendjudgeservice.codesandbox.CodeSandbox;
import com.zyf.zojbackendjudgeservice.codesandbox.impl.ExampleCodeSandbox;
import com.zyf.zojbackendjudgeservice.codesandbox.impl.RemoteCodeSandbox;
import com.zyf.zojbackendjudgeservice.codesandbox.impl.ThirdPartyCodeSandbox;

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
