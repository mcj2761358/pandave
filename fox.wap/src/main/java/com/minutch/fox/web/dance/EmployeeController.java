package com.minutch.fox.web.dance;

import com.minutch.fox.biz.dance.EmployeeService;
import com.minutch.fox.constants.dance.EmployeeConstants;
import com.minutch.fox.entity.dance.Employee;
import com.minutch.fox.param.Result;
import com.minutch.fox.param.dance.EmployeeLoginParam;
import com.minutch.fox.web.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Minutch on 16/3/20.
 */
@Controller
@RequestMapping("employee")
@Slf4j
public class EmployeeController extends BaseController{

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private HttpServletRequest request;

    @RequestMapping("login")
    @ResponseBody
    public Result<?> login(@RequestBody EmployeeLoginParam param) {

        if (StringUtils.isBlank(param.getMobilephone())) {
            log.error("login:手机号码不能为空.");
            return Result.wrapErrorResult("","手机号码不能为空.");
        }
        if (StringUtils.isBlank(param.getMobilephone())) {
            log.error("login:密码不能为空.");
            return Result.wrapErrorResult("","密码不能为空.");
        }

        Employee employee = employeeService.queryByMobile(param.getMobilephone().trim());
        if (employee == null) {
            log.error("login:不存在的手机号码["+param.getMobilephone().trim()+"].");
            return Result.wrapErrorResult("","不存在的手机号码["+param.getMobilephone().trim()+"].");
        }

        if (!param.getPassword().trim().equals(employee.getPassword())) {
            log.error("login:密码错误，请重新输入密码.");
            return Result.wrapErrorResult("", "密码错误，请重新输入密码.");
        }

        //设置Session
        HttpSession session = request.getSession();
        session.setAttribute(EmployeeConstants.ATTRIBUTE_EMPLOYEE_ID,employee.getId());

        return Result.wrapSuccessfulResult(null);
    }
}
