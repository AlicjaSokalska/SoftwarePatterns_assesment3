package Answer;

import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.util.Random;

import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.Random;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class FileManager {
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
    // Constructor
    public FileManager(EmployeeDetails frame) {
        this.frame = frame;
    }

    private void openFile() {
        final JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Open");
        fc.setFileFilter(datfilter);
        File newFile;

        if (file.length() != 0 || change) {
            int returnVal = JOptionPane.showOptionDialog(frame, "Do you want to save changes?", "Save",
                    JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
            if (returnVal == JOptionPane.YES_OPTION) {
                saveFile();
            }
        }

        int returnVal = fc.showOpenDialog(frame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            newFile = fc.getSelectedFile();
            if (file.getName().equals(generatedFileName))
                file.delete();
            file = newFile;
            application.openReadFile(file.getAbsolutePath());
            firstRecord();
            displayRecords(currentEmployee);
            application.closeReadFile();
        }
    }

    private void saveFile() {
        if (file.getName().equals(generatedFileName))
            saveFileAs();
        else {
            if (change) {
                int returnVal = JOptionPane.showOptionDialog(frame, "Do you want to save changes?", "Save",
                        JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                if (returnVal == JOptionPane.YES_OPTION) {
                    
					if (!idField.getText().equals("")) {
                        application.openWriteFile(file.getAbsolutePath());
                        currentEmployee = getChangedDetails();
                        application.changeRecords(currentEmployee, currentByteStart);
                        application.closeWriteFile();
                    }
                }
            }
            displayRecords(currentEmployee);
            frame.setEnabled(false);
        }
    }

    private Employee getChangedDetails() {
		// TODO Auto-generated method stub
		return null;
	}

	private void saveFileAs() {
        final JFileChooser fc = new JFileChooser();
        File newFile;
        String defaultFileName = "new_Employee.dat";
        fc.setDialogTitle("Save As");
        fc.setFileFilter(datfilter);
        fc.setApproveButtonText("Save");
        fc.setSelectedFile(new File(defaultFileName));

        int returnVal = fc.showSaveDialog(frame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            newFile = fc.getSelectedFile();
            if (!checkFileName(newFile)) {
                newFile = new File(newFile.getAbsolutePath() + ".dat");
                application.createFile(newFile.getAbsolutePath());
            } else
                application.createFile(newFile.getAbsolutePath());

            try {
                Files.copy(file.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                if (file.getName().equals(generatedFileName))
                    file.delete();
                file = newFile;
            } catch (IOException e) {
            }
        }
        changesMade = false;
    }

    private void createRandomFile() {
        generatedFileName = getFileName() + ".dat";
        file = new File(generatedFileName);
        application.createFile(file.getName());
    }

    private String getFileName() {
        String fileNameChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890_-";
        StringBuilder fileName = new StringBuilder();
        Random rnd = new Random();
        while (fileName.length() < 20) {
            int index = (int) (rnd.nextFloat() * fileNameChars.length());
            fileName.append(fileNameChars.charAt(index));
        }
        String generatedfileName = fileName.toString();
        return generatedfileName;
    }

    private void saveChanges() {
        int returnVal = JOptionPane.showOptionDialog(frame, "Do you want to save changes to current Employee?", "Save",
                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
        if (returnVal == JOptionPane.YES_OPTION) {
            application.openWriteFile(file.getAbsolutePath());
            currentEmployee = getChangedDetails();
            application.changeRecords(currentEmployee, currentByteStart);
            application.closeWriteFile();
            changesMade = false;
        }
        displayRecords(currentEmployee);
        frame.setEnabled(false);
    }

    private void exitApp() {
        if (file.length() != 0) {
            if (changesMade) {
                int returnVal = JOptionPane.showOptionDialog(frame, "Do you want to save changes?", "Save",
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                if (returnVal == JOptionPane.YES_OPTION) {
                    saveFile();
                    if (file.getName().equals(generatedFileName))
                        file.delete();
                    System.exit(0);
                } else if (returnVal == JOptionPane.NO_OPTION) {
                    if (file.getName().equals(generatedFileName))
                        file.delete();
                    System.exit(0);
                }
            } else {
                if (file.getName().equals(generatedFileName))
                    file.delete();
                System.exit(0);
            }
        } else {
            if (file.getName().equals(generatedFileName))
                file.delete();
            System.exit(0);
        }
    }
    private boolean checkFileName(File file) {
        // Implement this method to check the file name
        return false;
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
