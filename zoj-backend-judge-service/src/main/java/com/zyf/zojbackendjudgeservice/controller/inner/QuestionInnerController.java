package com.zyf.zojbackendjudgeservice.controller.inner;

import com.zyf.zojbackendjudgeservice.codesandbox.JudgeService;
import com.zyf.zojbackendmodel.entity.QuestionSubmit;
import com.zyf.zojbackendserviceclient.service.JudgeFeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * 仅内部调用，不提供给前端
 */
@RestController
@RequestMapping("/inner")
public class QuestionInnerController implements JudgeFeignClient {

    @Resource
    private JudgeService judgeService;

    /**
     * 判题
     *
     * @param questionSubmitId
     * @return
     */
    @PostMapping("/do")
    public QuestionSubmit doJudge(@RequestParam("questionSubmitId") Long questionSubmitId) {
        return judgeService.doJudge(questionSubmitId);
    }

}
