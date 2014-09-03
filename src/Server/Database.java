package Server;

import java.sql.*;

public class Database {
    Statement statement = null;
    Connection c;
    final String url = "jdbc:sqlite:pokerking.db";
    final String driver = "org.sqlite.JDBC";

    public Database (){
        try {
            Class.forName(driver);
            c = DriverManager.getConnection(url);
            c.setAutoCommit(false);
            statement = c.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void signUp(String username, String password){
        try {
            PreparedStatement prep = c.prepareStatement(
                    "insert into account values (?, ?, ?);");
            prep.setString(1, username);
            prep.setString(2, password);
            prep.setInt(3, 1000);
            prep.addBatch();
            prep.executeBatch();
            System.out.println("Created new account");
        }catch (Exception e){
            System.out.println("Can not insert in to table");
        }
    }

    public String getAccountInfo(String name){
        String nameAndPass = "";
        try{
            ResultSet rs = statement.executeQuery("SELECT * FROM account WHERE username ='" + name + "';");
            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                int money = Integer.valueOf(rs.getString("money"));
                nameAndPass = username + "," + password + "," + money;
            }
            System.out.println(nameAndPass);
            rs.close();
            statement.close();
            c.commit();
        } catch (Exception e){
            System.out.println("Can not take info from db");
        } finally {
            return nameAndPass;
        }
    }

    public void updateMoney(String name, int newMoney){
        try{
            statement = c.createStatement();
            String qry = "UPDATE account SET money = '" + newMoney + "' WHERE username = '" + name + "';";
            statement.executeUpdate(qry);
            statement.close();
            c.commit();
            System.out.println("Updated new money");
        } catch (Exception e){
            System.out.println("Can not update db");
        }
    }

    public int getPlayerMoney(String name){
        int money = 0;
        try{
            statement = c.createStatement();
            String qry = "SELECT money FROM account WHERE username ='" + name + "'";
            ResultSet rs = statement.executeQuery(qry);
            if (rs.getString("money") != null) {
                money = Integer.valueOf(rs.getString("money"));
            }
            System.out.println(name + " money is: " + money);
            statement.close();
            c.commit();
        } catch (Exception e){
            System.out.println("Can not get player money from db");
        } finally {
            return money;
        }
    }

    public void closeDB(){
        try {
            if (c != null) {
                c.close();
            }
        } catch (Exception e){
            System.out.println("Can not close db.");
        }
    }

    public static void main(String[] args) {
        Database database = new Database();
//        database.signUp("duy", "123");
        database.getAccountInfo("duy");
        database.updateMoney("duy", 100);
        database.getPlayerMoney("duy");
        database.closeDB();

    }

}
