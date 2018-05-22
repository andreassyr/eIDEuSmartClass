/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.utils.generators;

import java.security.SecureRandom;
import org.apache.commons.lang.RandomStringUtils;

/**
 *
 * @author nikos
 */
public class UtilGenerators {

    public static String generateRandomPass(int size) {
        int length = size;
        boolean useLetters = true;
        boolean useNumbers = false;
        return RandomStringUtils.random(length, useLetters, useNumbers);
    }

    public static String generateRandomPIN(int size) {
        int length = size;
        boolean useLetters = false;
        boolean useNumbers = true;
        return RandomStringUtils.random(length, useLetters, useNumbers);
    }

    public static String generateRandomADPass(int size) {
        char[] possibleCharacters = (new String("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?")).toCharArray();
        return RandomStringUtils.random(size, 0, possibleCharacters.length - 1, false, false, possibleCharacters, new SecureRandom());
    }

}
