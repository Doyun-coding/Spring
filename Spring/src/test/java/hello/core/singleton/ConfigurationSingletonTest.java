package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.*;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class ConfigurationSingletonTest {

    @Test
    void configurationTest() { // Spring 싱글톤 보장
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();

        System.out.println("memberRepository = " + memberRepository);
        System.out.println("memberService -> memberRepository1 = " + memberRepository1);
        System.out.println("orderService -> memberRepository2 = " + memberRepository2);

        Assertions.assertThat(memberService.getMemberRepository()).isNotSameAs(memberRepository);
        Assertions.assertThat(orderService.getMemberRepository()).isNotSameAs(memberRepository);
    }

    @Test
    void configurationDeep() { // 이것은 내가 만든 클래스가 아니라 스프링이 CGLIB 라는 바이트 코드 조작 라이브러리를 사용하여 AppConfig 클래스를 상속받은 임의의 클래스를 만들고 그 다른 클래스를 스프링 빈에 등록한다
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);

        System.out.println("bean.getClass() = " + bean.getClass());
    }

}
