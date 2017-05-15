package com.ft.uuidutils;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class GenerateV3UUID {

    private static final Charset UTF8 = Charset.forName("UTF-8");
    private static final ThreadLocal<MessageDigest> md5 = new ThreadLocal<MessageDigest>() {
        protected MessageDigest initialValue() {
            try {
                return MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
    };

    public static UUID doubleDigested(String data) {
        return UUID.nameUUIDFromBytes(md5.get().digest(data.getBytes(UTF8)));
    }

    public static UUID singleDigested(String data) {
        return UUID.nameUUIDFromBytes(data.getBytes(UTF8));
    }
}
