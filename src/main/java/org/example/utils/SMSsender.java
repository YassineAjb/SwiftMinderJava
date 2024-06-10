package org.example.utils;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SMSsender {

        // Twilio account credentials
        public static final String ACCOUNT_SID = "******";
        public static final String AUTH_TOKEN = "****";

        public static void Send(String toPhoneNumber, String messageBody){
            // Initialize Twilio client with your account credentials
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

            // Create a Twilio Message
            Message message = Message.creator(
                            new PhoneNumber(toPhoneNumber), // To phone number
                            new PhoneNumber("+***"), // From Twilio phone number
                            messageBody) // SMS body
                    .create();

            // Print the message SID
            System.out.println("Message SID: " + message.getSid());
        }
}
