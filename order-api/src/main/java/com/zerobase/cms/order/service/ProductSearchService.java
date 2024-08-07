package com.zerobase.cms.order.service;

import com.zerobase.cms.order.domain.model.Product;
import com.zerobase.cms.order.domain.repository.ProductRepository;
import com.zerobase.cms.order.exception.CustomException;
import com.zerobase.cms.order.exception.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductSearchService {

  private final ProductRepository productRepository;

  public List<Product> searchByName(String name) {
    return productRepository.searchByName(name);
  }

  public Product getByProductId(Long productId) {
    return productRepository.findWithProductItemsById(productId)
        .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
  }

  public List<Product> getListByProductIds(List<Long> productIds) {
    return productRepository.findAllById(productIds);
  }
}
