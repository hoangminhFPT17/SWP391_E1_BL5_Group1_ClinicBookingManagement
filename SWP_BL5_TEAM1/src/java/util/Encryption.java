/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Encryption {

    private static final String SALT = "asjrlkmcoewj@tjle;oxqskjhdjksjf1jurVn"; // Salt giúp làm mật khẩu phức tạp hơn

    public static String toSHA1(String str) {
        String result = null;
        str = str + SALT;

        try {
            byte[] dataBytes = str.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(dataBytes);
            result = Base64.getEncoder().encodeToString(digest);
        } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
            // Xử lý ngoại lệ rõ ràng hơn
            System.err.println("Error during encryption: " + e.getMessage());
            e.printStackTrace();
        }

        return result;
    }
}
