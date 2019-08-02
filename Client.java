import java.awt.List;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
	Connection cn;

	// currentResults holds current results from a search() so that other methods can access them
	ResultSet currentResults;

	// currentItem holds the row number of an item the user is looking at (so we can use currentResults.absolute(currentItem) to go to it
	Integer currentItem;

	public Client(String dbname, String userid, String password) {
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
		    		+ "4. Search by Description\n");
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
				ArrayList<String> listOfPhotos = new ArrayList<String>();
				while(rs1.next()) {
					listOfPhotos.add(rs1.getString(1));
					System.out.println(rs1.getString(1));
				}
				System.out.println(listOfPhotos);
				 File[] file = new File[listOfPhotos.size()];
				 FileOutputStream fos = null;
				 byte b[];
				 Blob blob;
				
				System.out.println("Download these Photos?(Y/N)");
				if(val.next().toLowerCase().equals("y")) {
					//download the images
					for(int j = 0; j < listOfPhotos.size(); j++) {
						rs1 = st1.executeQuery("SELECT photo FROM photo WHERE name=\"" + listOfPhotos.get(j)+"\";");
						file[j] = new File(System.getProperty("user.dir")+File.separator+File.separator+listOfPhotos.get(j));
						fos = new FileOutputStream(file[j]);
						while(rs1.next()) {
							blob=rs1.getBlob("photo");
			                b=blob.getBytes(1,(int)blob.length());
			                fos.write(b);
						}
					}
					
					fos.close();
				}
				/*test if query works
				System.out.println("You Selected " + photoGraphers.get(val.nextInt()-1));
				*/
				
				st1.close();
				break;
				}
			    catch (SQLException e) {
				System.out.println("Query failed: " + e);
			    } catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
			ArrayList<String> listOfPhotos = new ArrayList<String>();
			while(rs1.next()) {
				listOfPhotos.add(rs1.getString(1));
				System.out.println(rs1.getString(1));
			}
			System.out.println(listOfPhotos);
			 File[] file = new File[listOfPhotos.size()];
			 FileOutputStream fos = null;
			 byte b[];
			 Blob blob;
			
			System.out.println("Download these Photos?(Y/N)");
			if(val.next().toLowerCase().equals("y")) {
				//download the images
				for(int j = 0; j < listOfPhotos.size(); j++) {
					rs1 = st1.executeQuery("SELECT photo FROM photo WHERE name=\"" + listOfPhotos.get(j)+"\";");
					file[j] = new File(System.getProperty("user.dir")+File.separator+File.separator+listOfPhotos.get(j));
					fos = new FileOutputStream(file[j]);
					while(rs1.next()) {
						blob=rs1.getBlob("photo");
		                b=blob.getBytes(1,(int)blob.length());
		                fos.write(b);
					}
				}
				
				fos.close();
			}
			st1.close();
			break;
		    }
		    catch (SQLException e) {
			System.out.println("Query failed: " + e);
		    } catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
					ArrayList<String> listOfPhotos = new ArrayList<String>();
					while(rs1.next()) {
						listOfPhotos.add(rs1.getString(1));
						System.out.println(rs1.getString(1));
					}
					System.out.println(listOfPhotos);
					 File[] file = new File[listOfPhotos.size()];
					 FileOutputStream fos = null;
					 byte b[];
					 Blob blob;
					
					System.out.println("Download these Photos?(Y/N)");
					if(val.next().toLowerCase().equals("y")) {
						//download the images
						for(int j = 0; j < listOfPhotos.size(); j++) {
							rs1 = st1.executeQuery("SELECT photo FROM photo WHERE name=\"" + listOfPhotos.get(j)+"\";");
							file[j] = new File(System.getProperty("user.dir")+File.separator+File.separator+listOfPhotos.get(j));
							fos = new FileOutputStream(file[j]);
							while(rs1.next()) {
								blob=rs1.getBlob("photo");
				                b=blob.getBytes(1,(int)blob.length());
				                fos.write(b);
							}
						}
						
						fos.close();
					}
					st1.close();
					break;
			    }
			    catch (SQLException e) {
				System.out.println("Query failed: " + e);
			    } catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
					
					ArrayList<String> listOfPhotos = new ArrayList<String>();
					while(rs1.next()) {
						listOfPhotos.add(rs1.getString(1));
						System.out.println(rs1.getString(1));
					}
					System.out.println(listOfPhotos);
					 File[] file = new File[listOfPhotos.size()];
					 FileOutputStream fos = null;
					 byte b[];
					 Blob blob;
					
					System.out.println("Download these Photos?(Y/N)");
					if(val.next().toLowerCase().equals("y")) {
						//download the images
						for(int j = 0; j < listOfPhotos.size(); j++) {
							rs1 = st1.executeQuery("SELECT photo FROM photo WHERE name=\"" + listOfPhotos.get(j)+"\";");
							file[j] = new File(System.getProperty("user.dir")+File.separator+File.separator+listOfPhotos.get(j));
							fos = new FileOutputStream(file[j]);
							while(rs1.next()) {
								blob=rs1.getBlob("photo");
				                b=blob.getBytes(1,(int)blob.length());
				                fos.write(b);
							}
						}
						
						fos.close();
					}
					}
					else System.out.print("Photo with the following Description does not Exist.\n");
					st1.close();
					break;
			    }
			    catch (SQLException e) {
				System.out.println("Query failed: " + e);
			    } catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
		String userid = "adang";
		String password = "eivieher";

		Client client = new Client(dbname, userid, password);

		
	}
}