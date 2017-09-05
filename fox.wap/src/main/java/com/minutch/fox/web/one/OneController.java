package com.minutch.fox.web.one;

import com.minutch.fox.config.SystemConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Minutch on 17/4/19.
 */
@Controller
@RequestMapping("one")
@Slf4j
public class OneController {

    @Autowired
    private SystemConfig systemConfig;


    @RequestMapping()
    public String index(Model model){

        model.addAttribute("serverUrl", systemConfig.getServerUrl());
        return "main/index";
    }

    @RequestMapping("right")
    public String right(){

        return "one/right";
    }

    @RequestMapping("left")
    public String left(){

        return "one/left";
    }
}
