package hawkerControl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import ConnectionDB.ConnectionDB;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class HawkerControlController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView imgAadhar;

    @FXML
    private TextField txtMob;

    @FXML
    private TextField txtAdderess;

    @FXML
    private TextField txtSalary;

    @FXML
    private ListView<String> listArea;

    @FXML
    private ComboBox<String> comboName;

    @FXML
    private Label lblInfo;

    @FXML
    private ComboBox<String> comboArea;

    

    @FXML
    void doClear(ActionEvent event) {
    	
    	imgAadhar.setImage(null);
    	txtMob.setText("");;
    	txtAdderess.setText("");;
    	txtSalary.setText("");;
    	listArea.getItems().setAll(Arrays.asList(""));
    	comboName.getEditor().setText("");
    	lblInfo.setText("");
    	comboArea.getSelectionModel().select(-1);
    	
    	
    }
    @FXML
    void setAadhar(ActionEvent event) {
    	//getting file as choosed
    	FileChooser img=new FileChooser();
    	img.getExtensionFilters().add(new ExtensionFilter("image","*.jpg","*.png","*.JPG","*.jpeg","*.gif"));
    	File selected=img.showOpenDialog(null);
    	if(selected!=null)
    	{
    		imgPath=selected.getPath();
        	showImgView(imgPath);
    		
    	}
    	else
    	{
    		System.out.println("no image was selected");
    	}
    	
    }
    @FXML
    void setNew(ActionEvent event) {
    	    	try {
    	    		//gathering data for creating a record
    	        	String name=comboName.getEditor().getText();
//    	    		imgAadhar -- imgPath
    	        	String path=saveImg(imgPath,name);
    	        	String mobile=txtMob.getText();
    	        	String address=txtAdderess.getText();
    	        	int salary=Integer.parseInt(txtSalary.getText());
//    	        	listArea -- areas
    	        	String area="";
    	        	ObservableList<String> areas=listArea.getItems();
    	        	for(String x:areas)
    	        	{
    	        		area+=x+",";
    	        	}
    	        	
    	        	//executing query for adding a new record

			stmt=com.prepareStatement("insert into hawkers values(?,?,?,?,?,?,CURDATE())");
			stmt.setString(1,name);
			stmt.setString(2,mobile);
			stmt.setString(3, address);
			stmt.setString(4, area);
			stmt.setString(5,path);
			stmt.setInt(6,salary);
			stmt.executeUpdate();
			
			lblInfo.setText("New Record Added");
			System.out.println("New Record Added");
			
	    	//adding record form nameList in combo
	    	comboName.getItems().add(name);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			lblInfo.setText(e.getMessage());
			System.out.println("SQLException in NEW "+e.getMessage());
		}
    	catch(NumberFormatException e)
    	{
//    		e.printStackTrace();
    		lblInfo.setText("Salary must be Int(Number)");
			System.out.println("NumberFormatException in NEW "+e.getMessage());
		
    	}


    }

    @FXML
    void setRemove(ActionEvent event) {
    	//gathering data for delete
    	String name=comboName.getEditor().getText();
    	deleteImg(name);//deleting image from the image folder
    	showImgView("images/choose.png");
    	
    	//executing query for adding a new record
    	try {
			stmt=com.prepareStatement("delete from hawkers where name=?");
			stmt.setString(1,name);
			stmt.executeUpdate();
			
			lblInfo.setText("Record Deleted");
			System.out.println("Record Deleted");

	    	//removing record form nameList in combo
	    	comboName.getItems().remove(name);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			lblInfo.setText(e.getMessage());
			System.out.println("SQLException in Delete "+e.getMessage());
		}
    	
    }

    @FXML
    void setUpdate(ActionEvent event) {

    	
    	try {
    		String name=comboName.getEditor().getText();
        	
        	//gathering data for creating a record
//        	imgAadhar -- imgPath
    		//saving this Img and deleting prev
        	String path=saveImg(imgPath,name);
        	deleteImg(name);//delee prev image form the image folder
        	
        	String mobile=txtMob.getText();
        	String address=txtAdderess.getText();
        	int salary=Integer.parseInt(txtSalary.getText());
//        	listArea -- areas
        	String area="";
        	ObservableList<String> areas=listArea.getItems();
        	for(String x:areas)
        	{
        		area+=x;
        	}
        	
        	//executing query for adding a new record
			stmt=com.prepareStatement("update hawkers set mobile=?,address=?,areas=?,aadharpic=?,salary=?,doj=CURDATE() where name=?");
			stmt.setString(1,mobile);
			stmt.setString(2, address);
			stmt.setString(3, area);
			stmt.setString(4,path);
			stmt.setInt(5,salary);
			stmt.setString(6,name);
			stmt.executeUpdate();
			
			lblInfo.setText("Record Updates");
			System.out.println("Record Updated");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			lblInfo.setText(e.getMessage());
			System.out.println("SQLException in NEW "+e.getMessage());
		}
    	catch(NumberFormatException e)
    	{
//    		e.printStackTrace();
    		lblInfo.setText("Salary must be Int(Number)");
			System.out.println("NumberFormatException in NEW "+e.getMessage());
		
    	}
    }
    @FXML
    void setAreaList(KeyEvent event) {
    	//delete when delete is pressed on the list
    	if(event.getCode().equals(KeyCode.DELETE))
    	{
    		//if delete is pressed
    		doDeleteArea(null);
    		
    	}
    }
    @FXML
    void doAddArea(ActionEvent event) {
    	//add element to list
    	String area=comboArea.getSelectionModel().getSelectedItem();
    	comboArea.getSelectionModel().select(-1);
    	//added item to list
    	listArea.getItems().add(area);
    	//remove that item from combo
    	comboArea.getItems().remove(area);
    	
    	System.out.println("Area Added to List");
    	lblInfo.setText("Area Added to List");
    }
    @FXML
    void doDeleteArea(ActionEvent event) {
    	//remove element to list
    	ObservableList<String> selectedAreas=listArea.getSelectionModel().getSelectedItems();
//    	System.out.println(selectedAreas);
    	
    	//add items to combo
//    	System.out.println(selectedAreas);
    	comboArea.getItems().addAll(selectedAreas);
    	
    	//remove items from list,selectedAreas is dynamically changed in removeAll,so add first
    	listArea.getItems().removeAll(selectedAreas);
    	
    	System.out.println("Removed Selected Areas");
    	lblInfo.setText("Removed Selected Areas");
    }
    @FXML
    void getRecord(ActionEvent event) {
    	//fetching selected record from DB
    	String name=comboName.getEditor().getText();
    	try {
			stmt=com.prepareStatement("select * from hawkers where name=?");
			stmt.setString(1, name);
			ResultSet table=stmt.executeQuery();
			//1 or more records
			if(table.next())
			{
				//record fetched
				//setting data of fetched record
//	    		imgAadhar -- imgPath
	        	String path=table.getString("aadharpic");
	        	if(path!=null)
	        	{
	        		try {
		    			
		    			//showing preview of file in ImgViewer
		    			InputStream pic = new FileInputStream(path);
		    			Image preview=new Image(pic);
		    			imgAadhar.setImage(preview);
		    			System.out.println("Found Aadhar IMG");
		    		} catch (FileNotFoundException e) {
		    			//e.printStackTrace();
		    			System.out.println("Image Not Found "+e.getMessage());
		    			lblInfo.setText("Image not Found");
		    		}
	        	}
	        	
	        	String mobile=table.getString("mobile");
	        	txtMob.setText(mobile);
	        	
	        	String address=table.getString("address");
	        	txtAdderess.setText(address);
	        	
	        	int salary=table.getInt("salary");
	        	txtSalary.setText(String.valueOf(salary));
	        	
//	        	listArea -- areas
	        	String area=table.getString("areas");
	        	String[] areas=area.split(",");
	        	
	        	listArea.getItems().setAll(Arrays.asList(areas));
	        	
	        	System.out.println("Fetched Record from DB in getRecord()");
	        	lblInfo.setText("Fetched Record from DB");
				
			}
			else
			{
				System.out.println("No Record in DB by the name :"+name);
				lblInfo.setText("No Record in DB by the name :"+name);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println("SQLException in getRecord "+e.getMessage());
			lblInfo.setText(e.getMessage());
		}
//    	catch(NullPointerException e)
//    	{
//    		e.printStackTrace();
//			System.out.println("Null pointer ,Img not set in DB");
//			lblInfo.setText("image not found");
//    	}
    	
    	
    }

    String saveImg(String path,String name)
    {
    	//extracting name of image from name and path
    	int lastIndexOf = path.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return null; // empty extension
        }
        String extension=path.substring(lastIndexOf);//return ".png"
    	String copyName=name.replaceAll("\\s+", "");//removes all whitespace from name
    	String copyPath="images/"+copyName+extension;//copy to images folder in the current location of database
    	
    	//copy file to a folder of images in program		
    	File imgFile=new File(path);
    	File des=new File(copyPath);
//    	System.out.println(des.getAbsolutePath());
//    	System.out.println(imgFile.getPath());
    	FileOutputStream copyStream=null;
    	FileInputStream imgStream=null;
    	path=null;
    	try {
			copyStream=new FileOutputStream(des);
			imgStream = new FileInputStream(imgFile);
			int bit;
			while((bit=imgStream.read())!=-1) {
				copyStream.write(bit);
			}
			path=copyPath;
			
			System.out.println("Image copied Successfully..");
			lblInfo.setText("Image copied Successfully..");
			
		} 
		catch (FileNotFoundException e) {
			//e.printStackTrace();
			System.out.println("Failed to copy Image "+e.getMessage());
			lblInfo.setText("Failed to copy Image");
		}
		catch (IOException e) {
			//e.printStackTrace();
			System.out.println("Failed to copy Image "+e.getMessage());
			lblInfo.setText("Failed to copy Image");
		}
    	
    	//return path if everything is successful else null is returned,and img address is received only if path is not null,in getRecord
    	return path;

    }
    boolean deleteImg(String name)
    {
    	//extracting name of image from name and path
    	String copyName=name.replaceAll("\\s+", "");//removes all whitespace from name
    	File imgDir=new File("images");
    	//checking all the files for this name
    	boolean found=false;
    	File []list=imgDir.listFiles();
    	for(File pics:list)
    	{
    		if(pics.getName().contains(copyName))
    		{
    			found=true;
    			break;
    		}
    	}
    	
    	return found;
    }
    void showImgView(String selected)
    {
    	try {
			
			//showing preview of file in ImgViewer
			InputStream pic = new FileInputStream(selected);
			Image preview=new Image(pic);
			imgAadhar.setImage(preview);
//			System.out.println(selected.getPath());
//			System.out.println(selected.getName());
			System.out.println("Found Aadhar IMG");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println("Image Not Found "+e.getMessage());
			lblInfo.setText("Image not Found");
		}
    }
    
    Connection com=null;
    PreparedStatement stmt=null;
    String imgPath=null;
    
    void getNameList(){
    	try {
    		//executing query to fetch all names from DB
			stmt=com.prepareStatement("select name from hawkers where 1");
			ResultSet table=stmt.executeQuery();
			ArrayList<String> nameList=new ArrayList<String>();
			while(table.next())
			{
				nameList.add(table.getString("name"));
			}
			comboName.getItems().addAll(nameList);
			System.out.println("Fetched names from record");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println("Error in fetching nameList "+e.getMessage());
		}
    }

    @FXML
    void initialize() {
    	com=ConnectionDB.getConnection();
    	comboArea.getItems().addAll(Arrays.asList("flo","stu","mlo","stud","flood","ploug"));
        getNameList();
        listArea.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        assert imgAadhar != null : "fx:id=\"imgAadhar\" was not injected: check your FXML file 'HawkerControl.fxml'.";
        assert txtMob != null : "fx:id=\"txtMob\" was not injected: check your FXML file 'HawkerControl.fxml'.";
        assert txtAdderess != null : "fx:id=\"txtAdderess\" was not injected: check your FXML file 'HawkerControl.fxml'.";
        assert txtSalary != null : "fx:id=\"txtSalary\" was not injected: check your FXML file 'HawkerControl.fxml'.";
        assert listArea != null : "fx:id=\"listArea\" was not injected: check your FXML file 'HawkerControl.fxml'.";
        assert comboName != null : "fx:id=\"comboName\" was not injected: check your FXML file 'HawkerControl.fxml'.";
        assert lblInfo != null : "fx:id=\"lblInfo\" was not injected: check your FXML file 'HawkerControl.fxml'.";
        assert comboArea != null : "fx:id=\"comboName\" was not injected: check your FXML file 'HawkerControl.fxml'.";
    }
}
