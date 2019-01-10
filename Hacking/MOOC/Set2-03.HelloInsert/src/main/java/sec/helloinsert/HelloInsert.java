package sec.helloinsert;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import org.h2.tools.RunScript;

public class HelloInsert {

    public static void main(String[] args) throws Exception {
        // Open connection to a database -- do not alter this code
        String databaseAddress = "jdbc:h2:file:./database";
        if (args.length > 0) {
            databaseAddress = args[0];
        }

        Connection connection = DriverManager.getConnection(databaseAddress, "sa", "");

        try {
            // If database has not yet been created, insert content
            RunScript.execute(connection, new FileReader("sql/database-schema.sql"));
            RunScript.execute(connection, new FileReader("sql/database-import.sql"));
        } catch (Throwable t) {
            System.err.println(t.getMessage());
        }

        // Add the code that first reads the agents from the database, then
        // asks for an agent (id and name) and stores the agent to the database.
        // Finally, the program prints the agents in the database again.
        System.out.println("============== LECTURE INITIALE DE LA BASE ==============");  
        vPrintDB(connection);
        System.out.println("=================== AJOUT D'UN AGENT ====================");  
        vAddAgent(connection); 
        System.out.println("=========)===== LECTURE FINALE DE LA BASE =====)=========");  
        vPrintDB(connection);
        


        // Close the connection
        
        connection.close();
    }
    
   static void vPrintDB(Connection connection){
       
       try {
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM Agent");
        
        // Do something with the results -- here, we print the books
        while (resultSet.next()) {
            String id = resultSet.getString("id");
            String name = resultSet.getString("name");

            System.out.println(id + "\t" + name);            
        }       
            
            resultSet.close();
        
           } catch (SQLException  t) {
            System.err.println(t.getMessage());
        }
   };
   
   static void vAddAgent(Connection connection){
        try {
            
            String id ="";
            String name="";        
            String query = "INSERT INTO Agent (id, name) VALUES" + "(?,?)";
            Scanner reader = new Scanner(System.in);

            System.out.println("\nWhat is the id?");
            id = reader.nextLine();
            System.out.println("\nWhat is the name?");
            name = reader.nextLine();
            
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString( 1, id); 
            pstmt.setString( 2, name); 
            
            // execute insert SQL stetement
	    pstmt.executeUpdate();
            
            
        } catch (SQLException  t) {
            System.err.println(t.getMessage());
        }
   };
}
