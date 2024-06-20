package fr.eni.projet.encheres.bll;

import java.util.List;

import fr.eni.projet.encheres.bo.Enchere;

public interface EnchereService {
	
	List<Enchere> consulterEnchere();
	
	void creerEnchere();
	
	
}
