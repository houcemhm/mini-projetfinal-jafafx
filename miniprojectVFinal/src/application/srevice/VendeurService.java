package application.srevice;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import application.connexion.connexion;
import application.dao.IDaoVendeur;
import application.entity.Employee;
import application.entity.Vendeur;

public class VendeurService implements IDaoVendeur {

	@Override
	public boolean createVendeur(Vendeur v) {
		try {
			if(v.getRecruitDate()<2005)
				v.setSalaireFix(400);
			else
				v.setSalaireFix(280);
			
			Statement st =connexion.getCon().createStatement();
			
			int res=st.executeUpdate("INSERT INTO salarie VALUES ('"+v.getMatricule()+"','"+v.getNom()+"','"+v.getEmail()+"','"+v.getRecruitDate()+"','"+v.getSalaireFix()+"','"+v.getIdEntreprise()+"','"+v.getCat()+"')");
			int res1=st.executeUpdate("INSERT INTO vendeur VALUES('"+v.getMatricule()+"','"+v.getVente()+"','"+v.getPoucentage()+"')");


			if(res!=0 && res1!=0) {
				System.out.println("vendeur created successfully");
				return true;
			}
		} catch (SQLException exp) {
			exp.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteVendeur(Vendeur v) {
		try {
			PreparedStatement pst=connexion.getCon().prepareStatement("DELETE  FROM salarie WHERE matricule=?");
			pst.setLong(1, v.getMatricule());
			
			PreparedStatement pst1=connexion.getCon().prepareStatement("DELETE  FROM vendeur WHERE matricule=?");
			pst1.setLong(1, v.getMatricule());
			
			int res=pst.executeUpdate();
			int res1=pst1.executeUpdate();
			
			if(res != 0 && res1 != 0) {
				System.out.println("vendeur deleted successfully");
				return true;
			}
		} catch (SQLException exp) {
			exp.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateVendeur(Vendeur v) {
		try {
			PreparedStatement pst=connexion.getCon().prepareStatement("UPDATE salarie SET nom=?, email=?, categorie=?, anneRecruit=?, salaire=?,idEntreprise=? WHERE matricule=?");
			pst.setString(1,v.getNom());
			pst.setString(2, v.getEmail());
			pst.setString(3, v.getCat());
			pst.setDouble(4, v.getRecruitDate());
			pst.setDouble(5, v.getSalaireFix());
			pst.setInt(6,v.getIdEntreprise());
			pst.setInt(7, v.getMatricule());
			
			PreparedStatement pst1=connexion.getCon().prepareStatement("UPDATE vendeur SET Vente=?, Pourcentage=? WHERE matricule=?");
			pst1.setDouble(1, v.getVente());
			pst1.setDouble(2,v.getPoucentage());
			pst1.setInt(3, v.getMatricule());
			
			int res=pst.executeUpdate();
			int res1=pst1.executeUpdate();
			
			if(res!=0 && res1!=0) {
				System.out.println("vendeur Updated successfully");
				return true;
			}
		} catch (SQLException exp) {
			// TODO Auto-generated catch block
			exp.printStackTrace();
		}
		return false;
	}

	@Override
	public Vendeur findVendeurById(int mat) {
		try {
			Statement st =connexion.getCon().createStatement();
			Statement st1 =connexion.getCon().createStatement();

			ResultSet res=st.executeQuery("SELECT * FROM salarie WHERE matricule="+mat);
			ResultSet res1=st1.executeQuery("SELECT * FROM vendeur WHERE matricule="+mat);

			if(res.next() && res1.next()) {
				System.out.println("employee finded successfully");
				return new Vendeur(res.getInt("matricule"),res.getString("nom"),res.getString("email"),res.getString("categorie"),res.getDouble("anneRecruit"),res.getInt("idEntreprise"),res.getDouble("salaire")+res1.getDouble("Vente")*res1.getDouble("Pourcentage"),res1.getDouble("Vente"),res1.getDouble("Pourcentage"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Vendeur> findAll() {
		try {
			List<Vendeur> listEmp=new ArrayList<Vendeur>();
			Statement st =connexion.getCon().createStatement();
			ResultSet res=st.executeQuery("SELECT * FROM vendeur ");

			while(res.next()) {
				listEmp.add(findVendeurById(res.getInt("matricule")));
			}
			System.out.println("listed successfully");
			return listEmp;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
	}
	}
	
	public List<Vendeur> maxTauxVente(){
		try {
			List<Vendeur> listeVend=new ArrayList<Vendeur>();
			Statement st =connexion.getCon().createStatement();
			ResultSet res=st.executeQuery("SELECT * FROM vendeur WHERE Vente=(SELECT MAX(Vente) FROM vendeur)");
			while(res.next()) {
				listeVend.add(findVendeurById(res.getInt("matricule")));
			}
			System.out.println("vendeur max taux vente listed successfully");
			return listeVend;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error in listing entreprise items occured !!");
			return null;
	}
	}

}
