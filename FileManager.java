package Answer2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.*;
import javax.swing.*;

import java.io.*;

import java.io.File;
import java.io.IOException;
import java.io.IOException;

public class FileManager {
    private String filePath;
    private RandomFile randomFile;
    private boolean change;
    private Employee currentEmployee; 
    private long currentByteStart; 
    private JFrame frame; 
    private JTextField idField; 
    private EmployeeManager employeeManager;

    public FileManager(String filePath) {
        this.filePath = filePath;
        this.randomFile = new RandomFile();
        this.employeeManager = new EmployeeManager(null);
    }

    public void createFile() {
        randomFile.createFile(filePath);
    }

    public void openReadFile() {
        randomFile.openReadFile(filePath);
    }

    public void openWriteFile() {
        randomFile.openWriteFile(filePath);
    }

    public void closeReadFile() {
        randomFile.closeReadFile();
    }

    public void closeWriteFile() {
        randomFile.closeWriteFile();
    }

    public boolean isSomeoneToDisplay() {
        return randomFile.isSomeoneToDisplay();
    }

    public boolean isPpsExist(String pps, long currentByte) {
        return randomFile.isPpsExist(pps, currentByte);
    }

    public long getLast() {
        return randomFile.getLast();
    }


    public long addRecords(Employee newEmployee) {
        long currentRecordStart = 0;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            RandomAccessEmployeeRecord record = new RandomAccessEmployeeRecord(
                    newEmployee.getEmployeeId(),
                    newEmployee.getPps(),
                    newEmployee.getSurname(),
                    newEmployee.getFirstName(),
                    newEmployee.getGender(),
                    newEmployee.getDepartment(),
                    newEmployee.getSalary(),
                    newEmployee.getFullTime()
            );

            currentRecordStart = Paths.get(filePath).toFile().length(); 

            writer.write(record.toString()); 
            writer.newLine();

            writer.flush();

        } catch (IOException e) {
            System.out.println("An error occurred while adding records to the file.");
            e.printStackTrace();
        }

        return currentRecordStart;
    }

    public void deleteRecords(long byteStart) {
        randomFile.deleteRecords(byteStart);
    }


    public Employee readRecords(long byteStart) {
        return randomFile.readRecords(byteStart);
    }



    public void changeRecords(Employee employee, long byteStart) {
        randomFile.changeRecords(employee, byteStart);
    }
    
    public void openFile() {
        randomFile.openReadFile(filePath);
    }

	// save file
    void saveFile() {
        // Check if changes have been made
        if (change) {
            int returnVal = JOptionPane.showOptionDialog(frame, "Do you want to save changes?", "Save",
                    JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
            // Save changes if the user chooses to do so
            if (returnVal == JOptionPane.YES_OPTION) {
                // Check if the ID field is not empty
                if (!idField.getText().isEmpty()) {
                    // Open the file for writing
                    openWriteFile();
                    // Get the changes for the current Employee
                    // Assuming getChangedDetails() is a method to retrieve changes made in UI
                    currentEmployee = getChangedDetails(null, idField, idField, idField, idField, null, null, idField);
                    // Write the changes to the file for the corresponding Employee record
                    changeRecords(currentEmployee, currentByteStart);
                    // Close the file for writing
                    closeWriteFile();
                }
            }
        }}


 // get values from text fields and create Employee object
    Employee getChangedDetails(JComboBox fullTimeCombo, JTextField idField, JTextField ppsField,
            JTextField surnameField, JTextField firstNameField, JComboBox genderCombo,
            JComboBox departmentCombo, JTextField salaryField) {
 boolean fullTime = false;
 Employee theEmployee;
 if (fullTimeCombo.getSelectedItem() != null && fullTimeCombo.getSelectedItem().equals("Yes")) {
 fullTime = true;
 }
 try {
 int id = Integer.parseInt(idField.getText());
 String pps = ppsField.getText().toUpperCase();
 String surname = surnameField.getText().toUpperCase();
 String firstName = firstNameField.getText().toUpperCase();
 char gender = genderCombo.getSelectedItem().toString().charAt(0);
 String department = departmentCombo.getSelectedItem().toString();
 double salary = Double.parseDouble(salaryField.getText());
 theEmployee = new Employee(id, pps, surname, firstName, gender, department, salary, fullTime);
 } catch (NumberFormatException e) {
 e.printStackTrace();
 return null;
 }
 return theEmployee;
 }

	public void saveFileAs() {
        final JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("DAT files", "dat");
        fc.setFileFilter(filter);

        int returnVal = fc.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File newFile = fc.getSelectedFile();
            String newFilePath = newFile.getAbsolutePath();
            if (!newFilePath.toLowerCase().endsWith(".dat")) {
                newFilePath += ".dat";
                newFile = new File(newFilePath);
            }

            try {
                Files.copy(new File(filePath).toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File saved as: " + newFilePath);
            } catch (IOException e) {
                System.out.println("An error occurred while saving the file as: " + newFilePath);
                e.printStackTrace();
            }
        }
    }
    public void exitApp() {
        System.out.println("Exiting the application...");
        System.exit(0);
    }

  
	
}
