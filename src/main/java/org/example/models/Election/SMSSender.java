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
        String AUTH_TOKEN = "5e3aadeeec1f3b00fb445bdfd68526ed";

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        String message = "Bonjour " +username +lastName+"Election ajoutée avec succès";


        Message twilioMessage = Message.creator(
                new com.twilio.type.PhoneNumber("+21623990938"),
                new com.twilio.type.PhoneNumber("+17744257047"),message).create();

        System.out.println("SMS envoyé : " + twilioMessage.getSid());
    }
}