import java.awt.List;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Admin {
	Connection cn;

	// currentResults holds current results from a search() so that other methods can access them
	ResultSet currentResults;

	// currentItem holds the row number of an item the user is looking at (so we can use currentResults.absolute(currentItem) to go to it
	Integer currentItem;

	public Admin(String dbname, String userid, String password) {
		cn = null;
		currentResults = null;
		currentItem = null;

		try
		{
		    
		    try
		    {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+dbname, userid, password);
		    }
		    catch (Exception e)
		    {
			System.out.println("connection failed: " + e);
		    }
		    try
		    {
			System.out.println("SHOW databases");
			Statement st1 = cn.createStatement();
			ResultSet rs1 = st1.executeQuery("show databases");
			while (rs1.next())
			{
			    System.out.println("Database: "+rs1.getString(1));
			}
			st1.close();
		    }
		    catch (SQLException e) {
			System.out.println("Query failed: " + e);
		    }
		    try
		    {
			System.out.println("use " + dbname);
			Statement st2 = cn.createStatement();
			st2.executeUpdate("use " + dbname);
		    }
		    catch (SQLException e) {
			System.out.println("Update failed: " + e);
		    }
		   
		    System.out.println("Select an option to continue:\n"
		    		+ "1. Search by Photographer\n"
		    		+ "2. Search by Type\n"
		    		+ "3. Search by Model\n"
		    		+ "4. Search by Description\n"
		    		+ "5. Upload New Photo\n"
		    		+ "6. ALTER TABLE\n"
		    		+ "7. DROP TABLE\n"
		    		+ "8. CREATE TABLE\\Enter Custom MySQL Query\n"
		    		+ "9. Show Tables");
		    System.out.println("Make a Choice: ");
		    Scanner val= new Scanner(System.in);
		    while(!val.hasNextInt()) val.next();
		    {
		    int value=val.nextInt();	
		    switch (value)
		    {
		     
		    case 1:
		    	try
			    {
				Statement st1 = cn.createStatement();
				ResultSet rs1 = st1.executeQuery("SELECT DISTINCT photographer FROM photo");
				
				int i = 1;
				ArrayList<String> photoGraphers = new ArrayList<String>();
				
				while(rs1.next()) {
					
					photoGraphers.add(rs1.getString(1));
					System.out.println(i +". "+ rs1.getString(1));
					i++;
				}
				System.out.println("Select a Photographer from the Database: ");
				while(!val.hasNextInt()) val.next();
				String query = "SELECT name FROM photo WHERE photographer=\"" + photoGraphers.get(val.nextInt()-1) +"\";";
				rs1 = st1.executeQuery(query);
				while(rs1.next()) System.out.println(rs1.getString(1));
				
				/*test if query works
				System.out.println("You Selected " + photoGraphers.get(val.nextInt()-1));
				*/
				
				st1.close();
				break;
				}
			    catch (SQLException e) {
				System.out.println("Query failed: " + e);
			    }
		    	
		    case 2:
		    try
		    {
			System.out.println("Search by Type: ");
			Statement st1 = cn.createStatement();
			ResultSet rs1 = st1.executeQuery("SELECT DISTINCT type FROM photo");
			
			int i = 1;
			ArrayList<String> type = new ArrayList<String>();
			
			while(rs1.next()) {
				
				type.add(rs1.getString(1));
				System.out.println(i +". "+ rs1.getString(1));
				i++;
			}
			System.out.println("Select a Type from the Database: ");
			while(!val.hasNextInt()) val.next();
			String query = "SELECT name FROM photo WHERE type=\"" + type.get(val.nextInt()-1) +"\";";
			rs1 = st1.executeQuery(query);
			while(rs1.next()) System.out.println(rs1.getString(1));
			st1.close();
			break;
		    }
		    catch (SQLException e) {
			System.out.println("Query failed: " + e);
		    }
		    
		    case 3:
		    	try
			    {
		    		System.out.println("Search by Model: ");
					Statement st1 = cn.createStatement();
					ResultSet rs1 = st1.executeQuery("SELECT DISTINCT model FROM photo");
					
					int i = 1;
					ArrayList<String> model = new ArrayList<String>();
					
					while(rs1.next()) {
						
						model.add(rs1.getString(1));
						System.out.println(i +". "+ rs1.getString(1));
						i++;
					}
					System.out.println("Select a Model from the Database: ");
					while(!val.hasNextInt()) val.next();
					String query = "SELECT name FROM photo WHERE model=\"" + model.get(val.nextInt()-1) +"\";";
					rs1 = st1.executeQuery(query);
					while(rs1.next()) System.out.println(rs1.getString(1));
					st1.close();
					break;
			    }
			    catch (SQLException e) {
				System.out.println("Query failed: " + e);
			    }
			    
		    case 4:
		    	try
			    {
		    		System.out.println("Search by Description: \n	Enter the Description:(Single Word)");
					Statement st1 = cn.createStatement();
					while(!val.hasNext()) val.next();
					ResultSet rs1 = st1.executeQuery("SELECT descr from photo_descr");
					ArrayList<String> Description = new ArrayList<String>();
					int i=1;
					String checker = val.next();
					while(rs1.next()) {
					if(rs1.getString(1).toLowerCase().contains(checker.toLowerCase())) {
						
						Description.add(rs1.getString(1));
						System.out.println(i +". "+ rs1.getString(1));
						i++;
						}
					}
					if(Description.size()>0) {
					System.out.println("Select a Description from the Database: ");
					while(!val.hasNextInt()) val.next();
					String query = "SELECT name FROM photo WHERE ID = (SELECT photoID FROM photo_descr WHERE descr=\"" + Description.get(val.nextInt()-1) +"\");";
					rs1 = st1.executeQuery(query);
					while(rs1.next()) System.out.println(rs1.getString(1));
					}
					else System.out.print("Photo with the following Description does not Exist.\n");
					st1.close();
					break;
			    }
			    catch (SQLException e) {
				System.out.println("Query failed: " + e);
			    }
		    	
		    case 5:
		    	try
			    {
				Statement st1 = cn.createStatement();
				System.out.print("Add a New Photo to the Database\n");
				String name, photographer, type, model, photo, path;
				System.out.println("Enter a Name for the Photo:");
				name = val.next();
				System.out.println("Enter the Photographer's Name: ");
				photographer = val.next();
				System.out.println("Enter Photo Type: ");
				type = val.next();
				System.out.println("Enter Photo Model: ");
				model = val.next();
				System.out.println("Enter FULL path to photo(e.g. /USER/BIN/abc.jpg) ");
				path = val.next();
				
				
				File file=new File(path);
	            FileInputStream fis=new FileInputStream(file);
				
				
				PreparedStatement ps = cn.prepareStatement("INSERT INTO photo(name, photographer, type, model, photo) VALUES(?,?,?,?,?)");
				ps.setString(1, name);
				ps.setString(2, photographer);
				ps.setString(3, type);
				ps.setString(4, model);
				ps.setBinaryStream(5, fis, (int)file.length());
				
				int i = ps.executeUpdate();
				if(i>0) System.out.println("Successfully Uploaded the Image.");
				ps.close();
				fis.close();
				/*test if query works
				System.out.println("You Selected " + photoGraphers.get(val.nextInt()-1));
				*/
				
				st1.close();
				break;
				}
			    catch (Exception e) {
				System.out.println(e);
			    }
		    	
		    case 6:
		    	try
			    {
				Statement st1 = cn.createStatement();
				ResultSet rs1 = st1.executeQuery("SHOW TABLES");
				
				System.out.println("Select Table to Alter");
				int i = 1;
				ArrayList<String> tableNames = new ArrayList<String>();
				
				while(rs1.next()) {
					
					tableNames.add(rs1.getString(1));
					System.out.println(i +". "+ rs1.getString(1));
					i++;
				}
				
				ResultSetMetaData md = rs1.getMetaData();
				int col = md.getColumnCount();
				for (int j = 1; j <= col; j++){
				String col_name = md.getColumnName(j);
				System.out.print(col_name+"\t");
				}
				System.out.println();
				DatabaseMetaData dbm = cn.getMetaData();
				ResultSet rs2 = dbm.getColumns(null,"%",tableNames.get(val.nextInt()),"%");
				while (rs2.next()){
				String col_name = rs2.getString("COLUMN_NAME");
				String data_type = rs2.getString("TYPE_NAME");
				int data_size = rs2.getInt("COLUMN_SIZE");
				int nullable = rs2.getInt("NULLABLE");
				System.out.print(col_name+"\t"+data_type+"("+data_size+")"+"\t");
				if(nullable == 1){
					System.out.print("YES\t");
				}else{
					System.out.print("NO\t\n");
				}
				}
				
				System.out.println("Enter ALTER query for the Table(FORMAT: ALTER TABLE table_name ADD column_name datatype");
				st1.executeUpdate(val.next());
				System.out.println("Done!");
				st1.close();
				break;
				}
			    catch (SQLException e) {
				System.out.println("Query failed: " + e);
			    }
		    	
		    case 7:
		    	try
			    {
				Statement st1 = cn.createStatement();
				ResultSet rs1 = st1.executeQuery("SHOW TABLES");
				
				int i = 1;
				ArrayList<String> tableNames = new ArrayList<String>();
				
				while(rs1.next()) {
					
					tableNames.add(rs1.getString(1));
					System.out.println(i +". "+ rs1.getString(1));
					i++;
				}
				
				System.out.println("Select Table to DROP");
				
				System.out.println("Enter Table Number to Drop");
				st1.executeUpdate("DROP TABLE " +tableNames.get(val.nextInt()-1));
				System.out.println("Done!");
				st1.close();
				break;
				
				
		}  catch (SQLException e) {
			System.out.println("Query failed: " + e);
	    }
		    
		    case 8:
		    	try
			    {
		    	Scanner queryCatcher = new Scanner(System.in);
		    	String query;
		    	System.out.println("Enter Your Query(FORMAT CREATE TABLE table_name(column_name1 datatype(size) constraint): ");
				Statement st1 = cn.createStatement();
				query = queryCatcher.nextLine();
				System.out.println(query);
				st1.executeUpdate(query);
				System.out.println("Done!");
				st1.close();
				break;
				
				
		}  catch (SQLException e) {
			System.out.println("Query failed: " + e);
	    }
		    	
		    case 9:
		    	try
			    {
		    		
		    		Statement st1 = cn.createStatement();
					ResultSet rs1 = st1.executeQuery("SHOW TABLES");
					
					System.out.println("Select Table to See Description: ");
					int i = 1;
					ArrayList<String> tableNames = new ArrayList<String>();
					
					while(rs1.next()) {
						
						tableNames.add(rs1.getString(1));
						System.out.println(i +". "+ rs1.getString(1));
						i++;
					}
					
					ResultSetMetaData md = rs1.getMetaData();
					int col = md.getColumnCount();
					for (int j = 1; j <= col; j++){
					String col_name = md.getColumnName(j);
					System.out.print(col_name+"\t");
					}
					System.out.println();
					DatabaseMetaData dbm = cn.getMetaData();
					ResultSet rs2 = dbm.getColumns(null,"%",tableNames.get(val.nextInt()),"%");
					while (rs2.next()){
					String col_name = rs2.getString("COLUMN_NAME");
					String data_type = rs2.getString("TYPE_NAME");
					int data_size = rs2.getInt("COLUMN_SIZE");
					int nullable = rs2.getInt("NULLABLE");
					System.out.print(col_name+"\t"+data_type+"("+data_size+")"+"\t");
					if(nullable == 1){
						System.out.print("YES\t");
					}else{
						System.out.print("NO\t\n");
					}
					}
				break;
				
				
		}  catch (SQLException e) {
			System.out.println("Query failed: " + e);
	    }
		    	
		    cn.close();
		}
	    }
	}
		
		catch (SQLException e)
		{
		    System.out.println("Some other error: " + e);
		}
	}

	public static void main (String[] args) {
		String dbname = "photo_gallery";
		String userid = "";
		String password = "";

		Admin Admin = new Admin(dbname, userid, password);

		
	}
}