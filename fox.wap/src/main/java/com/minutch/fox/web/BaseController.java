package com.minutch.fox.web;

import com.minutch.fox.biz.decoration.EmployeeService;
import com.minutch.fox.constants.dance.EmployeeConstants;
import com.minutch.fox.entity.decoration.Employee;
import com.minutch.fox.pojo.PermissionRulePO;
import com.minutch.fox.result.decoration.EmployeeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Minutch on 16/3/20.
 */
@Slf4j
public class BaseController {

    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected EmployeeService employeeService;

    public Long getEmpId() {
        Object userObject = request.getSession().getAttribute(EmployeeConstants.ATTRIBUTE_EMPLOYEE_ID);
        if (userObject == null) {
            return null;
        }
        Long employeeId = null;
        try {
            employeeId = Long.valueOf(userObject.toString());
        } catch (Exception e) {
            log.error("getUserId:userId error[" + userObject + "]", e);
        }
        return employeeId;
    }
    public Long getStoreId() {
        Object userObject = request.getSession().getAttribute(EmployeeConstants.ATTRIBUTE_STORE_ID);
        if (userObject == null) {
            return null;
        }
        Long storeId = null;
        try {
            storeId = Long.valueOf(userObject.toString());
        } catch (Exception e) {
            log.error("getStoreId:storeId error[" + userObject + "]", e);
        }
        return storeId;
    }

    @ModelAttribute("permissionRule")
    public PermissionRulePO permissionRule() {

        Long empId = getEmpId();
        Employee employee = employeeService.getById(empId);
        if (employee == null) {
            return new PermissionRulePO();
        }


        EmployeeVO vo = new EmployeeVO();
        BeanUtils.copyProperties(employee, vo);

        return vo.getPermissionRulePO();
    }

}
