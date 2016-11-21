package com.minutch.fox.filter;

import com.google.gson.Gson;
import com.minutch.fox.config.SystemConfig;
import com.minutch.fox.constants.dance.EmployeeConstants;
import com.minutch.fox.param.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.OutputStream;

/**
 * Created by Minutch on 16/2/22.
 */
public class EmployeeLoginFilter extends HandlerInterceptorAdapter {

    @Autowired
    private SystemConfig systemConfig;

    private static final String LOGIN_URL = "fox/dance/";
    private static final String HEAD_AJAX_REQUEST = "XMLHttpRequest";
    private static final String HEAD__REQUEST_TYPE = "X-Requested-With";

    private static final String HEAD_CONTENT_TYPE = "Content-Type";
    private static final String HEAD_CONTENT_TYPE_VALUE = "application/json;charset=UTF-8";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();
        Long employeeId = (Long) session.getAttribute(EmployeeConstants.ATTRIBUTE_EMPLOYEE_ID);
        String requestType = request.getHeader(HEAD__REQUEST_TYPE);

        /************************************************************************************
         * 1.如果用户未登录，且是来自APP或者Ajax请求，返回Json格式的未登录错误码
         ************************************************************************************/
        if (employeeId == null &&  HEAD_AJAX_REQUEST.equals(requestType)) {

            //以application/json方式返回
            response.addHeader(HEAD_CONTENT_TYPE,HEAD_CONTENT_TYPE_VALUE);

            OutputStream out = response.getOutputStream();
            Result<?> result = Result.wrapErrorResult("","-1000");
            out.write(new Gson().toJson(result).getBytes());
            return false;
        }

        else if (employeeId == null) {
            response.sendRedirect(systemConfig.getServerUrl()+LOGIN_URL);
            return false;
        }

        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
}
