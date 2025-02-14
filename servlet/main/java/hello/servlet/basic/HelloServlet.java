package hello.servlet.basic;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "helloServlet", urlPatterns = "/hello") // name 은 서블릿의 이름을 나타내고 urlPatterns 은 URL 매핑이다
public class HelloServlet extends HttpServlet {
    // HTTP 요청을 통해 매핑된 URL이 호출되면 서블릿 컨테이너는 service 메소드를 실행한다.
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("HelloServlet.service");
        System.out.println("request = " + request);
        System.out.println("response = " + response);

        String username = request.getParameter("username"); // 요청
        System.out.println("username = " + username); // 응답

        response.setContentType("text/plain"); // Content-Type
        response.setCharacterEncoding("utf-8"); // Content-Type
        response.getWriter().write("hello " + username);


    }
}
