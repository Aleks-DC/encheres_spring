package fr.eni.projet.encheres.exception;

import java.util.ArrayList;
import java.util.List;

public class BusinessException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructeur par défaut.
     */
    public BusinessException() {
        super();
    }

    /**
     * Constructeur avec un message d'erreur.
     *
     * @param message Le message d'erreur.
     */
    public BusinessException(String message) {
        super(message);
    }

    /**
     * Constructeur avec un message d'erreur et la cause de l'exception.
     *
     * @param message Le message d'erreur.
     * @param cause   La cause de l'exception.
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructeur avec la cause de l'exception.
     *
     * @param cause La cause de l'exception.
     */
    public BusinessException(Throwable cause) {
        super(cause);
    }

	private List<String> clefsExternalisations;


	public List<String> getClefsExternalisations() {
		return clefsExternalisations;
	}

	public void add(String clef) {
		if (clefsExternalisations == null) {
			clefsExternalisations = new ArrayList<>();
		}
		clefsExternalisations.add(clef);
	}

}
