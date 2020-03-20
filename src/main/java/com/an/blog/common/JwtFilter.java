package com.an.blog.common;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT核心过滤器配置（重写shiro原生的方法）
 * 所有的请求都会先经过Filter，所以我们继承官方的BasicHttpAuthenticationFilter，并且重写鉴权的方法。
 * 执行流程 preHandle->isAccessAllowed->isLoginAttempt->executeLogin
 */
public class JwtFilter extends BasicHttpAuthenticationFilter {

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    /**
     * 如果带有 token，则对 token 进行检查，否则直接通过
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws AuthenticationException {
        // 判断请求的请求头是否带上token
        if (isLoginAttempt(request, response)) {
            // 如果存在，则进入executeLogin方法执行登入，检查token是否正确
            try {
                executeLogin(request, response);
                return true;
            } catch (AuthenticationException e) {
                // token错误
//                try {
//                    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//                    httpServletResponse.sendRedirect("/error/authentication");
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
//                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/error/authentication");
//                try {
//                    requestDispatcher.forward(request, response);
//                } catch (ServletException ex) {
//                    ex.printStackTrace();
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
                return false;
            }
        }
        // 如果请求头不存在token，则可能是执行登陆操作或者是游客状态访问，无需检查token，直接返回true
        return true;
    }

    /**
     * 判断用户是否想要登入。
     * 检测 header 里面是否包含 Token 字段
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        String token = getAuthzHeader(request);
        return token != null && !"".equals(token);
    }

    /**
     * 执行登陆操作
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws AuthenticationException {
        JwtToken token = new JwtToken(getAuthzHeader(request));
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(token);
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    /**
     * 重写拒绝访问
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        redirectToLogin(request, response);
        return false;
    }

    /**
     * 重写跳转方法
     */
    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        WebUtils.issueRedirect(request, response, "/error/authentication");
    }

}
