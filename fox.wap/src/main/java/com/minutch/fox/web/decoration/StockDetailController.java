package com.minutch.fox.web.decoration;

import com.minutch.fox.biz.decoration.StockDetailService;
import com.minutch.fox.entity.decoration.StockDetail;
import com.minutch.fox.param.Result;
import com.minutch.fox.result.decoration.StockDetailVO;
import com.minutch.fox.utils.FoxBeanUtils;
import com.minutch.fox.utils.ListUtils;
import com.minutch.fox.web.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Minutch on 17/9/3.
 */
@Controller
@RequestMapping("stockDetail")
@Slf4j
public class StockDetailController extends BaseController{

    @Autowired
    private StockDetailService stockDetailService;

    @RequestMapping("stockList")
    @ResponseBody
    public Result<?> stockList(Long goodsId) {

        if (goodsId == null) {
            return Result.wrapErrorResult("","商品ID不能为空.");
        }

        List<StockDetail> stockDetailList = stockDetailService.queryByGoodsId(goodsId);
        if (ListUtils.isBlank(stockDetailList)) {
            return Result.wrapSuccessfulResult(stockDetailList);
        }

        List<StockDetailVO> voList = FoxBeanUtils.copyList(stockDetailList, StockDetailVO.class);
        return Result.wrapSuccessfulResult(voList);
    }
}
