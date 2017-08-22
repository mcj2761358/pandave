package com.minutch.fox.web.decoration;

import com.minutch.fox.biz.decoration.GoodsService;
import com.minutch.fox.entity.decoration.Goods;
import com.minutch.fox.http.SessionInfo;
import com.minutch.fox.param.Result;
import com.minutch.fox.param.decoration.GoodsParam;
import com.minutch.fox.param.decoration.GoodsQueryParam;
import com.minutch.fox.result.PageResultVO;
import com.minutch.fox.result.decoration.GoodsVO;
import com.minutch.fox.utils.FoxBeanUtils;
import com.minutch.fox.web.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Minutch on 17/8/19.
 */
@RequestMapping("goods")
@Controller
@Slf4j
public class GoodsController extends BaseController {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private SessionInfo sessionInfo;

    @RequestMapping("queryList")
    @ResponseBody
    public Result<?> queryList(@RequestBody GoodsQueryParam param) {

        param.setStoreId(sessionInfo.getStoreId());

        int totalNum = goodsService.queryGoodsCount(param);
        PageResultVO<GoodsVO> pageResultVO = new PageResultVO<>();
        pageResultVO.setPageSize(param.getPageSize());
        pageResultVO.setCurPage(param.getCurPage());
        pageResultVO.setTotalSize(totalNum);
        if (totalNum > 0) {
            List<Goods> studentList = goodsService.queryGoods(param);
            pageResultVO.setDataList(FoxBeanUtils.copyList(studentList, GoodsVO.class));
        } else {
            pageResultVO.setDataList(new ArrayList<GoodsVO>());
        }
        return Result.wrapSuccessfulResult(pageResultVO);
    }


    @RequestMapping("deleteById")
    @ResponseBody
    public Result<?> deleteById(Long goodsId) {

        if (goodsId == null) {
            log.error("goodsId不能为空！");
            return Result.wrapErrorResult("","goodsId不能为空!");
        }
        goodsService.deleteById(goodsId);
        log.info("delete goods["+goodsId+"]");
        return Result.wrapSuccessfulResult(null);
    }

    @RequestMapping("save")
    @ResponseBody
    public Result<?> save(@RequestBody GoodsParam param) {

        //判断信息是否正确
        if (StringUtils.isBlank(param.getGoodsName())) {
            log.error("商品名称不能为空.");
            return Result.wrapErrorResult("","商品名称不能为空.");
        }
        if (StringUtils.isBlank(param.getGoodsModel())) {
            log.error("商品型号不能为空.");
            return Result.wrapErrorResult("","商品型号不能为空.");
        }
        Goods goods;
        if (param.getId() == null) {
            //判断当前商品是否已经被注册
            goods = goodsService.queryByNameAndModel(param.getGoodsName(), param.getGoodsModel(), sessionInfo.getStoreId());
            if (goods != null) {
                log.error("商品[" + param.getGoodsName()+param.getGoodsModel() + "]已存在.");
                return Result.wrapErrorResult("", "商品[" + param.getGoodsName()+param.getGoodsModel() + "]已存在.");
            }
        }

        goods = new Goods();
        BeanUtils.copyProperties(param, goods);
        goods.setDefaultBizValue(sessionInfo.getEmpId());
        goods.setStoreId(sessionInfo.getStoreId());
        goods.setEmpId(getEmpId());

        goodsService.save(goods);
        return Result.wrapSuccessfulResult(goods);
    }
}
