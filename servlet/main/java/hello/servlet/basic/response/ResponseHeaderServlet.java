package hello.servlet.basic.response;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ResponseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Http 응답에서 첫 번째는 status-line
        // [status-line]
        response.setStatus(HttpServletResponse.SC_OK); // SC_OK는 200을 뜻한다
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400을 나타낸다

        // response header 데이터 세팅 코드
        // [response-headers]
        response.setHeader("Content-Type", "text/plain;charset=utf-8");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("my-header", "hello");

        // [Header 편의 메서드]
        content(response);
        cookie(response);
        redirect(response);

        // [message body]
        PrintWriter writer = response.getWriter();
        writer.println("안녕하세요");

    }

    private void content(HttpServletResponse response) {
        //Content-Type: text/plain;charset=utf-8
        //Content-Length: 2 // 적지 않아도 자동으로 계산되어서 안 적어도 된다
        //response.setHeader("Content-Type", "text/plain;charset=utf-8");
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        //response.setContentLength(2); //(생략시 자동 생성)
    }

    private void cookie(HttpServletResponse response) {

        // Set-Cookie: myCookie=good; Max-Age=600;
        // response.setHeader("Set-Cookie", "myCookie=good; Max-Age=600");

        Cookie cookie = new Cookie("myCookie", "good");
        cookie.setMaxAge(600); //600초

        response.addCookie(cookie); // addCookie 에 객체를 넣어주면 위에 주석 처리한 코드와 똑같은 의미를 지닌다
    }

    private void redirect(HttpServletResponse response) throws IOException {
        //Status Code 302
        //Location: /basic/hello-form.html 이 위치로 보내는 것

//        response.setStatus(HttpServletResponse.SC_FOUND); // 302를 의미
//        response.setHeader("Location", "/basic/hello-form.html");

        response.sendRedirect("/basic/hello-form.html"); // 이 주소로 redirect
    }

}
