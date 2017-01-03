package com.minutch.fox.web.decoration;

import com.minutch.fox.biz.decoration.CustomerService;
import com.minutch.fox.entity.decoration.Customer;
import com.minutch.fox.param.Result;
import com.minutch.fox.web.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Minutch on 16/11/28.
 */
@RequestMapping("decoration")
@Controller
@Slf4j
public class DecorationController extends BaseController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping("")
    public String index() {
        return "decoration/index";
    }

    @RequestMapping("userList")
    public String userList() {
        return "decoration/userList";
    }

    @RequestMapping("createCustomer")
    public String createCustomer(Long cusId, Model model) {

        Customer customer = null;
        if (cusId != null) {
            customer = customerService.getById(cusId);
        }
        if (customer == null) {
            customer = new Customer();
        }
        model.addAttribute("customer", customer);
        return "decoration/createCustomer";
    }

    @RequestMapping("deleteCustomer")
    public String deleteCustomer(Long cusId) {

        if (cusId != null) {
            customerService.deleteById(cusId);
        }

        return "decoration/userList";
    }


    @RequestMapping("createOrder")
    public String createOrder(Long cusId, Model model) {

        if (cusId==null) {
            log.error("参数错误");
            model.addAttribute("errorMsg", "参数错误，客户ID为空！");
            return "decoration/error500";
        }

        Customer customer = customerService.getById(cusId);
        if (customer == null) {
            log.error("不存在的客户["+cusId+"]");
            model.addAttribute("errorMsg", "不存在的客户["+cusId+"]");
            return "decoration/error500";
        }
        model.addAttribute("customer", customer);
        model.addAttribute("cusId", cusId);
        return "decoration/createOrder";
    }



    @RequestMapping("customerDetail")
    public String customerDetail(Long cusId, Model model) {

        if (cusId==null) {
            log.error("参数错误");
            model.addAttribute("errorMsg", "参数错误，客户ID为空！");
            return "decoration/error500";
        }

        Customer customer = customerService.getById(cusId);
        if (customer == null) {
            log.error("不存在的客户["+cusId+"]");
            model.addAttribute("errorMsg", "不存在的客户["+cusId+"]");
            return "decoration/error500";
        }
        model.addAttribute("customer", customer);
        model.addAttribute("cusId", cusId);
        return "decoration/customerDetail";
    }

    @RequestMapping("orderList")
    public String orderList() {
        return "decoration/orderList";
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

    @RequestMapping("error500")
    public String error500() {
        return "decoration/error500";
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
