package Answer;

import java.awt.Font;
import java.awt.event.*;
import java.io.File;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class EventHandler implements ActionListener, DocumentListener, ItemListener, WindowListener {
	private static final DecimalFormat format = new DecimalFormat("\u20ac ###,###,##0.00");
	// decimal format for active currency text field
	private static final DecimalFormat fieldFormat = new DecimalFormat("0.00");
	// hold object start position in file
	private long currentByteStart = 0;
	private RandomFile application = new RandomFile();
	// display files in File Chooser only with extension .dat
	private FileNameExtensionFilter datfilter = new FileNameExtensionFilter("dat files (*.dat)", "dat");
	// hold file name and path for current file in use
	private File file;
	// holds true or false if any changes are made for text fields
	private boolean change = false;
	// holds true or false if any changes are made for file content
	boolean changesMade = false;
	private JMenuItem open, save, saveAs, create, modify, delete, firstItem, lastItem, nextItem, prevItem, searchById,
			searchBySurname, listAll, closeApp;
	private JButton first, previous, next, last, add, edit, deleteButton, displayAll, searchId, searchSurname,
			saveChange, cancelChange;
	private JComboBox<String> genderCombo, departmentCombo, fullTimeCombo;
	private JTextField idField, ppsField, surnameField, firstNameField, salaryField;
	private static EmployeeDetails frame = new EmployeeDetails();
	
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
	 private EmployeeDetails employeeDetails;
	 
	  public EventHandler(EmployeeDetails employeeDetails) {
	        this.employeeDetails = employeeDetails;
	    }
    
    public void actionPerformed(ActionEvent e) {

		if (e.getSource() == closeApp) {
			if (checkInput() && !checkForChanges())
				exitApp();
		} else if (e.getSource() == open) {
			if (checkInput() && !checkForChanges())
				openFile();
		} else if (e.getSource() == save) {
			if (checkInput() && !checkForChanges())
				saveFile();
			change = false;
		} else if (e.getSource() == saveAs) {
			if (checkInput() && !checkForChanges())
				saveFile();
			change = false;
		} else if (e.getSource() == searchById) {
			if (checkInput() && !checkForChanges())
				displaySearchByIdDialog();
		} else if (e.getSource() == searchBySurname) {
			if (checkInput() && !checkForChanges())
				displaySearchBySurnameDialog();
		} else if (e.getSource() == searchId || e.getSource() == searchByIdField)
			searchEmployeeBySurname();
		else if (e.getSource() == searchSurname || e.getSource() == searchBySurnameField)
			searchEmployeeBySurname();
		else if (e.getSource() == saveChange) {
			if (checkInput() && !checkForChanges())
				;
		} else if (e.getSource() == cancelChange)
			cancelChange();
		else if (e.getSource() == firstItem || e.getSource() == first) {
			if (checkInput() && !checkForChanges()) {
				firstRecord();
				displayRecords(currentEmployee);
			}
		} else if (e.getSource() == prevItem || e.getSource() == previous) {
			if (checkInput() && !checkForChanges()) {
				previousRecord();
				displayRecords(currentEmployee);
			}
		} else if (e.getSource() == nextItem || e.getSource() == next) {
			if (checkInput() && !checkForChanges()) {
				nextRecord();
				displayRecords(currentEmployee);
			}
		} else if (e.getSource() == lastItem || e.getSource() == last) {
			if (checkInput() && !checkForChanges()) {
				lastRecord();
				displayRecords(currentEmployee);
			}
		} else if (e.getSource() == listAll || e.getSource() == displayAll) {
			if (checkInput() && !checkForChanges())
				if (isSomeoneToDisplay())
					displayEmployeeSummaryDialog();
		} else if (e.getSource() == create || e.getSource() == add) {
			if (checkInput() && !checkForChanges())
				 new AddRecordDialog(employeeDetails);
		} else if (e.getSource() == modify || e.getSource() == edit) {
			if (checkInput() && !checkForChanges())
				editDetails();
		} else if (e.getSource() == delete || e.getSource() == deleteButton) {
			if (checkInput() && !checkForChanges())
				deleteRecord();
		} else if (e.getSource() == searchBySurname) {
			if (checkInput() && !checkForChanges())
				new SearchBySurnameDialog(employeeDetails);
		}
	}// 

    private void nextRecord() {
		// TODO Auto-generated method stub
		
	}

	private void deleteRecord() {
		// TODO Auto-generated method stub
		
	}

	private void editDetails() {
		// TODO Auto-generated method stub
		
	}

	private void displayEmployeeSummaryDialog() {
		// TODO Auto-generated method stub
		
	}

	private boolean isSomeoneToDisplay() {
		// TODO Auto-generated method stub
		return false;
	}

	private void previousRecord() {
		// TODO Auto-generated method stub
		
	}

	private void displayRecords(Employee currentEmployee2) {
		// TODO Auto-generated method stub
		
	}

	private void firstRecord() {
		// TODO Auto-generated method stub
		
	}

	private void lastRecord() {
		// TODO Auto-generated method stub
		
	}

	private void cancelChange() {
		// TODO Auto-generated method stub
		
	}

	private void displaySearchBySurnameDialog() {
		// TODO Auto-generated method stub
		
	}

	private void displaySearchByIdDialog() {
		// TODO Auto-generated method stub
		
	}

	private void searchEmployeeBySurname() {
		// TODO Auto-generated method stub
		
	}

	private void saveFile() {
		// TODO Auto-generated method stub
		
	}

	private void openFile() {
		// TODO Auto-generated method stub
		
	}

	private boolean checkForChanges() {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean checkInput() {
		// TODO Auto-generated method stub
		return false;
	}

	public void insertUpdate(DocumentEvent d) {
		change = true;
		new JTextFieldLimit(20);
	}
    @Override
    public void removeUpdate(DocumentEvent d) {
		change = true;
		new JTextFieldLimit(20);
	}

 // ItemListener method
 	public void itemStateChanged(ItemEvent e) {
 		change = true;
 	}
 	public void changedUpdate(DocumentEvent d) {
		change = true;
		new JTextFieldLimit(20);
	}
 	// WindowsListener methods
 	public void windowClosing(WindowEvent e) {
 		// exit application
 		exitApp();
 	}

 	private void exitApp() {
		// TODO Auto-generated method stub
		
	}

	public void windowActivated(WindowEvent e) {
 	}

 	public void windowClosed(WindowEvent e) {
 	}

 	public void windowDeactivated(WindowEvent e) {
 	}

 	public void windowDeiconified(WindowEvent e) {
 	}

 	public void windowIconified(WindowEvent e) {
 	}

 	public void windowOpened(WindowEvent e) {
 	}
 }// end