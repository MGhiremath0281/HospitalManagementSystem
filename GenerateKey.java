import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Base64;
import java.security.Key;

public class GenerateKey {
    public static void main(String[] args) {
        // Generate a new secret key for HS512
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());

        // Print the Base64-encoded key
        System.out.println(base64Key);
    }
}
