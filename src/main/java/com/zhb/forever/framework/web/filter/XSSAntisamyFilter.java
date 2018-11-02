package com.zhb.forever.framework.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.zhb.forever.framework.util.StringUtil;

/**
*@author   zhanghb<a href="mailto:zhb20111503@126.com">zhanghb</a>
*@createDate 2018年11月2日下午4:52:14
*/

public class XSSAntisamyFilter implements Filter {

    private FilterConfig filterConfig;
    private String[] excludedPageArray;
    private String excludedPages;   

   @Override
   public void init(FilterConfig paramFilterConfig) throws ServletException {
       this.filterConfig = paramFilterConfig;   
       excludedPages = filterConfig.getInitParameter("excludedPages");
       if (StringUtil.isNotEmpty(excludedPages)) {  
           excludedPageArray = excludedPages.replaceAll("[\\s]", "").split(",");  
       }  
   }

   @Override
   public void doFilter(ServletRequest paramServletRequest, ServletResponse paramServletResponse,
           FilterChain paramFilterChain) throws IOException, ServletException {
       paramFilterChain.doFilter(new RequestWrapper((HttpServletRequest) paramServletRequest), paramServletResponse);  
   }

   @Override
   public void destroy() {
       this.filterConfig = null;
   }

}


