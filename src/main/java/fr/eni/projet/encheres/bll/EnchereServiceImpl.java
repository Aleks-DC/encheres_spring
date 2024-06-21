package fr.eni.projet.encheres.bll;

import java.util.List;

import fr.eni.projet.encheres.bo.Enchere;

public class EnchereServiceImpl implements EnchereService {

	@Override
	public void createEnchere(long noArticle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Enchere> getAllEncheres() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Enchere> findByIdAcquereur(String nomAcquereur) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Enchere> findByIdArticle(long noArticle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateEnchere(long noArticle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteEnchere(long noArticle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Enchere> getEncheresOuvertes(long noArticle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Enchere> getMesEncheresEnCours(String pseudoAcquereur) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Enchere> getMesEncheresRemportees(String pseudoAcquereur) {
		// TODO Auto-generated method stub
		return null;
	}

}
