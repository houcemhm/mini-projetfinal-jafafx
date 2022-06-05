package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import application.entity.Entreprise;
import application.entity.Salarie;
import application.entity.Employee;
import application.srevice.EntrepriseService;
import application.srevice.EmployeeService;
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
public class EmployeeController implements Initializable {
	
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
	private TextField tfHsupp;
	@FXML
	private TextField tfPHsupp;
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
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		matCol.setCellValueFactory(new PropertyValueFactory<>("matricule"));
		emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
		nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
		catCol.setCellValueFactory(new PropertyValueFactory<>("cat"));
		salaireCol.setCellValueFactory(new PropertyValueFactory<>("salaireFix"));
		EmployeeService EmployeeService = new EmployeeService();
		EntrepriseService entrepriseService = new EntrepriseService();
		table.getItems().addAll(EmployeeService.findAll());
		try {
			for (Entreprise ent : entrepriseService.findAll()) {
				choiceEntreprise.getItems().add(ent.getNom() +" "+ ent.getMat());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			setHSuppEmp();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void add() {
		String entrep = (String) choiceEntreprise.getSelectionModel().getSelectedItem();
		EntrepriseService entrepriseService = new EntrepriseService();
			Employee v = new Employee(Integer.parseInt(tfMat.getText()), tfNom.getText(), tfEmail.getText(), "employe",
			Double.parseDouble(tfRec.getText()), Integer.parseInt(entrep.substring(entrep.indexOf(" ") + 1)),0,
			Double.parseDouble(tfHsupp.getText()), Double.parseDouble(tfPHsupp.getText()));
			EmployeeService EmployeeService = new EmployeeService();
			EmployeeService.createEmploye(v);
			table.getItems().add(EmployeeService.findEmployeById(v.getMatricule()));
			entrepriseService.findEntrepriseById(v.getIdEntreprise()).getListeE().put(v.getMatricule(), v);
	}
	public void edit() {
			EmployeeService EmployeeService = new EmployeeService();
			EntrepriseService entrepriseService = new EntrepriseService();
			Employee Employee = EmployeeService.findEmployeById(table.getSelectionModel().getSelectedItem().getMatricule());
			tfMat.setText(String.valueOf(Employee.getMatricule()));
			tfNom.setText(Employee.getNom());
			tfEmail.setText(Employee.getEmail());
			tfRec.setText(String.valueOf(Employee.getRecruitDate()));
			tfHsupp.setText(String.valueOf(Employee.getHsupp()));
			tfPHsupp.setText(String.valueOf(Employee.getPHsupp()));
			titre.setText("Modifier l'employee num"+Employee.getCat());
			addBtn.setVisible(false);
	}
	public void update() {
		EmployeeService EmployeeService = new EmployeeService();
		double sal;
		String entreprise =(String) choiceEntreprise.getSelectionModel().getSelectedItem();
		if (Double.parseDouble(tfRec.getText()) < 2005)
			sal = 400 + Double.parseDouble(tfHsupp.getText()) * Double.parseDouble(tfPHsupp.getText());
		else
			sal = 280 + Double.parseDouble(tfHsupp.getText()) * Double.parseDouble(tfPHsupp.getText());
			Employee v = new Employee(Integer.parseInt(tfMat.getText()), tfNom.getText(), tfEmail.getText(), "employe",
					Double.parseDouble(tfRec.getText()), Integer.parseInt(entreprise.substring(entreprise.indexOf("/") + 1)), sal,
					Double.parseDouble(tfHsupp.getText()), Double.parseDouble(tfPHsupp.getText()));
			EmployeeService.updateEmploye(v);
		table.getItems().clear();
		table.getItems().addAll(EmployeeService.findAll());
		titre.setText("Ajouter un Employee");
		addBtn.setVisible(true);
		
	}
	public void delete() {
			EmployeeService EmployeeService = new EmployeeService();
			EmployeeService.deleteEmploye(EmployeeService.findEmployeById(table.getSelectionModel().getSelectedItem().getMatricule()));
			table.getItems().remove(table.getSelectionModel().getSelectedItem());		
	}

	public void details() {
		EmployeeService EmployeeService = new EmployeeService();
			Employee Employee = EmployeeService.findEmployeById(table.getSelectionModel().getSelectedItem().getMatricule());
			tfDetail.setText(Employee.toString());
	}
	public void exporter() {
		File expFile = new File("C:\\files\\EmployeesFile.txt");
		try {
			FileWriter expFileReader = new FileWriter(expFile);
			expFileReader.write(table.getItems().toString());
			expFileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void setHSuppEmp() throws IOException {
		File inputFile = new File("C:\\fichiers\\data.txt");
		FileReader fr;
		BufferedReader br;
		String PHSupp = null ;

			fr = new FileReader(inputFile);
			br = new BufferedReader(fr);
			String s;
			boolean ok = false;
			try {
				while ((s = br.readLine()) != null) {
					String[] fileData = s.split(" ");
					if (fileData[0].equals("PHSupp")) {
						PHSupp = fileData[1];
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			br.close();
			tfPHsupp.setText(PHSupp);
	}

}
