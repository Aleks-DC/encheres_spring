package fr.eni.projet.encheres.exception;

public class UtilisateurDejaExistantException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UtilisateurDejaExistantException(String message) {
        super(message);
    }
}