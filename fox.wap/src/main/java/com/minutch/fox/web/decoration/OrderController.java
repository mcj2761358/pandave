package com.minutch.fox.web.decoration;

import com.minutch.fox.biz.decoration.CustomerService;
import com.minutch.fox.biz.decoration.OrderService;
import com.minutch.fox.entity.decoration.Customer;
import com.minutch.fox.entity.decoration.Order;
import com.minutch.fox.http.SessionInfo;
import com.minutch.fox.param.Result;
import com.minutch.fox.param.decoration.CustomerParam;
import com.minutch.fox.param.decoration.OrderParam;
import com.minutch.fox.param.decoration.OrderQueryParam;
import com.minutch.fox.result.PageResultVO;
import com.minutch.fox.result.decoration.OrderVO;
import com.minutch.fox.utils.FoxBeanUtils;
import com.minutch.fox.web.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Minutch on 16/11/28.
 */
@RequestMapping("decoration/order")
@Controller
@Slf4j
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private SessionInfo sessionInfo;

    @RequestMapping("queryList")
    @ResponseBody
    public Result<?> queryList(@RequestBody OrderQueryParam param) {

        param.setStoreId(sessionInfo.getStoreId());
        int totalNum = orderService.queryOrderCount(param);
        PageResultVO<OrderVO> pageResultVO = new PageResultVO<>();
        pageResultVO.setPageSize(param.getPageSize());
        pageResultVO.setCurPage(param.getCurPage());
        pageResultVO.setTotalSize(totalNum);
        if (totalNum > 0) {
            List<Order> orderList = orderService.queryOrder(param);
            pageResultVO.setDataList(FoxBeanUtils.copyList(orderList, OrderVO.class));
        } else {
            pageResultVO.setDataList(new ArrayList<OrderVO>());
        }
        return Result.wrapSuccessfulResult(pageResultVO);
    }


    @RequestMapping("save")
    @ResponseBody
    public Result<?> save(@RequestBody OrderParam param) {

        //数据检查
        if (StringUtils.isBlank(param.getGoodsName())) {
            log.error("goods name is blank.");
            return Result.wrapErrorResult("", "商品名称不能为空");
        }
        if (param.getGoodsNum()==null || param.getGoodsNum()<1) {
            log.error("goods num is null or less than 0");
            return Result.wrapErrorResult("","商品数量["+param.getGoodsNum()+"]必须为整数.");
        }
        if (param.getGoodsPrice()==null || param.getGoodsPrice().compareTo(BigDecimal.ZERO) < 1) {
            log.error("goods price is null or less than 0");
            return Result.wrapErrorResult("","商品单价["+param.getGoodsPrice()+"]必须大于0.");
        }
        if (param.getOrderAmount()==null || param.getOrderAmount().compareTo(BigDecimal.ZERO) < 1) {
            log.error("order amount is null or less than 0");
            return Result.wrapErrorResult("","订单金额["+param.getOrderAmount()+"]必须大于0.");
        }

        if (param.getCusId() == null) {
            log.error("客户ID为空！");
            return Result.wrapErrorResult("","系统出错，客户ID不能为空！");
        }

        Customer customer = customerService.getById(param.getCusId());
        if (customer == null) {
            log.error("不存在的客户ID["+param.getCusId()+"]");
            return Result.wrapErrorResult("","不存在的客户ID["+param.getCusId()+"]");
        }

        param.setCusName(customer.getCusName());
        param.setHouseName(customer.getHouseName());
        param.setMobilePhone(customer.getMobilePhone());
        param.setAddress(customer.getAddress());
        Order order = new Order();
        BeanUtils.copyProperties(param, order);
        order.setDefaultBizValue(sessionInfo.getStoreId());
        order.setStoreId(sessionInfo.getStoreId());
        orderService.save(order);
        return Result.wrapSuccessfulResult(order);
    }


    @RequestMapping("deleteById")
    @ResponseBody
    public Result<?> deleteById(Long orderId) {

        if (orderId == null) {
            log.error("订单id不能为空！");
            return Result.wrapErrorResult("","订单ID不能为空!");
        }
        orderService.deleteById(orderId);
        log.info("delete order["+orderId+"]");
        return Result.wrapSuccessfulResult(orderId);
    }


    @RequestMapping("finishById")
    @ResponseBody
    public Result<?> finishById(Long orderId) {

        if (orderId == null) {
            log.error("订单id不能为空！");
            return Result.wrapErrorResult("","订单ID不能为空!");
        }
        orderService.finishById(orderId);
        log.info("finish order["+orderId+"]");
        return Result.wrapSuccessfulResult(orderId);
    }
}