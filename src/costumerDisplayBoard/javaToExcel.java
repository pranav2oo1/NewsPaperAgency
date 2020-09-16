package costumerDisplayBoard;

import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class javaToExcel {
    public void start(TableView<UserBean> table) throws IOException {
        Workbook workbook = new HSSFWorkbook();
        Sheet spreadsheet = workbook.createSheet("Records");
        Row row = spreadsheet.createRow(0);
        for (int j = 0; j < table.getColumns().size(); j++) {
            row.createCell(j).setCellValue(table.getColumns().get(j).getText());
        }
        for (int i = 0; i < table.getItems().size(); i++) {
            row = spreadsheet.createRow(i + 1);
            for (int j = 0; j < table.getColumns().size(); j++) {
                if(table.getColumns().get(j).getCellData(i) != null) { 
                    row.createCell(j).setCellValue(table.getColumns().get(j).getCellData(i).toString()); 
                }
                else {
                    row.createCell(j).setCellValue("");
                }   
            }
        }
       
        FileOutputStream fileOut = new FileOutputStream( saveLoc());
        workbook.write(fileOut);
        fileOut.close();
    }
    public String saveLoc()
    {
    	FileChooser save=new FileChooser();
    	save.getExtensionFilters().add(new ExtensionFilter("Excel","*.xls","*.xltx","*.xltm"));
    	save.setTitle("Choose Location");
    	File selected=save.showSaveDialog(null);
   		if(selected==null)
   		{
			System.out.println("No Path was selected");
			return "Records.xls";
		}	
   		else {

   	    	System.out.println(selected.getAbsolutePath());
   			if(selected.isFile())
   	   		{
   	   			return selected.getAbsolutePath();
   	   		}
   			else
   			{
   				return selected.getAbsolutePath()+".xls";
   			}
   		}
    	
   		
   		
    }
    public String openDoc()
	{
    	FileChooser open=new FileChooser();
    	open.getExtensionFilters().add(new ExtensionFilter("Excel","*.xls","*.xltx","*.xltm"));
    	open.setTitle("Choose Location");
    	File selected=open.showOpenDialog(null);
   		if(selected==null)
   		{
			System.out.println("No Path was selected");
			return "No path was selected";
		}	
   		else {
   			if(selected.exists())
   	   		{
   				try {
					Desktop.getDesktop().open(selected);
				} catch (IOException e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
					return "Unable to open File "+e.getMessage();
				}
   	   			return "Opened selected File";
   	   		}
   			else {
   				return "No such file on loaction";
   			}
   			
   		}
 
	}
    
}