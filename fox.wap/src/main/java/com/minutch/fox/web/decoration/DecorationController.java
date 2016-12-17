package com.minutch.fox.web.decoration;

import com.minutch.fox.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Minutch on 16/11/28.
 */
@RequestMapping("decoration")
@Controller
public class DecorationController extends BaseController{
    @RequestMapping("")
    public String index() {
        return "decoration/index";
    }
    @RequestMapping("ui")
    public String ui() {
        return "decoration/ui";
    }
    @RequestMapping("form")
    public String form() {
        return "decoration/form";
    }
    @RequestMapping("chart")
    public String chart() {
        return "decoration/chart";
    }
    @RequestMapping("typography")
    public String typography() {
        return "decoration/typography";
    }
    @RequestMapping("gallery")
    public String gallery() {
        return "decoration/gallery";
    }
    @RequestMapping("table")
    public String table() {
        return "decoration/table";
    }
    @RequestMapping("calendar")
    public String calendar() {
        return "decoration/calendar";
    }
    @RequestMapping("grid")
    public String grid() {
        return "decoration/grid";
    }
    @RequestMapping("tour")
    public String tour() {
        return "decoration/tour";
    }
    @RequestMapping("icon")
    public String icon() {
        return "decoration/icon";
    }
    @RequestMapping("error")
    public String error() {
        return "decoration/error";
    }
    @RequestMapping("login")
    public String login() {
        return "decoration/login";
    }
}
