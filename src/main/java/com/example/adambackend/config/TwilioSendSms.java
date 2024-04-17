package com.example.adambackend.config;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;

public class TwilioSendSms {
	@Value("twillio-user")
	private String ACCOUNT_SID;
	@Value("twillio-token")
	private String AUTH_TOKEN;

	public void sendCode(String phoneNumber, Integer code) {
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		Message.creator(new PhoneNumber(phoneNumber), new PhoneNumber("+12569603623"),
						"Your verify is: " + code)
				.create();
	}
}
