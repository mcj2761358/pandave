package com.minutch.fox.web.decoration;

import com.google.gson.Gson;
import com.minutch.fox.biz.decoration.*;
import com.minutch.fox.entity.decoration.*;
import com.minutch.fox.http.SessionInfo;
import com.minutch.fox.param.Result;
import com.minutch.fox.result.decoration.*;
import com.minutch.fox.utils.FoxBeanUtils;
import com.minutch.fox.utils.ListUtils;
import com.minutch.fox.web.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Minutch on 16/11/28.
 */
@RequestMapping("decoration")
@Controller
@Slf4j
public class DecorationController extends BaseController {

    Gson gson = new Gson();
    @Autowired
    private CustomerService customerService;
    @Autowired
    private SessionInfo sessionInfo;
    @Autowired
    private OrderHeaderService orderHeaderService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private WarehouseService warehouseService;

    @RequestMapping("dashboard")
    public String dashboard() {
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
            model.addAttribute("errorMsg", "不存在的客户[" + cusId +"]");
            return "decoration/error500";
        }
        model.addAttribute("customer", customer);
        model.addAttribute("cusId", cusId);
        return "decoration/createOrder";
    }

    @RequestMapping("customerDetail")
    public String customerDetail(Long cusId,String orderSn,Long orderId, Model model) {

        if (cusId==null && StringUtils.isBlank(orderSn) && orderId==null) {
            log.error("参数错误");
            model.addAttribute("errorMsg", "参数错误，客户ID为空！");
            return "decoration/error500";
        }



        //如果orderSN为空，oderId不为空
        if (StringUtils.isBlank(orderSn) && orderId!=null) {
            Order order = orderService.getById(orderId);
            if (order!=null) {
                OrderHeader orderHeader = orderHeaderService.getById(order.getHeaderId());

                if (orderHeader!=null) {

                    if (!getStoreId().equals(orderHeader.getStoreId())) {
                        log.error("当前商家["+getStoreId()+"]不能查看商家["+orderHeader.getStoreId()+"]的订单["+orderId+"]");
                        model.addAttribute("errorMsg", "您不能查看其他人的订单哦！");
                        return "decoration/error500";
                    }

                    orderSn = orderHeader.getOrderSn();
                    cusId = orderHeader.getCusId();
                }
            }
        }

        if (orderId!=null && cusId==null) {
            log.error("参数错误");
            model.addAttribute("errorMsg", "订单["+orderId+"]已被删除！");
            return "decoration/error500";
        }

        Customer customer = customerService.getById(cusId);
        if (customer == null) {
            log.error("不存在的客户["+cusId+"]");
            model.addAttribute("errorMsg", "不存在的客户["+cusId+"]");
            return "decoration/error500";
        }

        //查询客户信息
        CustomerVO customerVO = new CustomerVO();
        BeanUtils.copyProperties(customer, customerVO);
        model.addAttribute("customer", customerVO);
        model.addAttribute("cusId", cusId);
        model.addAttribute("orderSn", orderSn);

        //查询订单信息
        List<OrderHeaderVO> orderHeaderVOList = new ArrayList<>();
        if (StringUtils.isNotBlank(orderSn)) {
            OrderHeader orderHeader = orderHeaderService.queryByOrderSn(orderSn, cusId);
            if (orderHeader != null) {
                OrderHeaderVO headerVO = new OrderHeaderVO();
                BeanUtils.copyProperties(orderHeader, headerVO);

                Long headerId = headerVO.getId();
                List<Order> orderList = orderService.queryByHeaderId(headerId);
                if (ListUtils.isNotBlank(orderList)) {
                    headerVO.setOrderList(FoxBeanUtils.copyList(orderList, OrderVO.class));
                }
                orderHeaderVOList.add(headerVO);
            }
        } else {
            orderHeaderVOList = queryCustomerOrderList(cusId);
        }
        model.addAttribute("orderHeaderList", orderHeaderVOList);

        //查询商品信息
        List<Goods> goodsList = goodsService.queryAllStoreGoods(sessionInfo.getStoreId());
        if (ListUtils.isNotBlank(goodsList)) {
           List<StoreGoodsSearchMapVO> searchMap = new ArrayList<>();
            List<StoreGoodsSearchVO> searchList = FoxBeanUtils.copyList(goodsList, StoreGoodsSearchVO.class);
            for (StoreGoodsSearchVO searchVO:searchList) {
                searchVO.makeSearchKey();
                StoreGoodsSearchMapVO mapVO = new StoreGoodsSearchMapVO();
                mapVO.setKey(searchVO.getSearchKey());
                mapVO.setValue(gson.toJson(searchVO));
                searchMap.add(mapVO);
            }
            model.addAttribute("searchMapJson", gson.toJson(searchMap));
        }


        //查询员工信息
        List<Employee> empList = employeeService.queryAllStoreEmp(sessionInfo.getStoreId());
        if (ListUtils.isNotBlank(empList)) {
            List<EmployeeVO> empVoList = FoxBeanUtils.copyList(empList, EmployeeVO.class);
            model.addAttribute("empList", empVoList);
        }


        return "decoration/customerDetail";
    }


    @RequestMapping("queryCustomerOrderList")
    @ResponseBody
    public List<OrderHeaderVO> queryCustomerOrderList(Long cusId) {

        if (cusId == null) {
            log.error("cus id is null.");
            return new ArrayList<>();
        }
        Long storeId = getStoreId();
        List<OrderHeader> orderHeaderList = orderHeaderService.queryByCusId(storeId, cusId);
        if (ListUtils.isBlank(orderHeaderList)) {
            return new ArrayList<>();
        }

        List<OrderHeaderVO> orderHeaderVOList = FoxBeanUtils.copyList(orderHeaderList, OrderHeaderVO.class);
        for (OrderHeaderVO headerVO:orderHeaderVOList) {
            Long headerId = headerVO.getId();
            List<Order> orderList = orderService.queryByHeaderId(headerId);
            if (ListUtils.isNotBlank(orderList)) {
                headerVO.setOrderList(FoxBeanUtils.copyList(orderList, OrderVO.class));
            }
        }
        return orderHeaderVOList;
    }



    @RequestMapping("orderList")
    public String orderList() {
        return "decoration/orderList";
    }

    @RequestMapping("orderHeaderList")
    public String orderHeaderList() {
        return "decoration/orderHeaderList";
    }


    @RequestMapping("employeeList")
    public String employeeList() {
        return "decoration/employeeList";
    }


    @RequestMapping("goodsList")
    public String goodsList(Model model) {

        Long storeId = getStoreId();
        List<Warehouse> whList = warehouseService.queryAllWarehouse(storeId);
        model.addAttribute("whList", whList);
        return "decoration/goodsList";
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

    @RequestMapping("contactUs")
    public String contactUs() {
        return "decoration/contactUs";
    }


    @RequestMapping("modifyPassword")
    public String modifyPassword() {
        return "decoration/modifyPassword";
    }
}
