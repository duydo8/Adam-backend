package com.example.adambackend.service.impl;

import com.example.adambackend.entities.Account;
import com.example.adambackend.security.jwtConfig.repository.AccountRepository;
import com.example.adambackend.service.AccountService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
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
    public void register(Account account, String siteURL) throws UnsupportedEncodingException, MessagingException {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


        account.setPassword(passwordEncoder.encode(account.getPassword()));

        String randomCode = RandomString.make(64);
        account.setVerificationCode(randomCode);
        account.setActive(false);

        account.setTimeValid(LocalDateTime.now().plusMinutes(30));
        accountRepository.save(account);

        sendVerificationEmail(account, siteURL);
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
    public boolean verify(String verificationCode) {
        Account account = accountRepository.findByVerificationCode(verificationCode);
        LocalDateTime localDateTime = LocalDateTime.now();
        if (account == null || account.getTimeValid().isBefore(localDateTime)) {
            return false;
        } else {
            account.setVerificationCode(null);
            account.isActive();
            accountRepository.save(account);

            return true;
        }

    }

    @Override
    public Optional<Account> findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }


}
