package org.example;import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

public class PKCS12Utils {
  public static KeyPair convertPKCS12ToKeyPair(String pkcs12String) throws Exception {
    BouncyCastleProvider provider = new BouncyCastleProvider();
    byte[] pkcs12Data = pkcs12String.getBytes(StandardCharsets.UTF_8);
    ByteArrayInputStream inputStream = new ByteArrayInputStream(pkcs12Data);

    java.security.KeyStore keystore = java.security.KeyStore.getInstance("PKCS12", provider);
    keystore.load(inputStream, null);

    String alias = keystore.aliases().nextElement();

    PrivateKey privateKey = (PrivateKey) keystore.getKey(alias, null);
    X509Certificate certificate = (X509Certificate) keystore.getCertificate(alias);

    // Convert private key to PKCS#8 format if it's in PKCS#1 format
    if ("PKCS#1".equals(privateKey.getFormat())) {
      PEMParser pemParser = new PEMParser(new StringReader(pkcs12String));
      Object object = pemParser.readObject();

      if (object instanceof PrivateKeyInfo) {
        PrivateKeyInfo privateKeyInfo = (PrivateKeyInfo) object;
        ASN1Primitive privateKeyPrimitive = privateKeyInfo.parsePrivateKey().toASN1Primitive();
        byte[] privateKeyBytes = privateKeyPrimitive.getEncoded();
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA", provider);
        privateKey = keyFactory.generatePrivate(privateKeySpec);
      }
    }

    PublicKey publicKey = certificate.getPublicKey();

    return new KeyPair(publicKey, privateKey);
  }

  public static void main(String[] args) {
    String pkcs12String = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDMTn9dwKUwLFOY\n" +
      "/P94OuXqk+r9e6zkjPPLz7C3R5ZiOMUxbYI2/Sggtl/S143cU2A2nfpLv4pLkzWB\n" +
      "GzlBUE/2b7SDCwDmo1Z7NZW3lCdOLwOUDGB5O/I39Dx6oXY8ewB30UCP+sJckAni\n" +
      "O2uluRi9hLtjNLmaeevLkjpP4tjLaFHH7mC/uNQSBoxclgHyIOJM/Qb8Vg+CrAqj\n" +
      "1msMdgxWlFl138V099LzmPASgNbsbTHzAAACa0oX8m1BDAH6oxVXkmn/CbTo3W5e\n" +
      "NIRK7krUge2MFBCDM0ysZbM3r/aRYYY8rvyVkthIfEeEPpavpqF99XlXHOGafI5C\n" +
      "6QH9/hyrAgMBAAECggEAEn5+mBGCwogYry4jwBHw0xm9uWFzJqM9xHLSB91ClzSV\n" +
      "YSKNGYY0FjX8GZ1qmYmwmXEWgbnefnXeb4fz2F45RtEY0Y3y8G3Tu8Z/fYU7Q6AM\n" +
      "3l96ci8Df54rxVwNVG87/RyjkAT+iBt/0YtYdMF7JFi6D8JrlLi5qA1eGvIpdswa\n" +
      "wnVnKi6cx5R5/9l6PO9nA0DS7VtjXq1JO6QdjOTE0asB0AQlCoJWfF/dSyFtvqDq\n" +
      "l7X1dXRZgT4r8koIrY+K84ChRVe1EPjiN9qH0x96/Kzn7U7Gk6KvBI07e2hAUBeh\n" +
      "isx31wXqrYSmCrBqDeyXpNBIgrvzcHf7nagkFpLPfQKBgQDdwvB/VP7KkgZmFg8k\n" +
      "qcZfSeJcQS0Sw7nsTHCg9upF+r/txa4+GiTJ9zPWxChG76QeCqTMCEgcChXfH/aP\n" +
      "7xiKRv6Dtbt5qplk9Z2n3+F8roQVHKhDhffOgedSqe5zjr0WSCaM9YD7Zbj2l+Pq\n" +
      "jEduiMhSGbx65UZDZbkffQiiBQKBgQDr2ajzqqUGD6SZThRVuVdirzk1+acA16/R\n" +
      "tNQZe9MICfpJmNmakon6kF66Br86DCmI6L4Op3zfk/iCdVFMhS1xJmM71i05Vjek\n" +
      "1RAh4u9e6sUYklgQSeyPnGIGVpNqcSLCZn9NqjiVTJndq8HhvXY3wG0RtMIysQIT\n" +
      "IIPOGuiS7wKBgQClxFt8Ay/2gpNP6jtln5cOka8oYPvtY+tt7HHgkNHsf5TKixNF\n" +
      "jstjQWs5piWpaOcYIcKwVZx5gTx6Lm1jiEmiy5DjKYy7VMAXTNaNr3fheJ55HZPV\n" +
      "7aWgc1BUZXw+WzuTFVnj0nyY76AEjOOs5zFaOdrsLBrNDuen57krBY7XdQKBgH4o\n" +
      "P6eXcA59753Rli8EpSm0cPXeROa+eN3DzHCVO5zhOXCEwmFcYVIWR5VaZo+2UcFa\n" +
      "Pc+456Lwk82003gLBPOxhQNM3YP+CmWxUp/NtB0hl6w9G74azN9E12pshQ461k0G\n" +
      "ZrI0i2/RObzJUzxdDA9mbvE5r2dOJ73zdo2Y9y8hAoGAOSmWqZb0Tb+5T+e5214h\n" +
      "mjAJoEExzpBtyyY27+7V+c6EKCWK5Wro9o0WfV6TQ7XND/b/tlmnSdvE8ykHB7Nt\n" +
      "o+6yvVa53fvlaGy65xr4t0/F/Iq2XYEU3SrvVCYGx6GgJDZ0d+HIoCGrj+g1zxOk\n" +
      "s/Y5kN1UXI/X1/mh/QA61zw=";



     pkcs12String = pkcs12String.replaceAll("\\r?\\n", "");




    try {
      KeyPair keyPair = convertPKCS12ToKeyPair(pkcs12String);
      PrivateKey privateKey = keyPair.getPrivate();
      PublicKey publicKey = keyPair.getPublic();

      System.out.println("Private Key: " + privateKey);
      System.out.println("Public Key: " + publicKey);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
