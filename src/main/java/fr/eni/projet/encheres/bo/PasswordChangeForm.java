package fr.eni.projet.encheres.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class PasswordChangeForm {
	
	@NotBlank(message = "L'ancien mot de passe est requis")
	private String oldPassword;

	@NotBlank(message = "Le nouveau mot de passe est requis")
	@Pattern(regexp = "^.{6,}$", message = "Le mot de passe doit contenir au moins 6 caractères")
	private String newPassword;

	public PasswordChangeForm() {}
	
	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	

}
