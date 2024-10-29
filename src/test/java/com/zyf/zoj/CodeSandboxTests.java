package com.zyf.zoj;

import com.zyf.zoj.judge.codesandbox.CodeSandbox;
import com.zyf.zoj.judge.codesandbox.CodeSandboxFactory;
import com.zyf.zoj.judge.codesandbox.CodeSandboxProxy;
import com.zyf.zoj.judge.codesandbox.impl.ExampleCodeSandbox;
import com.zyf.zoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.zyf.zoj.judge.codesandbox.model.ExecuteCodeResponse;
import com.zyf.zoj.model.enums.QuestionSubmitLanguageEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class CodeSandboxTests {

    @Value("${codesandbox.type:example}")
    private String type;
    @Test
    void contextLoads() {
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        CodeSandboxProxy codeSandboxProxy = new CodeSandboxProxy(codeSandbox);
        String code = "int main() {return 0;}";
        String language = QuestionSubmitLanguageEnum.CPLUSPLUS.getValue();
        List<String> inputList = Arrays.asList("1 2", "3 4");
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .inputList(inputList)
                .language(language)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandboxProxy.executeCode(executeCodeRequest);
    }
}
