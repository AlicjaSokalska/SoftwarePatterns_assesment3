package Answer2;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EmployeeManager {
	private Employee currentEmployee;
	private long currentByteStart;
	private RandomFile randomFile;
	private FileManager fileManager;

	public EmployeeManager(RandomFile randomFile) {
		this.randomFile = randomFile;
		fileManager = new FileManager(null);
	}

	public void firstRecord() {

		if (randomFile.isSomeoneToDisplay()) {

			randomFile.openReadFile(null);

			currentByteStart = randomFile.getFirst();

			currentEmployee = randomFile.readRecords(currentByteStart);

			randomFile.closeReadFile();

			if (currentEmployee.getEmployeeId() == 0) {
				nextRecord();
			}
		} else {
			JOptionPane.showMessageDialog(null, "No records found.");
		}
	}

	public void previousRecord() {
		if (randomFile != null) {
			
			if (randomFile.isSomeoneToDisplay()) {
				currentByteStart = randomFile.getPrevious(currentByteStart);
				currentEmployee = randomFile.readRecords(currentByteStart);
			} else {
				JOptionPane.showMessageDialog(null, "No records found.");
			}
		} else {
			JOptionPane.showMessageDialog(null, "RandomFile instance is not provided.");
		}
	}

	public void nextRecord() {
		if (randomFile != null) {
			if (randomFile.isSomeoneToDisplay()) {
				currentByteStart = randomFile.getNext(currentByteStart);
				currentEmployee = randomFile.readRecords(currentByteStart);
			} else {
				JOptionPane.showMessageDialog(null, "No records found.");
			}
		} else {
			JOptionPane.showMessageDialog(null, "RandomFile instance is not provided.");
		}
	}

	public void lastRecord() {
		if (randomFile != null) {
			currentByteStart = randomFile.getLast();
			currentEmployee = randomFile.readRecords(currentByteStart);
		} else {
			JOptionPane.showMessageDialog(null, "RandomFile instance is not provided.");
		}
	}

	public Employee searchEmployeeById(int employeeId) {
		Employee foundEmployee = null;
		if (randomFile != null) {
			long byteToStart = randomFile.getFirst();
			boolean employeeFound = false;

			while (byteToStart != -1) {
				Employee employee = randomFile.readRecords(byteToStart);
				if (employee != null && employee.getEmployeeId() == employeeId) {
					foundEmployee = employee;
					employeeFound = true;
					break;
				}
				byteToStart = randomFile.getNext(byteToStart);
			}

			if (!employeeFound) {
				JOptionPane.showMessageDialog(null, "Employee with ID " + employeeId + " not found.");
			}
		} else {
			JOptionPane.showMessageDialog(null, "RandomFile instance is not provided.");
		}
		return foundEmployee;
	}

	public ArrayList<Employee> searchEmployeeBySurname(String surname) {
		ArrayList<Employee> foundEmployees = new ArrayList<>();

		if (randomFile != null) {
			long byteToStart = randomFile.getFirst();

			
			while (byteToStart != -1) {
				
				Employee employee = randomFile.readRecords(byteToStart);
				
				if (employee != null && employee.getSurname().equalsIgnoreCase(surname)) {
					foundEmployees.add(employee);
				}
	
				byteToStart = randomFile.getNext(byteToStart);
			}

		
			if (foundEmployees.isEmpty()) {
				JOptionPane.showMessageDialog(null, "No employees with surname '" + surname + "' found.");
			}
		} else {
			JOptionPane.showMessageDialog(null, "RandomFile instance is not provided.");
		}
		return foundEmployees;
	}

	public void addRecord(Employee newEmployee) {
		if (fileManager != null) {
			
			long byteToStart = fileManager.addRecords(newEmployee);

		
			JOptionPane.showMessageDialog(null, "Employee record added successfully.");
		} else {
			JOptionPane.showMessageDialog(null, "FileManager instance is not provided.");
		}
	}

	public void deleteRecord(long byteToStart) {
		if (fileManager != null) {
	
			randomFile.deleteRecords(byteToStart);

		
			JOptionPane.showMessageDialog(null, "Employee record deleted successfully.");
		} else {
			JOptionPane.showMessageDialog(null, "FileManager instance is not provided.");
		}
	}

	public void editDetails(long byteToStart, Employee newDetails) {
		if (fileManager != null) {
		
			fileManager.openWriteFile();

			fileManager.changeRecords(newDetails, byteToStart);
			fileManager.closeWriteFile();

			JOptionPane.showMessageDialog(null, "Employee details updated successfully.");
		} else {
			JOptionPane.showMessageDialog(null, "FileManager instance is not provided.");
		}
	}

	public void cancelChange() {
		if (currentEmployee != null) {
			displayEmployeeDetails(currentEmployee);

			JOptionPane.showMessageDialog(null, "Changes canceled. No modifications saved.");
		} else {
			JOptionPane.showMessageDialog(null, "No employee details to cancel changes for.");
		}
	}

	public void displayEmployeeDetails(Employee employee) {
		JTextField idField = new JTextField(10);
		JTextField nameField = new JTextField(20);
		JTextField departmentField = new JTextField(20);
		JTextField salaryField = new JTextField(10);

		idField.setText(String.valueOf(employee.getEmployeeId()));
		nameField.setText(employee.getFirstName() + " " + employee.getSurname());
		departmentField.setText(employee.getDepartment());
		salaryField.setText(String.valueOf(employee.getSalary()));
		JPanel panel = new JPanel();
		panel.add(new JLabel("ID:"));
		panel.add(idField);
		panel.add(new JLabel("Name:"));
		panel.add(nameField);
		panel.add(new JLabel("Department:"));
		panel.add(departmentField);
		panel.add(new JLabel("Salary:"));
		panel.add(salaryField);
		JOptionPane.showMessageDialog(null, panel, "Employee Details", JOptionPane.PLAIN_MESSAGE);
	}

	Vector<Object> getAllEmployees() {
		Vector<Object> allEmployee = new Vector<Object>();
		Vector<Object> empDetails;
		long byteStart = currentByteStart;
		int firstId;

		firstRecord();
		firstId = currentEmployee.getEmployeeId();
		do {
			empDetails = new Vector<Object>();
			empDetails.addElement(new Integer(currentEmployee.getEmployeeId()));
			empDetails.addElement(currentEmployee.getPps());
			empDetails.addElement(currentEmployee.getSurname());
			empDetails.addElement(currentEmployee.getFirstName());
			empDetails.addElement(new Character(currentEmployee.getGender()));
			empDetails.addElement(currentEmployee.getDepartment());
			empDetails.addElement(new Double(currentEmployee.getSalary()));
			empDetails.addElement(new Boolean(currentEmployee.getFullTime()));

			allEmployee.addElement(empDetails);
			nextRecord();// 
		} while (firstId != currentEmployee.getEmployeeId());
		currentByteStart = byteStart;

		return allEmployee;
	}

}
