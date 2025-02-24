package com.example.adambackend.config;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TwilioSendSms {
    public static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    public static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");

    public void sendCode(String phoneNumber, Integer code) {

        Twilio.init(
                "AC2997aff34046863723298d818dd01090",
                "640ebd17a042422815e499ae5d5fa8dc");

        Message.creator(new PhoneNumber(phoneNumber), new PhoneNumber("+14173843028"),
                "Your verification code is: " + code).create();

    }
}
