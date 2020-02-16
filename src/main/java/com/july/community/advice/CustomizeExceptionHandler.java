package com.july.community.advice;

import com.alibaba.fastjson.JSON;
import com.july.community.dto.ResultDTO;
import com.july.community.exception.CustomizeErrorCode;
import com.july.community.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
   // Object
   void handle(Throwable e, Model model,
                  HttpServletRequest request, HttpServletResponse response){
        if(e instanceof CustomizeException){
            model.addAttribute("message",e.getMessage());
        }

        /*String contentType = request.getContentType();
        if ("application/json".equals(contentType)){
            ResultDTO resultDTO ;
            //返回JSON
            if(e instanceof CustomizeException){
                 resultDTO = ResultDTO.errorOf((CustomizeException) e);
            }else{
                //未知/未自己定义的异常时
                resultDTO = ResultDTO.errorOf(CustomizeErrorCode.UNKNOWN_EXCEPTION);
            }
            try {
                //手写要返回的json
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException ioExeption) {
                ioExeption.printStackTrace();
            }
            return null;
        }else {
            //错误页面跳转
            if(e instanceof CustomizeException){
               model.addAttribute("message",e.getMessage());
            }else{
                //未知/未自己定义的异常时
                model.addAttribute("message",CustomizeErrorCode.UNKNOWN_EXCEPTION.getMessage());
            }

            return new ModelAndView("error");
        }*/
    }
}
