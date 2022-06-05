package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.entity.Employee;
import application.entity.Salarie;
import application.entity.Vendeur;
import application.srevice.EmployeeService;
import application.srevice.SalarieService;
import application.srevice.VendeurService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class SalarieController implements Initializable {
	@FXML
	private TextArea tfDetail;
	@FXML
	private RadioButton rV;
	@FXML
	private RadioButton rE;
	@FXML
	private TextField tfMin;
	@FXML
	private TextField tfMax;
	@FXML
	private TextField tfTHV;
	@FXML
	private TextField tfHVS;
	@FXML
	private Button addBtn;
	@FXML
	private Button updateBtn;
	@FXML
	private Button exportBtn;
	@FXML
	private Button importBtn;
	@FXML
	private Button removeBtn;
	@FXML
	private Button detailsBtn;
	@FXML
	private Button exitBtn;
	@FXML
	private Button catBtn;
	@FXML
	private Button ancienteBtn;
	@FXML
	private Button maxTauxBtn;
	@FXML
	private Button minSalaireBtn;
	@FXML
	private Button minMaxBtn;
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
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		matCol.setCellValueFactory(new PropertyValueFactory<>("matricule"));
		emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
		nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
		catCol.setCellValueFactory(new PropertyValueFactory<>("cat"));
		salaireCol.setCellValueFactory(new PropertyValueFactory<>("salaireFix"));
		EmployeeService EmployeService = new EmployeeService();
		VendeurService VendeurService = new VendeurService();
		table.getItems().addAll(EmployeService.findAll());
		table.getItems().addAll(VendeurService.findAll());
	}

	public void MinMax() {
		SalarieService sv = new SalarieService();
		List<Salarie> listSalMinMax = sv.salBetween(Double.parseDouble(tfMin.getText()),
				Double.parseDouble(tfMax.getText()));
		table.getItems().clear();
		table.getItems().addAll(listSalMinMax);
	}

	public void listAnciennete() {
		SalarieService sv = new SalarieService();
		List<Salarie> listAnciennete = sv.anciennete();
		table.getItems().clear();
		table.getItems().addAll(listAnciennete);
	}

	public void MaxVente() {
		VendeurService vs = new VendeurService();
		List<Vendeur> Lmtv = vs.maxTauxVente();
		table.getItems().clear();
		table.getItems().addAll(Lmtv);
	}

	public void salaireFaible() {
		SalarieService sv = new SalarieService();
		List<Salarie> listFaibleSalaire = sv.salaireFaible();
		table.getItems().clear();
		table.getItems().addAll(listFaibleSalaire);
	}

	public void exporter() {
		File expFile = new File("C:\\fichiers\\exportedFile.txt");
		try {
			FileWriter expFileReader = new FileWriter(expFile);
			expFileReader.write(table.getItems().toString());
			expFileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public void listCat() {
		EmployeeService es = new EmployeeService();
		VendeurService vs = new VendeurService();
		table.getItems().clear();
		if (rE.isSelected()) {
			table.getItems().addAll(es.findAll());
		} else if (rV.isSelected()) {
			table.getItems().addAll(vs.findAll());
		}
	}

	public void details() {
		if (table.getSelectionModel().getSelectedItem().getCat().equals("employe")) {
			EmployeeService es = new EmployeeService();
			Employee emp = es.findEmployeById(table.getSelectionModel().getSelectedItem().getMatricule());
			tfDetail.setText(emp.toString());
		} else if (table.getSelectionModel().getSelectedItem().getCat().equals("vendeur")) {
			VendeurService vs = new VendeurService();
			Vendeur vend = vs.findVendeurById(table.getSelectionModel().getSelectedItem().getMatricule());
			tfDetail.setText(vend.toString());
		}
	}

	public void exit() {
		System.exit(0);
	}
}
