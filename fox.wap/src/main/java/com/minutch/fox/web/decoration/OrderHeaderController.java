package com.minutch.fox.web.decoration;

import com.minutch.fox.biz.decoration.*;
import com.minutch.fox.constants.GoodsConstants;
import com.minutch.fox.entity.decoration.*;
import com.minutch.fox.enu.decoration.OrderHeaderStatusEnum;
import com.minutch.fox.enu.decoration.StockDetailObjTypeEnum;
import com.minutch.fox.enu.decoration.StoreLevelEnum;
import com.minutch.fox.http.SessionInfo;
import com.minutch.fox.param.Result;
import com.minutch.fox.param.decoration.OrderHeaderQueryParam;
import com.minutch.fox.param.decoration.OrderParam;
import com.minutch.fox.param.decoration.OrderQueryParam;
import com.minutch.fox.param.decoration.order.CustomerTotalAmountParam;
import com.minutch.fox.param.decoration.order.OrderSaleParam;
import com.minutch.fox.param.decoration.order.ReturnOrderParam;
import com.minutch.fox.result.PageResultVO;
import com.minutch.fox.result.decoration.OrderHeaderVO;
import com.minutch.fox.result.decoration.OrderVO;
import com.minutch.fox.utils.DateUtils;
import com.minutch.fox.utils.FoxBeanUtils;
import com.minutch.fox.utils.GenerationUtils;
import com.minutch.fox.utils.ListUtils;
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
@RequestMapping("orderHeader")
@Controller
@Slf4j
public class OrderHeaderController extends BaseController {

    @Autowired
    private OrderHeaderService orderHeaderService;


    @RequestMapping("deleteById")
    @ResponseBody
    public Result<?> deleteById(Long headerId) {

        if (headerId == null) {
            log.error("订单id不能为空！");
            return Result.wrapErrorResult("","订单ID不能为空!");
        }

        int num = orderHeaderService.deleteOrderHeader(headerId, getStoreId());

        return Result.wrapSuccessfulResult(num);
    }

}