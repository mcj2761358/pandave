package com.minutch.pandave.web;

import com.minutch.pandave.constants.dance.EmployeeConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Minutch on 16/3/20.
 */
@Slf4j
public class BaseController {

    @Autowired
    protected HttpServletRequest request;

    public Long getEmployeeId() {
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
}
