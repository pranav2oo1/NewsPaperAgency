package billCollector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application 
{
	@Override
 public void start(Stage primaryStage) 
   {
		try {
//				System.out.println('1');
				Parent root=FXMLLoader.load(getClass().getResource("BillCollector.fxml")); 
//				System.out.println('1');
				Scene scene = new Scene(root);
				//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.setTitle("Total Bill");
				primaryStage.show();
		    } 
		catch(Exception e)
			{
				e.printStackTrace();
			}
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}
}
