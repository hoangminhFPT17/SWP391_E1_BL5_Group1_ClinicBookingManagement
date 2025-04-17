/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import java.nio.charset.Charset;
import java.util.Random;

/**
 *
 * @author ADMIN
 */
public class RandomStringService {
    public static String random(int len) {
    byte[] array = new byte[len]; // length is bounded by 7
    int i = 0;
    while(i < len) {
        int ran = new Random().nextInt((122 - 48) + 1) + 48;
        if((ran >= '0' && ran <= '9') || (ran >= 'a' && ran <= 'z') || (ran >= 'A' && ran <= 'Z')) {
            array[i] = (byte)ran;
            i++;
        }
    }
    String generatedString = new String(array, Charset.forName("UTF-8"));

    return generatedString;
}
}
