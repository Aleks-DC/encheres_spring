package fr.eni.projet.encheres.dal;

import java.util.List;

import fr.eni.projet.encheres.bo.Adresse;

public interface AdresseDAO {
	
	Adresse create(Adresse adresse); // Create
	
	Adresse findById(long noAdresse); // Read

	Adresse update(Adresse Adresse); // Update
	
	List<Adresse> findAll(); // Read

	void deleteById(int noAdresse); // Delete
}
