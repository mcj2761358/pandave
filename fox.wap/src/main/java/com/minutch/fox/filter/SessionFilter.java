package com.minutch.fox.filter;


import com.minutch.fox.constants.dance.EmployeeConstants;
import com.minutch.fox.http.SessionInfo;
import com.minutch.fox.http.impl.SessionInfoImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Minutch on 15/9/23.
 */
@Slf4j
public class SessionFilter extends HandlerInterceptorAdapter {

    @Autowired
    private SessionInfo sessionInfo;

    public void init() {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        SessionInfoImpl sessionData = new SessionInfoImpl();
        sessionData.setStoreId(getStoreId(request));
        sessionData.setIp(getRemoteIp(request));
        ((SessionInfoImpl)sessionInfo).copy(sessionData);
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        ((SessionInfoImpl)sessionInfo).clean();
        super.postHandle(request, response, handler, modelAndView);
    }


    public Long getStoreId(HttpServletRequest request) {
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

    static String[] headsNames = { "X-Real-IP", "X-Forwarded-For", "remote_addr" };

    public static String getRemoteIp(HttpServletRequest request) {
        for (String headName : headsNames) {
            String header = request.getHeader(headName);
            if (header != null) {
                return header;
            }
        }
        return request.getRemoteAddr();
    }
}
