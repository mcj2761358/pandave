package com.minutch.fox.biz.decoration.impl;

import com.minutch.fox.biz.base.BaseServiceImpl;
import com.minutch.fox.biz.decoration.OrderService;
import com.minutch.fox.dao.decoration.OrderDao;
import com.minutch.fox.entity.decoration.Order;
import com.minutch.fox.param.decoration.OrderQueryParam;
import com.minutch.fox.param.decoration.order.DashboardOrderGoodsParam;
import com.minutch.fox.utils.DateUtils;
import com.minutch.fox.view.decoration.OrderGoodsView;
import com.minutch.fox.view.decoration.OrderView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Service
public class OrderServiceImpl extends BaseServiceImpl implements OrderService {

    private static final String START_TIME_POSTFIX = " 00:00:00";
    private static final String END_TIME_POSTFIX = " 23:59:59";
    @Autowired
    private OrderDao orderDao;

    public List<Order> getAll() {
        return super.getAll(orderDao);
    }

    public Order getById(Long id) {
        return super.getById(orderDao, id);
    }

    public boolean save(Order order) {
        return super.save(orderDao, order);
    }

    public boolean deleteById(Long id) {
        return super.deleteById(orderDao, id);
    }

    public int deleteByIds(Long[] ids) {
        return super.deleteByIds(orderDao, ids);
    }

    @Override
    public List<OrderView> queryOrder(OrderQueryParam param) {
        return orderDao.queryOrder(param, param.getStart(), param.getEnd());
    }

    @Override
    public int queryOrderCount(OrderQueryParam param) {
        return orderDao.queryOrderCount(param);
    }

    @Override
    public int finishById(Long orderId) {

        if (orderId == null) {
            return 0;
        }
        return orderDao.finishById(orderId);
    }

    @Override
    public int handleRemindById(Long orderId) {
        return orderDao.handleRemindById(orderId);
    }

    @Override
    public List<Order> queryByIds(List<Long> idList) {
        return orderDao.queryByIds(idList);
    }

    @Override
    public List<Order> queryByHeaderId(Long headerId) {
        return orderDao.queryByHeaderId(headerId);
    }

    @Override
    public int handleReturnOrder(Long orderId, int goodsNum, BigDecimal orderAmount) {
        return orderDao.handleReturnOrder(orderId, goodsNum, orderAmount);
    }

    @Override
    public void handOrderQueryParam(OrderQueryParam param) {
        String timeName = param.getTimeName();
        String queryTime = param.getQueryTime();
        if (StringUtils.isNotBlank(timeName)) {

            Date date = new Date();
            String currentDate = DateUtils.dateFormat(date, DateUtils.Y_M_D);
            String startDate = currentDate + START_TIME_POSTFIX;
            String endDate = currentDate + END_TIME_POSTFIX;

            if ("today".equals(timeName)) {

            }
            else if ("tomorrow".equals(timeName)) {
                Date tomorrowDate = DateUtils.afterNDays(date, 1);
                startDate = DateUtils.dateFormat(tomorrowDate, DateUtils.Y_M_D) + START_TIME_POSTFIX;
                endDate = DateUtils.dateFormat(tomorrowDate, DateUtils.Y_M_D) + END_TIME_POSTFIX;
            }
            else if ("nearly3".equals(timeName)) {
                Date nearly3Date = DateUtils.afterNDays(date, 2);
                endDate = DateUtils.dateFormat(nearly3Date, DateUtils.Y_M_D) + END_TIME_POSTFIX;
            }
            else if ("nearly7".equals(timeName)) {
                Date nearly7Date = DateUtils.afterNDays(date, 6);
                endDate = DateUtils.dateFormat(nearly7Date, DateUtils.Y_M_D) + END_TIME_POSTFIX;
            }
            else if ("all".equals(timeName)) {
                Date allDate = DateUtils.afterNDays(date, 10000);
                endDate = DateUtils.dateFormat(allDate, DateUtils.Y_M_D) + END_TIME_POSTFIX;
            }

            param.setQueryTimeStart(startDate);
            param.setQueryTimeEnd(endDate);
        }

        if (StringUtils.isNotBlank(queryTime)) {
            param.setGmtCreateStart(queryTime + START_TIME_POSTFIX);
            param.setGmtCreateEnd(queryTime + END_TIME_POSTFIX);
        }
    }

    @Override
    public List<OrderGoodsView> queryOrderGoodsNumByTime(DashboardOrderGoodsParam param) {
        return orderDao.queryOrderGoodsNumByTime(param);
    }
}
