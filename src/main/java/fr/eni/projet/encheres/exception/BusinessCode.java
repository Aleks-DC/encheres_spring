package fr.eni.projet.encheres.exception;

public class BusinessCode {
	// Clefs de validation des BO
	public static final String VALIDATION_UTILISATEUR_NULL = "validation.utilisateur.null";
	public static final String VALIDATION_UTILISATEUR_PSEUDO_BLANK= "validation.utilisateur.pseudo.blank";
	public static final String VALIDATION_UTILISATEUR_PSEUDO_LENGTH= "validation.utilisateur.pseudo.length";
	public static final String VALIDATION_UTILISATEUR_NOM_BLANK = "validation.utilisateur.nom.blank";
	public static final String VALIDATION_UTILISATEUR_NOM_LENGTH = "validation.utilisateur.nom.length";
	public static final String VALIDATION_UTILISATEUR_PRENOM_BLANK = "validation.utilisateur.prenom.blank";
	public static final String VALIDATION_UTILISATEUR_PRENOM_LENGTH = "validation.utilisateur.prenom.length";
	public static final String VALIDATION_UTILISATEUR_EMAIL_BLANK = "validation.utilisateur.email.blank";
	public static final String VALIDATION_UTILISATEUR_EMAIL_PATTERN = "validation.utilisateur.email.pattern";
	public static final String VALIDATION_UTILISATEUR_COURS_EMPTY = "validation.utilisateur.cours.empty";
	public static final String VALIDATION_UTILISATEUR_COURS_ID_INCONNU = "validation.utilisateur.cours.id.inconnu";
	public static final String VALIDATION_UTILISATEUR_UNIQUE_EMAIL = "validation.utilisateur.unique.email";
	public static final String VALIDATION_UTILISATEUR_UNIQUE_PSEUDO= "validation.utilisateur.unique.pseudo";
	public static final String VALIDATION_UTILISATEUR_UNIQUE_ADRESSE= "validation.utilisateur.unique.adresse";

	
	public static final String BLL_UTILISATEUR_UPDATE_ERREUR = "bll.utilisateur.update.erreur";
}
