package com.example.adambackend.config;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;

public class TwilioSendSms {

  @Value("${twilio.account.sid}")
  private String accountSid;

  @Value("${twilio.account.token}")
  private String authToken;

  @Value("${twilio.account.phone-number}")
  private String twilioPhoneNumber;

  @Value("${twilio.message.verify}")
  private String messageCode;

  public void sendCode(String phoneNumber, Integer code) {

    Twilio.init(accountSid, authToken);

    Message.creator(new PhoneNumber(phoneNumber), new PhoneNumber(twilioPhoneNumber),
        messageCode + " " + code).create();
  }
}
