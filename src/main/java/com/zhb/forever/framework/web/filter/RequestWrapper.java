package com.zhb.forever.framework.web.filter;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.Policy;
import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import com.zhb.forever.framework.util.StringUtil;

/**
*@author   zhanghb<a href="mailto:zhb20111503@126.com">zhanghb</a>
*@createDate 2018年11月2日下午4:53:19
*/

public class RequestWrapper extends HttpServletRequestWrapper {

    protected Logger logger = LoggerFactory.getLogger(RequestWrapper.class);

    private AntiSamy antiSamy = null;

    public RequestWrapper(HttpServletRequest request) {
        super(request);
        inintAntisamy();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> request_map = super.getParameterMap();
        if (null != request_map) {
            request_map.forEach((key,vals)->{
                if (null != vals) {
                    for (int i = 0; i < vals.length; i++) {
                        vals[i] = xssClean(vals[i]);
                    }
                }
            });
        }
        
        return request_map;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public String[] getParameterValues(String name) {
        String[] v = super.getParameterValues(name);
        if (v == null || v.length == 0) {
            return v;
        }
        for (int i = 0; i < v.length; i++) {
            v[i] = xssClean(v[i]);
        }
        return v;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public String getParameter(String name) {
        String v = super.getParameter(name);
        if (StringUtil.isBlank(v)) {
            return null;
        }
        return xssClean(v);
    }

    private String xssClean(String value) {
        try {
            final CleanResults cr = antiSamy.scan(value);
            logger.info("clean:{}", cr.getCleanHTML());
            return cr.getCleanHTML();
        } catch (ScanException e) {
            e.printStackTrace();
        } catch (PolicyException e) {
            e.printStackTrace();
        }
        return value;
    }

    private void inintAntisamy() {
        ClassPathResource classpathResource = new ClassPathResource("antisamy/antisamy.xml");//jar包 下的文件
        //应用中的文件
        /*classpathResource = new ClassPathResource("classpath*:conf/userInputSecurityConfig.properties");*/
        InputStream is = null;
        try {
            is = classpathResource.getInputStream();
            Policy policy = Policy.getInstance(is);
            this.antiSamy = new AntiSamy(policy);
        } catch (PolicyException e) {
            if (this.logger.isErrorEnabled()) {
                this.logger.info(e.toString());
            }
            throw new RuntimeException(e);
        } catch (IOException e) {
            if (this.logger.isErrorEnabled()) {
                this.logger.info(e.toString());
            }
            throw new RuntimeException(e);
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean containsIllegelChar(String context) {
        try {
            CleanResults result = this.antiSamy.scan(context);
            context = result.getCleanHTML();
            List errorList = result.getErrorMessages();
            if ((errorList != null) && (errorList.size() > 0)) {
                return true;
            }
        } catch (PolicyException e) {
            if (this.logger.isInfoEnabled())
                this.logger.info(e.toString());
        } catch (ScanException e) {
            if (this.logger.isInfoEnabled())
                this.logger.info(e.toString());
        }
        return false;
    }

}


