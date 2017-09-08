package com.minutch.fox.web.decoration;

import com.minutch.fox.biz.decoration.*;
import com.minutch.fox.entity.decoration.*;
import com.minutch.fox.enu.decoration.StockDetailObjTypeEnum;
import com.minutch.fox.enu.decoration.StoreLevelEnum;
import com.minutch.fox.http.SessionInfo;
import com.minutch.fox.param.Result;
import com.minutch.fox.param.decoration.OrderHeaderQueryParam;
import com.minutch.fox.param.decoration.OrderParam;
import com.minutch.fox.param.decoration.OrderQueryParam;
import com.minutch.fox.param.decoration.ReturnOrderQueryParam;
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
import com.minutch.fox.view.decoration.ReturnOrderView;
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
@RequestMapping("returnOrder")
@Controller
@Slf4j
public class ReturnOrderController extends BaseController {

    @Autowired
    private ReturnOrderService returnOrderService;
    @Autowired
    private SessionInfo sessionInfo;


    @RequestMapping("queryList")
    @ResponseBody
    public Result<?> queryList(@RequestBody ReturnOrderQueryParam param) {

        param.setStoreId(sessionInfo.getStoreId());
        int totalNum = returnOrderService.queryReturnOrderCount(param);
        PageResultVO<ReturnOrderView> pageResultVO = new PageResultVO<>();
        pageResultVO.setPageSize(param.getPageSize());
        pageResultVO.setCurPage(param.getCurPage());
        pageResultVO.setTotalSize(totalNum);
        if (totalNum > 0) {
            List<ReturnOrderView> orderList = returnOrderService.queryReturnOrder(param);
            pageResultVO.setDataList(orderList);
        } else {
            pageResultVO.setDataList(new ArrayList<ReturnOrderView>());
        }
        return Result.wrapSuccessfulResult(pageResultVO);
    }


    @RequestMapping("queryByHeaderId")
    @ResponseBody
    public Result<?> queryByHeaderId(Long headerId) {

        List<ReturnOrderView> returnOrderList = returnOrderService.queryByHeaderId(headerId);
        return Result.wrapSuccessfulResult(returnOrderList);
    }
}