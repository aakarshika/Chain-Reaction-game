
/**
 * Write a description of class Mydb here.
 *
 * @author (Richa Sharma/Aakarshika Priydarshi/Viswa Teja)
 * @version (a version number or a date)
 */
import java.util.HashMap;
import java.util.ArrayList;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.*;
public class Mydb
{
    // instance variables - replace the example below with your own
    Connection dbConnection = null;
    private String dbusername,dbpassword;
    PreparedStatement preparedStatement = null;
    public Mydb(String username, String password)
    {
        dbusername=username;
        dbpassword=password;
    }

    int createPlayer(String username, String password, String name){
  
        String insertTableSQL = "INSERT INTO players"
                + "(USERNAME, NAME, PASSWORD) VALUES"
                + "(?,?,SHA1(?))";
        
        String insertTableSQL2 = "INSERT INTO login"
                + "(PID,STATUS) VALUES"
                + "(?,0)";
                        
        try {
            dbConnection = getDBConnection();
            
            preparedStatement = dbConnection.prepareStatement(insertTableSQL);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, password);
            preparedStatement.executeUpdate();
            System.out.println("Record is inserted into Players table!");
            
            String q2="select MAX(pid) as lpid from players;";
            preparedStatement = dbConnection.prepareStatement(q2);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            String lastid = rs.getString("lpid");
            System.out.println(lastid);
            
            preparedStatement = dbConnection.prepareStatement(insertTableSQL2);
            preparedStatement.setString(1, lastid);
            preparedStatement.executeUpdate();
            
            
            
            close();
            return 1;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
    
   int updateScore(String pid){
    String updateTableSQL = "UPDATE players set score = ? where pid= ?;";
    String selectTableSQL = "SELECT score from players where pid= ?;";
    
    try{
        dbConnection = getDBConnection();
        
        preparedStatement = dbConnection.prepareStatement(selectTableSQL);
        preparedStatement.setString(1, pid);
        ResultSet rs = preparedStatement.executeQuery();
        
        System.out.println(rs.toString());
        rs.next();
        int score = Integer.parseInt(rs.getString("score"));
        score= score+10;
        System.out.println(score + "sfdghgfh");
        
        
            preparedStatement = dbConnection.prepareStatement(updateTableSQL);
            preparedStatement.setString(1, ""+score);
            preparedStatement.setString(2, pid);
            
            preparedStatement.executeUpdate();
            
        
        close();
        return 1;
                
  } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    return 0;
    }
    
   int createGame(int nop){
        String insertTableSQL= "INSERT into games" + "(NOP) VALUES" + "(?)";
        String selectTableSQL= "SELECT MAX(gid) as lgid from games;";   
         try{
            dbConnection = getDBConnection();
            
            preparedStatement = dbConnection.prepareStatement(insertTableSQL);
            preparedStatement.setString(1, ""+nop);
            preparedStatement.executeUpdate();     
            
            
            preparedStatement = dbConnection.prepareStatement(selectTableSQL);
            ResultSet rs = preparedStatement.executeQuery();
                rs.next();
                int lastid = Integer.parseInt(rs.getString("lgid"));
                System.out.println(lastid);
            
            close();
            return lastid;
                    
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }  
    
   int updateWinner(int pid, int gid){
    String updateTableSQL = "UPDATE games set winner = ? where gid= ?;";
    try{
       dbConnection = getDBConnection();
        
       preparedStatement = dbConnection.prepareStatement(updateTableSQL);
       preparedStatement.setInt(1,pid);
       preparedStatement.setInt(2,gid);
            
       preparedStatement.executeUpdate();
                    
       close();
       return 1;
                
      } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
   }
   
   int addPlayers(int gid, int pid){
       String insertTableSQL= "INSERT into games_played" + "(GID,PID) VALUES" + "(?,?)"; 
       
       try{
        dbConnection = getDBConnection();
        
        preparedStatement = dbConnection.prepareStatement(insertTableSQL);
        preparedStatement.setInt(1, gid);
        preparedStatement.setInt(2,pid);
        preparedStatement.executeUpdate();     
        
        close();
        return 1;
                    
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0; 
    }
   
   int moves(int pid, int gid, int x, int y, int order){
       String insertTableSQL= "INSERT into moves" + "(GID,PID,X,Y,ORDER) VALUES" + "(?,?,?,?,?)"; 
            
       try{
            dbConnection = getDBConnection();
            
            preparedStatement = dbConnection.prepareStatement(insertTableSQL);
            preparedStatement.setInt(1, gid);
            preparedStatement.setInt(2, pid);
            preparedStatement.setInt(3, x);
            preparedStatement.setInt(4, y);
            preparedStatement.setInt(5, order);
            preparedStatement.executeUpdate();     
            
            close();
            return 1;
                    
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;   
   }
   
   int login(int  pid){
       String updateTableSQL= "UPDATE login set status='1' where pid=?;";
       
       try{
           dbConnection = getDBConnection();
            
           preparedStatement = dbConnection.prepareStatement(updateTableSQL);
           preparedStatement.setInt(1,pid);         
           preparedStatement.executeUpdate();
                        
           close();
           return 1;
                    
          } catch (SQLException e) {
                System.out.println(e.getMessage());
           }
            return 0;
           
    }
    
   ArrayList <HashMap <String, Integer>> getMoves(int gid){
       ArrayList <HashMap <String, Integer>> result= new ArrayList<>(); 
       String selectTableSQL= "SELECT * from moves where gid=? order by movesorder;";   
       try{
            dbConnection = getDBConnection();
            
            preparedStatement = dbConnection.prepareStatement(selectTableSQL);
            preparedStatement.setInt(1, gid);
            ResultSet rs = preparedStatement.executeQuery();
            HashMap<String, Integer> map=new HashMap<>();
            while(rs.next()){
                int x = rs.getInt("x");
                int y = rs.getInt("y");
                int pid = rs.getInt("pid");
                map.put("x",x);
                map.put("y",x);
                map.put("pid",x);
                
                result.add(map);
                
                map=new HashMap<>();
                
            }
            close();
            System.out.println(result.toString());
            return result;
                    
       } catch (SQLException e) {
            System.out.println(e.getMessage());
       }
       return result;
    } 
    
    ArrayList <HashMap <String, String>> getScores(){
       ArrayList <HashMap <String, String>> result= new ArrayList<>(); 
       String selectTableSQL= "SELECT * from players;";   
       try{
            dbConnection = getDBConnection();
            
            preparedStatement = dbConnection.prepareStatement(selectTableSQL);

            ResultSet rs = preparedStatement.executeQuery();
            HashMap<String, String> map=new HashMap<>();
            while(rs.next()){
                String name = rs.getString("name");
                String score = rs.getString("score");

                map.put("name",name);
                map.put("score",score);
                
                result.add(map);
                
                map=new HashMap<>();
                
            }
            close();
            System.out.println(result.toString());
            return result;
                    
       } catch (SQLException e) {
            System.out.println(e.getMessage());
       }
       return result;
    } 
    
    ArrayList <HashMap <String, Integer>> getGames(){
       ArrayList <HashMap <String, Integer>> result= new ArrayList<>(); 
       String selectTableSQL= "SELECT * from games";   
       try{
            dbConnection = getDBConnection();
            
            preparedStatement = dbConnection.prepareStatement(selectTableSQL);

            ResultSet rs = preparedStatement.executeQuery();
            HashMap<String, Integer> map=new HashMap<>();
            while(rs.next()){
                int gid = rs.getInt("gid");
                int nop = rs.getInt("nop");
                int pid = rs.getInt("winner");
                map.put("gid",gid);
                map.put("nop",nop);
                map.put("winner",pid);
                
                result.add(map);
                
                map=new HashMap<>();
                
            }
            close();
            System.out.println(result.toString());
            return result;
                    
       } catch (SQLException e) {
            System.out.println(e.getMessage());
       }
       return result;
    } 
    
       
    String getPlayerName(int pid){
        String selectTableSQL= "SELECT name from players where pid=?;";   
         try{
            dbConnection = getDBConnection();
            
            preparedStatement = dbConnection.prepareStatement(selectTableSQL);
            preparedStatement.setInt(1, pid);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            String name = rs.getString("name");
            System.out.println(name +" is the player at pid= "+pid);
            
            close();
            return name;
                    
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;

    }
    
    
    //do not touch below this line
    //------------------------------------------------------------------------------
    private void close()
    {
       try{ 
           if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }
          }
          catch(SQLException e){e.printStackTrace();}
    }
    private static Connection getDBConnection() {

        Connection dbConnection = null;

         try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
    
        }
    
             try {
            dbConnection = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/chainreaction","root", "root");
            System.out.println("ho gya");
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();

        }
    
        if (dbConnection != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }

        return dbConnection;

    }
    public static void main(String args[])
    {
        Mydb a = new Mydb("","");
        a.createGame(3);
    }
}
