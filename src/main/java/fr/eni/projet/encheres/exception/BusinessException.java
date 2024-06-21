package fr.eni.projet.encheres.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

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

	public boolean containsCode(String code) {
		return clefsExternalisations != null && clefsExternalisations.contains(code);
	}

}
