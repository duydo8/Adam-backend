package com.example.adambackend.config;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TwilioSendSms {
    public void sendCode(String phoneNumber,String code) {

        Twilio.init(
                System.getenv("AC07311f9dc9c439a7e2e5ca5fb8e2ef2a"),
                System.getenv("eee2d0a92e9c0de8efd1dcc8dddfe101"));

        Message.creator(new PhoneNumber("+19036229068"),new PhoneNumber(phoneNumber),
                "Mã xác nhận của bạn là " + code +" sẽ hết hạn sau 30p").create();
    }
}
