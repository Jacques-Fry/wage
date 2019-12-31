package com.transeesoft.wage.interceptor;

import com.transeesoft.wage.exception.CommonException;
import entity.StatusCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 花落泪知雨
 * @create 2019/9/3
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {
  @Autowired
  private JwtUtil jwtUtil;

  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String token = request.getHeader("token");

    try {
      if (StringUtils.isNotBlank(token)) {
        request.setAttribute("claims", jwtUtil.parseJWT(token));
      }
    } catch (Exception e) {
      throw new CommonException(StatusCode.TOKENERROR,"签名无效!");
    }

    //无论如何都放行
    //拦截器负责解析token令牌
    return true;
  }
}
