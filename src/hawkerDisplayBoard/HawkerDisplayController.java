/**
 * Sample Skeleton for 'HawkerDisplay.fxml' Controller Class
 */

package hawkerDisplayBoard;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import ConnectionDB.ConnectionDB;
import billHistory.BillBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class HawkerDisplayController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
   
    @FXML // fx:id="lblInfo"
    private Label lblInfo; // Value injected by FXMLLoader

    @FXML // fx:id="tblHawker"
    private TableView<HawkerBean> tblHawker; // Value injected by FXMLLoader
    @FXML
    void fetchAll(ActionEvent event) {
    	try {
			ObservableList<HawkerBean> items=getAllItems();
			
			tblHawker.getItems().setAll(items);
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
			System.out.println("No details fetched "+e.getMessage());
//			lblInfo.Text("Error fetching details of "+mob);
		}
    }
    ObservableList<HawkerBean> getAllItems() throws SQLException
    {
//    	name	mobile	address	areas	aadharpic	salary	doj
    	//get all the items present in the list and store in ObservaleList of BillBean
    	psmt=com.prepareStatement("select *,DATE_FORMAT(doj, '%d-%b-%y') as doj2 from hawkers");
    	ResultSet table=psmt.executeQuery();
    	
		return generateTableItems(table);
    	
    }
    ObservableList<HawkerBean> generateTableItems(ResultSet table) throws SQLException

    {
    	salary=0;
    	ObservableList<HawkerBean> hawkerList=FXCollections.observableArrayList();
//    	name	mobile	address	areas	aadharpic	salary	doj
    	
    	while(table.next()) {
			
			salary+=table.getInt("salary");
			
			//making an array item
//HawkerBean(String name, String mobile, String address, String areas, String aadharpic, String doj,int salary
			HawkerBean row=new HawkerBean(table.getString("name"),
					table.getString("mobile"), 
					table.getString("address"), 
					table.getString("areas"),
					table.getString("aadharpic"),
					table.getString("doj2"),
					table.getInt("salary"));
			//storing in array
			hawkerList.add(row);
		}
    	return hawkerList;
    }
    void calcTotalAmount() {
    	lblInfo.setText("Total Salary: "+salary);
    	System.out.println("Total Salary: "+salary);
    }
    void setTableColumns()
    {
//    	name	mobile	address	areas	aadharpic	salary	doj

    	//setting all the table columns for the data fetched from the bill
    	TableColumn<HawkerBean,String> name=new  TableColumn<HawkerBean,String>("name");
    	name.setCellValueFactory(new PropertyValueFactory<>("name"));
    	
    	TableColumn<HawkerBean,String> mob=new  TableColumn<HawkerBean,String>("mobile");
    	mob.setCellValueFactory(new PropertyValueFactory<>("mobile"));
    	
    	TableColumn<HawkerBean,String> address=new  TableColumn<HawkerBean,String>("address");
    	address.setCellValueFactory(new PropertyValueFactory<>("address"));
    	
    	TableColumn<HawkerBean,String> areas=new  TableColumn<HawkerBean,String>("areas");
    	areas.setCellValueFactory(new PropertyValueFactory<>("areas"));
    	
    	TableColumn<HawkerBean,String> aadharpic=new  TableColumn<HawkerBean,String>("aadharpic");
    	aadharpic.setCellValueFactory(new PropertyValueFactory<>("aadharpic"));
  
    	TableColumn<HawkerBean,Integer> salary=new  TableColumn<HawkerBean,Integer>("salary");
    	salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
    	//adding declared tables to the tableView
    	tblHawker.getColumns().add(name);
    	tblHawker.getColumns().add(mob);
    	tblHawker.getColumns().add(address);
    	tblHawker.getColumns().add(areas);
    	tblHawker.getColumns().add(aadharpic);
    	tblHawker.getColumns().add(salary);
    	
    }
    
    @FXML
    void openPic(MouseEvent event)
    {
    	if (event.getClickCount() == 2) //Checking double click
        {
    		String location=tblHawker.getSelectionModel().getSelectedItem().getAadharpic();
            System.out.println(tblHawker.getSelectionModel().getSelectedItem().getAadharpic());
//            System.out.println(tableID.getSelectionModel().getSelectedItem().getBrewery());
//            System.out.println(tableID.getSelectionModel().getSelectedItem().getCountry());
    		try {
				Desktop.getDesktop().open(new File(location) );
				System.out.println("Double Click And opened. "+location);
			} catch (IOException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				lblInfo.setText("These pic does not exist."+location);
				System.out.println("These pic does not exist."+location);
			}
        }
    }
    Connection com;
    PreparedStatement psmt;
    int salary;
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	com=ConnectionDB.getConnection();
    	setTableColumns();
        assert tblHawker != null : "fx:id=\"tblHawker\" was not injected: check your FXML file 'HawkerDisplay.fxml'.";

    }
}
