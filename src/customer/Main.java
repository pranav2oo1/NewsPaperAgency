package customer;
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
				Parent root=FXMLLoader.load(getClass().getResource("Customer.fxml")); 
//				System.out.println('1');
				Scene scene = new Scene(root);
				//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.setTitle("Customer");
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
