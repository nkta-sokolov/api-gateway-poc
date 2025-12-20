package com.sokolov.apigateway.helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class HashCalculator {

    private static final String SHA_256 = "SHA-256";

    private final HexFormat hexFormat;

    public String sha256Hex(String value) {
        byte[] digest = getDigest(SHA_256).digest(value.getBytes());
        return hexFormat.formatHex(digest);
    }

    private MessageDigest getDigest(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException ex) {
            String message = "Could not find MessageDigest with algorithm %s";
            throw new IllegalStateException(message.formatted(algorithm), ex);
        }
    }

}