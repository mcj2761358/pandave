package com.minutch.fox.web.decoration;

import com.minutch.fox.biz.decoration.*;
import com.minutch.fox.entity.decoration.*;
import com.minutch.fox.enu.decoration.StoreLevelEnum;
import com.minutch.fox.http.SessionInfo;
import com.minutch.fox.param.Result;
import com.minutch.fox.param.decoration.*;
import com.minutch.fox.param.decoration.order.CustomerTotalAmountParam;
import com.minutch.fox.param.decoration.order.OrderSaleParam;
import com.minutch.fox.param.decoration.order.ReturnOrderParam;
import com.minutch.fox.result.PageResultVO;
import com.minutch.fox.result.decoration.OrderHeaderVO;
import com.minutch.fox.result.decoration.OrderVO;
import com.minutch.fox.utils.DateUtils;
import com.minutch.fox.utils.FoxBeanUtils;
import com.minutch.fox.utils.GenerationUtils;
import com.minutch.fox.view.decoration.OrderView;
import com.minutch.fox.web.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Minutch on 16/11/28.
 */
@RequestMapping("decoration/order")
@Controller
@Slf4j
public class OrderController extends BaseController {

    private static final String START_TIME_POSTFIX = " 00:00:00";
    private static final String END_TIME_POSTFIX = " 23:59:59";
    @Autowired
    private OrderService orderService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private SessionInfo sessionInfo;
    @Autowired
    private OrderHeaderService orderHeaderService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private ReturnOrderService returnOrderService;

    @RequestMapping("queryHeaderList")
    @ResponseBody
    public Result<?> queryHeaderList(@RequestBody OrderHeaderQueryParam param) {

        handOrderHeaderQueryParam(param);

        param.setStoreId(sessionInfo.getStoreId());
        int totalNum = orderHeaderService.queryHeaderCount(param);
        PageResultVO<OrderHeaderVO> pageResultVO = new PageResultVO<>();
        pageResultVO.setPageSize(param.getPageSize());
        pageResultVO.setCurPage(param.getCurPage());
        pageResultVO.setTotalSize(totalNum);
        if (totalNum > 0) {
            List<OrderHeader> orderList = orderHeaderService.queryHeader(param);
            pageResultVO.setDataList(FoxBeanUtils.copyList(orderList, OrderHeaderVO.class));
        } else {
            pageResultVO.setDataList(new ArrayList<OrderHeaderVO>());
        }
        return Result.wrapSuccessfulResult(pageResultVO);

    }

    private void handOrderHeaderQueryParam(OrderHeaderQueryParam param) {

        String queryTime = param.getQueryTime();
        if (StringUtils.isNotBlank(queryTime)) {
            param.setGmtCreateStart(queryTime + START_TIME_POSTFIX);
            param.setGmtCreateEnd(queryTime + END_TIME_POSTFIX);
        }
    }

    @RequestMapping("queryList")
    @ResponseBody
    public Result<?> queryList(@RequestBody OrderQueryParam param) {

        orderService.handOrderQueryParam(param);

        param.setStoreId(sessionInfo.getStoreId());
        int totalNum = orderService.queryOrderCount(param);
        PageResultVO<OrderVO> pageResultVO = new PageResultVO<>();
        pageResultVO.setPageSize(param.getPageSize());
        pageResultVO.setCurPage(param.getCurPage());
        pageResultVO.setTotalSize(totalNum);
        if (totalNum > 0) {
            List<OrderView> orderList = orderService.queryOrder(param);
            pageResultVO.setDataList(FoxBeanUtils.copyList(orderList, OrderVO.class));
        } else {
            pageResultVO.setDataList(new ArrayList<OrderVO>());
        }
        return Result.wrapSuccessfulResult(pageResultVO);
    }


    @RequestMapping("save")
    @ResponseBody
    @Transactional
    public Result<?> save(@RequestBody OrderParam param) {

        if (0L ==   param.getHeaderId()) {
            param.setHeaderId(null);
        }

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

        //判断是否有订单头
        Long headerId = param.getHeaderId();
        if (headerId == null) {


            //判断当前等级的商家是否还能创建订单
            int totalNum = orderHeaderService.queryTotalCount(sessionInfo.getStoreId());
            StoreLevelEnum storeLevel = storeService.queryStoreLevel(sessionInfo.getStoreId());
            if (storeLevel.getOrderHeaderNum() <= totalNum) {
                log.error("您当前的套餐是【"+storeLevel.getLevelName()+"】,最多创建["+storeLevel.getOrderHeaderNum()+"]个订单,请联系客服升级套餐.");
                return Result.wrapErrorResult("", "您当前的套餐是【"+storeLevel.getLevelName()+"】,最多创建["+storeLevel.getOrderHeaderNum()+"]个订单,请联系客服升级套餐.");
            }

            OrderHeader orderHeader = new OrderHeader();
            orderHeader.setDefaultBizValue(sessionInfo.getEmpId());
            orderHeader.setStoreId(sessionInfo.getStoreId());
            orderHeader.setCusId(param.getCusId());
            orderHeader.setCusName(customer.getCusName());
            orderHeader.setHouseName(customer.getHouseName());
            orderHeader.setMobilePhone(customer.getMobilePhone());
            orderHeader.setAddress(customer.getAddress());
            orderHeader.setOrderSn(generateOrderSn());
            orderHeader.setTotalAmount(BigDecimal.ZERO);
            orderHeader.setPreAmount(BigDecimal.ZERO);
            orderHeaderService.save(orderHeader);
            headerId = orderHeader.getId();
        }

//        param.setCusName(customer.getCusName());
//        param.setHouseName(customer.getHouseName());
//        param.setMobilePhone(customer.getMobilePhone());
//        param.setAddress(customer.getAddress());
        param.setCusId(null);

        Order order = new Order();
        BeanUtils.copyProperties(param, order);

        if (order.getId() == null) {
            order.setBeFinish("N");
            order.setHeaderId(headerId);

            //如果是新订单，判断商品是否来自库存商品，如果是，减去库存量
            Long goodsId = param.getGoodsId();
            if (goodsId != null) {
                Goods goods = goodsService.getById(goodsId);
                if (goods != null
                        && goods.getGoodsName().equals(param.getGoodsName())
                        && goods.getGoodsModel().equals(param.getGoodsModel())) {
                    goodsService.updateStockNum(goodsId, param.getGoodsNum());
                }
            }
        }
        order.setDefaultBizValue(sessionInfo.getEmpId());

        if (param.getCreateTime() != null) {
            order.setGmtCreate(param.getCreateTime());
        }

        order.setStoreId(sessionInfo.getStoreId());
        order.setEmpId(sessionInfo.getEmpId());
        orderService.save(order);
        return Result.wrapSuccessfulResult(order);
    }


    private String generateOrderSn() {
        Date currentDate = new Date();
        String dateStr = DateUtils.dateFormat(currentDate, DateUtils.MDHMS);
        return "N"+ dateStr + GenerationUtils.generateRandomCode(3);
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

    @RequestMapping("handleRemindById")
    @ResponseBody
    public Result<?> handleRemindById(Long orderId) {

        if (orderId == null) {
            log.error("订单id不能为空！");
            return Result.wrapErrorResult("","订单ID不能为空!");
        }
        orderService.handleRemindById(orderId);
        log.info("handle remind order["+orderId+"]");
        return Result.wrapSuccessfulResult(orderId);
    }


    @RequestMapping("saveOrderTotalAmount")
    @ResponseBody
    public Result<?> saveOrderTotalAmount(@RequestBody CustomerTotalAmountParam param) {

        if (param.getHeaderId() == null) {
            log.error("headerId 不能为空");
            return Result.wrapErrorResult("", "未知的订单,请刷新页面后重试.");
        }

        param.setStoreId(getStoreId());
        orderHeaderService.saveTotalAmount(param);
        return Result.wrapSuccessfulResult(null);
    }

    @RequestMapping("updateEmpName")
    @ResponseBody
    public Result<?> updateEmpName(@RequestBody OrderSaleParam param) {

        if (param.getHeaderId() == null) {
            log.error("headerId 不能为空");
            return Result.wrapErrorResult("", "未知的订单,请刷新页面后重试.");
        }

        param.setStoreId(getStoreId());
        orderHeaderService.updateEmpName(param);
        return Result.wrapSuccessfulResult(null);
    }

    @RequestMapping("handleReturnOrder")
    @ResponseBody
    @Transactional
    public Result<?> handleReturnOrder(@RequestBody ReturnOrderParam param) {

        if (param.getOrderId() == null) {
            log.error("订单号ID不能为空");
            return Result.wrapSuccessfulResult("未知的订单,请刷新页面后重试.");
        }

        //判断退货数量
        if (param.getGoodsNum() < 1) {
            log.error("订单["+param.getOrderId()+"]退货数量["+param.getGoodsNum()+"]不能小于1");
            return Result.wrapErrorResult("", "退货数量[" + param.getGoodsNum() + "]不能小于1");
        }
        if (param.getOrderAmount()==null) {
            log.error("订单["+param.getOrderId()+"]退货金额["+param.getOrderAmount()+"]不能为空");
            return Result.wrapErrorResult("", "退货金额量[" + param.getOrderAmount() + "]不能为空");
        }
        //判断创建订单时，是否有减库存
        Order order = orderService.getById(param.getOrderId());
        if (order == null) {
            log.error("不存在的订单["+param.getOrderId()+"]");
            return Result.wrapErrorResult("", "不存在的订单["+param.getOrderId()+"]");
        }
        Long goodsId = order.getGoodsId();
        if (goodsId != null) {
            Goods goods = goodsService.getById(goodsId);
            if (goods!=null
                    && goods.getGoodsName().equals(order.getGoodsName())
                    && goods.getGoodsModel().equals(order.getGoodsModel())) {
                //恢复库存
                goodsService.updateStockNum(goodsId, -param.getGoodsNum());
            }
        }

        //更新订单信息
        orderService.handleReturnOrder(param.getOrderId(), param.getGoodsNum(), param.getOrderAmount());
        //记录退货信息
        ReturnOrder returnOrder = new ReturnOrder();
        returnOrder.setOrderId(param.getOrderId());
        returnOrder.setGoodsNum(param.getGoodsNum());
        returnOrder.setOrderAmount(param.getOrderAmount());
        returnOrder.setStoreId(sessionInfo.getStoreId());
        returnOrder.setEmpId(sessionInfo.getEmpId());
        returnOrder.setDefaultBizValue(sessionInfo.getEmpId());
        returnOrderService.save(returnOrder);


//        param.setStoreId(getStoreId());
//        orderHeaderService.saveTotalAmount(param);
        return Result.wrapSuccessfulResult(null);
    }

}