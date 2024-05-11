package org.example.models.Article;

import com.twilio.Twilio;


public class Sendsms {

    // Twilio credentials
    private static final String accountSid = "ACd411a1b330f9b8d6c148c415ee1a4f3d";
    private static final String token ="95e3ae77cc9c4539bc828161c3504f45";
    private static String mynumber ="+19292961575";

    static {
        // Initialize Twilio
        Twilio.init(accountSid, token);
    }




    public String getAccountSid() {
        return accountSid;
    }

    public String getToken() {
        return token;
    }

    public String getMynumber() {
        return mynumber;
    }
}

