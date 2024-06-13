package fr.eni.projet.encheres.exception;

public class EmailDejaUtiliseException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmailDejaUtiliseException(String message) {
        super(message);
    }
}