package com.zerobase.cms.order.domain.repository;

import com.zerobase.cms.order.domain.model.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductItemRepository extends JpaRepository<ProductItem, Long> {


}
