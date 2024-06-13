package fr.eni.projet.encheres.exception;

public class PseudoDejaUtiliseException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PseudoDejaUtiliseException(String message) {
        super(message);
    }
}