package com.zerobase.cms.user.application;

import com.zerobase.cms.user.domain.SignInForm;
import com.zerobase.cms.user.domain.model.Customer;
import com.zerobase.cms.user.domain.model.Seller;
import com.zerobase.cms.user.exception.CustomException;
import com.zerobase.cms.user.exception.ErrorCode;
import com.zerobase.cms.user.service.customer.CustomerService;
import com.zerobase.cms.user.service.seller.SellerService;
import com.zerobase.domain.config.JwtAuthenticationProvider;
import com.zerobase.domain.domain.common.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignInApplication {

  private final CustomerService customerService;
  private final SellerService sellerService;
  private final JwtAuthenticationProvider provider;

  public String customerLoginToken(SignInForm form) {
    Customer c = customerService.findValidCustomer(form.getEmail(), form.getPassword())
        .orElseThrow(() -> new CustomException(ErrorCode.SIGN_IN_ERROR));

    return provider.createToken(c.getEmail(), c.getId(), UserType.CUSTOMER);
  }

  public String sellerLoginToken(SignInForm form) {
    Seller c = sellerService.findValidSeller(form.getEmail(), form.getPassword())
        .orElseThrow(() -> new CustomException(ErrorCode.SIGN_IN_ERROR));

    return provider.createToken(c.getEmail(), c.getId(), UserType.CUSTOMER);
  }
}