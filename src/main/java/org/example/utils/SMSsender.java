package org.example.utils;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SMSsender {

        // Twilio account credentials
        public static final String ACCOUNT_SID = "ACb8c9ae38a6e3b1ad0661e7fe67bf0092";
        public static final String AUTH_TOKEN = "5447eee704635b0685587c3daef114ab";

        public static void Send(String toPhoneNumber, String messageBody){
            // Initialize Twilio client with your account credentials
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

            // Create a Twilio Message
            Message message = Message.creator(
                            new PhoneNumber(toPhoneNumber), // To phone number
                            new PhoneNumber("+18627019330"), // From Twilio phone number
                            messageBody) // SMS body
                    .create();

            // Print the message SID
            System.out.println("Message SID: " + message.getSid());
        }
}
