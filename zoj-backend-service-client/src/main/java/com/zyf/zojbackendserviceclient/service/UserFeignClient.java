package com.zyf.zojbackendserviceclient.service;


import com.zyf.zojbackendmodel.entity.User;
import com.zyf.zojbackendmodel.enums.UserRoleEnum;
import com.zyf.zojbackendmodel.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

/**
 * 用户服务
 *
 * @author zyf
 * 
 */
@FeignClient(name = "zoj-backend-user-service", path = "/api/user/inner")
public interface UserFeignClient {

    /**
     * 根据 id 获取用户
     *
     * @param id
     * @return
     */
    @GetMapping("/get/id")
    User getUserById(@RequestParam("id") Long id);

    /**
     * 根据 ids 获取用户列表
     *
     * @param ids
     * @return
     */
    @GetMapping("/get/ids")
    List<User> listUserByIds(@RequestParam("ids") Collection<Long> ids);

    /**
     * 获取当前登录用户
     *
     * @param authorization
     * @return
     */
    @GetMapping("/get/login")
    User getLoginUser(@RequestHeader("Authorization") String authorization);

    /**
     * 是否为管理员
     *
     * @param user
     * @return
     */
    default boolean isAdmin(User user) {
        return user != null && UserRoleEnum.ADMIN.getValue().equals(user.getUserRole());
    }


    /**
     * 获取脱敏的用户信息
     *
     * @param user
     * @return
     */
    default UserVO getUserVO(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

}
