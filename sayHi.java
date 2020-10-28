// interface iGreet
// {
//   public void greet();
// }
// class Hi implements iGreet
// {
//    public void greet()
//    {
//      System.out.println("Hi");
//    }
// }
// class Hello implements iGreet
// {
//    public void greet()
//    {
//      System.out.println("Hello");
//    }
// }
// class implementingInterface
// {
//    public static void main(String args[])
//    {
//    	try
//    	{
//    		String className = args[0];
//    		iGreet obj = (iGreet)Class.forName(className).newInstance();
//    		obj.greet();
//    	}
//    	catch(Exception e)
//    	{
//    		e.printStackTrace();
//    	}
//    }
// }























































// Java program to perform CRUD operations using ODBC Connectivity.
import java.sql.*;
import java.io.*;
import java.util.Scanner;
import java.lang.*;

interface iDB
{
	public void insert();
	public void display();
	public void update();
	public void delete();
	public void search();
	public void exit();
}

class dbConnection
{
	public static Connection connect()
	{
		Connection con = null;
		try
		{
			// String url = "jdbc:mysql://165.22.14.77/dbRusheel?useSSL=false";	
			// String user="rusheel";
			// String password = "rusheel";
			// Class.forName("com.mysql.jdbc.Driver");
			// con = DriverManager.getConnection(url, user, password);
			String url = "jdbc:sqlite:dbRusheel1.db";
			// String url = "jdbc:sqlite:D:\\Training\\sqlite\\dbRusheel1.db";
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection(url);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return con;
	}
}

class dbMySql implements iDB
{
	Connection con;
	public dbMySql()
	{
		con = dbConnection.connect();
	}

	public void insert()
	{
		try
		{
			while(true)
			{
				String query = "INSERT INTO  Item VALUES(?,?,?,?,?,?)";
				PreparedStatement ps = con.prepareStatement(query);
				Scanner sc = new Scanner(System.in);
				System.out.print("Enter Status: ");
				ps.setString(1, sc.next());
				System.out.print("Enter Item ID: ");
				ps.setString(2, sc.next());
				System.out.print("Enter Item Description: ");
				ps.setString(3, sc.next());
				System.out.print("Enter Unit Price: ");
				ps.setFloat(4, sc.nextFloat());
				System.out.print("Enter Stock Quantity: ");
				ps.setInt(5, sc.nextInt());
				System.out.print("Enter Supplier ID: ");
				ps.setString(6, sc.next());
				ps.executeUpdate();
				ps.close();
				System.out.print("Do you want to continue? [Y/N]");
				char choice = sc.next().charAt(0);
				char check = Character.toUpperCase(choice);
				if(check != 'Y')
				{
					break;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void display()
	{
		try
		{
			int recordFound = 0;
			String query = "select * from Item where Status = 'Active'";
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				recordFound++;
				printColumnNames(recordFound);
				String Item_ID = rs.getString("Item_ID");
				String Item_Description = rs.getString("Item_Description");
				int Unit_Price = rs.getInt("Unit_Price");
				int Stock_Quantity = rs.getInt("Stock_Quantity");
				String Supplier_ID = rs.getString("Supplier_ID");
				System.out.println(Item_ID + "\t " + Item_Description + " \t\t\t   " + Unit_Price + " \t\t " + Stock_Quantity + " \t\t" + Supplier_ID);
			}
			ps.close();
		}	
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}	


	public void update()
	{
		try
		{
			Scanner sc = new Scanner(System.in);
			System.out.print("Enter Item ID to update: ");
			String Item_ID = sc.next(); 
			System.out.println("\n1. To Update Item Description");
			System.out.println("2. To Update Unit Price");
			System.out.println("3. To Update Stock Quantity");
			System.out.print("Enter your choice: ");
			int choice = sc.nextInt();
			if(choice == 1)
			{
				String query = "UPDATE Item SET Item_Description = ? WHERE Item_ID = ? and Status = 'Active'";
				PreparedStatement ps = con.prepareStatement(query);
				System.out.print("\nEnter Item Description: ");
				ps.setString(1, sc.next());
				ps.setString(2, Item_ID);
				ps.executeUpdate();
				ps.close();
			}
			if(choice == 2)
			{
				String query = "UPDATE Item SET Unit_Price = ? WHERE Item_ID = ? and Status = 'Active'";
				PreparedStatement ps = con.prepareStatement(query);
				System.out.print("\nEnter Unit Price: ");
				ps.setFloat(1, sc.nextFloat());
				ps.setString(2, Item_ID);
				ps.executeUpdate();
				ps.close();
			}
			if(choice == 3)
			{
				String query = "UPDATE Item SET Stock_Quantity = ? WHERE Item_ID = ? and Status = 'Active'";
				PreparedStatement ps = con.prepareStatement(query);
				System.out.print("\nEnter Stock Quantity: ");
				ps.setInt(1, sc.nextInt());
				ps.setString(2, Item_ID);
				ps.executeUpdate();
				ps.close();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}		

	public void search()
	{
		try
		{
			int recordFound = 0;
			String query = "select * from Item where Status = 'Active' and Item_ID = ?";
			PreparedStatement ps = con.prepareStatement(query);
			Scanner sc = new Scanner(System.in);
			System.out.print("\nEnter Item ID to Search: ");
			ps.setString(1, sc.next());
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				recordFound++;
				printColumnNames(recordFound);
				String Item_ID = rs.getString("Item_ID");
				String Item_Description = rs.getString("Item_Description");
				int Unit_Price = rs.getInt("Unit_Price");
				int Stock_Quantity = rs.getInt("Stock_Quantity");
				String Supplier_ID = rs.getString("Supplier_ID");
				System.out.println(Item_ID + " \t\t" + Item_Description + "\t\t " + Unit_Price + " \t\t" + Stock_Quantity + " \t\t" + Supplier_ID);
			}
			ps.close();
			if(recordFound == 0)
			{
				System.out.println("No record found");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void printColumnNames(int recordFound)
	{
		if(recordFound == 1)
		{
			System.out.println("Item_ID\t\tItem_Description\t\tUnit_Price\tStock_Quantity\tSupplier_ID\n");
		}
	}

	public void delete()
	{
		try
		{
			Scanner sc = new Scanner(System.in);
			System.out.print("Enter Item ID to Delete: ");
			String Item_ID = sc.next(); 
			String query = "UPDATE Item SET Status = 'Inactive' WHERE Item_ID = ? and Status = 'Active'";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, Item_ID);
			ps.executeUpdate();
			ps.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void exit()
	{
		try
		{
			con.close();
			System.exit(0);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}

class crudDB 
{
	public static void main(String args[])
	{
		try
		{
			/*String url = "jdbc:sqlite:D:\\Training\\sqlite\\dbRusheel1.db";
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection(url);*/

			File myObj = new File("databaseName.cfg");
			Scanner myReader = new Scanner(myObj);
			String className = myReader.next();
			myReader.close();
			iDB obj = (iDB)Class.forName(className).newInstance();
			while(true)
			{
				System.out.println("\n--------------Big Bazar Item Details--------------");
				System.out.println("\nPlease choose: \n1. Add New item\n2. Show all items\n3. Update an item\n4. Delete an item\n5. Search an item\n6. exit\n");
				System.out.println("----------------------------------------------------");
				System.out.print("Enter your choice: ");
				Scanner sc = new Scanner(System.in);
				int choice = sc.nextInt();
				if(choice == 1)
				{
					obj.insert();
				}
				if(choice == 2)
				{
					obj.display();
				}
				if(choice == 3)
				{
					obj.update();
				}
				if(choice == 4)
				{
					obj.delete();
				}
				if(choice == 5)
				{
					obj.search();
				}
				if(choice == 6)
				{
					obj.exit();
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}

















// interface iGreet
// {
//   public void greet();
// }
// class Hi implements iGreet
// {
//    public void greet()
//    {
//      System.out.println("Hi");
//    } 
//    public static void main(String args[])
//    {
//      iGreet obj = new Hi();
//      obj.greet();
//  	}
// }