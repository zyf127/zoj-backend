package com.zyf.zojbackenduserservice.controller.inner;

import com.zyf.zojbackendmodel.entity.User;
import com.zyf.zojbackendserviceclient.service.UserFeignClient;
import com.zyf.zojbackenduserservice.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * 仅内部调用，不提供给前端
 */
@RestController
@RequestMapping("/inner")
public class UserInnerController implements UserFeignClient {

    @Resource
    private UserService userService;

    /**
     * 根据 id 获取用户
     *
     * @param id
     * @return
     */
    @GetMapping("/get/id")
    @Override
    public User getUserById(@RequestParam("id") Long id) {
        return userService.getById(id);
    }

    /**
     * 根据 ids 获取用户列表
     *
     * @param ids
     * @return
     */
    @GetMapping("/get/ids")
    @Override
    public List<User> listUserByIds(@RequestParam("ids") Collection<Long> ids) {
        return userService.listByIds(ids);
    }
}
