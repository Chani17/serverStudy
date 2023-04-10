package inu.appcenter.demo.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass       // 이 클래스를 상속받으면 이 클래스의 필드가 상속받은 entity의 속성이 되는 것이다.
@EntityListeners(AuditingEntityListener.class)  // JPA Auditing
@Getter
public abstract class BaseEntity {

    @CreatedDate
    private LocalDateTime creatdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

}
