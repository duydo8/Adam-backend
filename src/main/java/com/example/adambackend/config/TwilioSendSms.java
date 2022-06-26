package com.example.adambackend.config;

import com.twilio.Twilio;

public class TwilioSendSms {
    public static void main(String[] args) {
        Twilio.init(
                System.getenv("TWILIO_ACCOUNT_SID"),
                System.getenv("TWILIO_AUTH_TOKEN"));
    }
}
