package billGenerator;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import ConnectionDB.ConnectionDB;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class BillGeneratorController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboMobile;

    @FXML
    private ListView<String> listPaper;

    @FXML
    private ListView<Float> listPrice;

    @FXML
    private Label lblTotal;

    @FXML
    private Label lblDays;

    @FXML
    private Label lblInfo;

    @FXML
    void calcBill(ActionEvent event) {
    	String  mobile=comboMobile.getEditor().getText();
    	
    	//getting customer last stored date - curdate using mysql and mobile number
    	try {
    		
    		int success=generateBill(mobile);	
			if(success==1)
			{
				System.out.println("Calculated Bill.");
				lblInfo.setText("Calculated Bill");
				
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println("Unable to fetch difference of dates "+e.getMessage());
			lblInfo.setText("Unable to fetch differnece of dates");
		}
    	
    }

    @FXML
    void getClientDetails(ActionEvent event) {
    	 String mobile=comboMobile.getEditor().getText();
    	//fetching all the client details present in database of this mobile number
    	try {
			psmt=com.prepareStatement("select * from customers where  mobile=?");
			psmt.setString(1,mobile);
			ResultSet table=psmt.executeQuery();
			if(table.next())
			{
//				name=table.getString("name");
//				address=table.getString("address");
				setListView(table.getString("papers"));
//				date=table.getDate("curdate");
			}
			
			flagMobChange=true;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println("Unable to fetch mobile numbers"+e.getMessage());
			lblInfo.setText("Unable to fetch mobile numbers");
		}

    }

    @FXML
    void saveAllBill(ActionEvent event) {
    	try {
    		System.out.println("Stated Generate All");
    		ObservableList<String> mobileList=comboMobile.getItems();
        	int count=0;
    		mobileList.remove("");
        	for(String mobile:mobileList)
        	{
        		System.out.println("making bill for "+count+"th -"+mobile);
        		comboMobile.getSelectionModel().select(mobile);
        		//for all the values in mobileList we generate the bill then send the bill to the database
        		if((generateBill(mobile)==1))
        			{
        				if(setBill(mobile)==1)
        				{

                			//if both are successfull
                			count++;		
        				}
        			}
//        		if(count==0)
//        		{
//        			break;
//        		}
        	}
        	if(count!=0)
        	{
        		lblInfo.setText("Successfully saved "+count+" out of "+mobileList.size()+" customers");
        	}
        	System.out.println("Successfully saved "+count+" out of "+mobileList.size()+" customers");
        	doClear();
    	}
    	catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println("Unable to set All billings "+e.getMessage());
			lblInfo.setText("Unable to set All billings");
		}
    	
    	
    }

    @FXML
    void saveBill(ActionEvent event) {
    	String mobile=comboMobile.getEditor().getText();
    	try {
    		//saving billing details with partial insertion
    		int success=setBill(mobile);
			if(success==1)
			{
				System.out.println("Saved bill in database ");
				lblInfo.setText("Saved bill in database");
				doClear();
			}
				
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println("Unable to save bill in database "+e.getMessage());
			lblInfo.setText("Unable to save bill in database");
		}
    }
    void getMobileNumbers() {
    	//fetching all the mobile numbers present in database
    	try {
			psmt=com.prepareStatement("select mobile from customers");
			ResultSet table=psmt.executeQuery();
			ArrayList<String> mobileList=new ArrayList<String>();
			while(table.next())
			{
				mobileList.add(table.getString("mobile"));
			}
			comboMobile.getItems().addAll(mobileList);
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println("Unable to fetch mobile numbers"+e.getMessage());
			lblInfo.setText("Unable to fetch mobile numbers");
		}
    	
    }
    void setListView(String papers)
    {
    	try {
    		String [] paperList=papers.substring(0,papers.length()-1).split(",");
    		//writing query for checking list of price of each paper

    		String query="select * from papers where";
    		for(int i=0;i<paperList.length;i++)
    		{
    			if(i==paperList.length-1)
    			{
    				query+=" paper='"+paperList[i]+"'";
    				break;
    			}
    			query+=" paper='"+paperList[i]+"' or";
    			
    		}
//    		System.out.println(query);
    		//sening query for fetching details 
    		psmt=com.prepareStatement(query);
			ResultSet table=psmt.executeQuery();
			ArrayList<String> paperListView=new ArrayList<String>();
			ArrayList<Float> priceListView =new ArrayList<Float>();
			while(table.next())
			{
				paperListView.add(table.getString("paper"));
				priceListView.add(table.getFloat("price"));
			}
			listPaper.getItems().setAll(paperListView);
			listPrice.getItems().setAll(priceListView);
    	}
    	catch(SQLException e)
    	{
    		// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println("Error in fetching list of prices numbers"+e.getMessage());
			lblInfo.setText("Unable to fetching list of prices numbers");
    	}
    	
    }
    int generateBill(String mobile) throws SQLException
    {
    	//returns 1 or 0 or throws SQLException
    	if((mobile==null)|(mobile==""))
    	{
    		System.out.println("select a mobile number.");
    		lblInfo.setText("select a mobile number.");
    		return 0;
    	}
    	
    	int diff=0;
		psmt=com.prepareStatement("SELECT DATEDIFF(curdate(),curdate) as diff FROM customers WHERE mobile=?");
		psmt.setString(1,mobile);
		ResultSet table=psmt.executeQuery();
		if(table.next())
		{
			diff=table.getInt("diff");
		}
		else
		{
			System.out.println("This mobile does not exist in customer table");
			lblInfo.setText("This mobile does not exist in customer table");
		}
//		System.out.println(diff);
		
		//multiply the prices with the number of days and generate the bill
		ObservableList<Float> priceList=listPrice.getItems();
		float bill=0;
		for(float price :priceList)
		{
			bill+=price*diff;
		}
		//setting data to the screen
		lblTotal.setText(String.valueOf(bill));
		lblDays.setText(String.valueOf(diff));
//		System.out.println("1");
		flagMobChange=false;
		return 1;
		
    }
    int setBill(String mobile) throws SQLException{
    	//save bill details to the database
    	//return 1 if successfull else 0
    	//billid 	cust_mobile 	noofdays 	date_of_billing 	amount 	status 
    	Float bill=Float.valueOf(lblTotal.getText());
    	Integer days=Integer.valueOf(lblDays.getText());
//    	System.out.println(bill+" "+days);
    	if((mobile=="")|flagMobChange)
    	{
    		System.out.println("Generate Bill again. Incomplete/Wrong data.");
			lblInfo.setText("Generate Bill again. Incomplete/Wrong data.");
			return 0;
    	}
    	if((bill==0)|(days==0))
    	{
    		System.out.println("No pending bill.");
			lblInfo.setText("No pending bill.");
			return 0;
    	}
    	//saving billing details with partial insertion
		psmt=com.prepareStatement("insert into billing (cust_mobile,noofdays,date_of_billing,amount) values(?,?,CURDATE(),?)");
		psmt.setString(1, mobile);
		psmt.setInt(2, days);
		psmt.setFloat(3, bill);
		
		psmt.executeUpdate();
		
		//if successful then change the date
		//changing date in customers table
		changeDateInCustomer(mobile);
		String success=sms.SST_SMS.bceSunSoftSend(mobile,"Your pending Bill amount is "+bill+". For any queries contact 9******89.");
		System.out.println(success);
		return 1;
    }
    void changeDateInCustomer(String mobile) throws SQLException
    {
    	//changing date in customers table
		psmt=com.prepareStatement("update customers set curdate=DATE_ADD(CURDATE(),INTERVAL 1 DAY ) where mobile=?");
		psmt.setString(1, mobile);
		psmt.executeUpdate();
    }
    void doClear() {
    	comboMobile.getSelectionModel().select(-1);
    	listPaper.getItems().setAll(new ArrayList<String>());
        listPrice.getItems().setAll(new ArrayList<Float>());
        lblTotal.setText("");;
        lblDays.setText("");;

    }
    Connection com;
    PreparedStatement psmt;
    Boolean flagMobChange=false;//a flag to check that the mobile number has not been changed after generating the bill
//    java.sql.Date date;
    @FXML
    void initialize() {
    	com=ConnectionDB.getConnection();
    	getMobileNumbers();
        assert comboMobile != null : "fx:id=\"comboMobile\" was not injected: check your FXML file 'BillGenerator.fxml'.";
        assert listPaper != null : "fx:id=\"listPaper\" was not injected: check your FXML file 'BillGenerator.fxml'.";
        assert listPrice != null : "fx:id=\"listPrice\" was not injected: check your FXML file 'BillGenerator.fxml'.";
        assert lblTotal != null : "fx:id=\"lblTotal\" was not injected: check your FXML file 'BillGenerator.fxml'.";
        assert lblDays != null : "fx:id=\"lblDays\" was not injected: check your FXML file 'BillGenerator.fxml'.";
        assert lblInfo != null : "fx:id=\"lblInfo\" was not injected: check your FXML file 'BillGenerator.fxml'.";

    }
}
