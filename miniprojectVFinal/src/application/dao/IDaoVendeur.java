package application.dao;

import java.util.List;

import application.entity.Employee;
import application.entity.Vendeur;

public interface IDaoVendeur {
	boolean createVendeur(Vendeur v);
	boolean deleteVendeur(Vendeur v );
	boolean updateVendeur(Vendeur v);
	Vendeur findVendeurById(int matricule);
	List<Vendeur > findAll();
}
