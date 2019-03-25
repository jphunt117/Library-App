package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;

public class Project 
{   
    public static void main(String[] args) 
    {
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "password");
            
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println("1: Show Customer List");
            System.out.println("2: Add Customer");
            System.out.println("3: Rent Book");
            System.out.println("4: Show Rented Books");
            System.out.println("5: Exit");
            System.out.println();
            
            Scanner in = new Scanner(System.in);
            while(true)
            {
                int option;
                System.out.print("Select option: ");
                option = in.nextInt();
                System.out.println();
                if(option == 1)
                {
                    Statement stmt = conn.createStatement();
                    String query = "select * from customers order by cid";
                    ResultSet res = stmt.executeQuery(query);
                    System.out.println("CusID  Name");
                    System.out.println("-----  ----");
                    while(res.next()) 
                    {
                        String cid = res.getString("cid");
                        String name = res.getString("cname");
                        System.out.println(cid + "   " + name);
                    }
                }
                if(option == 2)
                {
                    int cid;
                    String cname;
                    String phone;
                    
                    Scanner data = new Scanner(System.in);
                    
                    System.out.print("Enter Customer Name: ");
                    cname = data.nextLine();
                    System.out.print("Enter Customer Phone Number: ");
                    phone = data.nextLine();
                    System.out.print("Enter Customer ID: ");
                    cid = data.nextInt();
                    
                    String query = "insert into customers (cid, cname, phone)" + "values (?, ?, ?)";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setInt(1, cid);
                    stmt.setString(2, cname);
                    stmt.setString(3, phone);
                    
                    stmt.execute();
                    System.out.println("Customer added.");
                }
                if(option == 3)
                {
                    int cid;
                    String cname;
                    String title;
                    String since;
                    
                    Scanner data = new Scanner(System.in);
                    
                    System.out.print("Enter Customer Name: ");
                    cname = data.nextLine();
                    System.out.print("Enter Book Title: ");
                    title = data.nextLine();
                    System.out.print("Enter Date Rented: ");
                    since = data.nextLine();
                    System.out.print("Enter Customer ID: ");
                    cid = data.nextInt();
                    
                    String query = "insert into rent (cid, cname, title, since)" + "values (?, ?, ?, ?)";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setInt(1, cid);
                    stmt.setString(2, cname);
                    stmt.setString(3, title);
                    stmt.setString(4, since);
                    
                    stmt.execute();
                    System.out.println("Book rented.");
                }
                if(option == 4)
                {
                    Statement stmt = conn.createStatement();
                    String query = "select * from rent order by cid";
                    ResultSet res = stmt.executeQuery(query);
                    while(res.next()) 
                    {
                        String cid = res.getString("cid");
                        String name = res.getString("cname");
                        String title = res.getString("title");
                        String since = res.getString("since");
                        System.out.println(cid + "   " + name + "   " + title + "   " + since);
                        //System.out.format("%4s%15s%45s%50s", cid, name, title, since);
                        //System.out.println();
                    }
                }
                if(option >= 5)
                {
                    System.out.println("Goodbye.");
                    conn.close();
                    break;
                }
                System.out.println();
            }
        } 
        catch (ClassNotFoundException ex) 
        {
        Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (SQLException ex) 
        {
        Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
