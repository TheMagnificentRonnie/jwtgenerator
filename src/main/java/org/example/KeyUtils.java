package org.example;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class KeyUtils {
  public static RSAPublicKey convertToRSAPublicKey(String publicKeyStr) {
    try {
      // Remove the "-----BEGIN PUBLIC KEY-----" and "-----END PUBLIC KEY-----" markers
      publicKeyStr = publicKeyStr
        .replace("-----BEGIN PUBLIC KEY-----", "")
        .replace("-----END PUBLIC KEY-----", "")
        .replaceAll("\\s", ""); // Remove any whitespace characters

      // Decode the public key from base64
      byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyStr);

      // Create the PublicKey object
      X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKeyBytes);
      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      PublicKey publicKey = keyFactory.generatePublic(spec);

      // Convert the PublicKey to RSAPublicKey
      if (publicKey instanceof RSAPublicKey) {
        return (RSAPublicKey) publicKey;
      } else {
        throw new IllegalArgumentException("Provided key is not an RSA public key.");
      }
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      e.printStackTrace();
    }

    return null;
  }

  public static RSAPrivateKey convertToRSAPrivateKey(String privateKeyStr) {
    try {
      // Remove the "-----BEGIN PRIVATE KEY-----" and "-----END PRIVATE KEY-----" markers
      privateKeyStr = privateKeyStr
        .replace("-----BEGIN PRIVATE KEY-----", "")
        .replace("-----END PRIVATE KEY-----", "")
        .replaceAll("\\s", ""); // Remove any whitespace characters

      // Decode the private key from base64
      byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyStr);

      // Create the PrivateKey object
      PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privateKeyBytes);
      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      PrivateKey privateKey = keyFactory.generatePrivate(spec);

      // Convert the PrivateKey to RSAPrivateKey
      if (privateKey instanceof RSAPrivateKey) {
        return (RSAPrivateKey) privateKey;
      } else {
        throw new IllegalArgumentException("Provided key is not an RSA private key.");
      }
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      e.printStackTrace();
    }

    return null;
  }
}
