package inu.appcenter.study7.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {

    private String profileImageUrl;

    private String thumbnailImageUrl;

    private String imageStoreName;

    public static Image createImage(String profileImageUrl, String thumbnailImageUrl, String imageStoreName) {
        Image image = new Image();
        image.profileImageUrl = profileImageUrl;
        image.thumbnailImageUrl = thumbnailImageUrl;
        image.imageStoreName = imageStoreName;
        return image;
    }
}