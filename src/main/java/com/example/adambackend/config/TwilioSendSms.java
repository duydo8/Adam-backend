package com.example.adambackend.config;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TwilioSendSms {
    public static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    public static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");
    public void sendCode(String phoneNumber,String code) {

        Twilio.init(
                "AC3ac486fb125060125c0433ca45a05009",
                "e3c30102ce6da495e7916d846ab94f57");

        Message.creator(new PhoneNumber("+19036229068"),new PhoneNumber(phoneNumber),
                "Mã xác nhận của bạn là " + code ).create();
    }
}
