package RSA;

import java.util.Scanner;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        RSA rsa = new RSA();

        rsa.Keys();

        String message = Input();
        logger.info("Messaggio da cifrare: " + message);
        String encrypted = rsa.encrypt(message);
        logger.info("Messaggio cifrato: " + encrypted);

        String decrypted = rsa.decrypt(encrypted);
        logger.info("Messaggio decifrato: " + decrypted);
        logger.info("------------------------------------------------------------");

    }

    public static String Input() {
        String message = "";
        do {
            System.out.print("Inserire messaggio: ");
            message = scanner.nextLine();
            if (message.isEmpty()) {
                logger.error("Inserito messaggio vuoto");
            }
        } while (message.isEmpty());
        scanner.close();
        return message;
    }
}
