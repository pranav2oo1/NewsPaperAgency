package customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ConnectionDB.ConnectionDB;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;

public class CustomerController {
	@FXML
    private ListView<String> listPaper;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtHawker;

    @FXML
    private Label lblInfo;

    @FXML
    private TextField txtMob;

    @FXML
    private ComboBox<String> comboArea;

    @FXML
    void doClear(ActionEvent event) {
    	listPaper.getSelectionModel().clearSelection();
    	comboArea.getSelectionModel().select(-1);
    	txtName.setText("");
    	txtAddress.setText("");
    	txtHawker.setText("");
    	txtMob.setText("");
    	lblInfo.setText("cleared.");
    }
    @FXML
    void getFetch(ActionEvent event) {
    	//fetching the customer number based on the mobile number
    	String mobile=txtMob.getText();
    	try {
			psmt=com.prepareStatement("select * from customers where mobile=?");
			psmt.setString(1, mobile);
			ResultSet row=psmt.executeQuery();
			if(row.next())
			{
				//row exists detail so customer exists
				
				//no area as we are storing only hawker name
		    	//listArea.getSelectionModel().clearSelection();
		    	
		    	//selecting all the papers using loop
		    	listPaper.getSelectionModel().clearSelection();
		    	String[] papers=row.getString("papers").split(",");
		    	for(String paper:papers)
		    	{
		    		listPaper.getSelectionModel().select(paper);
		    	}
		    	
		    	txtName.setText(row.getString("name"));
		    	txtAddress.setText(row.getString("address"));
		    	txtHawker.setText(row.getString("hawker"));

				System.out.println("Fetched "+mobile +" details");
				lblInfo.setText("Fetched "+mobile +" details");
			}
			else
			{
				//No customer exists by this mobile
				System.out.println("No customer exists by mobile "+mobile);
				lblInfo.setText("No customer exists by mobile "+mobile);				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error in fetching customer details "+e.getMessage());
			lblInfo.setText("Error in fetching customer details");
		}
    }

    @FXML
    void setDelete(ActionEvent event) {
    	//fetching the customer number based on the mobile number
    	String mobile=txtMob.getText();
    	try {
			psmt=com.prepareStatement("delete from customers where mobile=?");
			psmt.setString(1, mobile);
			int count=psmt.executeUpdate();
			System.out.println("Deleted "+count+" record");
			lblInfo.setText("Deleted "+count+" record");				
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error in removing customer record "+e.getMessage());
			lblInfo.setText("Error in removing customer record");
		}
    }

    @FXML
    void setSave(ActionEvent event) {
    	//saving the customer details
    	try {
			psmt=com.prepareStatement("insert into customers values(?,?,?,?,?,CURDATE())");
			psmt.setString(1,txtName.getText() );
			psmt.setString(2,txtMob.getText());
			psmt.setString(3, txtAddress.getText());
			
			//paper concatenated string
			ObservableList<String> papers=listPaper.getSelectionModel().getSelectedItems();
	    	String paperConcat="";
//	    	String priceConcat="";
	    	for(String paper:papers)
	    	{
	    		paperConcat=paperConcat+paper+",";
//	    		paperConcat=paperList.get(index)+",";
//	    		priceConcat=priceList.get(index)+",";
	    	}
	    	
			psmt.setString(4, paperConcat);
//			psmt.setString(5, priceConcat);
			psmt.setString(5, txtHawker.getText());
			int count=psmt.executeUpdate();
			
			System.out.println("Sent "+count+" new record");
			lblInfo.setText("Sent "+count+" new record");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error in saving details "+e.getMessage());
			lblInfo.setText("Error in saving details details");
		}
    }

    @FXML
    void setUpdate(ActionEvent event) {
    	//update the customer details
    	try {
			psmt=com.prepareStatement("update customers set name=?,address=?,papers=?,hawker=? where mobile=?");
			psmt.setString(1,txtName.getText() );
			psmt.setString(2, txtAddress.getText());
			//forming concated string of papers and respective prices
	    	ObservableList<String> papers=listPaper.getSelectionModel().getSelectedItems();
	    	String paperConcat="";
//	    	String priceConcat="";
	    	for(String paper:papers)
	    	{
	    		paperConcat=paperConcat+paper+",";
//	    		paperConcat=paperList.get(index)+",";
//	    		priceConcat=priceList.get(index)+",";
	    	}
	    	
			psmt.setString(3, paperConcat);
//			psmt.setString(4, priceConcat);
			psmt.setString(4, txtHawker.getText());
			psmt.setString(5,txtMob.getText());
			int count=psmt.executeUpdate();
			
			System.out.println("Updated "+count+" record");
			lblInfo.setText("Updates "+count+" record");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error in updating details "+e.getMessage());
			lblInfo.setText("Error in updating details details");
		}
    }
    @FXML
    void setHawkerName(ActionEvent event) {
//    	hawkerList.get(listArea.getSelectionModel().getSelectedItem())
//    	txtHawker.setText("x");
    	if(comboArea.getSelectionModel().getSelectedItem()=="")
    	{
    		System.out.println("no area name selected");
    		lblInfo.setText("no area name selected");
    		return;
    	}
    	try {
        	//fetching hawker name depending on the area selected
        	
			psmt=com.prepareStatement("select name from hawkers where areas like ?");
			psmt.setString(1, "%"+comboArea.getSelectionModel().getSelectedItem()+"%");
			ResultSet rs=psmt.executeQuery();
			//if record of hawker was found
			if(rs.next())
			{
				txtHawker.setText(rs.getString("name"));
			}
			else {
				System.out.println("no hawker record found .Check code.");
				lblInfo.setText("No hawker record by this name");
			}
			System.out.println("foung hawker of the same area");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println("Failed to fetch hawker detais "+e.getMessage());
			lblInfo.setText("Failed to fetch hawker details");
			
		}
    }
    
    void getAreaList()
    {
    	try {
        	//fetching data for list view of address
        	psmt=com.prepareStatement("select areas from hawkers");
			ResultSet rs=psmt.executeQuery();
			
			while(rs.next())
			{
				String [] areas=rs.getString("areas").split(",");
				comboArea.getItems().addAll(areas);	
			}
			//removing empty element if any
			comboArea.getItems().remove("");
//			listArea.getItems().addAll(hawkerList.values());
//			listArea.getItems().addAll(hawkerList.keySet());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println("Failed to fetch hawker detais "+e.getMessage());
			lblInfo.setText("Failed to fetch hawker details");
		}
    }
    void getPaperList()
    {
    	try {
        	//fetching data for list view of newspapers
        	
			psmt=com.prepareStatement("select * from papers");
			ResultSet rs=psmt.executeQuery();
			while(rs.next())
			{
				paperList.add(rs.getString("paper"));
//				priceList.add(rs.getFloat("price"));
			}
			listPaper.getItems().addAll(paperList);
//			listPrice.getItems().addAll(priceList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println("Failed to fetch paper detais "+e.getMessage());
			lblInfo.setText("Failed to fetch paper details");
			
		}
    }
//    @FXML
//    void syncPrice(ScrollEvent event) {
//    	System.out.println("X");
//    	listPrice.refresh();
//    }
    Connection com;
    PreparedStatement psmt;
    ArrayList<String> paperList=new ArrayList<String>();
//    HashMap<String,String> hawkerList=new HashMap<String,String>();
   @FXML
    void initialize() {
       	com=ConnectionDB.getConnection();
    	listPaper.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        getAreaList();
        getPaperList();
        assert listPaper != null : "fx:id=\"listPaper\" was not injected: check your FXML file 'Customer.fxml'.";
        assert txtName != null : "fx:id=\"txtName\" was not injected: check your FXML file 'Customer.fxml'.";
        assert txtAddress != null : "fx:id=\"txtAddress\" was not injected: check your FXML file 'Customer.fxml'.";
        assert txtHawker != null : "fx:id=\"txtHawker\" was not injected: check your FXML file 'Customer.fxml'.";
        assert lblInfo != null : "fx:id=\"lblInfo\" was not injected: check your FXML file 'Customer.fxml'.";
        assert txtMob != null : "fx:id=\"txtMob\" was not injected: check your FXML file 'Customer.fxml'.";
        assert comboArea != null : "fx:id=\"comboArea\" was not injected: check your FXML file 'Customer.fxml'.";

    }
}


