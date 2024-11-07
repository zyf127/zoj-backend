package com.zyf.zojbackendserviceclient.service;

import com.zyf.zojbackendmodel.entity.Question;
import com.zyf.zojbackendmodel.entity.QuestionSubmit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 题目服务
 *
 * @author zyf
 * 
 */
@FeignClient(name = "zoj-backend-question-service", path = "/api/question/inner")
public interface QuestionFeignClient {

    /**
     * 根据 id 获取题目
     * @param questionId
     * @return
     */
    @GetMapping("/get/id")
    Question getQuestionById(@RequestParam("questionId") Long questionId);

    /**
     * 根据 id 获取题目提交记录
     *
     * @param questionSubmitId
     * @return
     */
    @GetMapping("/question_submit/get/id")
    QuestionSubmit getQuestionSubmitById(@RequestParam("questionSubmitId") Long questionSubmitId);

    /**
     * 根据 id 更新题目提交记录
     *
     * @param questionSubmit
     * @return
     */
    @PostMapping("/question_submit/update")
    boolean updateQuestionSubmitById(@RequestBody QuestionSubmit questionSubmit);
}
