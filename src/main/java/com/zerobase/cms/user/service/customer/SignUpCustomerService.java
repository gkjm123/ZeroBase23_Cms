package com.zerobase.cms.user.service.customer;

import static com.zerobase.cms.user.exception.ErrorCode.ALREADY_VERIFY;
import static com.zerobase.cms.user.exception.ErrorCode.CODE_NOT_MATCH;
import static com.zerobase.cms.user.exception.ErrorCode.DATE_EXPIRED;
import static com.zerobase.cms.user.exception.ErrorCode.USER_NOT_FOUND;

import com.zerobase.cms.user.domain.SignUpForm;
import com.zerobase.cms.user.domain.model.Customer;
import com.zerobase.cms.user.domain.repository.CustomerRepository;
import com.zerobase.cms.user.exception.CustomException;
import java.time.LocalDateTime;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignUpCustomerService {

  private final CustomerRepository customerRepository;

  public Customer signUp(SignUpForm form) {
    return customerRepository.save(Customer.from(form));
  }

  public boolean isEmailExist(String email) {
    return customerRepository.findByEmail(email.toLowerCase(Locale.ROOT)).isPresent();
  }

  @Transactional
  public void verifyEmail(String email, String code) {
    Customer customer = customerRepository.findByEmail(email)
        .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

    if (customer.isVerify()) {
      throw new CustomException(ALREADY_VERIFY);
    }

    if (!customer.getVerificationCode().equals(code)) {
      throw new CustomException(CODE_NOT_MATCH);
    }

    if (customer.getVerifyExpiredAt().isBefore(LocalDateTime.now())) {
      throw new CustomException(DATE_EXPIRED);
    }

    customer.setVerify(true);
  }

  @Transactional
  public void changeCustomerValidateEmail(Long customerId, String verificationCode) {
    Customer customer = customerRepository.findById(customerId)
        .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    customer.setVerificationCode(verificationCode);
    customer.setVerifyExpiredAt(LocalDateTime.now().plusDays(1));
  }
}
