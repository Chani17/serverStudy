package inu.appcenter.study4.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="role_id")
    private Long id;

    private String roleName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public static Role createRole(Member member) {
        Role role = new Role();
        role.roleName = "ROLE_MEMBER";
        role.member = member;
        return role;
    }

    public static Role createAdminMember(Member member) {
        Role role = new Role();
        role.roleName = "ROLE_MEMBER";  // admin
        role.member = member;
        return role;
    }
}
