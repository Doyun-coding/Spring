package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello") // hello 로 Mapping 하고 hello 를 호출하면 Controller 실행
    public String hello(Model model) {
        model.addAttribute("data", "hello!!!!");
        return "hello";
    }

}
