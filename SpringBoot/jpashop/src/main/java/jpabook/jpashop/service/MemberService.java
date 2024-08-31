package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.PublicKey;
import java.util.List;

@Service
@RequiredArgsConstructor // 생성자 생성해줌, memberRepository 를 변수로 하는
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원 가입
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        // Exception
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()) {
             throw new IllegalStateException("이미 존재하는 회원입니다."); // 에러 메세지
        }
    }

    // 회원 전체 조회
    @Transactional(readOnly = true) // 읽기 전용일 경우, 데이터 접근에만 사용하므로 메모적으로 이득
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

}
