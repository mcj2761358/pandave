package com.minutch.fox.web.decoration;

import com.minutch.fox.biz.decoration.EmployeeService;
import com.minutch.fox.biz.decoration.StoreService;
import com.minutch.fox.entity.decoration.Employee;
import com.minutch.fox.enu.decoration.StoreLevelEnum;
import com.minutch.fox.http.SessionInfo;
import com.minutch.fox.param.Result;
import com.minutch.fox.param.decoration.EmployeeParam;
import com.minutch.fox.param.decoration.EmployeeQueryParam;
import com.minutch.fox.result.PageResultVO;
import com.minutch.fox.result.decoration.EmployeeVO;
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
 * Created by Minutch on 17/8/30.
 */
@RequestMapping("employee")
@Controller
@Slf4j
public class EmployeeController extends BaseController{

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private SessionInfo sessionInfo;
    @Autowired
    private StoreService storeService;


    @RequestMapping("queryList")
    @ResponseBody
    public Result<?> queryList(@RequestBody EmployeeQueryParam param) {

        param.setStoreId(sessionInfo.getStoreId());

        int totalNum = employeeService.queryEmployeeCount(param);
        PageResultVO<EmployeeVO> pageResultVO = new PageResultVO<>();
        pageResultVO.setPageSize(param.getPageSize());
        pageResultVO.setCurPage(param.getCurPage());
        pageResultVO.setTotalSize(totalNum);
        if (totalNum > 0) {
            List<Employee> employeeList = employeeService.queryEmployee(param);
            pageResultVO.setDataList(FoxBeanUtils.copyList(employeeList, EmployeeVO.class));
        } else {
            pageResultVO.setDataList(new ArrayList<EmployeeVO>());
        }
        return Result.wrapSuccessfulResult(pageResultVO);
    }



    @RequestMapping("deleteById")
    @ResponseBody
    public Result<?> deleteById(Long empId) {

        if (empId == null) {
            log.error("empId不能为空！");
            return Result.wrapErrorResult("","员工不能为空!");
        }
        employeeService.deleteById(empId);
        log.info("delete employee["+empId+"]");
        return Result.wrapSuccessfulResult(null);
    }

    @RequestMapping("save")
    @ResponseBody
    public Result<?> save(@RequestBody EmployeeParam param) {

        //判断信息是否正确
        if (StringUtils.isBlank(param.getEmpName())) {
            log.error("员工名称不能为空.");
            return Result.wrapErrorResult("","员工名称不能为空.");
        }

        if (!DataCheckUtils.checkMobile(param.getEmpMobile())) {
            log.error("手机号码[" + param.getEmpMobile() + "]格式不正确.");
            return Result.wrapErrorResult("", "手机号格式不正确.");
        }

        Employee employee  = new Employee();
        if (param.getId() == null) {
            //判断当前等级的商家是否还能创建员工
            int totalNum = employeeService.queryTotalCount(sessionInfo.getStoreId());
            StoreLevelEnum storeLevel = storeService.queryStoreLevel(sessionInfo.getStoreId());
            if (storeLevel.getEmployeeNum() <= totalNum) {
                log.error("您当前的套餐是【"+storeLevel.getLevelName()+"】,最多创建["+storeLevel.getEmployeeNum()+"]个员工,请联系客服升级套餐.");
                return Result.wrapErrorResult("", "您当前的套餐是【"+storeLevel.getLevelName()+"】,最多创建["+storeLevel.getEmployeeNum()+"]个员工,请联系客服升级套餐.");
            }
            param.setEmpPassword("123456");
        }

        BeanUtils.copyProperties(param, employee);
        employee.setDefaultBizValue(sessionInfo.getEmpId());
        employee.setStoreId(sessionInfo.getStoreId());

        employeeService.save(employee);
        return Result.wrapSuccessfulResult(employee);
    }
}
