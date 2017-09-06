package com.minutch.fox.web.decoration;

import com.minutch.fox.biz.decoration.OrderHeaderService;
import com.minutch.fox.biz.decoration.OrderService;
import com.minutch.fox.dao.decoration.OrderDao;
import com.minutch.fox.param.Result;
import com.minutch.fox.utils.DateUtils;
import com.minutch.fox.view.decoration.HotGoodsView;
import com.minutch.fox.view.decoration.OrderHeaderAmountView;
import com.minutch.fox.web.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * Created by Minutch on 17/9/6.
 */
@Controller
@RequestMapping("chart")
@Slf4j
public class ChartController extends BaseController{

    @Autowired
    private OrderHeaderService orderHeaderService;
    @Autowired
    private OrderService orderService;

    @RequestMapping()
    public String chart() {

        return "decoration/chart";
    }

    /**
     * 近10天销售额
     * @return
     */
    @RequestMapping("reportNearlyDaysOrderAmount")
    @ResponseBody
    public Result<?> reportNearlyDaysOrderAmount() {

        Date currentDate = new Date();
        Date fromDate = DateUtils.afterNDays(currentDate, -90);
        List<OrderHeaderAmountView> reportNearlyDaysOrderAmount = orderHeaderService.reportTotalAmount(getStoreId(), DateUtils.formatDate(fromDate, DateUtils.Y_M_D_HMS));

        return Result.wrapSuccessfulResult(reportNearlyDaysOrderAmount);
    }

    /**
     * 近90天高销售额商品
     * @return
     */
    @RequestMapping("reportHotGoodsByAmount")
    @ResponseBody
    public Result<?> reportHotGoodsByAmount() {

        Date currentDate = new Date();
        Date fromDate = DateUtils.afterNDays(currentDate, -90);
        List<HotGoodsView> reportHotGoodsByAmount = orderService.reportHotGoodsByAmount(getStoreId(), DateUtils.formatDate(fromDate, DateUtils.Y_M_D_HMS));

        return Result.wrapSuccessfulResult(reportHotGoodsByAmount);
    }


    /**
     * 近90天高销量商品
     * @return
     */
    @RequestMapping("reportHotGoodsByNum")
    @ResponseBody
    public Result<?> reportHotGoodsByNum() {

        Date currentDate = new Date();
        Date fromDate = DateUtils.afterNDays(currentDate, -90);
        List<HotGoodsView> reportHotGoodsByNum = orderService.reportHotGoodsByNum(getStoreId(), DateUtils.formatDate(fromDate, DateUtils.Y_M_D_HMS));

        return Result.wrapSuccessfulResult(reportHotGoodsByNum);
    }
}






































