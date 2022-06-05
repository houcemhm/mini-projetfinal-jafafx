package application.salarieController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.entity.Employee;
import application.entity.Entreprise;
import application.entity.Salarie;
import application.entity.Vendeur;
import application.srevice.EntrepriseService;
import application.srevice.EmployeeService;
import application.srevice.SalarieService;
import application.srevice.VendeurService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
	private ChoiceBox cbEntreprise;
	@FXML
	private RadioButton re;
	@FXML
	private RadioButton rbLemp;
	@FXML
	private RadioButton rbLvend;
	@FXML
	private RadioButton rv;
	@FXML
	private TextField tfMat;
	@FXML
	private TextField tfNom;
	@FXML
	private TextField tfEmail;
	@FXML
	private TextField tfDateRec;
	@FXML
	private TextField tfSalaireMin;
	@FXML
	private TextField tfSalaireMax;
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
	private Button quitBtn;
	@FXML
	private Button catBtn;
	@FXML
	private Button dateBtn;
	@FXML
	private Button maxTauxBtn;
	@FXML
	private Button minSalaireBtn;
	@FXML
	private Button minMaxBtn;
	@FXML
	private TableView<Salarie> table;
	@FXML
	private TableColumn<Salarie, Integer> matriculeCol;
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
		emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
		catCol.setCellValueFactory(new PropertyValueFactory<>("cat"));
		salaireCol.setCellValueFactory(new PropertyValueFactory<>("salaireFix"));
		matriculeCol.setCellValueFactory(new PropertyValueFactory<>("matricule"));
		nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));


		EmployeeService es = new EmployeeService();
		VendeurService vs = new VendeurService();
		EntrepriseService esr = new EntrepriseService();
		for (Entreprise i : esr.findAll()) {
			cbEntreprise.getItems().add(i.getNom() +"/"+ i.getMat());
		}
		table.getItems().addAll(es.findAll());
		table.getItems().addAll(vs.findAll());

	}

	public void setHSuppEmp() {
		File inputFile = new File("C:\\fichiers\\data.txt");
		FileReader fr;
		BufferedReader br;
		String PHSupp = "";
		try {
			rv.setSelected(false);
			fr = new FileReader(inputFile);
			br = new BufferedReader(fr);
			String s;
			boolean ok = false;
			while ((s = br.readLine()) != null) {
				String[] fileData = s.split(" ");
				if (fileData[0].equals("PHSupp")) {
					PHSupp = fileData[1];
				}
			}
			br.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		tfTHV.setText(PHSupp);
	}

	public void setPourcentageVend() {
		File inputFile = new File("C:\\fichiers\\data.txt");
		FileReader fr;
		BufferedReader br;
		String pourcentage = "";
		try {
			re.setSelected(false);
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
		tfTHV.setText(pourcentage);
	}

	public void addSalarie() {
		String str = (String) cbEntreprise.getSelectionModel().getSelectedItem();
		EntrepriseService ents = new EntrepriseService();
		if (re.isSelected()) {
			Employee emp = new Employee(Integer.parseInt(tfMat.getText()), tfNom.getText(), tfEmail.getText(), "employe",
					Double.parseDouble(tfDateRec.getText()), Integer.parseInt(str.substring(str.indexOf("/") + 1)), 0,
					Double.parseDouble(tfTHV.getText()), Double.parseDouble(tfHVS.getText()));
			EmployeeService es = new EmployeeService();
			es.createEmploye(emp);
			table.getItems().add(es.findEmployeById(emp.getMatricule()));
			ents.findEntrepriseById(emp.getIdEntreprise()).getListeE().put(emp.getMatricule(), emp);
		} else if (rv.isSelected()) {
			Vendeur v = new Vendeur(Integer.parseInt(tfMat.getText()), tfNom.getText(), tfEmail.getText(), "vendeur",
					Double.parseDouble(tfDateRec.getText()), Integer.parseInt(str.substring(str.indexOf("/") + 1)), 0,
					Double.parseDouble(tfTHV.getText()), Double.parseDouble(tfHVS.getText()));
			VendeurService vs = new VendeurService();
			vs.createVendeur(v);
			table.getItems().add(vs.findVendeurById(v.getMatricule()));
			ents.findEntrepriseById(v.getIdEntreprise()).getListeE().put(v.getMatricule(), v);
		}

	}

	public void modif() {
		if (table.getSelectionModel().getSelectedItem().getCat().equals("employe")) {
			EmployeeService es = new EmployeeService();
			Employee emp = es.findEmployeById(table.getSelectionModel().getSelectedItem().getMatricule());
			tfMat.setText(String.valueOf(emp.getMatricule()));
			tfNom.setText(emp.getNom());
			tfEmail.setText(emp.getEmail());
			tfDateRec.setText(String.valueOf(emp.getRecruitDate()));
			re.setSelected(true);
			rv.setSelected(false);
			tfTHV.setText(String.valueOf(emp.getPHsupp()));
			tfHVS.setText(String.valueOf(emp.getHsupp()));
		} else if (table.getSelectionModel().getSelectedItem().getCat().equals("vendeur")) {
			VendeurService vs = new VendeurService();
			EntrepriseService ents = new EntrepriseService();
			Vendeur vend = vs.findVendeurById(table.getSelectionModel().getSelectedItem().getMatricule());
			tfMat.setText(String.valueOf(vend.getMatricule()));
			tfNom.setText(vend.getNom());
			tfEmail.setText(vend.getEmail());
			tfDateRec.setText(String.valueOf(vend.getRecruitDate()));

			rv.setSelected(true);
			re.setSelected(false);
			tfTHV.setText(String.valueOf(vend.getPoucentage()));
			tfHVS.setText(String.valueOf(vend.getVente()));
		}

	}

	public void Validermodif() {
		String str = (String) cbEntreprise.getSelectionModel().getSelectedItem();
		EmployeeService es = new EmployeeService();
		VendeurService vs = new VendeurService();
		double sal;

		if (Double.parseDouble(tfDateRec.getText()) < 2005)
			sal = 400 + Double.parseDouble(tfHVS.getText()) * Double.parseDouble(tfTHV.getText());
		else
			sal = 280 + Double.parseDouble(tfHVS.getText()) * Double.parseDouble(tfTHV.getText());

		if (re.isSelected()) {

			Employee emp = new Employee(Integer.parseInt(tfMat.getText()), tfNom.getText(), tfEmail.getText(), "employe",
					Double.parseDouble(tfDateRec.getText()), Integer.parseInt(str.substring(str.indexOf("/") + 1)), sal,
					Double.parseDouble(tfHVS.getText()), Double.parseDouble(tfTHV.getText()));
			es.updateEmploye(emp);

		} else if (rv.isSelected()) {
			Vendeur v = new Vendeur(Integer.parseInt(tfMat.getText()), tfNom.getText(), tfEmail.getText(), "vendeur",
					Double.parseDouble(tfDateRec.getText()), Integer.parseInt(str.substring(str.indexOf("/") + 1)), sal,
					Double.parseDouble(tfHVS.getText()), Double.parseDouble(tfTHV.getText()));
			vs.updateVendeur(v);
		}
		table.getItems().clear();
		table.getItems().addAll(es.findAll());
		table.getItems().addAll(vs.findAll());

	}

	public void listCat() {
		EmployeeService es = new EmployeeService();
		VendeurService vs = new VendeurService();
		table.getItems().clear();
		if (rbLemp.isSelected()) {
			table.getItems().addAll(es.findAll());
		} else if (rbLvend.isSelected()) {
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

	public void supprimer() {
		if (table.getSelectionModel().getSelectedItem().getCat().equals("employe")) {
			EmployeeService es = new EmployeeService();
			es.deleteEmploye(es.findEmployeById(table.getSelectionModel().getSelectedItem().getMatricule()));
			table.getItems().remove(table.getSelectionModel().getSelectedItem());
		} else if (table.getSelectionModel().getSelectedItem().getCat().equals("vendeur")) {
			VendeurService vs = new VendeurService();
			vs.deleteVendeur(vs.findVendeurById(table.getSelectionModel().getSelectedItem().getMatricule()));
			table.getItems().remove(table.getSelectionModel().getSelectedItem());
		}

	}

	public void listerMinMax() {
		SalarieService sv = new SalarieService();
		List<Salarie> listSalMinMax = sv.salBetween(Double.parseDouble(tfSalaireMin.getText()),
				Double.parseDouble(tfSalaireMax.getText()));
		table.getItems().clear();
		table.getItems().addAll(listSalMinMax);
	}

	public void listAnciennete() {
		SalarieService sv = new SalarieService();
		List<Salarie> listAnciennete = sv.anciennete();
		table.getItems().clear();
		table.getItems().addAll(listAnciennete);
	}

	public void listerMaxTauxVente() {
		VendeurService vs = new VendeurService();
		List<Vendeur> Lmtv = vs.maxTauxVente();
		table.getItems().clear();
		table.getItems().addAll(Lmtv);
	}

	public void salaireF() {
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

	public void exit() {
		System.exit(0);
	}

}
