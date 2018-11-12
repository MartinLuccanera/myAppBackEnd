package com.martin.spring.security.oauth2;

import static utils.StringUtils.print;
import com.martin.spring.security.oauth2.config.encryption.Encoders;
import org.apache.commons.codec.binary.Base64;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;

@SpringBootApplication(exclude = JmxAutoConfiguration.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        byte[] encodedBytes = Base64.encodeBase64("spring-security-oauth2-read-client:spring-security-oauth2-read-client-password1234".getBytes());
        print("encodedBytes to read: " + new String(encodedBytes));
        encodedBytes = Base64.encodeBase64("spring-security-oauth2-read-write-client:spring-security-oauth2-read-write-client-password1234".getBytes());
        print("encodedBytes to read/write: " + new String(encodedBytes));
        byte[] decodedBytes = Base64.decodeBase64("c3ByaW5nLXNlY3VyaXR5LW9hdXRoMi1yZWFkLWNsaWVudDpzcHJpbmctc2VjdXJpdHktb2F1dGgyLXJlYWQtY2xpZW50LXBhc3N3b3JkMTIzNA==".getBytes());
        print("decodedBytes: " + new String(decodedBytes));
        Encoders enc = new Encoders();
        print("Encoded pass = " +
                enc.userPasswordEncoder().matches("spring-security-oauth2-read-client-password1234",
                        "$2a$04$WGq2P9egiOYoOFemBRfsiO9qTcyJtNRnPKNBl5tokP7IP.eZn93km"));
        print("Encoded pass = " +
                enc.oauthClientPasswordEncoder().matches("spring-security-oauth2-read-write-client-password1234",
                        "$2a$04$soeOR.QFmClXeFIrhJVLWOQxfHjsJLSpWrU1iGxcMGdu.a5hvfY4W"));
    }
}
