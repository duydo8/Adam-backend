package com.example.adambackend.config;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
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
    Twilio.init("AC2997aff34046863723298d818dd01090", "640ebd17a042422815e499ae5d5fa8dc");
    Message.creator(new com.twilio.type.PhoneNumber(formatPhoneNumber(phoneNumber)),
            new com.twilio.type.PhoneNumber("+14173843028"),
            "Your verification code is: " + code)
        .create();
  }

  public String formatPhoneNumber(String phoneNumber) {
    if (!phoneNumber.startsWith("0")) {
      return "+84" + phoneNumber;
    }
    return phoneNumber.replace("0","+84");
  }
}
