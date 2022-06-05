package application;

import java.net.URL;
import java.util.ResourceBundle;

import application.Controller.EntrepriseDataClass;
import application.entity.Entreprise;
import application.srevice.EntrepriseService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class EntrepriseController implements Initializable {
	
	
	@FXML
	private TextField tfMat;
	@FXML
	private TextField tfName;
	@FXML
	private Button addBtn;
	@FXML
	private Button editBtn;

	
	@FXML
	private Text titre;
	@FXML
	private Button removeBtn;
	@FXML
	private Button exitBtn;
	@FXML
	private Button updateBtn;
	
	@FXML
	private Button cnBtn;
	
	@FXML
	private TableView<Entreprise> table;

	@FXML
	private TableColumn<Entreprise, Integer> matCol;
	@FXML
	private TableColumn<Entreprise, String> nameCol;
	private EntrepriseDataClass data;
	
	
	@FXML
	private Button importBtn;
	@FXML
	private Button exportBtn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	EntrepriseService entrepriseservice = new EntrepriseService();
		matCol.setCellValueFactory(new PropertyValueFactory<>("mat"));
		nameCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
		this.data = new EntrepriseDataClass();
		table.getItems().addAll(entrepriseservice.findAll());
}


	public void importer() {
		table.getItems().clear();
		table.getItems().addAll(data.getImportList());
	}

	public void add() {
		EntrepriseService es = new EntrepriseService();
		Entreprise e = new Entreprise(tfName.getText());
		es.createEntreprise(e);
		e.setMat(es.listEntreprise().getMat() + 1);
		data.getImportList().add(e);
		importer();
	}
	
	public void clear() {
		tfName.setText(null);
	}
	public void modif() {
	 
	    tfName.setText(table.getSelectionModel().getSelectedItem().getNom());
	    addBtn.setVisible(false);
	    titre.setText("Modifer Entreprise");
	}
	
	
	
	public void update() {
		Entreprise en = table.getSelectionModel().getSelectedItem();
		EntrepriseService es = new EntrepriseService();
		es.updateEntreprise(table.getSelectionModel().getSelectedItem());
		data.getImportList().remove(table.getSelectionModel().getSelectedItem());
		en.setNom(tfName.getText());
		data.getImportList().add(en);
		importer();
		addBtn.setVisible(true);
	    titre.setText("Ajouter Entreprise");
		
	}

	public void supprimer() {
		EntrepriseService es = new EntrepriseService();
		es.deleteEntreprise(table.getSelectionModel().getSelectedItem());
		data.getImportList().remove(table.getSelectionModel().getSelectedItem());
		table.getItems().remove(table.getSelectionModel().getSelectedItem());
	}

	public void exit() {
		System.exit(0);
	}
}
