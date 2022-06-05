package application.srevice;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import application.connexion.connexion;
import application.dao.IDaoEmploye;
import application.entity.Employee;
import application.entity.Entreprise;
import application.entity.Vendeur;

public class EmployeeService implements IDaoEmploye{
	@Override
	public List<Employee> findAll() {
		try {
			List<Employee> listEmp=new ArrayList<Employee>();
			Statement st =connexion.getCon().createStatement();
			ResultSet res=st.executeQuery("SELECT * FROM employee ");

			while(res.next()) {
				listEmp.add(findEmployeById(res.getInt("matricule")));
			}
			System.out.println("listed successfully");
			return listEmp;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error in listing entreprise items occured !!");
			return null;
	}}
	

	@Override
	public boolean createEmploye(Employee employee) {
		try {
			if(employee.getRecruitDate()<2005)
				employee.setSalaireFix(400);
			else
				employee.setSalaireFix(280);	
			Statement st =connexion.getCon().createStatement();	
			int res=st.executeUpdate("INSERT INTO salarie VALUES ('"+employee.getMatricule()+"','"+employee.getNom()+"','"+employee.getEmail()+"','"+employee.getRecruitDate()+"','"+employee.getSalaireFix()+"','"+employee.getIdEntreprise()+"','"+employee.getCat()+"')");
			int res1=st.executeUpdate("INSERT INTO employee VALUES('"+employee.getMatricule()+"','"+employee.getHsupp()+"','"+employee.getPHsupp()+"')");
			if(res!=0 && res1!=0) {
				System.out.println("employe created successfully");
				return true;
			}
		} catch (SQLException exp) {
			// TODO Auto-generated catch block
			exp.printStackTrace();
		}
		return false;
	}

	

	@Override
	public boolean updateEmploye(Employee employee) {
		try {
			PreparedStatement pst=connexion.getCon().prepareStatement("UPDATE salarie SET nom=?, email=?, categorie=?, anneRecruit=?, salaire=?,idEntreprise=? WHERE matricule=?");
			pst.setString(1,employee.getNom());
			pst.setString(2, employee.getEmail());
			pst.setString(3,employee.getCat());
			pst.setDouble(4, employee.getRecruitDate());
			pst.setDouble(5, employee.getSalaireFix());
			pst.setInt(6,employee.getIdEntreprise());
			pst.setInt(7, employee.getMatricule());
			
			PreparedStatement pst1=connexion.getCon().prepareStatement("UPDATE employee SET HSupp=?, PHSupp=? WHERE matricule=?");
			pst1.setDouble(1, employee.getHsupp());
			pst1.setDouble(2,employee.getPHsupp());
			pst1.setInt(3, employee.getMatricule());
			
			int res=pst.executeUpdate();
			int res1=pst1.executeUpdate();
			
			if(res!=0 && res1!=0) {
				System.out.println("employe Updated successfully");
				return true;
			}
		} catch (SQLException exp) {
			exp.printStackTrace();
		}
		return false;
	}
	@Override
	public boolean deleteEmploye(Employee employee) {
		try {
			PreparedStatement pst=connexion.getCon().prepareStatement("DELETE  FROM salarie WHERE matricule=?");
			pst.setLong(1, employee.getMatricule());
			
			PreparedStatement pst1=connexion.getCon().prepareStatement("DELETE  FROM employee WHERE matricule=?");
			pst1.setLong(1, employee.getMatricule());
			
			int res=pst.executeUpdate();
			int res1=pst1.executeUpdate();
			
			if(res != 0 && res1 != 0) {
				System.out.println("employee deleted successfully");
				return true;
			}
		} catch (SQLException exp) {
			// TODO Auto-generated catch block
			exp.printStackTrace();
		}
		return false;
	}

	@Override
	public Employee findEmployeById(int mat) {
		try {
			Statement st =connexion.getCon().createStatement();
			Statement st1 =connexion.getCon().createStatement();

			ResultSet res=st.executeQuery("SELECT * FROM salarie WHERE matricule="+mat);
			ResultSet res1=st1.executeQuery("SELECT * FROM employee WHERE matricule="+mat);

			if(res.next() && res1.next()) {
				System.out.println("employee finded successfully");
				return new Employee(res.getInt("matricule"),res.getString("nom"),res.getString("email"),res.getString("categorie"),res.getDouble("anneRecruit"),res.getInt("idEntreprise"),res.getDouble("salaire")+res1.getDouble("HSupp")*res1.getDouble("PHSupp"),res1.getDouble("HSupp"),res1.getDouble("PHSupp"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}




	}