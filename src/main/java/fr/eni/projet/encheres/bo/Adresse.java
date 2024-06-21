package fr.eni.projet.encheres.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Adresse {
	private long id;
	
	@NotBlank(message = "Le champ 'rue' ne peut pas être vide.")
	@Size(max=120, message = "Le champ 'rue' doit contenir au maximum 120 caractères.")
    private String rue;
	
	 @NotBlank(message = "Le champ 'codePostal' ne peut pas être vide.")
	 @Pattern(regexp = "\\d{5}", message = "Le code postal doit être valide (ex: 12345).")
    private String codePostal;
	
    @NotBlank(message = "Le champ 'ville' ne peut pas être vide.")
    @Size(max = 50, message = "Le champ 'ville' doit contenir au maximum 50 caractères.")
    private String ville;
    
	public Adresse() {
	}

	public Adresse(String rue, String codePostal, String ville) {
		super();
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
	}

	public Adresse(long id, String rue, String codePostal, String ville) {
		super();
		this.id = id;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Adresse [id=");
		builder.append(id);
		builder.append(", rue=");
		builder.append(rue);
		builder.append(", codePostal=");
		builder.append(codePostal);
		builder.append(", ville=");
		builder.append(ville);
		builder.append("]");
		return builder.toString();
	}
}
