package com.minutch.fox.web.decoration;

import com.minutch.fox.biz.decoration.GoodsService;
import com.minutch.fox.biz.decoration.StockDetailService;
import com.minutch.fox.biz.decoration.StoreService;
import com.minutch.fox.biz.decoration.SubGoodsService;
import com.minutch.fox.constants.GoodsConstants;
import com.minutch.fox.entity.decoration.Goods;
import com.minutch.fox.entity.decoration.StockDetail;
import com.minutch.fox.entity.decoration.SubGoods;
import com.minutch.fox.enu.decoration.StockDetailObjTypeEnum;
import com.minutch.fox.enu.decoration.StoreLevelEnum;
import com.minutch.fox.http.SessionInfo;
import com.minutch.fox.param.Result;
import com.minutch.fox.param.decoration.GoodsParam;
import com.minutch.fox.param.decoration.GoodsQueryParam;
import com.minutch.fox.param.decoration.SubGoodsParam;
import com.minutch.fox.pojo.PermissionRulePO;
import com.minutch.fox.result.PageResultVO;
import com.minutch.fox.result.decoration.GoodsVO;
import com.minutch.fox.utils.FoxBeanUtils;
import com.minutch.fox.utils.ListUtils;
import com.minutch.fox.view.decoration.SubGoodsView;
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
    @Autowired
    private SubGoodsService subGoodsService;

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
                for (Goods goods : goodsList) {
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
            return Result.wrapErrorResult("", "goodsId不能为空!");
        }
        goodsService.deleteById(goodsId);
        log.info("delete goods[" + goodsId + "]");
        return Result.wrapSuccessfulResult(null);
    }

    @RequestMapping("save")
    @ResponseBody
    @Transactional
    public Result<?> save(@RequestBody GoodsParam param) {

        //判断信息是否正确
        if (StringUtils.isBlank(param.getGoodsName())) {
            log.error("商品名称不能为空.");
            return Result.wrapErrorResult("", "商品名称不能为空.");
        }
        if (StringUtils.isBlank(param.getGoodsModel())) {
            log.error("商品型号不能为空.");
            return Result.wrapErrorResult("", "商品型号不能为空.");
        }
        Goods goods;
        StockDetail stockDetail = null;
        if (param.getId() == null) {
            //判断当前商品是否已经被注册
            goods = goodsService.queryByNameAndModel(param.getGoodsName(), param.getGoodsModel(), sessionInfo.getStoreId(), param.getWhId());
            if (goods != null) {
                log.error("商品[" + param.getGoodsName() + param.getGoodsModel() + "]已存在.");
                return Result.wrapErrorResult("", "商品[" + param.getGoodsName() + param.getGoodsModel() + "]已存在.");
            }

            //判断当前等级的商家是否还能创建商品
            int totalNum = goodsService.queryTotalCount(sessionInfo.getStoreId());
            StoreLevelEnum storeLevel = storeService.queryStoreLevel(sessionInfo.getStoreId());
            if (storeLevel.getGoodsNum() <= totalNum) {
                log.error("您当前的套餐是【" + storeLevel.getLevelName() + "】,最多创建[" + storeLevel.getGoodsNum() + "]个商品,请联系客服升级套餐.");
                return Result.wrapErrorResult("", "您当前的套餐是【" + storeLevel.getLevelName() + "】,最多创建[" + storeLevel.getGoodsNum() + "]个商品,请联系客服升级套餐.");
            }

            //记录库存明细
            if (param.getStockNum() != null && param.getStockNum().compareTo(BigDecimal.ZERO) > 0) {
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

        if (stockDetail != null) {
            stockDetail.setGoodsId(goods.getId());
            stockDetailService.save(stockDetail);
        }
        return Result.wrapSuccessfulResult(goods);
    }


    @RequestMapping("saveSubGoods")
    @ResponseBody
    @Transactional
    public Result<?> saveSubGoods(@RequestBody SubGoodsParam param) {

        //判断信息是否正确
        if (StringUtils.isBlank(param.getGoodsName())) {
            log.error("子商品名称不能为空.");
            return Result.wrapErrorResult("", "子商品名称不能为空.");
        }
        if (StringUtils.isBlank(param.getGoodsModel())) {
            log.error("子商品型号不能为空.");
            return Result.wrapErrorResult("", "子商品型号不能为空.");
        }
        if (param.getSubNum() == null || param.getSubNum() < 1) {
            log.error("子商品数量不能小于0.");
            return Result.wrapErrorResult("", "子商品数量不能小于0.");
        }
        if (param.getGoodsId() == null) {
            log.error("套餐商品不能为空.");
            return Result.wrapErrorResult("", "套餐商品id不能为空!");
        }

        //判断商品是否存在
        Goods goods = goodsService.queryByNameAndModel(param.getGoodsName(), param.getGoodsModel(), null, getStoreId());
        if (goods == null) {
            log.error("不存在的商品["+param.getGoodsName()+" "+param.getGoodsModel()+"]");
            return Result.wrapErrorResult("", "不存在的商品["+param.getGoodsName()+" "+param.getGoodsModel()+"]");
        } else  {

            //判断商品是否套餐，套餐商品不能加入子商品
            if (GoodsConstants.GOODS_TYPE_PACKAGE.equals(goods.getGoodsType())) {
                log.error("套餐商品不能加入子商品.");
                return Result.wrapErrorResult("", "套餐商品不能加入子商品.");
            }
        }

        //判断该子商品是否已经存在
        SubGoods subGoods = subGoodsService.queryByGoodsIdAndSubGoodsId(param.getGoodsId(), goods.getId());
        if (subGoods!=null) {
            log.error("该套餐已经存在商品["+param.getGoodsName()+" "+param.getGoodsModel()+"]");
            return Result.wrapErrorResult("", "该套餐已经存在商品["+param.getGoodsName()+" "+param.getGoodsModel()+"]");
        }

        subGoods = new SubGoods();
        subGoods.setGoodsId(param.getGoodsId());
        subGoods.setSubGoodsId(goods.getId());
        subGoods.setSubNum(param.getSubNum());
        subGoods.setDefaultBizValue(sessionInfo.getEmpId());
        subGoodsService.save(subGoods);

        SubGoodsView subGoodsView = new SubGoodsView();
        subGoodsView.setId(subGoods.getId());
        subGoodsView.setGoodsName(param.getGoodsName());
        subGoodsView.setGoodsModel(param.getGoodsModel());
        subGoodsView.setSubNum(param.getSubNum());

        return Result.wrapSuccessfulResult(subGoodsView);
    }


    @RequestMapping("querySubGoodsList")
    @ResponseBody
    public Result<?> querySubGoodsList(Long goodsId) {

        if (goodsId == null) {
            log.error("goods id is null");
            return Result.wrapErrorResult("", "商品ID不能为空.");
        }

        List<SubGoodsView> goodsList = subGoodsService.queryViewByGoodsId(goodsId);

        return Result.wrapSuccessfulResult(goodsList);
    }
}