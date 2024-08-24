package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component // 자동으로 스프링 빈에 등록하는 방법
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Autowired // 의존 관계를 자동으로 가져와서 주입     like) ac.getBean(MemberService.class);
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }

}
