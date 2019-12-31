package com.transeesoft.wage.handler;

import com.transeesoft.wage.exception.CommonException;
import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Jack_YD
 * @create 2019/8/27 16:07
 */
@CrossOrigin("*")
@RestControllerAdvice
public class CommonExceptionHandler {

  @ExceptionHandler(value = {CommonException.class})
  public Result commonException(CommonException e, HttpServletResponse response) {
    response.setHeader("Access-Control-Allow-Origin","*");
    response.setHeader("Content-Type","application/json;charset=UTF-8");
    return new Result(false, e.getCode(), e.getMsg());
  }


  @ExceptionHandler(value = {Exception.class})
  public Result exception(Exception e, HttpServletResponse response) {
    e.printStackTrace();
    response.setHeader("Access-Control-Allow-Origin","*");
    response.setHeader("Content-Type","application/json;charset=UTF-8");
    return new Result(false, StatusCode.ERROR, "Api请求执行失败");
  }

}
