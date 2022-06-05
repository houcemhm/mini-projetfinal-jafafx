package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

public class sidebarController implements Initializable   {
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}
	@FXML
	private BorderPane bp;
	@FXML
	private Text title;
	@FXML
	private void home(MouseEvent event) {
		
		loadPage("welcome");
	}
	@FXML
	private void page1(MouseEvent event) {
		loadPage("Entreprise");
	}
	@FXML
	private void page2(MouseEvent event) {
		loadPage("Salarie");
	}
	
	@FXML
	private void page3(MouseEvent event) {
		loadPage("Vendeur");
	}
	
	@FXML
	private void page4(MouseEvent event) {
		loadPage("Employee");
	}
	
	private void loadPage(String page) {
		Parent root=null;
		
		try {
			root=FXMLLoader.load(getClass().getResource(page+".fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		bp.setCenter(root);
	}
	
}
	
