package com.zerobase.cms.user.service.seller;

import com.zerobase.cms.user.domain.model.Seller;
import com.zerobase.cms.user.domain.repository.SellerRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerService {

  private final SellerRepository sellerRepository;

  public Optional<Seller> findByIdAndEmail(Long id, String email) {
    return sellerRepository.findById(id).stream()
        .filter(seller -> seller.getEmail().equals(email))
        .findFirst();
  }

  public Optional<Seller> findValidSeller(String email, String password) {
    return sellerRepository.findByEmail(email).stream()
        .filter(seller -> seller.getPassword().equals(password) && seller.isVerify())
        .findFirst();
  }
}
