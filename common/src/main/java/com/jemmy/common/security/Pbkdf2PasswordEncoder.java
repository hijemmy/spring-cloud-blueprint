package com.jemmy.common.security;

import org.bouncycastle.util.encoders.Hex;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.GeneralSecurityException;

public class Pbkdf2PasswordEncoder implements PasswordEncoder {
    private final int hashWidth;
    private final int iterations;
    private String algorithm = org.springframework.security.crypto.password.Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA1.name();
    private final BytesKeyGenerator saltGenerator;

    public Pbkdf2PasswordEncoder(int hashWidth, int iterations,int keyLength) {
        this.hashWidth = hashWidth;
        this.iterations = iterations;
        saltGenerator=KeyGenerators.secureRandom(keyLength);
    }


    public String generateSalt(){
        return Hex.toHexString(saltGenerator.generateKey());
    }

    private byte[] encode(CharSequence rawPassword, byte[] salt) {
        try {
            PBEKeySpec spec = new PBEKeySpec(rawPassword.toString().toCharArray(),
                    salt, this.iterations, this.hashWidth);
            SecretKeyFactory skf = SecretKeyFactory.getInstance(this.algorithm);
            return skf.generateSecret(spec).getEncoded();
        }
        catch (GeneralSecurityException e) {
            throw new IllegalStateException("Could not create hash", e);
        }
    }


    @Override
    public String encodePassword(String rawPass, Object salt) {
        byte[] newPass=null==salt?rawPass.getBytes():encode(rawPass,salt.toString().getBytes());
        return Hex.toHexString(newPass);
    }

    @Override
    public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
        String userPass=encodePassword(rawPass,salt);
        return userPass.equals(encPass);
    }
}
