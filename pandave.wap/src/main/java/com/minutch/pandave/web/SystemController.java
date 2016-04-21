package com.minutch.pandave.web;

import com.minutch.pandave.biz.sys.UserService;
import com.minutch.pandave.biz.topic.TopicService;
import com.minutch.pandave.entity.system.User;
import com.minutch.pandave.entity.topic.Topic;
import com.minutch.pandave.param.Result;
import com.minutch.pandave.param.SystemParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
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
    @Autowired
    private TopicService topicService;

    @RequestMapping()
    public String system(Model model){

        log.info("now,this is a test common log.");
        User user = userService.getById(1000L);
        if (user != null) {
            System.out.println("user:"+user.getId());
        }
        log.error("now,this is a test error log.");


        Topic topic = topicService.getById(1000L);
        if (topic != null) {
            System.out.println("topic:"+topic);
        }
        model.addAttribute("model", "world!");
        return "dance/index";
    }

    @RequestMapping("index")
    public String index(Model model){


        return "dance/mainpage";
    }

    @RequestMapping("queryAll")
    @ResponseBody
    public Result<?> queryAll(@RequestBody SystemParam param){

        return Result.wrapSuccessfulResult("A");
    }
}
