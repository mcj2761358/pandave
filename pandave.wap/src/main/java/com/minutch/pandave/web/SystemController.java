package com.minutch.pandave.web;

import com.minutch.pandave.biz.sys.UserService;
import com.minutch.pandave.entity.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Minutch on 16/2/22.
 */
@Controller
@RequestMapping("system")
public class SystemController {

    @Autowired
    private UserService userService;

    @RequestMapping()
    @ResponseBody
    public String system(){

        User user = userService.getById(1000L);
        if (user != null) {
            System.out.println("user:"+user.getId());
        }
        return "hello";
    }
}
