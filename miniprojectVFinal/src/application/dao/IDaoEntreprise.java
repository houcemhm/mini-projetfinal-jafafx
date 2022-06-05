package application.dao;

import java.util.List;

import application.entity.Entreprise;


public interface IDaoEntreprise {
	boolean createEntreprise(Entreprise e);
boolean deleteEntreprise(Entreprise e );
boolean updateEntreprise(Entreprise e);
Entreprise findEntrepriseById(int id);
List<Entreprise > findAll();
Entreprise listEntreprise();
}
