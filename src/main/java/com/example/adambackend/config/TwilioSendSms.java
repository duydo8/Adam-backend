package com.example.adambackend.config;

import com.twilio.Twilio;

public class TwilioSendSms {
    public static void main(String[] args) {
        Twilio.init(
                System.getenv("AC3ac486fb125060125c0433ca45a05009"),
                System.getenv("e3c30102ce6da495e7916d846ab94f57"));
    }
}
