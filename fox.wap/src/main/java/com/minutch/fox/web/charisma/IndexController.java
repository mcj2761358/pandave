package com.minutch.fox.web.charisma;

import com.minutch.fox.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Minutch on 16/11/28.
 */
@RequestMapping("index")
@Controller
public class IndexController extends BaseController{

    @RequestMapping("")
    public String index() {

        return "charisma/index";
    }
}
