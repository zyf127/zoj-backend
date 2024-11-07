package com.zyf.zojbackendquestionservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyf.zojbackendmodel.entity.Question;
import org.apache.ibatis.annotations.Mapper;

/**
* @author zyf
* @description 针对表【question(题目)】的数据库操作Mapper
* 
* @Entity com.zyf.zoj.model.entity.Question
*/
@Mapper
public interface QuestionMapper extends BaseMapper<Question> {

}




