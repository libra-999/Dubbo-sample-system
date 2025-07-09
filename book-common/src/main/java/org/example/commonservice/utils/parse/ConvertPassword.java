package org.example.commonservice.utils.parse;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ConvertPassword {

    public static String encryptPassword(String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(password);
    }

    public static boolean matchPassword(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.matches(rawPassword,encodedPassword);
    }

}
