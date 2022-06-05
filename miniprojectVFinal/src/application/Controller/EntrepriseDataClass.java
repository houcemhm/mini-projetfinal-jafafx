package application.Controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import application.entity.Entreprise;
import application.srevice.EntrepriseService;


public class EntrepriseDataClass {
	private List<Entreprise> importList;
	private List<Entreprise> exportList;
public EntrepriseDataClass() {
	EntrepriseService es =new EntrepriseService();
	importList=new ArrayList<Entreprise>(es.findAll());
	
	exportList=new ArrayList<Entreprise>();
}
public List<Entreprise> getImportList(){
	return importList; 
}

public List<Entreprise> getExportList(){
	return exportList; 
}

public void setExportList(List<Entreprise> exportList) {
	this.exportList.addAll(exportList);
	for (Entreprise p:this.exportList)
		System.out.println(p);
}



}
