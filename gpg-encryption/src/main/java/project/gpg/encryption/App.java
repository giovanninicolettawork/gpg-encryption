package project.gpg.encryption;

import java.io.File;
import java.util.Scanner;
import java.util.logging.Logger;


public class App {

	private static Logger logger = Logger.getLogger(App.class.getSimpleName());

	public static void main(String[] args) {

		System.setProperty("java.util.logging.SimpleFormatter.format", "%4$s: %5$s %n");

		logger.info("Avvio Task");

		try (Scanner scan = new Scanner(System.in)) {
			String toEncryptFilePath = getInputFromUser("Incolla il path del file da criptare e premi invio", scan);
			String publicKeyFilePath = getInputFromUser("Incolla il path del della chiave pubblica e premi invio",
					scan);

			File publicKey = new File(publicKeyFilePath);
			File toEncrypt = new File(toEncryptFilePath);

			publicKeyImport(scan, publicKey);
			String publicKeyId = getInputFromUser("Incolla il valore del'ID della chiave e premi invio", scan);

			File out = new File(toEncrypt.getAbsolutePath().concat(".gpg"));
			logger.info("Avvio criptazione del file " + out.getName());
			LinuxCommandExecutor.encryptFileWithGPG(toEncrypt, publicKeyId, out);
			
			logger.info("Avvio rimozione chiave pubblica");
			LinuxCommandExecutor.removePublicKey(publicKeyId);
			
		} catch (Exception e) {
			logger.info(String.format("Errore inatteso %s", e.getMessage()));
		}
	}


	private static void publicKeyImport(Scanner scan, File publicKey) {
		logger.info("Avvio import chiave pubblica");
		boolean importPublicKeyCommandResult = LinuxCommandExecutor.importPublicKey(publicKey);
		if (!importPublicKeyCommandResult) {
			logger.info("Import chiave pubblica fallito");
			closeConsole(scan);
		}
	}

	private static void closeConsole(Scanner scan) {
		logger.info("Premi invio per chiudere...");
		scan.nextLine();
		System.exit(0);
	}

	/**
	 * Allows the user to input into console
	 * 
	 * @return the string entered by the user
	 */
	private static String getInputFromUser(String message, Scanner scan) {
		logger.info(message);
		return scan.next();
	}

}
