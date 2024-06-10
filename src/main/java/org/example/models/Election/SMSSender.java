package org.example.models.Election;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class SMSSender {


    public void  send_SMS (String username,String lastName) {


        String ACCOUNT_SID = "***";
        String AUTH_TOKEN = "*****";

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        String message = "Bonjour " +username +lastName+"Election ajoutée avec succès";


        Message twilioMessage = Message.creator(
                new com.twilio.type.PhoneNumber("+\"*****\";"),
                new com.twilio.type.PhoneNumber("+\"*****\";"),message).create();

        System.out.println("SMS envoyé : " + twilioMessage.getSid());
    }
}