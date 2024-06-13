package fr.eni.projet.encheres.bll;

import java.util.List;

import fr.eni.projet.encheres.bo.Enchere;

public abstract class EnchereService {
	
	abstract List<Enchere> consulterEnchere();
	
	abstract void creerEnchere();
	
	
}
