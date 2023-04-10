package inu.appcenter.study7.model;

import inu.appcenter.study7.domain.Image;
import inu.appcenter.study7.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponse {

    private Long id;

    private String name;

    private String profileImageUrl;

    private String thumbnailImageUrl;

    public static MemberResponse from(Member member) {
        MemberResponse memberResponse = new MemberResponse();
        memberResponse.id = member.getId();
        memberResponse.name = member.getName();
        memberResponse.profileImageUrl = member.getImage().getProfileImageUrl();
        memberResponse.thumbnailImageUrl = member.getImage().getThumbnailImageUrl();
        return memberResponse;
    }
}
