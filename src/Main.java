
import java.sql.*;

public class Main {
    public static void main(String []args) throws Exception{
        String url = "jdbc:mysql://localhost:3306/student_db";
        String uname = "root";
        String pass = "rashon@sql123";
        String query = "select * from student_info";

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(url, uname, pass);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);

        String userData = "";

        while(rs.next()) {
            userData = rs.getInt(1) + " : " + rs.getString(2);
            System.out.println(userData);
        }

        st.close();
        con.close();
    }


}