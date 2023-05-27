package org.example;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Map;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class Main {

  public static void main(String[] args) {
    String privateKeyStr = "-----BEGIN PRIVATE KEY-----\n" +
      "MIIEwAIBADANBgkqhkiG9w0BAQEFAASCBKowggSmAgEAAoIBAQDpLtqxS7OrlD/d\n" +
      "T2tuz4+QNUh2OCa2Bat4bmpY+wL3FdkqIxXUCJX0tfKpCwBikKoQMzddt+ZmoZvj\n" +
      "zIuFv9eploqBJhoL+HYOMzuWCshACn33TZGvx9SYs3aK+vm2cvFRQ6cw5zZJC2v1\n" +
      "2DNM41hblm7c/DK8BaTkPq54hSEu1jOlwH562g10vcivbvjoojL9VSwPAAzt2Gup\n" +
      "IrxTbEUIaVq7iKQ5O2/MOjCcAwcyt8TurUHpZlAMBCUGbFFCzIqWfkMiwq/rFq42\n" +
      "wdGAEApy1TFkbwzhAkjHdLoC6CF3dFkLgJrkB7193wvyaU1gEKtCE5nt1LR/hq3h\n" +
      "quUtxqO3AgMBAAECggEBANX6C+7EA/TADrbcCT7fMuNnMb5iGovPuiDCWc6bUIZC\n" +
      "Q0yac45l7o1nZWzfzpOkIprJFNZoSgIF7NJmQeYTPCjAHwsSVraDYnn3Y4d1D3tM\n" +
      "5XjJcpX2bs1NactxMTLOWUl0JnkGwtbWp1Qq+DBnMw6ghc09lKTbHQvhxSKNL/0U\n" +
      "C+YmCYT5ODmxzLBwkzN5RhxQZNqol/4LYVdji9bS7N/UITw5E6LGDOo/hZHWqJsE\n" +
      "fgrJTPsuCyrYlwrNkgmV2KpRrGz5MpcRM7XHgnqVym+HyD/r9E7MEFdTLEaiiHcm\n" +
      "Ish1usJDEJMFIWkF+rnEoJkQHbqiKlQBcoqSbCmoMWECgYEA/4379mMPF0JJ/EER\n" +
      "4VH7/ZYxjdyphenx2VYCWY/uzT0KbCWQF8KXckuoFrHAIP3EuFn6JNoIbja0NbhI\n" +
      "HGrU29BZkATG8h/xjFy/zPBauxTQmM+yS2T37XtMoXNZNS/ubz2lJXMOapQQiXVR\n" +
      "l/tzzpyWaCe9j0NT7DAU0ZFmDbECgYEA6ZbjkcOs2jwHsOwwfamFm4VpUFxYtED7\n" +
      "9vKzq5d7+Ii1kPKHj5fDnYkZd+mNwNZ02O6OGxh40EDML+i6nOABPg/FmXeVCya9\n" +
      "Vump2Yqr2fAK3xm6QY5KxAjWWq2kVqmdRmICSL2Z9rBzpXmD5o06y9viOwd2bhBo\n" +
      "0wB02416GecCgYEA+S/ZoEa3UFazDeXlKXBn5r2tVEb2hj24NdRINkzC7h23K/z0\n" +
      "pDZ6tlhPbtGkJodMavZRk92GmvF8h2VJ62vAYxamPmhqFW5Qei12WL+FuSZywI7F\n" +
      "q/6oQkkYT9XKBrLWLGJPxlSKmiIGfgKHrUrjgXPutWEK1ccw7f10T2UXvgECgYEA\n" +
      "nXqLa58G7o4gBUgGnQFnwOSdjn7jkoppFCClvp4/BtxrxA+uEsGXMKLYV75OQd6T\n" +
      "IhkaFuxVrtiwj/APt2lRjRym9ALpqX3xkiGvz6ismR46xhQbPM0IXMc0dCeyrnZl\n" +
      "QKkcrxucK/Lj1IBqy0kVhZB1IaSzVBqeAPrCza3AzqsCgYEAvSiEjDvGLIlqoSvK\n" +
      "MHEVe8PBGOZYLcAdq4YiOIBgddoYyRsq5bzHtTQFgYQVK99Cnxo+PQAvzGb+dpjN\n" +
      "/LIEAS2LuuWHGtOrZlwef8ZpCQgrtmp/phXfVi6llcZx4mMm7zYmGhh2AsA9yEQc\n" +
      "acgc4kgDThAjD7VlXad9UHpNMO8=\n" +
      "-----END PRIVATE KEY-----";

    String payload = "{\n" +
      "  \"sub\": \"1234567890\",\n" +
      "  \"name\": \"John Doe\",\n" +
      "  \"admin\": true,\n" +
      "  \"iat\": 1685179575,\n" +
      "  \"exp\": 1685183175\n" +
      "}";

    String headers = "{\n" +
      "  \"typ\": \"JWT\",\n" +
      "  \"alg\": \"RS512\"\n" +
      "}";

    privateKeyStr = formatPrivateKey(privateKeyStr);

    String jwtToken = generateJWT(privateKeyStr, payload, headers);
    System.out.println("JWT Token: " + jwtToken);
  }

  public static String formatPrivateKey(String privateKey) {
    // Remove first and last lines
    privateKey = privateKey.replaceAll("-----BEGIN PRIVATE KEY-----\n", "");
    privateKey = privateKey.replaceAll("-----END PRIVATE KEY-----", "");

    // Remove newlines
    privateKey = privateKey.replaceAll("\n", "");

    return privateKey;
  }

  private static String generateJWT(String privateKeyStr, String payload, String headers) {
    try {
      // Decode the private key from base64
      byte[] privateKeyBytes = Base64.getMimeDecoder().decode(privateKeyStr);

      // Create the PrivateKey object
      PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privateKeyBytes);
      KeyFactory kf = KeyFactory.getInstance("RSA");
      PrivateKey privateKey = kf.generatePrivate(spec);

      Map<String, Object> Map = JsonUtils.jsonToMap(headers);


      // Generate the JWT token
      String jwtToken = Jwts.builder()
        .setHeader(Map)
        .setPayload(payload)
        .signWith(privateKey, SignatureAlgorithm.RS512)
        .compact();

      return jwtToken;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }
}
