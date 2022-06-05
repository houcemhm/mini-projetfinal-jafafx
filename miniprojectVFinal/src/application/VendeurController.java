package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Controller.EntrepriseDataClass;
import application.entity.Entreprise;
import application.entity.Salarie;
import application.entity.Vendeur;
import application.srevice.EntrepriseService;
import application.srevice.VendeurService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class VendeurController implements Initializable {
	
	
	@FXML
	private Text titre;
	@FXML
	private TextArea tfDetail;
	@FXML
	private ChoiceBox choiceEntreprise;
	@FXML
	private TextField tfMat;
	@FXML
	private TextField tfNom;
	@FXML
	private TextField tfEmail;
	@FXML
	private TextField tfRec;
	@FXML
	private TextField tfVente;
	@FXML
	private TextField tfPourcentage;
	@FXML
	private Button addBtn;
	@FXML
	private Button editBtn;
	@FXML
	private Button deleteBtn;
	@FXML
	private Button updateBtn;

	@FXML
	private Button detailsBtn;
	@FXML
	private Button exportBtn;
	@FXML
	private TableView<Salarie> table;
	@FXML
	private TableColumn<Salarie, Integer> matCol;
	@FXML
	private TableColumn<Salarie, String> nomCol;
	@FXML
	private TableColumn<Salarie, String> emailCol;
	@FXML
	private TableColumn<Salarie, String> catCol;
	@FXML
	private TableColumn<Salarie, Double> salaireCol;
	
	
	private Entreprise data;
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		matCol.setCellValueFactory(new PropertyValueFactory<>("matricule"));
		emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
		nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
		catCol.setCellValueFactory(new PropertyValueFactory<>("cat"));
		salaireCol.setCellValueFactory(new PropertyValueFactory<>("salaireFix"));
		VendeurService vendeurService = new VendeurService();
		EntrepriseService entrepriseService = new EntrepriseService();
		table.getItems().addAll(vendeurService.findAll());
		try {
			for (Entreprise ent : entrepriseService.findAll()) {
				choiceEntreprise.getItems().add(ent.getNom() +" "+ ent.getMat());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setPourcentage();
	}
	public void add() {
		String entrep = (String) choiceEntreprise.getSelectionModel().getSelectedItem();
		EntrepriseService entrepriseService = new EntrepriseService();
	
			Vendeur v = new Vendeur(Integer.parseInt(tfMat.getText()), tfNom.getText(), tfEmail.getText(), "vendeur",
					Double.parseDouble(tfRec.getText()), Integer.parseInt(entrep.substring(entrep.indexOf(" ") + 1)),0,
					Double.parseDouble(tfVente.getText()), Double.parseDouble(tfPourcentage.getText()));
			VendeurService vendeurService = new VendeurService();
			vendeurService.createVendeur(v);
			table.getItems().add(vendeurService.findVendeurById(v.getMatricule()));
			entrepriseService.findEntrepriseById(v.getIdEntreprise()).getListeE().put(v.getMatricule(), v);
	}

	
	public void edit() {
			VendeurService vendeurService = new VendeurService();
			EntrepriseService entrepriseService = new EntrepriseService();
			Vendeur vendeur = vendeurService.findVendeurById(table.getSelectionModel().getSelectedItem().getMatricule());
			tfMat.setText(String.valueOf(vendeur.getMatricule()));
			tfNom.setText(vendeur.getNom());
			tfEmail.setText(vendeur.getEmail());
			tfRec.setText(String.valueOf(vendeur.getRecruitDate()));
			tfVente.setText(String.valueOf(vendeur.getVente()));
			tfPourcentage.setText(String.valueOf(vendeur.getPoucentage()));
			titre.setText("Modifier l'employee de mat :"+vendeur.getMatricule());
			addBtn.setVisible(false);
	}
	
	public void update() {
		VendeurService vendeurService = new VendeurService();
		double sal;
		String entreprise =(String) choiceEntreprise.getSelectionModel().getSelectedItem();
		if (Double.parseDouble(tfRec.getText()) < 2005)
			sal = 400 + Double.parseDouble(tfVente.getText()) * Double.parseDouble(tfPourcentage.getText());
		else
			sal = 280 + Double.parseDouble(tfVente.getText()) * Double.parseDouble(tfPourcentage.getText());
			Vendeur v = new Vendeur(Integer.parseInt(tfMat.getText()), tfNom.getText(), tfEmail.getText(), "vendeur",
					Double.parseDouble(tfRec.getText()), Integer.parseInt(entreprise.substring(entreprise.indexOf("/") + 1)), sal,
					Double.parseDouble(tfVente.getText()), Double.parseDouble(tfPourcentage.getText()));
			vendeurService.updateVendeur(v);
	
		table.getItems().clear();
		table.getItems().addAll(vendeurService.findAll());
		titre.setText("Ajouter un Employee");
		addBtn.setVisible(true);
	}
	
	public void delete() {

			VendeurService vendeurService = new VendeurService();
			vendeurService.deleteVendeur(vendeurService.findVendeurById(table.getSelectionModel().getSelectedItem().getMatricule()));
			table.getItems().remove(table.getSelectionModel().getSelectedItem());

	}

	public void details() {
		VendeurService vendeurService = new VendeurService();
			Vendeur vendeur = vendeurService.findVendeurById(table.getSelectionModel().getSelectedItem().getMatricule());
			tfDetail.setText(vendeur.toString());

	}

	public void exporter() {
		File expFile = new File("C:\\files\\VendeursFile.txt");
		try {
			FileWriter expFileReader = new FileWriter(expFile);
			expFileReader.write(table.getItems().toString());
			expFileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public void setPourcentage() {
		File inputFile = new File("C:\\fichiers\\data.txt");
		FileReader fr;
		BufferedReader br;
		String pourcentage = "";
		try {
			fr = new FileReader(inputFile);
			br = new BufferedReader(fr);
			String s;
			boolean ok = false;
			while ((s = br.readLine()) != null) {
				String[] fileData = s.split(" ");
				if (fileData[0].equals("pourcentage")) {
					pourcentage = fileData[1];
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		tfPourcentage.setText(pourcentage);
	}
	
}
