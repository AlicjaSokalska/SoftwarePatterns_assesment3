package Answer;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Vector;

public class EmployeeManager {
	// decimal format for inactive currency text field
		private static final DecimalFormat format = new DecimalFormat("\u20ac ###,###,##0.00");
		// decimal format for active currency text field
		private static final DecimalFormat fieldFormat = new DecimalFormat("0.00");
		// hold object start position in file
		private long currentByteStart = 0;
		private RandomFile application = new RandomFile();
		private static EmployeeDetails frame = new EmployeeDetails();

		// display files in File Chooser only with extension .dat
		private FileNameExtensionFilter datfilter = new FileNameExtensionFilter("dat files (*.dat)", "dat");
		// hold file name and path for current file in use
		private File file;
		// holds true or false if any changes are made for text fields
		private boolean change = false;
		// holds true or false if any changes are made for file content
		boolean changesMade = false;
		private JMenuItem open, save, saveAs, create, modify, delete, firstItem, lastItem, prevItem, searchById,
				searchBySurname, listAll, closeApp;
		private JButton first, previous, next, last, add, edit, deleteButton, displayAll, searchId, searchSurname,
				saveChange, cancelChange;
		private JComboBox<String> genderCombo, departmentCombo, fullTimeCombo;
		private JTextField idField, ppsField, surnameField, firstNameField, salaryField;
		
		// font for labels, text fields and combo boxes
		Font font1 = new Font("SansSerif", Font.BOLD, 16);
		// holds automatically generated file name
		String generatedFileName;
		// holds current Employee object
		Employee currentEmployee;
		JTextField searchByIdField, searchBySurnameField;
		// gender combo box values
		String[] gender = { "", "M", "F" };
		// department combo box values
		String[] department = { "", "Administration", "Production", "Transport", "Management" };
		// full time combo box values
		String[] fullTime = { "", "Yes", "No" };

    private RandomFile randomFile;
    
    public EmployeeManager() {
        randomFile = new RandomFile();
    }

    public void addRecord(Employee newEmployee) {
        application.openWriteFile(file.getAbsolutePath());
        currentByteStart = application.addRecords(newEmployee);
        application.closeWriteFile();
    }

    private void deleteRecord() {
        if (isSomeoneToDisplay()) {
            int returnVal = JOptionPane.showOptionDialog(frame, "Do you want to delete record?", "Delete",
                    JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
            if (returnVal == JOptionPane.YES_OPTION) {
                application.openWriteFile(file.getAbsolutePath());
                application.deleteRecords(currentByteStart);
                application.closeWriteFile();
                if (isSomeoneToDisplay()) {
                    nextRecord();
                    displayRecords(currentEmployee);
                }
            }
        }
    }

    private Employee getChangedDetails() {
        boolean fullTime = false;
        Employee theEmployee;
        if (((String) fullTimeCombo.getSelectedItem()).equalsIgnoreCase("Yes"))
            fullTime = true;

        theEmployee = new Employee(Integer.parseInt(idField.getText()), ppsField.getText().toUpperCase(),
                surnameField.getText().toUpperCase(), firstNameField.getText().toUpperCase(),
                genderCombo.getSelectedItem().toString().charAt(0), departmentCombo.getSelectedItem().toString(),
                Double.parseDouble(salaryField.getText()), fullTime);

        return theEmployee;
    }

    private Vector<Object> getAllEmployees() {
        Vector<Object> allEmployee = new Vector<>();
        Vector<Object> empDetails;
        long byteStart = currentByteStart;
        int firstId;

        firstRecord();
        firstId = currentEmployee.getEmployeeId();
        do {
            empDetails = new Vector<>();
            empDetails.addElement(currentEmployee.getEmployeeId());
            empDetails.addElement(currentEmployee.getPps());
            empDetails.addElement(currentEmployee.getSurname());
            empDetails.addElement(currentEmployee.getFirstName());
            empDetails.addElement(currentEmployee.getGender());
            empDetails.addElement(currentEmployee.getDepartment());
            empDetails.addElement(currentEmployee.getSalary());
            empDetails.addElement(currentEmployee.getFullTime());

            allEmployee.addElement(empDetails);
            nextRecord();
        } while (firstId != currentEmployee.getEmployeeId());
        currentByteStart = byteStart;

        return allEmployee;
    }

    public void searchEmployeeById() {
        boolean found = false;
        if (isSomeoneToDisplay()) {
            firstRecord();
            int firstId = currentEmployee.getEmployeeId();
            if (searchByIdField.getText().trim().equals(idField.getText().trim()))
                found = true;
            else if (searchByIdField.getText().trim().equals(Integer.toString(currentEmployee.getEmployeeId()))) {
                found = true;
                displayRecords(currentEmployee);
            } else {
                nextRecord();
                while (firstId != currentEmployee.getEmployeeId()) {
                    if (Integer.parseInt(searchByIdField.getText().trim()) == currentEmployee.getEmployeeId()) {
                        found = true;
                        displayRecords(currentEmployee);
                        break;
                    } else
                        nextRecord();
                }
            }
            if (!found)
                JOptionPane.showMessageDialog(null, "Employee not found!");
        }
        searchByIdField.setBackground(Color.WHITE);
        searchByIdField.setText("");
    }


	public void searchEmployeeBySurname() {
        boolean found = false;
        if (isSomeoneToDisplay()) {
            firstRecord();
            String firstSurname = currentEmployee.getSurname().trim();
            if (searchBySurnameField.getText().trim().equalsIgnoreCase(surnameField.getText().trim()))
                found = true;
            else if (searchBySurnameField.getText().trim().equalsIgnoreCase(currentEmployee.getSurname().trim())) {
                found = true;
                displayRecords(currentEmployee);
            } else {
                nextRecord();
                while (!firstSurname.trim().equalsIgnoreCase(currentEmployee.getSurname().trim())) {
                    if (searchBySurnameField.getText().trim().equalsIgnoreCase(currentEmployee.getSurname().trim())) {
                        found = true;
                        displayRecords(currentEmployee);
                        break;
                    } else
                        nextRecord();
                }
            }
            if (!found)
                JOptionPane.showMessageDialog(null, "Employee not found!");
        }
        searchBySurnameField.setText("");
    }

    public int getNextFreeId() {
        int nextFreeId = 0;
        if (file.length() == 0 || !isSomeoneToDisplay())
            nextFreeId++;
        else {
            lastRecord();
            nextFreeId = currentEmployee.getEmployeeId() + 1;
        }
        return nextFreeId;
    }

    public boolean isSomeoneToDisplay() {
        return randomFile.isSomeoneToDisplay();
    }

    private void lastRecord() {
    	  currentByteStart = application.getNext(currentByteStart);
          currentEmployee = application.readRecords(currentByteStart);
    }

    private void firstRecord() {
    	  currentByteStart = application.getNext(currentByteStart);
          currentEmployee = application.readRecords(currentByteStart);
    }

    private void nextRecord() {
        currentByteStart = application.getNext(currentByteStart);
        currentEmployee = application.readRecords(currentByteStart);
    }

    private void displayRecords(Employee employee) {
        frame.displayRecords(employee); // Call displayRecords method from EmployeeDetails
    }
}


