package paperMaster;

import java.lang.reflect.Array;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ConnectionDB.ConnectionDB;

public class PaperMasterController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboTitle;

    @FXML
    private TextField txtPrice;

    @FXML
    private Label lblInfo;
    
    @FXML
    void getTitle(ActionEvent event) {
    	//fetch details of the selected row
    	String paper=comboTitle.getEditor().getText();
    	
    	//statement to fetch
    	try {
			stmt=com.prepareStatement("select price from papers where paper =?");
			stmt.setString(1, paper);
			ResultSet table=stmt.executeQuery();
//			while(table.next())
//			{
//				txtPrice.setText(String.valueOf(table.getFloat("price")));
//			}
			//OR
			if(table.next())
			{
				txtPrice.setText(String.valueOf(table.getFloat("price")));
				System.out.println("Fetched Record");
				lblInfo.setText("Fetched Record");
			}
			else
			{
				lblInfo.setText("No such record exists");
				System.out.println("Wrong record Enetered,Unable to Fetch.");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println("SOL EXception in Title "+e.getMessage());
//			setUpdate(event);
			lblInfo.setText(e.getMessage());
		}
    	

    }
    @FXML
    void setNew(ActionEvent event) {
    	//adding new record
    	try {
    		//getting record from the scene
    		String title=comboTitle.getEditor().getText();
    		float price =Float.parseFloat(txtPrice.getText());
    		
    		//executing statement
			stmt=com.prepareStatement("insert into papers values(?,?)");
			stmt.setString(1, title);
			stmt.setFloat(2,price);
			stmt.executeUpdate();
			
			System.out.println("Record Added");
			lblInfo.setText("Record Added");
			
			//updating arrayLisst of title
			getList();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println("SOL EXception in New "+e.getMessage());
//			setUpdate(event);
			lblInfo.setText(e.getMessage());

		}
    	catch (NumberFormatException e)
    	{
    	   lblInfo.setText("Price must be decimal(float)");
    	}

    }

    @FXML
    void setRemove(ActionEvent event) {
    	//removing  record
    	try {
    		//getting record from the scene
    		String title=comboTitle.getEditor().getText();
    		

    		//executing statement
			stmt=com.prepareStatement("delete from papers where paper=?");
			stmt.setString(1, title);
			stmt.executeUpdate();

			System.out.println("Record Removed");
			lblInfo.setText("Record Removed");
			
			//updating arrayLisst of title
			getList();
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			lblInfo.setText(e.getMessage());
		}

    }

    @FXML
    void clearAll(ActionEvent event) {
    	comboTitle.getEditor().setText("");
    	txtPrice.setText("");
    	System.out.println("Record Cleared");
		lblInfo.setText("Record Cleared");
		
    }

    @FXML
    void setUpdate(ActionEvent event) {
    	//updating record
    	try {
    		//getting record from the scene
    		String title=comboTitle.getEditor().getText();
    		float price =Float.parseFloat(txtPrice.getText());
    		

    		//executing statement
			stmt=com.prepareStatement("UPDATE papers set price=? WHERE paper=?");
			stmt.setFloat(1,price);
			stmt.setString(2, title);
			stmt.executeUpdate();

			System.out.println("Record Updated");
			lblInfo.setText("Record Updated");
			
			//updating arrayLisst of title
			getList();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("SQL EXception in Update "+e.getMessage());
//			e.printStackTrace();
//			setNew(event);
			lblInfo.setText(e.getMessage());
		}
    	catch (NumberFormatException e)
    	{
    	   lblInfo.setText("Price must be decimal(float)");
    	}

    }
    Connection com=null;
    PreparedStatement stmt=null;
    void getList()
    {
    	ArrayList<String> papers=new ArrayList<String>();
    	//fetch details of all the present papers in the record
    	//statement to fetch
    	try {
			stmt=com.prepareStatement("select paper from papers");
			ResultSet table=stmt.executeQuery();
			while(table.next())
			{
				papers.add(table.getString("paper"));
			}
			
		//setting list to the combo
		comboTitle.getItems().setAll(papers);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println("SQL EXception in List "+e.getMessage());
//			setUpdate(event);
			lblInfo.setText(e.getMessage());
		}
    	
    }

    @FXML
    void initialize() {
    	com=ConnectionDB.getConnection();
    	getList();
        assert comboTitle != null : "fx:id=\"comboTitle\" was not injected: check your FXML file 'PaperMaster.fxml'.";
        assert txtPrice != null : "fx:id=\"txtPrice\" was not injected: check your FXML file 'PaperMaster.fxml'.";
        assert lblInfo != null : "fx:id=\"lblInfo\" was not injected: check your FXML file 'PaperMaster.fxml'.";

    }
}
