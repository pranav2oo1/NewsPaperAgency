package billCollector;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import ConnectionDB.ConnectionDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class BillCollectorController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboMob;

    @FXML
    private ListView<String> listDate;

    @FXML
    private ListView<Float> listAmount;

    @FXML
    private Label lblAmount;

    @FXML
    private Label lblInfo;
    @FXML
    void doBilling(ActionEvent event) {
    	//get all the mobile numbers according to the mobile number selected
    	String mobile=comboMob.getEditor().getText();
    	if(mobile=="") {
    		return;
    	}
    	try {
//    		date_of_billing 	amount 
			psmt=com.prepareStatement("select DATE_FORMAT(date_of_billing, '%d-%b-%y') as date,amount from billing where status=0 and cust_mobile=?");
			psmt.setString(1, mobile);
			ResultSet table=psmt.executeQuery();
			ArrayList<String> dateList=new ArrayList<String>();
			ArrayList<Float> amountList=new ArrayList<Float>();
			while(table.next())
			{
				dateList.add(table.getString("date"));
				amountList.add(table.getFloat("amount"));
			}
			listDate.getItems().setAll(dateList);
			listAmount.getItems().setAll(amountList);
			setTotalAmount(amountList);
			
			System.out.println("Fetched  pending bills ");
			lblInfo.setText("Fetched pending bills");
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println("Unable to fetch details of pending bills "+e.getMessage());
			lblInfo.setText("Unable to fetch details of pending bills");
		}
    }

    @FXML
    void setPaid(ActionEvent event) {
    	//change status of the stored bills to 1
    	String mobile=comboMob.getEditor().getText();
    	Float amount=Float.valueOf(lblAmount.getText());
    	if((mobile=="")|(amount==0)) {
    		return;
    	}
    	try {
//    		date_of_billing 	amount 
			psmt=com.prepareStatement("update billing set status=1 where cust_mobile=?");
			psmt.setString(1, mobile);
			psmt.executeUpdate();
			
			System.out.println("Changed all due bills");
			lblInfo.setText("Changed all due bills");
			
			//clear all boxes
			doClear();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println("Unable to change staus to paid for pending bills "+e.getMessage());
			lblInfo.setText("Unable to change staus to paid for pending bills");
		}
    }
    void getMobileNumbers() {
    	//fetch all mobile numbers
    	try {
			psmt=com.prepareStatement("select mobile from customers");
			ResultSet table=psmt.executeQuery();
			ArrayList<String> mobileList=new ArrayList<String>();
			while(table.next())
			{
				mobileList.add(table.getString("mobile"));
			}
			comboMob.getItems().setAll(mobileList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println("Unable to fetch list of all mobile numbers "+e.getMessage());
			lblInfo.setText("Unable to fetch list of all mobile numbers");
		}
    	
    	
    }
    void setTotalAmount(ArrayList<Float> amountList) {
    	float amount=0;
    	for(float x:amountList) {
    		amount+=x;
    	}
    	lblAmount.setText(String.valueOf(amount));
    }
    void doClear() {
    	comboMob.getSelectionModel().select(-1);;
    	listDate.getItems().setAll(new ArrayList<String>());
    	listAmount.getItems().setAll(new ArrayList<Float>());
    	lblAmount.setText("");;
	
    }
    Connection com;
    PreparedStatement psmt;
    @FXML
    void initialize() {
    	com=ConnectionDB.getConnection();
    	getMobileNumbers();
        assert comboMob != null : "fx:id=\"comboMob\" was not injected: check your FXML file 'BillCollector.fxml'.";
        assert listDate != null : "fx:id=\"listDate\" was not injected: check your FXML file 'BillCollector.fxml'.";
        assert listAmount != null : "fx:id=\"listAmount\" was not injected: check your FXML file 'BillCollector.fxml'.";
        assert lblAmount != null : "fx:id=\"lblAmount\" was not injected: check your FXML file 'BillCollector.fxml'.";

    }
}
