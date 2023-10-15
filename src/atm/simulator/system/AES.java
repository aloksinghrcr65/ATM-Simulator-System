
package atm.simulator.system;

/**
 *
 * @author aloks
 */
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

//Advanced Encrypted Standard
public class AES {

    private static final String ALGORITHM = "AES";
    private static final String KEY = "mySecretKey12345"; // Replace this with your own secret key

    public static String encrypt(String value) throws Exception {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedByteValue = cipher.doFinal(value.getBytes("utf-8"));
        return Base64.getEncoder().encodeToString(encryptedByteValue);
    }

    public static String decrypt(String value) throws Exception {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedByteValue = cipher.doFinal(Base64.getDecoder().decode(value));
        return new String(decryptedByteValue, "utf-8");
    }

    private static Key generateKey() throws Exception {
        return new SecretKeySpec(KEY.getBytes(), ALGORITHM);
    }
}

