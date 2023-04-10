package inu.appcenter.demo.service;

import inu.appcenter.demo.domain.Member;
import inu.appcenter.demo.domain.Team;
import inu.appcenter.demo.exception.MemberException;
import inu.appcenter.demo.exception.TeamException;
import inu.appcenter.demo.model.member.MemberSaveRequest;
import inu.appcenter.demo.repository.MemberRepository;
import inu.appcenter.demo.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    @Transactional
    public void save(Long teamId, MemberSaveRequest memberSaveRequest) {
        Team findTeam = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamException("존재하지 않는 Team id 입니다."));

        validateDuplicatedMemberEmail(memberSaveRequest.getEmail());

        Member member = Member.createMember(memberSaveRequest.getEmail(), memberSaveRequest.getName(),
                memberSaveRequest.getAge(), findTeam);

        memberRepository.save(member);
    }

    private void validateDuplicatedMemberEmail(String name) {
        Optional<Member> result = memberRepository.findByEmail(name);
        if (result.isPresent()) {
            throw new MemberException("이메일이 중복되었습니다.");
        }
    }

    public Member findById(Long memberId) {
        Member member = memberRepository.findWithTeamById(memberId)
                .orElseThrow(() -> new MemberException("존재하지 않는 회원 id입니다."));

        return member;
    }

    public List<Member> findAll() {
        return memberRepository.findWithTeamAll();
    }
}
