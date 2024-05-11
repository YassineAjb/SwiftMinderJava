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


        String ACCOUNT_SID = "AC5bc580ea6b3b2aee76723258977dfd6a";
        String AUTH_TOKEN = "8dc745423c9cebc334a0a7b9de304001";

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        String message = "Bonjour Mr " +username +lastName+" Ajout Succes";


        Message twilioMessage = Message.creator(
                new PhoneNumber("+21623990938"),
                new PhoneNumber("+17744257047"),message).create();

        System.out.println("SMS envoyé : " + twilioMessage.getSid());
    }
}