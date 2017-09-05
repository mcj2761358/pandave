package com.minutch.fox.web.decoration;

import com.minutch.fox.biz.decoration.CustomerService;
import com.minutch.fox.biz.decoration.GoodsService;
import com.minutch.fox.biz.decoration.OrderHeaderService;
import com.minutch.fox.biz.decoration.OrderService;
import com.minutch.fox.entity.decoration.Goods;
import com.minutch.fox.param.Result;
import com.minutch.fox.param.decoration.OrderQueryParam;
import com.minutch.fox.param.decoration.customer.DashboardCustomerParam;
import com.minutch.fox.param.decoration.order.DashboardOrderGoodsParam;
import com.minutch.fox.param.decoration.order.DashboardOrderHeaderParam;
import com.minutch.fox.result.decoration.DashboardGoodsVO;
import com.minutch.fox.utils.DateUtils;
import com.minutch.fox.view.decoration.OrderGoodsView;
import com.minutch.fox.web.BaseController;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Minutch on 17/9/2.
 */
@Controller
@RequestMapping("dashboard")
@Slf4j
public class DashboardController extends BaseController{

    private static final String START_TIME_POSTFIX = " 00:00:00";
    private static final String END_TIME_POSTFIX = " 23:59:59";
    private static Map<Integer, String> colorMap = new HashMap<>();

    static {
        colorMap.put(0, "green");
        colorMap.put(2, "red");
        colorMap.put(3, "blue");
        colorMap.put(4, "yellow");
    }

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderHeaderService orderHeaderService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private GoodsService goodsService;

    @RequestMapping
    public String  main(Model model) {

        Date date = new Date();
        String currentDate = DateUtils.dateFormat(date, DateUtils.Y_M_D);
        String startTime = currentDate + START_TIME_POSTFIX;
        String endTime = currentDate + END_TIME_POSTFIX;

        //查询今日待处理订单数
        OrderQueryParam remindNumParam = new OrderQueryParam();
        remindNumParam.setRemindStatus(0);
        remindNumParam.setStoreId(getStoreId());
        remindNumParam.setQueryTimeStart(startTime);
        remindNumParam.setQueryTimeEnd(endTime);
        int remindOrderNum = orderService.queryOrderCount(remindNumParam);
        model.addAttribute("remindOrderNum",remindOrderNum);

        //查询今日订单总销售额
        DashboardOrderHeaderParam totalOrderAmountParam = new DashboardOrderHeaderParam();
        totalOrderAmountParam.setStoreId(getStoreId());
        totalOrderAmountParam.setStartTime(startTime);
        totalOrderAmountParam.setEndTime(endTime);
        BigDecimal totalOrderAmount = orderHeaderService.queryTotalAmountByTime(totalOrderAmountParam);

        if (totalOrderAmount == null) {
            totalOrderAmount = BigDecimal.ZERO;
        }

        model.addAttribute("totalOrderAmount",totalOrderAmount);

        //查询今日订单毛利润
        BigDecimal inTotalOrderAmount = orderHeaderService.queryInTotalAmountByTime(totalOrderAmountParam);
        if (inTotalOrderAmount == null) {
            inTotalOrderAmount = BigDecimal.ZERO;
        }

        BigDecimal profitOrderAmount = totalOrderAmount.subtract(inTotalOrderAmount);
        if (profitOrderAmount.compareTo(BigDecimal.ZERO) == -1) {
            profitOrderAmount = BigDecimal.ZERO;
        }

        model.addAttribute("profitOrderAmount",profitOrderAmount);


        //查询今日订单总数量
        DashboardOrderHeaderParam totalOrderNumParam = new DashboardOrderHeaderParam();
        totalOrderNumParam.setStoreId(getStoreId());
        totalOrderNumParam.setStartTime(startTime);
        totalOrderNumParam.setEndTime(endTime);
        int totalOrderNum = orderHeaderService.queryTotalNumByTime(totalOrderNumParam);
        model.addAttribute("totalOrderNum",totalOrderNum);

        //查询今日新增客户数
        DashboardCustomerParam totalCustomerNumParam = new DashboardCustomerParam();
        totalCustomerNumParam.setStoreId(getStoreId());
        totalCustomerNumParam.setStartTime(startTime);
        totalCustomerNumParam.setEndTime(endTime);
        int totalCustomerNum = customerService.queryTotalNumByTime(totalCustomerNumParam);
        model.addAttribute("totalCustomerNum",totalCustomerNum);

        //查询库存排行
        List<Goods> stockGoodsList = goodsService.queryStockNumAsc(getStoreId());
        List<DashboardGoodsVO> daStockList = handleStockGoodsList(stockGoodsList);
        model.addAttribute("stockGoodsList",daStockList);

        //查询今日热卖商品
        DashboardOrderGoodsParam orderGoodsParam = new DashboardOrderGoodsParam();
        orderGoodsParam.setStoreId(getStoreId());
        orderGoodsParam.setStartTime(startTime);
        orderGoodsParam.setEndTime(endTime);
        List<OrderGoodsView> orderGoodsViewList = orderService.queryOrderGoodsNumByTime(orderGoodsParam);
        List<DashboardGoodsVO> daGoodsList =handleOrderGoodsList(orderGoodsViewList);
        model.addAttribute("orderGoodsViewList",daGoodsList);

        return "decoration/dashboard";
    }

    private List<DashboardGoodsVO> handleStockGoodsList(List<Goods> goodsList) {
        List<DashboardGoodsVO> viewList = new ArrayList<>();

        //不满10个，补足10个
        int goodsLength = goodsList.size();
        int left = 10 - goodsLength;
        for (int i=0;i<left;i++) {
            goodsList.add(null);
        }

        //处理样式
        for (int i=0;i<goodsList.size();i++) {
            Goods goods = goodsList.get(i);
            DashboardGoodsVO view = new DashboardGoodsVO();
            view.setGoodsNum("-");
            if (goods == null) {
                view.setGoodsName("------");
                view.setGoodsModel("------");
                view.setIcon("glyphicon-minus");
            } else {
                view.setGoodsName(goods.getGoodsName());
                view.setGoodsModel(goods.getGoodsModel());

                Integer goodsNum = goods.getStockNum();
                if (goodsNum!=null) {
                    view.setGoodsNum(String.valueOf(goodsNum));
                }

                view.setIcon("glyphicon-arrow-down");
            }

            int n = i%4;
            view.setColor(colorMap.get(n));
            viewList.add(view);
        }


        return viewList;
    }

    private List<DashboardGoodsVO> handleOrderGoodsList(List<OrderGoodsView> goodsList) {
        List<DashboardGoodsVO> viewList = new ArrayList<>();

        //不满10个，补足10个
        int goodsNum = goodsList.size();
        int left = 10 - goodsNum;
        for (int i=0;i<left;i++) {
            goodsList.add(null);
        }

        //处理样式
        for (int i=0;i<goodsList.size();i++) {
            OrderGoodsView goods = goodsList.get(i);
            DashboardGoodsVO view = new DashboardGoodsVO();
            view.setGoodsNum("-");
            if (goods == null) {
                view.setGoodsName("------");
                view.setGoodsModel("------");
                view.setIcon("glyphicon-minus");
            } else {
                view.setGoodsName(goods.getGoodsName());
                view.setGoodsModel(goods.getGoodsModel());
                view.setIcon("glyphicon-arrow-up");
                view.setGoodsNum(String.valueOf(goods.getGoodsNum()));
            }

            int n = i%4;
            view.setColor(colorMap.get(n));
            viewList.add(view);
        }


        return viewList;
    }
}

