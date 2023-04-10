package inu.appcenter.study6.service;

import inu.appcenter.study6.domain.Member;
import inu.appcenter.study6.model.MemberCreateRequest;
import inu.appcenter.study6.model.MemberUpdateRequest;
import inu.appcenter.study6.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member saveMember(MemberCreateRequest memberCreateRequest) {
        Member member = Member.createMember(memberCreateRequest.getName(), memberCreateRequest.getAge());

        Member savedMember = memberRepository.save(member);     // 회원을 저장하고 회원 id값을 반환해주는 역할
        return savedMember;
    }

    @Transactional
    public Member updateMember(long memberId, MemberUpdateRequest memberUpdateRequest) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException());

        findMember.update(memberUpdateRequest.getName(), memberUpdateRequest.getAge());
        return findMember;
    }

    @Transactional
    public boolean deleteMember(Long memberId) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException());

        findMember.delete();

        return true;
    }
}
