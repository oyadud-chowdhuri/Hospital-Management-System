package HospitalManagemntSystem;

import java.sql.*;
import java.util.Scanner;

public class HospitalManagement {
    public static final String url = "jdbc:mysql://localhost:3306/hospital";
    public static final String userName = "root";
    public static final String password = "rashon@sql123";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);

        try {
            Connection connection = DriverManager.getConnection(url,userName,password);

            Patients patients = new Patients(connection,scanner);
            Doctors doctors = new Doctors(connection);

            while (true){
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patients");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.println();
                System.out.println("Enter your choice");
                System.out.println();
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        patients.addPatient();
                        break;
                    case 2:
                        patients.viewPatients();
                        break;
                    case 3:
                        doctors.viewDoctor();
                        break;
                    case 4:
                        bookAppointment(patients, doctors, connection, scanner);
                        break;
                    case 5:
                        return;

                    default:
                        System.out.println("Invalid choice, Enter a valid number");
                        break;

                }


            }

        }catch (SQLException e){
            e.printStackTrace();

        }
    }

    public static void bookAppointment(Patients patients, Doctors doctors, Connection connection, Scanner scanner)  {
        System.out.print("Enter patient id ");
        int patientId = scanner.nextInt();
        System.out.println();
        System.out.print("Enter doctor id ");
        int doctorId = scanner.nextInt();
        System.out.println();
        System.out.print("Enter appointment date (YYYY-MM-DD): ");
        String appointmentDate = scanner.next();

        if (patients.getPatientById(patientId) && doctors.getDoctorById(doctorId)){
            if (checkDoctorAvailable(doctorId,appointmentDate, connection)){
                String appointmentQuery = "INSERT INTO appointment(pid, d_id, a_date) VALUES (?, ?, ?)";
                try {
                    PreparedStatement  preparedStatement = connection.prepareStatement(appointmentQuery);
                    preparedStatement.setInt(1,patientId);
                    preparedStatement.setInt(2,doctorId);
                    preparedStatement.setString(3,appointmentDate);
                    int affectedRows = preparedStatement.executeUpdate();
                    if (affectedRows>0){
                        System.out.println("Appointment Booked");

                    }else {
                        System.out.println("Sorry, fail to book appointment");
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }

            }else {
                System.out.println("Doctor not available at this particular date");
            }

        }else {
            System.out.println("Either Doctor or Patient not found");
        }

    }

    public static boolean checkDoctorAvailable(int doctorId, String appointmentDate, Connection  connection){
        String query = "SELECT COUNT(*) FROM appointment WHERE d_id = ? AND a_date = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,doctorId);
            preparedStatement.setString(2,appointmentDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                int count = resultSet.getInt(1);
                if (count == 0){
                    return  true;
                }else {
                    return false;

                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return  false;

    }

}
