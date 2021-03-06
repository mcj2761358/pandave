package com.minutch.fox.filter;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Minutch on 15/6/13.
 */
public class MonitorFilter implements Filter {
    /**
     * Session UserId常量属性
     */
    private String opKey = "userId";
    /**
     * Session 实际操作人常量属性
     */
    private String realOpKey = "operatorUserId";

    private String noLog = "";

    private static final Logger logger = LoggerFactory.getLogger(MonitorFilter.class);

    String MDC_IP = "IP";
    String MDC_OP = "OP"; // 实际操作人
    String MDC_OPAS = "OPAS"; // 代理掉的人

    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info(MonitorFilter.class.getName() + " Initing...");
        String opKeyStr = filterConfig.getInitParameter("opKey");
        if (StringUtils.isNotBlank(opKeyStr)) {
            this.opKey = opKeyStr;
        }
        String realOpKeyStr = filterConfig.getInitParameter("realOpKey");
        if (StringUtils.isNotBlank(realOpKeyStr)) {
            this.realOpKey = realOpKeyStr;
        }
        String noLogStr = filterConfig.getInitParameter("noLog");
        if (StringUtils.isNotBlank(noLogStr)) {
            this.noLog = noLogStr;
        }
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        long start = System.currentTimeMillis();

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        Object userIdAs = session.getAttribute(opKey);
        Object userId = session.getAttribute(realOpKey);
        String remoteIp = getRemoteIp(request);
        // 将数据存入MDC
        MDC.put(MDC_IP, (remoteIp != null ? remoteIp : "N/A"));
        MDC.put(MDC_OP, (userId != null ? userId : "N/A"));
        MDC.put(MDC_OPAS, (userIdAs != null ? userIdAs : "N/A"));

        boolean hasExp = true;
        try {
            filterChain.doFilter(servletRequest, servletResponse);
            hasExp = false;
        } catch (IOException e) {
            logger.error("IOException in page:", e);
            throw e;
        } catch (ServletException e) {
            logger.error("ServletException in page:", e);
            throw e;
        } finally {
            // 组织需要记录的信息
            String requestURI = request.getRequestURI();
            int post = requestURI.lastIndexOf(".");
            String uriEx = null;
            boolean doLog = true;
            if (post >= 0) {
                uriEx = requestURI.substring(post).toLowerCase();
                if (this.noLog.indexOf(uriEx) > 0) {
                    doLog = false;
                }
            }
            if (doLog || hasExp) {
                Long spendTime = System.currentTimeMillis() - start;
                String exp = hasExp ? "has Exception " : "OK ";
                logger.info(spendTime + "ms " + exp + requestURI);
            }
        }
    }

    public void destroy() {

    }

    static String[] headsNames = {"X-Real-IP", "X-Forwarded-For", "remote_addr"};

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
