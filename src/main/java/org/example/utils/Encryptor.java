package org.example.utils;

import org.mindrot.jbcrypt.BCrypt;

import java.util.Random;

public class Encryptor {

    public static String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }
    public static Boolean TestPassword(String encryptedPassword,String password){
        return BCrypt.checkpw(password,encryptedPassword);

    }

    public static String generateCode(int length) {
        // Define the character set for alphanumeric characters
        String charSet = "abcdefghijklmnopqrstuvwxyz0123456789";

        // Create a StringBuilder to store the generated code
        StringBuilder code = new StringBuilder();

        // Create an instance of Random
        Random random = new Random();

        // Generate random code of specified length
        for (int i = 0; i < length; i++) {
            // Get a random index within the character set length
            int randomIndex = random.nextInt(charSet.length());

            // Append the randomly selected character to the code
            code.append(charSet.charAt(randomIndex));
        }

        // Convert the StringBuilder to a String and return
        return code.toString();
    }

        }


