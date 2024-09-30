package HospitalManagemntSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patients {

    private Connection connection;

    private Scanner scanner;

    public Patients(Connection connection, Scanner scanner) {

        this.connection = connection;
        this.scanner = scanner;
    }

    public void addPatient(){
        System.out.print("Enter Patient Name: ");
        String name = scanner.next();
        System.out.print("Enter Patient Age: ");
        int age = scanner.nextInt();
        System.out.print("Enter Patient Gender: ");
        String gender = scanner.next();

        try {
            String query = "INSERT INTO patients(name, age, gender) VALUES(?,?,?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Patient Successfully Added");
            }else{
                System.out.println("Patient Not Added");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void viewPatients(){
        String query = "SELECT * FROM patients";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            System.out.println("Patient ");
            System.out.println("+--------------+----------------------+------------+--------------+");
            System.out.println("| Patient Id   | Name                 | Age        | Gender       |");
            System.out.println("+--------------+----------------------+------------+--------------+");
            while (rs.next()) {
                int patientId = rs.getInt("id");
                String patientName = rs.getString("name");
                int age = rs.getInt("age");
                String gender = rs.getString("gender");
                System.out.printf("| %-12s | %-20s | %-10s | %-12s |\n",patientId,patientName,age,gender);
                System.out.println("+--------------+----------------------+------------+--------------+");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean getPatientById(int id){
        String query = "SELECT * FROM patients WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }else {
                return false;
            }
        }catch(SQLException e){
            e.printStackTrace();;
        }

        return false ;
    }
}
