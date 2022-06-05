package application.entity;

import java.util.HashMap;

public class Entreprise {
	private int mat;
	private String nom;
	private HashMap<Integer,Salarie>  listeE;
	
	
	
	public Entreprise(String nom) {
		this.nom = nom;
		this.listeE=new HashMap<Integer,Salarie>();
	}
	
	public Entreprise(int idE,String nomE) {
		super();
		this.mat=idE;
		this.nom = nomE;
		this.listeE=new HashMap<Integer,Salarie>();
	}

	public int getMat() {
		return mat;
	}

	public void setMat(int mat) {
		this.mat = mat;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public HashMap<Integer, Salarie> getListeE() {
		return listeE;
	}

	public void setListeE(HashMap<Integer, Salarie> listeE) {
		this.listeE = listeE;
	}

	@Override
	public String toString() {
		return "Entreprise [mat=" + mat + ", nom=" + nom + ", listeE=" + listeE + "]";
	}
	

	

}
