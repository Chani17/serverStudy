package inu.appcenter.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing  // AuditingEntityListener.class 사용 가능
public class JpaConfig {

}
