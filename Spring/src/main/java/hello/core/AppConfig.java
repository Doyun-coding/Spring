package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // 설정 정보에 @Configuration 적어주게 되어 있음 -> 사용하면 싱글톤을 보장한다(CGLIB)
public class AppConfig { // 앱 전체를 설정하고 구성, DIP 를 지킴, AppConfig가 like) DI 컨테이너 역할

    @Bean // 각 함수에 Bean 사용하면 Spring 컨테이너에 등록된다
    public MemberService memberService() {
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public static MemoryMemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        // return new FixDiscountPolicy(); // FixDiscount 사용할 시
        return new RateDiscountPolicy(); // RateDiscount 사용할 시
    }

}
