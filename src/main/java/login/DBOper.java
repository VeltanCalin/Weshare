package login;

import java.sql.*;

public class DBOper {
    public final static String URL = "jdbc:postgresql://localhost:5432/calinv8";
    public final static String USERNAME = "fasttrackit_dev";
    public final static String PASSWORD = "fasttrackit_dev";

    /* -1 daca nu am gasit , id-ul daca am gasit */
    public int login (String user, String pwd) {

        int found = -1;
        try {
            Class.forName("org.postgresql.Driver");

            // 3. obtain a connection
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);


            // 4. create a query statement
            Statement st = conn.createStatement();

            // 5. execute a query, in a not  secured way
            String query = "SELECT id FROM users where username='"+user+"' and password='"+pwd+"'";
            System.out.println(query);
            ResultSet rs = st.executeQuery(query);

            // 6. iterate the result set and print the values



            while (rs.next()) {
                found = rs.getInt("id");
            }

            // 7. close the objects
            rs.close();
            st.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return found;


    }



    public String displayUser (int value) {

        String found = null;
        try {
            Class.forName("org.postgresql.Driver");

            // 3. obtain a connection
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);


            // 4. create a query statement
            Statement st = conn.createStatement();

            // 5. execute a query, in a not  secured way
            String query = "SELECT username FROM users where id='"+value+"'";
            System.out.println(query);
            ResultSet rs = st.executeQuery(query);

            // 6. iterate the result set and print the values



            while (rs.next()) {
                found = rs.getString("username");
            }

            // 7. close the objects
            rs.close();
            st.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return found;


    }










    /* -1 daca nu am gasit , id-ul daca am gasit */
    public int register (String user, String pwd) {

        int found = -1;
        try {
            Class.forName("org.postgresql.Driver");

            // 3. obtain a connection
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            PreparedStatement pSt = conn.prepareStatement("INSERT INTO users (username,password) VALUES (?,?)");
            pSt.setString(1, user);
            pSt.setString(2, pwd);



            pSt.close();

            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return found;


    }



    public static void main(String[] args) {

        DBOper d = new DBOper();
        int value = d.login("ionel", "password1");
        System.out.println(value);
    }

    public void insertFile(String name, String nameFileToSaveInDB) {
        int found = -1;
        try {
            Class.forName("org.postgresql.Driver");

            // 3. obtain a connection
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            PreparedStatement pSt = conn.prepareStatement("INSERT INTO activities (message, files) VALUES (?,?)");
            pSt.setString(1, name);
            pSt.setString(2, nameFileToSaveInDB);



           // int rowsInserted = pSt.executeUpdate();

            pSt.close();

            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}

