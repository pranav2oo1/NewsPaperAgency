/**
 * Sample Skeleton for 'CostumerDispay.fxml' Controller Class
 */
//
package costumerDisplayBoard;

import java.io.IOException;
import java.net.URL;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import ConnectionDB.ConnectionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CostumerDisplayController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="lblInfo"
    private Label lblInfo; // Value injected by FXMLLoader

    @FXML // fx:id="comboArea"
    private ComboBox<String> comboArea; // Value injected by FXMLLoader

    @FXML // fx:id="comboPaper"
    private ComboBox<String> comboPaper; // Value injected by FXMLLoader

    @FXML // fx:id="tblRecords"
    private TableView<UserBean> tblRecords; // Value injected by FXMLLoader

    @FXML
    void getAllRecords(ActionEvent event) {
    	//DATE_FORMAT(date_of_billing, '%d-%b-%y')
    	//name	mobile	address	papers	hawker	curdate
    	try {
    		psmt=com.prepareStatement("select *,DATE_FORMAT(curdate, '%d-%b-%y') as dob from customers");
    		ResultSet table=psmt.executeQuery();
    		//adding fetched record to tables
    		tblRecords.setItems(setTablerow(table));
    		System.out.println("Fetched all records from customers.");
    		lblInfo.setText("Fetched all records from customers.");
    	}
    	catch(SQLException e) {
//    		e.printStackTrace();
    		System.out.println("Unable to fetch Sorted Records from Customers."+e.getMessage());
    		lblInfo.setText("Unable to fetch Sorted Records from Customers.");
    	}
    }

    @FXML
    void getSortedRecords(ActionEvent event) {
//    	DATE_FORMAT(date_of_billing, '%d-%b-%y')
    	String area=comboArea.getEditor().getText();
    	String paper=comboPaper.getEditor().getText();
    	
    	//getting hawker name corresponding to the area,can be multiple
    	ArrayList<String> hawkerList=getHawkerName(area);
		if(hawkerList==null)
		{
			//if no hawker was found return and solve the error
//    		lblInfo.setText("Unable to fetch name from Hawkers.");
    		return;
		}
		
    	try {
    		
    		//execute query generated respectively because of conditions
    		ResultSet table=getCustomResultSet(area ,paper,hawkerList);
    		//adding fetched record to tables
    		tblRecords.setItems(setTablerow(table));
    		System.out.println("Fetched all records from customers. area="+area+" paper="+paper);
    		lblInfo.setText("Fetched all records from customers.");
    	}
    	catch(SQLException e) {
    		e.printStackTrace();
    		System.out.println("Unable to fetch Sorted Records from Customers."+e.getMessage());
    		lblInfo.setText("Unable to fetch Sorted Records from Customers.");
    	}
    	catch(NullPointerException e) {
//    		e.printStackTrace();
    		System.out.println("Nll returned by getAppropriateResultSet."+e.getMessage());
    		lblInfo.setText("Empty records.");
    	}
    }
    ResultSet getCustomResultSet(String area ,String paper,ArrayList<String> hawkerList) throws SQLException
    {
    	if(area.equals("")&paper.equals(""))
    	{
			//if both paper and area is not set fetch all records
    		getAllRecords(null);
    		return null;
    	}
    	else if(area.equals(""))
    	{
			//if area is not set fetch according to paper 
    		psmt=com.prepareStatement("select *,DATE_FORMAT(curdate, '%d-%b-%y') as dob from customers where papers like '%"+paper+"%'");
//    		psmt.setString(1, paper);
    	}
    	else if(paper.equals(""))
    	{
    		//calling function to make a query out of hawker list using or operator
    		psmt=com.prepareStatement(getHawkerQuery(hawkerList));
    	}
    	else {
    		//means both paper and area are set
    		//adding an extra condition to query made out of hawker list using or operator
    		psmt=com.prepareStatement(getHawkerQuery(hawkerList)+"and papers like '%"+paper+"%'");
//    		psmt.setString(1, paper);
    	}
    	//execute query generated respectively because of conditions
		ResultSet table=psmt.executeQuery();
		System.out.println("Fetched Sorted resultSet.");
		return table;
    	
    }
    String getHawkerQuery(ArrayList<String> hawkerList)
    {
    	//if paper is not set fetch according to area i.e hawker
		String query1="select *,DATE_FORMAT(curdate, '%d-%b-%y') as dob from customers where ";
		int count=0; 
		for(String hawker:hawkerList)
		{
			
			if(count == hawkerList.size()-1)
			{
				//special treatment if this is the last element
				query1+=" hawker = '"+hawker+"'";
				break;
			}
			query1+=" hawker = '"+hawker+"' or ";
			count++;
		}
//		System.out.println(count+""+hawkerList.size());
		
		System.out.println("Hawker's Query Sent "+query1);
		return query1;
    }
    ObservableList<UserBean> setTablerow(ResultSet table) throws SQLException
    {
    	//making an array of observable List
		ObservableList<UserBean> all=FXCollections.observableArrayList();
		while(table.next()) {
			UserBean record =new UserBean(table.getString("name"),
					table.getString("mobile"),
					table.getString("address"),
					table.getString("papers"),
					table.getString("hawker"),
					table.getString("dob")
			);
			//System.out.println(record);
			all.add(record);
		}
		return all;
    }
    ArrayList<String> getHawkerName(String area) {
    	try {
    		//checking for area in hawker's table
    		psmt=com.prepareStatement("select name from hawkers where areas like '%"+area+"%'");
//    		psmt.setString(1, area);
    		ResultSet table=psmt.executeQuery();
    		
    		ArrayList<String> nameList=new ArrayList<String>();
    		while(table.next()) {
    			nameList.add(table.getString("name"));
    		}
    		System.out.println("Fetched names of hawker's using the area selected.");
    		return nameList;
    		
    	}
    	catch(SQLException e) {
//    		e.printStackTrace();
    		System.out.println("Unable to fetch name from Hawkers."+e.getMessage());
    		lblInfo.setText("Unable to fetch name from Hawkers.");
    		return null;
    	}
    }
    void setAreaCombo() {
    	//filling the area with distinct areas of 
    	try {
    		psmt=com.prepareStatement("select distinct areas from hawkers");
    		ResultSet table=psmt.executeQuery();
    		ArrayList<String> areaAry=new ArrayList<String>();
    		while(table.next()) {
    			String areas= table.getString("areas");
    			//filter that some record is fetched
    			if(!(areas.length()>=1))
    				break;
    			
    			String []areaList=areas.substring(0,areas.length()-1).split(",");//making a list of areas for that hawker	
    			//adding those list of areas to the area List only,union of areas is taken
        		areaAry.addAll(Arrays.asList(areaList));
    			
    		}
    		//adding AryList to combo
    		comboArea.getItems().addAll(areaAry);
    		System.out.println("combo Area set.");
    	}
    	catch(SQLException e) {
//    		e.printStackTrace();
    		System.out.println("Unable to fetch Areas from Hawkers."+e.getMessage());
    		lblInfo.setText("Unable to fetch Areas from Hawkers.");
    	}
    }
	void setPaperCombo() {
		//filling all the papers to PaperCombo 
    	try {
    		psmt=com.prepareStatement("select paper from papers");
    		ResultSet table=psmt.executeQuery();
    		while(table.next()) {
    			String paper= table.getString("paper");
    			//adding paper to combo
        		comboPaper.getItems().addAll(paper);
    	
    		}
    		System.out.println("comob Paper set.");
    	}
    	catch(SQLException e) {
//        	e.printStackTrace();
        	System.out.println("Unable to fetch Paper from Papers "+e.getMessage());
        	lblInfo.setText("Unable to fetch Paper from Papers");
        }
    }
	void setTableColumns() {
		//name	mobile	address	papers	hawker	curdate
		//initializing table columns
		TableColumn<UserBean,String> name=new TableColumn<UserBean,String>("name");
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		TableColumn<UserBean,String> mobile=new TableColumn<UserBean,String>("mobile");
		mobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
		TableColumn<UserBean,String> address=new TableColumn<UserBean,String>("address");
		address.setCellValueFactory(new PropertyValueFactory<>("address"));
		TableColumn<UserBean,String> hawker=new TableColumn<UserBean,String>("hawker");
		hawker.setCellValueFactory(new PropertyValueFactory<>("hawker"));
		TableColumn<UserBean,String> curdate=new TableColumn<UserBean,String>("LastBill");
		curdate.setCellValueFactory(new PropertyValueFactory<>("curdate"));
		
		//declaring table columns
		//table columns set
		tblRecords.getColumns().add(name);
		tblRecords.getColumns().add(mobile);
		tblRecords.getColumns().add(address);
		tblRecords.getColumns().add(hawker);
		tblRecords.getColumns().add(curdate);
		
		//TableMenuButtonVisible set to true
		tblRecords.setTableMenuButtonVisible(true);
		System.out.println("table Columns set");
	}
	public void openExcel(ActionEvent event) {
		lblInfo.setText(new javaToExcel().openDoc());
	}
	public void setExcel(ActionEvent event) {
		try {
			new javaToExcel().start(tblRecords);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	Connection com;
    PreparedStatement psmt;
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	com=ConnectionDB.getConnection();
    	setAreaCombo();
    	setPaperCombo();
    	setTableColumns();
        assert lblInfo != null : "fx:id=\"lblInfo\" was not injected: check your FXML file 'CostumerDispay.fxml'.";
        assert comboArea != null : "fx:id=\"comboArea\" was not injected: check your FXML file 'CostumerDispay.fxml'.";
        assert comboPaper != null : "fx:id=\"comboPaper\" was not injected: check your FXML file 'CostumerDispay.fxml'.";
        assert tblRecords != null : "fx:id=\"tblRecords\" was not injected: check your FXML file 'CostumerDispay.fxml'.";

    }
}
