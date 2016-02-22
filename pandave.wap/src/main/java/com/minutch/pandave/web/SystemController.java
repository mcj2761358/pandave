package com.minutch.pandave.web;

import com.minutch.pandave.biz.sys.UserService;
import com.minutch.pandave.entity.system.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Minutch on 16/2/22.
 */
@Controller
@RequestMapping("system")
@Slf4j
public class SystemController {

    @Autowired
    private UserService userService;

    @RequestMapping()
    @ResponseBody
    public String system(){

        log.info("now,this is a test common log.");
        User user = userService.getById(1000L);
        if (user != null) {
            System.out.println("user:"+user.getId());
        }
        log.error("now,this is a test error log.");
        return "hello";
    }
}
