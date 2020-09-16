/**
 * Sample Skeleton for 'DashBoard.fxml' Controller Class
 */

package dashBoard;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class DashBoardController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="lblInfo"
    private Label lblInfo; // Value injected by FXMLLoader

    @FXML
    void openBillCollector(MouseEvent event) {
    	getResource("billCollector/BillCollector.fxml","Bill Collector");
    }
    

    @FXML
    void openBillGenerator(MouseEvent event) {
    	getResource("billGenerator/BillGenerator.fxml","Bill Generator");
    }

    @FXML
    void openBillHistory(MouseEvent event) {
    	getResource("billHistory/BillHistory.fxml","Bill History");
    }

    @FXML
    void openCustomer(MouseEvent event) {
    	getResource("customer/Customer.fxml","Customer");
    }

    @FXML
    void openCustomerDispay(MouseEvent event) {
    	getResource("costumerDisplayBoard/CostumerDisplay.fxml","Costumer Display");
    }

    @FXML
    void openHawkerControl(MouseEvent event) {
    	getResource("hawkerControl/HawkerControl.fxml","Hawker Control");
    }

    @FXML
    void openHawkerDisplay(MouseEvent event) {
    	getResource("hawkerDisplayBoard/HawkerDisplay.fxml","Hawker Display");
    }
    @FXML
    void openPaperMaster(MouseEvent event) {
    	getResource("paperMaster/PaperMaster.fxml","Paper Master");
    }
    void getResource(String path,String title)
    {
		try {
//			System.out.println(path);
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(path));
//			System.out.println('1');
//			System.out.println(root);
			Scene scene = new Scene(root);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.setTitle(title);
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println(e.getMessage());
		} 	
		catch( Exception e) {
			System.out.println(e.getMessage());
		}
    }
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert lblInfo != null : "fx:id=\"lblInfo\" was not injected: check your FXML file 'DashBoard.fxml'.";

    }
}
