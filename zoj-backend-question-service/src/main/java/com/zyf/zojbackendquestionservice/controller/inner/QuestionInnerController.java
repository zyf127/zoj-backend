package com.zyf.zojbackendquestionservice.controller.inner;

import com.zyf.zojbackendmodel.entity.Question;
import com.zyf.zojbackendmodel.entity.QuestionSubmit;
import com.zyf.zojbackendquestionservice.service.QuestionService;
import com.zyf.zojbackendquestionservice.service.QuestionSubmitService;
import com.zyf.zojbackendserviceclient.service.QuestionFeignClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 仅内部调用，不提供给前端
 */
@RestController
@RequestMapping("/inner")
public class QuestionInnerController implements QuestionFeignClient {

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    /**
     * 根据 id 获取题目
     * @param questionId
     * @return
     */
    @GetMapping("/get/id")
    @Override
    public Question getQuestionById(@RequestParam("questionId") Long questionId) {
        return questionService.getById(questionId);
    }

    /**
     * 根据 id 获取题目提交记录
     *
     * @param questionSubmitId
     * @return
     */
    @GetMapping("/question_submit/get/id")
    @Override
    public QuestionSubmit getQuestionSubmitById(@RequestParam("questionSubmitId") Long questionSubmitId) {
        return questionSubmitService.getById(questionSubmitId);
    }

    /**
     * 根据 id 更新题目提交记录
     *
     * @param questionSubmit
     * @return
     */
    @PostMapping("/question_submit/update")
    public boolean updateQuestionSubmitById(@RequestBody QuestionSubmit questionSubmit) {
        return questionSubmitService.updateById(questionSubmit);
    }

}
