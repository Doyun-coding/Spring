package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan( // 기존 예제 코드를 남기기 위해서 Filter 사용
        // 범위를 지정하지 않으면 현재 패키지, hello.core에서부터 하위 파일 모두 탐색한다
        basePackages = "hello.core.member",  // basePackages 는 스캔의 시작, 범위를 지정해준다

        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class) // 필터를 이용하여 스캔하는데 뺄 것을 지정해주는 것
)
public class AutoAppConfig {

//    @Bean(name = "memoryMemberRepository") // 스프링 빈에 등록되면 맨 앞 글자가 소문자로 저장된다
//    MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//    }
}


