package inu.appcenter.study3.service;

import inu.appcenter.study3.domain.Member;
import inu.appcenter.study3.exception.MemberException;
import inu.appcenter.study3.model.member.MemberSaveRequest;
import inu.appcenter.study3.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long saveMember(MemberSaveRequest memberSaveRequest) {
        if(memberRepository.findByEmail(memberSaveRequest.getEmail()).isPresent()) {
            throw new MemberException(("이미 가입된 이메일이 존재합니다."));
        }

        Member member = Member.createMember(memberSaveRequest.getEmail(),
                                            memberSaveRequest.getPassword(),
                                            memberSaveRequest.getName());

        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }
}
