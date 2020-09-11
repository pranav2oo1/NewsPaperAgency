package ConnectionDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
	public static Connection getConnection()
	{
		Connection com=null;
		try {
			com=DriverManager.getConnection("jdbc:mysql://localhost/NewsAgency","root", "");
			if(!com.isClosed())
			{
				System.out.println("Connection Established Successfully");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
			
		}
		
		return com;
	}

}
