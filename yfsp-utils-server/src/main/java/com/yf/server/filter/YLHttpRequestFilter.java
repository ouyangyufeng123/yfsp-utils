package com.yf.server.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import java.io.IOException;
import java.util.Enumeration;

/**
 *
 * @author ouyangyufeng
 * @date 2019/9/24
 */
@Order(0)
public class YLHttpRequestFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(YLHttpRequestFilter.class);

    public YLHttpRequestFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        StringBuffer sb = new StringBuffer();
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String uri = request.getRequestURI();
        sb.append("\t请求地址：" + uri + "\r\n\t数据格式：" + request.getContentType() + "\r\n\t请求类型：" + request.getMethod() + "\r\n\t请求参数：");
        Enumeration e = request.getParameterNames();

        while(e.hasMoreElements()) {
            String paramName = (String)e.nextElement();
            String paramValue = request.getParameter(paramName);
            sb.append(paramName + ":" + paramValue + ";\t");
        }

        logger.info("接口请求信息如下：\r\n" + sb);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}

