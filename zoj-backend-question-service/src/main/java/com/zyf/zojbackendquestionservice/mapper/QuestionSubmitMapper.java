package com.zyf.zojbackendquestionservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyf.zojbackendmodel.entity.QuestionSubmit;
import org.apache.ibatis.annotations.Mapper;

/**
* @author zyf
* @description 针对表【question_submit(题目提交)】的数据库操作Mapper
* 
* @Entity com.zyf.zoj.model.entity.QuestionSubmit
*/
@Mapper
public interface QuestionSubmitMapper extends BaseMapper<QuestionSubmit> {

}




