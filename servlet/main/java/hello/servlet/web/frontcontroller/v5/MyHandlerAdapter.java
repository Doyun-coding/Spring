package hello.servlet.web.frontcontroller.v5;

import hello.servlet.web.frontcontroller.ModelView;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface MyHandlerAdapter {

    // 1. 핸들러 조회
    boolean supports(Object handler); // 컨트롤러가 넘어왔을 때 내가 지원할 수 있는 컨트롤러인지 판단

    // Object : 컨트롤러를 담는 파라미터
    ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException;

}