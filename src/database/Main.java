package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		createTable();
		Scanner reader = new Scanner(System.in);
		while (true) {
			System.out.println("");
	        System.out.println("1) Add a to do item.");
	        System.out.println("2) Remove a to do item.");
	        System.out.println("3) Print a list of my to do items.");
	        System.out.println("4) Exit and save data to database.");
	        System.out.print("Enter the number of the desired action: ");

	        String input = reader.nextLine();

	        
	        	if (input.equals("4")) {
	        		break;
	        	} else if (input.equals("1")) {
	        		System.out.println("Enter your first name: ");
	        		String fn = reader.nextLine();
	        		System.out.println("Enter your last name: ");
	        		String ln = reader.nextLine();
	        		System.out.println("Enter your to do list item: ");
	        		String item = reader.nextLine();
	        		post(fn, ln, item);
	        	} else if (input.equals("2")) {
	        		
	        	} else if (input.equals("3")) {
	        		get();
	        	}
	        }
		
		
		
        
	}
	
	public static ArrayList<String> get() throws Exception{
        try{
            Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT * FROM toDoUsers td join toDoList tdl on td.id = tdl.id ORDER BY first DESC");
            
            ResultSet result = statement.executeQuery();
            
            ArrayList<String> array = new ArrayList<String>();
            
            while(result.next()){
                System.out.print(result.getString("first"));
                System.out.print(" ");
                System.out.print(result.getString("last"));
                System.out.print(": ");
                System.out.println(result.getString("toDoItem"));
                System.out.println(" ");
                array.add(result.getString("last"));
            }
            System.out.println("All records have been selected! \n");
            return array;
            
        }catch(Exception e){System.out.println(e);}
        
        return null;
        
    }
	
    public static void post(String fn, String ln, String item) throws Exception{
        //final String fn = "Ryan";
        //final String ln = "Walsh";
        //final String item = "y";
        try{
            Connection con = getConnection();
            PreparedStatement posted = con.prepareStatement("INSERT INTO toDousers (first, last) VALUES ('"+fn+"', '"+ln+"')");      
            posted.executeUpdate();
            
            PreparedStatement posted2 = con.prepareStatement("INSERT INTO toDoList (toDoItem) VALUES ('"+item+"')");      
            posted2.executeUpdate();
        } catch(Exception e){System.out.println(e);}
        
        finally {
            System.out.println("Insert Completed. \n");
        }
    }
	
	public static void createTable() throws Exception {
		try {
			Connection con = getConnection();
			
			PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS toDoUsers(id int NOT NULL AUTO_INCREMENT, first varchar(255), last varchar(255), PRIMARY KEY(id))");
			create.executeUpdate();
			
			PreparedStatement create2 = con.prepareStatement("CREATE TABLE IF NOT EXISTS toDoList(id int NOT NULL AUTO_INCREMENT, toDoItem varchar(255), PRIMARY KEY(id))");
			create2.executeUpdate();
		}catch(Exception e){System.out.println(e);}
		finally {System.out.println("Function complete. \n");}
	}
	
	 public static Connection getConnection() throws Exception{

		  try{
		   //enter the database information that pertains to your machine
		   String driver = "com.mysql.jdbc.Driver";
		   String url = "jdbc:mysql://localhost:3306/javadb?useSSL=false";
		   String username = "";
		   String password = "";
		   Class.forName(driver);
		   
		   Connection conn = DriverManager.getConnection(url,username,password);
		   System.out.println("Connected \n");
		   return conn;
		  } catch(Exception e){System.out.println(e);}
		  
		  return null;
		 

	 }
	 
}
