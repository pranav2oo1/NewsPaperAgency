/**
 * Sample Skeleton for 'BillHistory.fxml' Controller Class
 */

package billHistory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import ConnectionDB.ConnectionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

public class BillHistoryController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="lblInfo"
    private Label lblInfo; // Value injected by FXMLLoader

    @FXML // fx:id="tblBill"
    private TableView<BillBean> tblBill; // Value injected by FXMLLoader

    @FXML // fx:id="radPaid"
    private RadioButton radPaid; // Value injected by FXMLLoader

    @FXML // fx:id="radioPaid"
    private ToggleGroup radioPaid; // Value injected by FXMLLoader

    @FXML // fx:id="radUnpaid"
    private RadioButton radUnpaid; // Value injected by FXMLLoader

    @FXML // fx:id="comboMob"
    private ComboBox<String> comboMob; // Value injected by FXMLLoader

    @FXML
    void fetchAll(ActionEvent event) {
    	try {
			ObservableList<BillBean> items=getAllItems();
			tblBill.getItems().setAll(items);
			System.out.println("Successfully fetched All details.");
			lblInfo.setText("Successfully fetched All details.");
			calcTotalAmount();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//			e.printStackTrace();
			System.out.println("Error fetching details."+e.getMessage());
			lblInfo.setText("Error fetching details.");
		}
    	catch (NullPointerException e) {
			// TODO Auto-generated catch block
			//			e.printStackTrace();
			System.out.println("No details fethed "+e.getMessage());
//			lblInfo.Text("Error fetching details of "+mob);
		}
    }

    @FXML
    void fetchSelected(ActionEvent event) {
    	String mob=comboMob.getEditor().getText();
    	try {
			ObservableList<BillBean> items=getItems(mob);
			tblBill.getItems().setAll(items);
			System.out.println("Successfully fetched All details.");
			lblInfo.setText("Successfully fetched All details.");
			calcTotalAmount();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//			e.printStackTrace();
			System.out.println("Error fetching details of "+mob+e.getMessage());
			lblInfo.setText("Error fetching details of "+mob);
		} 
    	catch (NullPointerException e) {
			// TODO Auto-generated catch block
			//			e.printStackTrace();
			System.out.println("No details fethed "+mob+e.getMessage());
//			lblInfo.Text("Error fetching details of "+mob);
		} 

    }
    ObservableList<BillBean> getAllItems() throws SQLException
    {
    	int status=generateStatus();
    	if(status==-1)
    	{
    		return null;
    	}
    	//get all the items present in the list and store in ObservaleList of BillBean
    	psmt=com.prepareStatement("select *,DATE_FORMAT(date_of_billing, '%d-%b-%y') as dob from billing where status=?");
    	psmt.setInt(1, status);
    	ResultSet table=psmt.executeQuery();
    	
		return generateTableItems(table);
    	
    }
    ObservableList<BillBean> getItems(String mob) throws SQLException
    {
    	int status=generateStatus();
    	if(status==-1)
    	{
    		return null;
    	}
    	//get all the items present in the list and store in ObservaleList of BillBean
    	psmt=com.prepareStatement("select *,DATE_FORMAT(date_of_billing, '%d-%b-%y') as dob from billing where cust_mobile=? and status=?");
    	psmt.setString(1, mob);
    	psmt.setInt(2,status);
    	ResultSet table=psmt.executeQuery();
    	return generateTableItems(table);
    	
    }
    int generateStatus()
    {
    	if(radPaid.isSelected())
    	{
    		return 1;
    	}
    	if(radUnpaid.isSelected())
    	{
    		return 0;
    	}
    	lblInfo.setText("Select which type to fetch.");
    	System.out.println("No Radio Button Selected");
    	return -1;
    }
    ObservableList<BillBean> generateTableItems(ResultSet table) throws SQLException

    {
    	amount=0;
    	ObservableList<BillBean> billList=FXCollections.observableArrayList();
//    	1 	billid Primary 	int(11) 	
//    	2 	cust_mobile 	varchar(12)  	
//    	3 	noofdays 	varchar(3) 	 	
//    	4 	date_of_billing 	date  	
//    	5 	amount 	float 	
//    	6 	status 	tinyint(1)
		while(table.next()) {
			//creating string value for status
			String status;
			if(table.getInt("status")==1)
			{
				status="paid";
			}
			else
			{
				status="pending";
			}
			
			amount+=table.getFloat("amount");
			
			//making an array item
			BillBean row=new BillBean(table.getInt("billid"),
					table.getString("cust_mobile"), 
					table.getString("dob"), 
					table.getString("date_of_billing"), 
					status, 
					table.getFloat("amount"));
			//storing in array
			billList.add(row);
		}
    	return billList;
    }
    void calcTotalAmount() {
    	lblInfo.setText("Total Amount: "+amount);
    	System.out.println("Total Amount: "+amount);
    }
    void fetchMob()
    {
    	//fetch all Mobile number for combo
    	try {
			psmt=com.prepareStatement("select mobile from customers");
			ResultSet table=psmt.executeQuery();
			ArrayList<String> mobList=new ArrayList<String>();
			while(table.next())
			{
				mobList.add(table.getString("mobile"));
			}
			//adding all fetched mobile numbers to the combo
			comboMob.getItems().addAll(mobList);
			System.out.println("Successfully fetched all mobile numbers");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println("Unable to fetch the mobile numbers."+e.getMessage());
			lblInfo.setText("Unable to fetch the mobile numbers.");
		}
    	
    }
    void setTableColumns()
    {
//    	1 	billid Primary 	int(11) 	
//    	2 	cust_mobile 	varchar(12)  	
//    	3 	noofdays 	varchar(3) 	 	
//    	4 	date_of_billing 	date  	
//    	5 	amount 	float 	
//    	6 	status 	tinyint(1)
    	//setting all the table columns for the data fetched from the bill
    	TableColumn<BillBean,Integer> id=new  TableColumn<BillBean,Integer>("id");
    	id.setCellValueFactory(new PropertyValueFactory<>("id"));
    	
    	TableColumn<BillBean,String> mob=new  TableColumn<BillBean,String>("mob");
    	mob.setCellValueFactory(new PropertyValueFactory<>("cust_mobile"));
    	
    	TableColumn<BillBean,String> dateOfBilling=new  TableColumn<BillBean,String>("dateOfBilling");
    	dateOfBilling.setCellValueFactory(new PropertyValueFactory<>("date_of_billing"));
    	
    	TableColumn<BillBean,String> status=new  TableColumn<BillBean,String>("status");
    	status.setCellValueFactory(new PropertyValueFactory<>("status"));
    	
    	TableColumn<BillBean,String> days=new  TableColumn<BillBean,String>("days");
    	days.setCellValueFactory(new PropertyValueFactory<>("noofdays"));
  
    	TableColumn<BillBean,Float> amount=new  TableColumn<BillBean,Float>("amount");
    	amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
    	//adding declared tables to the tableView
    	tblBill.getColumns().add(id);
    	tblBill.getColumns().add(mob);
    	tblBill.getColumns().add(dateOfBilling);
    	tblBill.getColumns().add(status);
    	tblBill.getColumns().add(days);
    	tblBill.getColumns().add(amount);
    	
    }
    Connection com;
    PreparedStatement psmt;
    float amount;
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	com=ConnectionDB.getConnection();
    	fetchMob();
    	setTableColumns();
        assert lblInfo != null : "fx:id=\"lblInfo\" was not injected: check your FXML file 'BillHistory.fxml'.";
        assert tblBill != null : "fx:id=\"tblBill\" was not injected: check your FXML file 'BillHistory.fxml'.";
        assert radPaid != null : "fx:id=\"radPaid\" was not injected: check your FXML file 'BillHistory.fxml'.";
        assert radioPaid != null : "fx:id=\"radioPaid\" was not injected: check your FXML file 'BillHistory.fxml'.";
        assert radUnpaid != null : "fx:id=\"radUnpaid\" was not injected: check your FXML file 'BillHistory.fxml'.";
        assert comboMob != null : "fx:id=\"comboMob\" was not injected: check your FXML file 'BillHistory.fxml'.";

    }
}
