package application.srevice;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import application.connexion.connexion;
import application.dao.IDaoEntreprise;
import application.entity.Entreprise;

public class EntrepriseService implements IDaoEntreprise {

	public EntrepriseService() {
	}

	

	@Override
	public Entreprise listEntreprise() {
		try {
			Entreprise ent = new Entreprise("dummyent");
			Statement st = connexion.getCon().createStatement();
			ResultSet res = st.executeQuery("SELECT * FROM entreprise ");
			while (res.next()) {
				ent = new Entreprise(res.getInt("idEntrepise"), res.getString("nom"));
			}
			System.out.println("entreprises listed successfully");
			return ent;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error in listing entreprise items occured !!");
			return null;
		}
	}

	
	
	public boolean createEntreprise(Entreprise e) {
		try {
			Statement st = connexion.getCon().createStatement();
			int res = st.executeUpdate("INSERT INTO entreprise(nom)VALUES('" +e.getNom()+"')");
			if (res != 0) {
				return true;
			}
		} catch (SQLException exp) {
			exp.printStackTrace();
		}
		return false;
	}

	
	@Override
	public boolean updateEntreprise(Entreprise e) {
		try {
			PreparedStatement pst = connexion.getCon()
					.prepareStatement("UPDATE entreprise SET nom=? WHERE idEntrepise=?");
			pst.setString(1, e.getNom());
			pst.setLong(2, e.getMat());
			int res = pst.executeUpdate();
			if (res != 0) {
				System.out.println("entreprise Updated successfully");
				return true;
			}
		} catch (SQLException exp) {
			// TODO Auto-generated catch block
			exp.printStackTrace();
		}
		return false;
	}
	
	
	public boolean deleteEntreprise(Entreprise e) {
		try {
			PreparedStatement pst = connexion.getCon().prepareStatement("DELETE  FROM entreprise WHERE idEntrepise=?");
			pst.setLong(1, e.getMat());
			int res = pst.executeUpdate();
			if (res != 0) {
				System.out.println("entreprise deleted successfully");
				return true;
			}
		} catch (SQLException exp) {
			// TODO Auto-generated catch block
			exp.printStackTrace();
		}
		return false;
	}

	

	@Override
	public Entreprise findEntrepriseById(int id) {
		try {
			Statement st = connexion.getCon().createStatement();
			ResultSet res = st.executeQuery("SELECT * FROM entreprise WHERE idEntrepise=" + id);
			if (res.next()) {
				System.out.println("entreprise finded successfully");
				return new Entreprise(res.getInt("idEntrepise"), res.getString("nom"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Entreprise> findAll() {
		try {
			List<Entreprise> listEnt = new ArrayList<Entreprise>();
			Statement st = connexion.getCon().createStatement();
			ResultSet res = st.executeQuery("SELECT * FROM entreprise ");

			while (res.next()) {
				listEnt.add(new Entreprise(res.getInt("idEntrepise"), res.getString("nom")));
			}
			System.out.println("entreprises listed successfully");
			return listEnt;

		} catch (SQLException e) {

			e.printStackTrace();
			System.out.println("error in listing entreprise items occured !!");
			return null;
		}

	}



}
