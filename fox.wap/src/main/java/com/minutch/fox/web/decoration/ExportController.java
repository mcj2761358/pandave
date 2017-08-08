package com.minutch.fox.web.decoration;

import com.minutch.fox.biz.decoration.CustomerService;
import com.minutch.fox.biz.decoration.OrderService;
import com.minutch.fox.entity.decoration.Customer;
import com.minutch.fox.entity.decoration.Order;
import com.minutch.fox.result.decoration.CustomerVO;
import com.minutch.fox.result.decoration.OrderVO;
import com.minutch.fox.utils.DateUtils;
import com.minutch.fox.utils.FoxBeanUtils;
import com.minutch.fox.web.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Minutch on 17/8/8.
 */
@Controller
@RequestMapping("export")
@Slf4j
public class ExportController extends BaseController{

    @Autowired
    private OrderService orderService;
    @Autowired
    private CustomerService customerService;

    @RequestMapping("orderList")
    public String orderList(String orderIds,String cusId,HttpServletResponse response,Model model) {
        // 生成提示信息，
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("content-disposition", "attachment;filename=" + (DateUtils.dateFormat(new Date(), DateUtils.YMDHMS)) + ".xls");

        if (StringUtils.isNotBlank(orderIds)) {
            List<Long> idList = new ArrayList<>();
            String[] arr = orderIds.split(",");
            for (String idStr:arr) {
                if (StringUtils.isNotBlank(idStr)) {
                    idList.add(Long.valueOf(idStr));
                }
            }

            List<Order> orderList = orderService.queryByIds(idList);
            List<OrderVO> voList = FoxBeanUtils.copyList(orderList, OrderVO.class);
            model.addAttribute("orderList", voList);
        }

        Customer customer = customerService.getById(Long.valueOf(cusId));
        CustomerVO vo = new CustomerVO();
        try {
            BeanUtils.copyProperties(vo, customer);
        } catch (Exception e) {
            log.error("拷贝出错", e);
        }
        model.addAttribute("customer", vo);

        return "decoration/export/orderList";
    }

}
