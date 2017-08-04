package com.minutch.fox.web.decoration;

import com.minutch.fox.biz.decoration.CustomerService;
import com.minutch.fox.entity.decoration.Customer;
import com.minutch.fox.http.SessionInfo;
import com.minutch.fox.param.Result;
import com.minutch.fox.result.decoration.CustomerVO;
import com.minutch.fox.utils.FoxBeanUtils;
import com.minutch.fox.web.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
    @Autowired
    private SessionInfo sessionInfo;

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

        Customer customer = customerService.getById(cusId);
        if (customer == null) {
            log.error("不存在的客户["+cusId+"]");
            return "decoration/userList";
        }

        if (!sessionInfo.getStoreId().equals(customer.getStoreId())) {
            log.error("客户["+cusId+"]不属于当前商家["+customer.getStoreId()+"]，无法删除.");
            return "decoration/userList";
        }

        if (cusId != null) {
            log.info("商家["+sessionInfo.getStoreId()+"]删除了客户["+cusId+"]");
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

        CustomerVO customerVO = new CustomerVO();
        BeanUtils.copyProperties(customer, customerVO);


        model.addAttribute("customer", customerVO);
        model.addAttribute("cusId", cusId);
        return "decoration/customerDetail";
    }

    @RequestMapping("orderList")
    public String orderList() {
        return "decoration/orderList";
    }

    @RequestMapping("remindOrderList")
    public String remindOrderList() {
        return "decoration/remindOrderList";
    }

    @RequestMapping("ui")
    public String ui() {
        return "decoration/other/ui";
    }

    @RequestMapping("form")
    public String form() {
        return "decoration/other/form";
    }

    @RequestMapping("chart")
    public String chart() {
        return "decoration/other/chart";
    }

    @RequestMapping("typography")
    public String typography() {
        return "decoration/other/typography";
    }

    @RequestMapping("gallery")
    public String gallery() {
        return "decoration/other/gallery";
    }

    @RequestMapping("table")
    public String table() {
        return "decoration/other/table";
    }

    @RequestMapping("calendar")
    public String calendar() {
        return "decoration/other/calendar";
    }

    @RequestMapping("grid")
    public String grid() {
        return "decoration/other/grid";
    }

    @RequestMapping("tour")
    public String tour() {
        return "decoration/other/tour";
    }

    @RequestMapping("icon")
    public String icon() {
        return "decoration/other/icon";
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


    @RequestMapping("modifyPassword")
    public String modifyPassword() {
        return "decoration/modifyPassword";
    }
}
