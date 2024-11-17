package RSA;

import java.math.BigInteger;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RSA {
    private static final Logger logger = LogManager.getLogger(RSA.class);
    private BigInteger p, q, n, d, e;
    private static int BIT = 1024;

    

    public void Keys() {
        
        Random ran = new Random();

        p = BigInteger.probablePrime(BIT, ran);
        logger.debug("p=" + p);

        q = BigInteger.probablePrime(BIT, ran);

        while (p.equals(q)){
           q = BigInteger.probablePrime(BIT, ran); 
           logger.debug("p=q rigenerazione");
        }
        logger.debug("q =" + q);

        n = p.multiply(q);
        logger.debug("n=" + n);

        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        logger.debug("phi=" + phi);

        do {
            e = BigInteger.probablePrime(BIT, ran);
        } while (e.compareTo(phi) >= 0 || !e.gcd(phi).equals(BigInteger.ONE));
        logger.debug("e=" + e);

        d = e.modInverse(phi);
        logger.debug("d=" + d);
        
        logger.info("Chiave pubblica: " + n + "; " + e);
        logger.info("Chiave privata: " + n + "; " + d);
    }

    public String encrypt(String message) {
        StringBuilder encrypted = new StringBuilder();

        for (char c : message.toCharArray())
        {
            BigInteger m = BigInteger.valueOf((int) c);
            BigInteger cryp = m.modPow(e, n);
            encrypted.append(cryp.toString());
            encrypted.append(";");
            
            logger.debug(c + " cifrato in " + cryp);
        }

        if (encrypted.length() > 0) {
            encrypted.setLength(encrypted.length() - 1);
            logger.debug("Tolto ultimo ;");
        }

        return encrypted.toString();
    }

    public String decrypt(String encrypted) {

        if (encrypted == null || encrypted.isEmpty()) {
            logger.error("Stringa vuota");
            return "";
        }

        StringBuilder decrypted = new StringBuilder();

        String[] numbers = encrypted.split(";");

        for (String number : numbers) {

            BigInteger c = new BigInteger(number.trim());
            BigInteger m = c.modPow(d, n);

            decrypted.append((char) m.intValue());
            logger.debug(c + " decifrato in " + m);
        }

        return decrypted.toString();
    }
}


