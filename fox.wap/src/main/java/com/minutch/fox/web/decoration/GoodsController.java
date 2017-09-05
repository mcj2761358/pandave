package com.minutch.fox.web.decoration;

import com.minutch.fox.biz.decoration.GoodsService;
import com.minutch.fox.biz.decoration.StockDetailService;
import com.minutch.fox.biz.decoration.StoreService;
import com.minutch.fox.entity.decoration.Goods;
import com.minutch.fox.entity.decoration.StockDetail;
import com.minutch.fox.enu.decoration.StockDetailObjTypeEnum;
import com.minutch.fox.enu.decoration.StoreLevelEnum;
import com.minutch.fox.http.SessionInfo;
import com.minutch.fox.param.Result;
import com.minutch.fox.param.decoration.GoodsParam;
import com.minutch.fox.param.decoration.GoodsQueryParam;
import com.minutch.fox.pojo.PermissionRulePO;
import com.minutch.fox.result.PageResultVO;
import com.minutch.fox.result.decoration.GoodsVO;
import com.minutch.fox.utils.FoxBeanUtils;
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
    @Autowired
    private StoreService storeService;
    @Autowired
    private StockDetailService stockDetailService;

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
            List<Goods> goodsList = goodsService.queryGoods(param);

            //获取当前用户权限
            PermissionRulePO rulePO = permissionRule();
            if ("Y".equals(rulePO.getNoInPrice())) {
                for (Goods goods:goodsList) {
                    goods.setInGoodsPrice(null);
                }
            }

            pageResultVO.setDataList(FoxBeanUtils.copyList(goodsList, GoodsVO.class));
        } else {
            pageResultVO.setDataList(new ArrayList<GoodsVO>());
        }
        return Result.wrapSuccessfulResult(pageResultVO);
    }


    @RequestMapping("deleteById")
    @ResponseBody
    @Transactional
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
    @Transactional
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
        StockDetail stockDetail = null;
        if (param.getId() == null) {
            //判断当前商品是否已经被注册
            goods = goodsService.queryByNameAndModel(param.getGoodsName(), param.getGoodsModel(), sessionInfo.getStoreId(), param.getWhId());
            if (goods != null) {
                log.error("商品[" + param.getGoodsName()+param.getGoodsModel() + "]已存在.");
                return Result.wrapErrorResult("", "商品[" + param.getGoodsName()+param.getGoodsModel() + "]已存在.");
            }

            //判断当前等级的商家是否还能创建商品
            int totalNum = goodsService.queryTotalCount(sessionInfo.getStoreId());
            StoreLevelEnum storeLevel = storeService.queryStoreLevel(sessionInfo.getStoreId());
            if (storeLevel.getGoodsNum() <= totalNum) {
                log.error("您当前的套餐是【"+storeLevel.getLevelName()+"】,最多创建["+storeLevel.getGoodsNum()+"]个商品,请联系客服升级套餐.");
                return Result.wrapErrorResult("", "您当前的套餐是【"+storeLevel.getLevelName()+"】,最多创建["+storeLevel.getGoodsNum()+"]个商品,请联系客服升级套餐.");
            }

            //记录库存明细
            if (param.getStockNum()!=null && param.getStockNum()!=0) {
                stockDetail = new StockDetail();
                stockDetail.setDefaultBizValue(getEmpId());
                stockDetail.setEmpId(getEmpId());
                stockDetail.setStoreId(getStoreId());
                stockDetail.setObjType(StockDetailObjTypeEnum.goodsIn.name());
                stockDetail.setStockNum(param.getStockNum());
            }
        }

        goods = new Goods();
        BeanUtils.copyProperties(param, goods);
        goods.setDefaultBizValue(sessionInfo.getEmpId());
        goods.setStoreId(sessionInfo.getStoreId());
        goods.setEmpId(getEmpId());
        goodsService.save(goods);

        if (stockDetail!=null) {
            stockDetail.setGoodsId(goods.getId());
            stockDetailService.save(stockDetail);
        }
        return Result.wrapSuccessfulResult(goods);
    }
}
