package com.minutch.fox.web.decoration;

import com.minutch.fox.biz.decoration.OrderHeaderService;
import com.minutch.fox.param.Result;
import com.minutch.fox.view.decoration.OrderHeaderAmountView;
import com.minutch.fox.web.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

        List<OrderHeaderAmountView> reportNearlyDaysOrderAmount = orderHeaderService.reportNearlyDays(getStoreId());

        return Result.wrapSuccessfulResult(reportNearlyDaysOrderAmount);
    }
}
