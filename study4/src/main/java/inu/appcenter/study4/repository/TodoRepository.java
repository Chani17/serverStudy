package inu.appcenter.study4.repository;

import inu.appcenter.study4.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
