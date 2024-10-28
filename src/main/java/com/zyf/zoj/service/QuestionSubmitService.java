package com.zyf.zoj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyf.zoj.model.dto.questionsumbit.QuestionSubmitAddRequest;
import com.zyf.zoj.model.dto.questionsumbit.QuestionSubmitQueryRequest;
import com.zyf.zoj.model.entity.Question;
import com.zyf.zoj.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zyf.zoj.model.entity.User;
import com.zyf.zoj.model.vo.QuestionSubmitVO;
import com.zyf.zoj.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author zyf
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* 
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {

    /**
     * 题目提交
     *
     * @param questionSubmitAddRequest 题目提交信息
     * @param loginUser 用户信息
     * @return
     */
    Long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);


    /**
     * 获取题目封装
     *
     * @param questionSubmit
     * @param loginUser
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser);

    /**
     * 分页获取题目封装
     *
     * @param questionSubmitPage
     * @param loginUser
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser);
}
