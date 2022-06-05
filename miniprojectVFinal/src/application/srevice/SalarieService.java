package application.srevice;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import application.connexion.connexion;
import application.entity.Employee;
import application.entity.Salarie;
import application.entity.Vendeur;

public class SalarieService {

	public List<Salarie> salBetween(double min, double max) {
		List<Salarie> Lsalarie = new ArrayList<Salarie>();
		try {
			Statement st = connexion.getCon().createStatement();
			ResultSet res = st.executeQuery("SELECT * FROM salarie ");
			while (res.next()) {
				if (res.getString("categorie").equals("employe")) {
					EmployeeService es = new EmployeeService();
					Employee emp = es.findEmployeById(res.getInt("matricule"));
					double sal = res.getDouble("salaire") + emp.getHsupp() * emp.getPHsupp();
					if (sal >= min && sal <= max) {
						Lsalarie.add(new Salarie(res.getInt("matricule"), res.getString("nom"), res.getString("email"),
								res.getString("categorie"), res.getDouble("anneRecruit"),
								res.getDouble("salaire") + emp.getHsupp() * emp.getPHsupp(),
								res.getInt("idEntreprise")));
					}
				} else if (res.getString("categorie").equals("vendeur")) {
					VendeurService vs = new VendeurService();
					Vendeur vend = vs.findVendeurById(res.getInt("matricule"));
					double sal = res.getDouble("salaire") + vend.getVente() * vend.getPoucentage();
					if (sal >= min && sal <= max) {
						Lsalarie.add(new Salarie(res.getInt("matricule"), res.getString("nom"), res.getString("email"),
								res.getString("categorie"), res.getDouble("anneRecruit"),
								res.getDouble("salaire") + vend.getVente() * vend.getPoucentage(),
								res.getInt("idEntreprise")));
					}
				}
			}
			System.out.println("success");
			return Lsalarie;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Salarie> anciennete() {
		List<Salarie> Lsalarie = new ArrayList<Salarie>();
		try {
			Statement st = connexion.getCon().createStatement();
			ResultSet res = st.executeQuery("SELECT * FROM salarie ORDER BY anneRecruit ASC");
			while (res.next()) {
				if (res.getString("categorie").equals("employe")) {
					EmployeeService es = new EmployeeService();
					Employee emp = es.findEmployeById(res.getInt("matricule"));
					Lsalarie.add(new Salarie(res.getInt("matricule"), res.getString("nom"), res.getString("email"),
							res.getString("categorie"), res.getDouble("anneRecruit"),
							res.getDouble("salaire") + emp.getHsupp() * emp.getPHsupp(), res.getInt("idEntreprise")));
				} else if (res.getString("categorie").equals("vendeur")) {
					VendeurService vs = new VendeurService();
					Vendeur vend = vs.findVendeurById(res.getInt("matricule"));
					Lsalarie.add(new Salarie(res.getInt("matricule"), res.getString("nom"), res.getString("email"),
							res.getString("categorie"), res.getDouble("anneRecruit"),
							res.getDouble("salaire") + vend.getVente() * vend.getPoucentage(),
							res.getInt("idEntreprise")));
				}
			}
			System.out.println("success");
			return Lsalarie;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public double minimumSalaire() {
		try {
			EmployeeService es = new EmployeeService();
			VendeurService vs = new VendeurService();
			Statement st = connexion.getCon().createStatement();
			ResultSet res = st.executeQuery("SELECT * FROM salarie ");
			double minSal = 0;
			res.next();
			if (res.getString("categorie").equals("employe")) {
				Employee emp = es.findEmployeById(res.getInt("matricule"));
				minSal = res.getDouble("salaire") + emp.getHsupp() * emp.getPHsupp();

			} else if (res.getString("categorie").equals("vendeur")) {
				Vendeur vend = vs.findVendeurById(res.getInt("matricule"));
				minSal = res.getDouble("salaire") + vend.getVente() * vend.getPoucentage();
			}

			while (res.next()) {
				if (res.getString("categorie").equals("employe")) {
					Employee empp = es.findEmployeById(res.getInt("matricule"));
					double nextMinSal = res.getDouble("salaire") + empp.getHsupp() * empp.getPHsupp();
					if (nextMinSal < minSal) {
						minSal = nextMinSal;
					}
				} else if (res.getString("categorie").equals("vendeur")) {
					Vendeur vend = vs.findVendeurById(res.getInt("matricule"));
					double nextMinSal = res.getDouble("salaire") + vend.getVente() * vend.getPoucentage();
					if (nextMinSal < minSal) {
						minSal = nextMinSal;
					}
				}
			}
			System.out.println("success");
			return minSal;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public List<Salarie> salaireFaible() {
		double minSal = minimumSalaire();
		List<Salarie> Lsalarie = new ArrayList<Salarie>();

		try {
			Statement st = connexion.getCon().createStatement();
			ResultSet res = st.executeQuery("SELECT * FROM salarie ");
			while (res.next()) {
				if (res.getString("categorie").equals("employe")) {
					EmployeeService es = new EmployeeService();
					Employee emp = es.findEmployeById(res.getInt("matricule"));
					double sal = res.getDouble("salaire") + emp.getHsupp() * emp.getPHsupp();
					if (sal == minSal) {
						Lsalarie.add(new Salarie(res.getInt("matricule"), res.getString("nom"), res.getString("email"),
								res.getString("categorie"), res.getDouble("anneRecruit"),
								res.getDouble("salaire") + emp.getHsupp() * emp.getPHsupp(),
								res.getInt("idEntreprise")));
					}
				} else if (res.getString("categorie").equals("vendeur")) {
					VendeurService vs = new VendeurService();
					Vendeur vend = vs.findVendeurById(res.getInt("matricule"));
					double sal = res.getDouble("salaire") + vend.getVente() * vend.getPoucentage();
					if (sal == minSal) {
						Lsalarie.add(new Salarie(res.getInt("matricule"), res.getString("nom"), res.getString("email"),
								res.getString("categorie"), res.getDouble("anneRecruit"),
								res.getDouble("salaire") + vend.getVente() * vend.getPoucentage(),
								res.getInt("idEntreprise")));
					}
				}
			}
			System.out.println("success");
			return Lsalarie;
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;
	}

}
