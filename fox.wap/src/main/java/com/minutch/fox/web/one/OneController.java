package com.minutch.fox.web.one;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Minutch on 17/4/19.
 */
@Controller
@RequestMapping("one")
@Slf4j
public class OneController {

    @RequestMapping()
    public String index(){

        return "one/index";
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
