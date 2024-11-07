package com.zyf.zojbackenduserservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyf.zojbackendmodel.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户数据库操作
 *
 * @author zyf
 * 
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




