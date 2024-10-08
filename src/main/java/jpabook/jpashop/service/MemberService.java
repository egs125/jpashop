package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원 가입
    @Transactional
    public Long join(Member member) {
        this.validateDuplicateMember(member);
        this.memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = this.memberRepository.findByName(member.getName());
        if ( !findMembers.isEmpty() ) {
            throw new IllegalStateException("이미 존재하는 회원");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return this.memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return this.memberRepository.findOne(memberId);
    }
}
