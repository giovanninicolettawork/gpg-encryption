package project.gpg.encryption;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Logger;

public class LinuxCommandExecutor {

	private static Logger logger = Logger.getLogger(LinuxCommandExecutor.class.getSimpleName());

	private LinuxCommandExecutor() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Import a public key in the current keyring
	 * @param publicKey
	 * @return command execution result
	 */
	public static boolean importPublicKey(File publicKey) {
		return executeCommand(List.of("gpg", "--import", publicKey.getAbsolutePath()));

	}

	/**
	 * Remove a public key from the current keyring
	 * @param publicKey id
	 * @return command execution result
	 */
	public static void removePublicKey(String keyId) {
		executeCommand(List.of("gpg", "--delete-key", keyId));
	}

	
	/**
	 * Encrypt a file with gpg command
	 * @param file to be encrypted
	 * @param public key id
	 * @param encrypted file
	 */
	public static void encryptFileWithGPG(File toCrypt, String keyId, File cryptedFile) {
		executeCommand(List.of("gpg", "--trust-model", "classic", "--output", cryptedFile.getAbsolutePath(), "--encrypt",
				"--recipient", keyId, toCrypt.getAbsolutePath()));
	}

	private static boolean executeCommand(List<String> commandParams) {
		Process process = null;
		try {
			ProcessBuilder builder = new ProcessBuilder(commandParams);
			builder.redirectErrorStream(true);
			process = builder.start();

			try (InputStreamReader reader = new InputStreamReader(process.getInputStream());
					BufferedReader in = new BufferedReader(reader)) {
				String line;
				while ((line = in.readLine()) != null) {
					logger.info(line);
				}
			} finally {
				process.waitFor();
				process.destroy();
			}
			return getProcessExitCode(process) == 0;
		} catch (Exception e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
			return false;
		}

	}

	private static int getProcessExitCode(Process process) {
		try {
			return process == null? -1 : process.waitFor();
		} catch (InterruptedException e) {
			process.destroy();
			Thread.currentThread().interrupt();
			return -1;
		}
	}

}
