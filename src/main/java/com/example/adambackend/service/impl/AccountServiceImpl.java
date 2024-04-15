package com.example.adambackend.service.impl;

import com.example.adambackend.common.CommonUtil;
import com.example.adambackend.entities.Account;
import com.example.adambackend.payload.account.AccountDTOs;
import com.example.adambackend.payload.account.AccountResponse;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.repository.AccountRepository;
import com.example.adambackend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public Optional<Account> findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public List<AccountResponse> findAll() {
        return accountRepository.findAlls();
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public void deleteById(Integer id) {
        accountRepository.deleteById(id);
    }

    @Override
    public Optional<Account> findById(Integer id) {
        return accountRepository.findById(id);
    }

    @Override
    public List<Account> findByRoleName(String roleName) {
        return accountRepository.findByRoleName(roleName);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return accountRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return accountRepository.existsByEmail(email);
    }


    @Override
    public AccountDTOs findByIds(Integer id) {
        return accountRepository.findByIds(id);
    }


    @Override
    public void sendVerificationEmail(Account account, String siteURL) throws MessagingException, UnsupportedEncodingException {
        String toAddress = account.getEmail();
        String fromAddress = "adamstoreservice@gmail.com";
        String senderName = "Adam Store";
        String subject = "Please verify your registration";
        String content = "Dear name,<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"URL\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Adam Store";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("name", account.getFullName());
        String verifyURL = siteURL + "/verify?code=" + account.getVerificationCode();

        content = content.replace("URL", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);
    }


    @Override
    public Optional<Account> findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    public Optional<Account> findByPhoneNumber(String phoneNumber) {
        return accountRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public List<Double> countTotalAccount(Integer year) {
        return accountRepository.countTotalAccount(year);
    }

    @Override
    public List<Double> countTotalSignUpAccount(Integer year) {
        return accountRepository.countTotalSignUpAccount(year);

    }

    @Override
    public List<Double> countTotalAccountInOrder(Integer year) {
        return accountRepository.countTotalAccountInOrder(year);
    }

    @Override
    public void updateAccountDeleted(String id) {
        accountRepository.updateAccountDeleted(id);
    }

    @Override
    public Account getById(Integer id){
        return accountRepository.getById(id);
    }

    @Override
    public Account findByUserNameAndEmailAndPhoneNumber(String username, String email, String phoneNumber) {
        return accountRepository.findByUserNameAndEmailAndPhoneNumber(username, email, phoneNumber);
    }

    @Override
    public String checkAccountRegistration(String username, String email, String phoneNumber) {
        Account account = accountRepository.findByUserNameAndEmailAndPhoneNumber(username, email, phoneNumber);
        if (CommonUtil.isNotNull(account)) {
            if (account.getUsername().equals(username)) {
                return "Username has been used";
            }
            if (account.getEmail().equals(email)) {
                return "Email has been used";
            }
            if (account.getPhoneNumber().equals(phoneNumber)) {
                return "PhoneNumber has been used";
            }
        }
        return "";
    }
}
