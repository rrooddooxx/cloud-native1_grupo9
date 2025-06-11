package com.skravetz.cn1grupo9.repository;

import com.skravetz.cn1grupo9.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}