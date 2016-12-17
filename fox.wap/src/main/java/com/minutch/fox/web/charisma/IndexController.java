package com.minutch.fox.web.charisma;

import com.minutch.fox.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Minutch on 16/11/28.
 */
@RequestMapping("index")
@Controller
public class IndexController extends BaseController{

    @Autowired
    private HttpServletRequest request;

    @RequestMapping("")
    public String index() {
        return "charisma/index";
    }

    @RequestMapping("ui")
    public String ui() {
        return "charisma/ui";
    }
    @RequestMapping("form")
    public String form() {
        return "charisma/form";
    }
    @RequestMapping("chart")
    public String chart() {
        return "charisma/chart";
    }
    @RequestMapping("typography")
    public String typography() {
        return "charisma/typography";
    }
    @RequestMapping("gallery")
    public String gallery() {
        return "charisma/gallery";
    }
    @RequestMapping("table")
    public String table() {
        return "charisma/table";
    }
    @RequestMapping("calendar")
    public String calendar() {
        return "charisma/calendar";
    }
    @RequestMapping("grid")
    public String grid() {
        return "charisma/grid";
    }
    @RequestMapping("tour")
    public String tour() {
        return "charisma/tour";
    }
    @RequestMapping("icon")
    public String icon() {
        return "charisma/icon";
    }
    @RequestMapping("error")
    public String error() {
        return "charisma/error";
    }
    @RequestMapping("login")
    public String login() {
        return "charisma/login";
    }
}
