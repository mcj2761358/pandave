package com.minutch.fox.web.decoration;

import com.minutch.fox.biz.decoration.CustomerService;
import com.minutch.fox.entity.decoration.Customer;
import com.minutch.fox.http.SessionInfo;
import com.minutch.fox.param.Result;
import com.minutch.fox.param.decoration.CustomerParam;
import com.minutch.fox.param.decoration.CustomerQueryParam;
import com.minutch.fox.result.PageResultVO;
import com.minutch.fox.result.decoration.CustomerVO;
import com.minutch.fox.utils.DataCheckUtils;
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
 * Created by Minutch on 16/11/28.
 */
@RequestMapping("decoration/customer")
@Controller
@Slf4j
public class CustomerController extends BaseController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private SessionInfo sessionInfo;

    @RequestMapping("queryList")
    @ResponseBody
    public Result<?> queryList(@RequestBody CustomerQueryParam param) {

        param.setStoreId(sessionInfo.getStoreId());

        int totalNum = customerService.queryCustomerCount(param);
        PageResultVO<CustomerVO> pageResultVO = new PageResultVO<>();
        pageResultVO.setPageSize(param.getPageSize());
        pageResultVO.setCurPage(param.getCurPage());
        pageResultVO.setTotalSize(totalNum);
        if (totalNum > 0) {
            List<Customer> studentList = customerService.queryCustomer(param);
            pageResultVO.setDataList(FoxBeanUtils.copyList(studentList, CustomerVO.class));
        } else {
            pageResultVO.setDataList(new ArrayList<CustomerVO>());
        }
        return Result.wrapSuccessfulResult(pageResultVO);
    }

    @RequestMapping("deleteById")
    @ResponseBody
    public Result<?> deleteById(Long cusId) {

        if (cusId == null) {
            log.error("客户id不能为空！");
            return Result.wrapErrorResult("","客户ID不能为空!");
        }
        customerService.deleteById(cusId);
        log.info("delete customer["+cusId+"]");
        return Result.wrapSuccessfulResult(null);
    }

    @RequestMapping("save")
    @ResponseBody
    public Result<?> save(@RequestBody CustomerParam param) {

        //判断信息是否正确
        if (StringUtils.isBlank(param.getCusName())) {
            log.error("客户名称不能为空.");
            return Result.wrapErrorResult("","客户姓名不能为空.");
        }
        if (StringUtils.isBlank(param.getMobilePhone())) {
            log.error("手机号码不能为空.");
            return Result.wrapErrorResult("","手机号码不能为空.");
        }
        if (!DataCheckUtils.checkMobile(param.getMobilePhone())) {
            log.error("手机号码格式不正确.");
            return Result.wrapErrorResult("","手机号码格式不正确.");
        }
        if (StringUtils.isBlank(param.getHouseName())) {
            log.error("小区名称不能为空.");
            return Result.wrapErrorResult("","小区名称不能为空.");
        }
        if (StringUtils.isBlank(param.getAddress())) {
            log.error("详细地址不能为空.");
            return Result.wrapErrorResult("","详细地址不能为空.");
        }

        Customer customer;
        if (param.getId() == null) {
            //判断当前手机号是否已经被注册
            customer = customerService.queryByMobilePhone(param.getMobilePhone());
            if (customer != null && customer.getStoreId().equals(sessionInfo.getStoreId())) {
                log.error("客户[" + param.getMobilePhone() + "]已存在.");
                return Result.wrapErrorResult("", "客户[" + param.getMobilePhone() + "]已存在，请到[客户管理]查询此客户信息.");
            }
        }

        customer = new Customer();
        BeanUtils.copyProperties(param, customer);
        customer.setDefaultBizValue(sessionInfo.getStoreId());
        customer.setStoreId(sessionInfo.getStoreId());

        customerService.save(customer);
        return Result.wrapSuccessfulResult(customer);
    }
}