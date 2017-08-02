package com.minutch.fox.web.decoration;

import com.minutch.fox.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Minutch on 17/8/2.
 */
@Controller
@RequestMapping("doc")
public class DocController extends BaseController {

    @RequestMapping("index")
    public String index() {
        return "docs/index";
    }


    @RequestMapping("createCustomer")
    public String createCustomer() {
        return "docs/createCustomer";
    }


    @RequestMapping("queryCustomer")
    public String queryCustomer() {
        return "docs/queryCustomer";
    }


    @RequestMapping("modifyCustomer")
    public String modifyCustomer() {
        return "docs/modifyCustomer";
    }


    @RequestMapping("deleteCustomer")
    public String deleteCustomer() {
        return "docs/deleteCustomer";
    }


    @RequestMapping("createOrder")
    public String createOrder() {
        return "docs/createOrder";
    }


    @RequestMapping("queryOrder")
    public String queryOrder() {
        return "docs/queryOrder";
    }


    @RequestMapping("modifyOrder")
    public String modifyOrder() {
        return "docs/modifyOrder";
    }


    @RequestMapping("deleteOrder")
    public String deleteOrder() {
        return "docs/deleteOrder";
    }


    @RequestMapping("payOrder")
    public String payOrder() {
        return "docs/payOrder";
    }
}
