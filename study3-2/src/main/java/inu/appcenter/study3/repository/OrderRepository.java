package inu.appcenter.study3.repository;

import inu.appcenter.study3.domain.Member;
import inu.appcenter.study3.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {


}
