package org.example;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

public class JWTCreationExample {

  public static void main(String[] args) throws Exception {
    // Read the PKCS8 private key from a string in PEM format
    String privateKeyPem = "<PKCS8 Private Key in PEM Format>";

    // Remove the header and footer lines from the PEM string
    privateKeyPem = privateKeyPem.replace("-----BEGIN PRIVATE KEY-----", "")
      .replace("-----END PRIVATE KEY-----", "")
      .replaceAll("\\s", ""); // Remove all whitespace characters

    // Base64 decode the private key content
    byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyPem);

    // Create a PKCS8EncodedKeySpec from the decoded bytes
    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);

    // Generate the private key object
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

    // Read the JSON payload from file
    String payloadFilePath = "<Path to JSON Payload File>";
    String payloadJson = new String(Files.readAllBytes(Paths.get(payloadFilePath)));

    // Set the expiration time to 1 hour from now
    Instant expirationTime = Instant.now().plus(1, ChronoUnit.HOURS);

    // Generate the JWT token
    String token = Jwts.builder()
      .setPayload(payloadJson)
      .signWith(privateKey, SignatureAlgorithm.RS512)
      .setExpiration(java.util.Date.from(expirationTime))
      .compact();

    System.out.println("Generated JWT token:");
    System.out.println(token);
  }
}
