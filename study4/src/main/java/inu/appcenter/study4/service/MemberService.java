package inu.appcenter.study4.service;

import inu.appcenter.study4.config.security.JwtTokenProvider;
import inu.appcenter.study4.domain.Member;
import inu.appcenter.study4.model.member.MemberLoginRequest;
import inu.appcenter.study4.model.member.MemberSaveRequest;
import inu.appcenter.study4.repository.MemberRepository;
import inu.appcenter.study4.repository.query.MemberQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService  {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberQueryRepository memberQueryRepository;
    //private final JwtTokenProvider jwtTokenProvider;

    @PostConstruct
    //@Transactional
    public void saveAdminMember() {     // 관리자 권한 추가
        Member.AdminMember("admin", passwordEncoder.encode(("admin", )))
        memberRepository.save(adminMember);
    }

    @Transactional
    public void saveMember(MemberSaveRequest memberSaveRequest) {
        // 이메일 검증 생략

        /*
            회원 가입할 때 비밀번호를 passwordEncoder를 사용하여 암호화한다.
            암호화한 비밀번호를 데이터베이스에 저장
         */
        Member member = Member.createMember(memberSaveRequest.getEmail(),
                passwordEncoder.encode(memberSaveRequest.getPassword()),
                        memberSaveRequest.getName());

        memberRepository.save(member);
    }


    public Member loginMember(MemberLoginRequest memberLoginRequest) {

        /**
         * 2. 이메일로 회원을 조회해와서 비밀번호가 일치하는지 passwordEncoder를 사용해서 확인
         * 로그인 요청 비밀번호와 DB의 암호화된 비밀번호가 일치하는지 확인
         */
        Member findMember = memberRepository.findByEmail(memberLoginRequest.getEmail())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 이메일입니다."));

        if(!passwordEncoder.matches(memberLoginRequest.getPassword(), findMember.getPassword())){
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }
        //jwtTokenProvider.createToken(String.valueOf(findMember.getId()));   //-> long 을 string으로

        return findMember;

    }

    public List<Member> findMembers() {
        List<Member> members = memberQueryRepository.findMemberWithTodoById();

        return members;
    }
}
