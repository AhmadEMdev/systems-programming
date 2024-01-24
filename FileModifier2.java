/**
 * Done by Student: Ahmad Mohamed #21808164
 * CMPE 411
 */

import java.io.*;
import java.util.*;

public class FileModifier2 {

    private static final int STUDENT_RECORD_SIZE = 52; // Assuming fixed-length records

    public static void main(String[] args) {
        // Example usage
        addStudent("1111", "John Doe", 2.46, "CMPE", "TT001");
        addStudent("2222", "Jane Smith", 3.78, "CVLE", "TT002");

        displayAllStudents();
        displayStudentsWithAdvisors();

        modifyStudent("1111", "dept", "CVLE");
        displayAllStudents();

        deleteStudent("2222");
        displayAllStudents();

        displayStudentAndAdvisor("1111");

        advisorListToFile("TT001");
    }

    public static void addStudent(String id, String name, double cgpa, String department, String advisorId) {
        try (RandomAccessFile file = new RandomAccessFile("students.txt", "rw")) {
            file.seek(file.length());
            String studentRecord = String.format("%-4s,%-30s,%-4.2f,%-4s,%-5s", id, name, cgpa, department, advisorId);
            file.writeBytes(studentRecord + "\n");
            System.out.println("Student added successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void modifyStudent(String id, String field, String value) {
        try (RandomAccessFile file = new RandomAccessFile("students.txt", "rw")) {
            long currentPosition = 0;
            String line;

            while ((line = file.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(id)) {
                    switch (field.toLowerCase()) {
                        case "id":
                            currentPosition = file.getFilePointer() - 4;
                            file.writeBytes(String.format("%-4s", value));
                            break;
                        case "name":
                            currentPosition = file.getFilePointer() - 30;
                            file.writeBytes(String.format("%-30s", value));
                            break;
                        case "cgpa":
                            currentPosition = file.getFilePointer() - 4;
                            file.writeBytes(String.format("%-4.2f", Double.parseDouble(value)));
                            break;
                        case "dept":
                            currentPosition = file.getFilePointer() - 4;
                            file.writeBytes(String.format("%-4s", value));
                            break;
                        case "adv":
                            currentPosition = file.getFilePointer() - 5;
                            file.writeBytes(String.format("%-5s", value));
                            break;
                        default:
                            System.out.println("Invalid field to modify.");
                            return;
                    }
                    System.out.println("Student modified successfully.");
                    break;
                }
                currentPosition = file.getFilePointer();
            }

            // Reset the file pointer to the end if the modification was at the last record
            file.seek(currentPosition);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteStudent(String id) {
        try (RandomAccessFile file = new RandomAccessFile("students.txt", "rw")) {
            long currentPosition = 0;
            String line;

            while ((line = file.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(id)) {
                    currentPosition = file.getFilePointer() - STUDENT_RECORD_SIZE;
                    file.setLength(currentPosition);
                    System.out.println("Student deleted successfully.");
                    break;
                }
                currentPosition = file.getFilePointer();
            }

            // Reset the file pointer to the end if the deletion was at the last record
            file.seek(currentPosition);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void displayAllStudents() {
        System.out.println("All Students:");
        displayFileContents("students.txt");
    }

    public static void displayStudentsWithAdvisors() {
        System.out.println("All Students with Advisors:");
        List<String> lines = getFileContents("students.txt");
        for (String line : lines) {
            String[] parts = line.split(",");
            System.out.printf("%s,%s%n", line, getAdvisorName(parts[4]));
        }
    }

    public static void displayStudentAndAdvisor(String id) {
        System.out.println("Student and Advisor Information:");
        List<String> lines = getFileContents("students.txt");
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts[0].equals(id)) {
                System.out.println(String.format("%s,%s", line, getAdvisorName(parts[4])));
                return;
            }
        }
        System.out.println("Student not found.");
    }

    public static void advisorListToFile(String advisorId) {
        List<String> advisorList = new ArrayList<>();
        List<String> lines = getFileContents("students.txt");
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts[4].equals(advisorId)) {
                advisorList.add(parts[1]); // Assuming name is at index 1
            }
        }

        // Write advisor list to a file
        try (PrintWriter writer = new PrintWriter(new FileWriter(advisorId + "_advisor_list.txt"))) {
            for (String studentName : advisorList) {
                writer.println(studentName);
            }
            System.out.println("Advisor list written to file: " + advisorId + "_advisor_list.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void displayFileContents(String fileName) {
        List<String> lines = getFileContents(fileName);
        for (String line : lines) {
            System.out.println(line);
        }
    }

    private static List<String> getFileContents(String fileName) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    private static String getAdvisorName(String advisorId) {
        List<String> lines = getFileContents("instructors.txt");
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts[0].equals(advisorId)) {
                return parts[1]; // Assuming name is at index 1
            }
        }
        return "Advisor not found";
    }
}
