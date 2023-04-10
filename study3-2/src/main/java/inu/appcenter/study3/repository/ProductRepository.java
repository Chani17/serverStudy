package inu.appcenter.study3.repository;

import inu.appcenter.study3.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
