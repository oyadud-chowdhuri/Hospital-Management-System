package HospitalManagemntSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctors {
    private Connection connection ;


    public Doctors(Connection connection ) {

        this.connection = connection;

    }

    public void viewDoctor(){
        String query = "select * from doctors";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Doctor ");
            System.out.println("+--------------+----------------------+--------------------------------+");
            System.out.println("| Doctors Id   | Name                 | Specialization                 |");
            System.out.println("+------------+--------------------+------------------------------------+");
            while (resultSet.next()){
                int doctorId = resultSet.getInt("id");
                String doctorName = resultSet.getString("name");
                String specialization = resultSet.getString("specialization");
                System.out.printf("| %-12s | %-20s | %-30s |\n",doctorId,doctorName,specialization);
                System.out.println("+--------------+----------------------+--------------------------------+");
            }

        }catch (SQLException e){
            e.printStackTrace();;
        }
    }

    public  boolean getDoctorById(int doctorId){
        String query = "SELECT * FROM doctors WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, doctorId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return true;
            }else {
                return false;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }
}
