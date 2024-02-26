package Answer2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import javax.swing.SwingUtilities;


public class Controller {
    private EmployeeManager employeeManager;
    private GUIManager guiManager;
    private FileManager fileManager;
    private EventHandler eventHandler;

    public Controller() {
        this.fileManager = new FileManager("file_path_here"); 
        this.employeeManager = new EmployeeManager(null);
        this.guiManager = new GUIManager(this);
        this.eventHandler = new EventHandler(null);
    }


    public void openFile() {
        fileManager.openFile();
    }

    public void saveFile() {
        fileManager.saveFile();
    }

    public void saveFileAs() {
        fileManager.saveFileAs();
    }

    public void exitApp() {
        fileManager.exitApp();
    }

    public void searchEmployeeById() {
        employeeManager.searchEmployeeById(0);
    }

    public void searchEmployeeBySurname() {
        employeeManager.searchEmployeeBySurname(null);
    }

    public void addRecord(Employee newEmployee) {
        employeeManager.addRecord(newEmployee);
    }

    public void deleteRecord() {
        employeeManager.deleteRecord(0);
    }

    public void editDetails() {
        employeeManager.editDetails(0, null);
    }

    public void cancelChange() {
        employeeManager.cancelChange();
    }

    public void firstRecord() {
        employeeManager.firstRecord();
    }

    public void previousRecord() {
        employeeManager.previousRecord();
    }

    public void nextRecord() {
        employeeManager.nextRecord();
    }

    public void lastRecord() {
        employeeManager.lastRecord();
    }

    public void displayAllEmployees() {
        employeeManager.getAllEmployees();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Controller::new);
    }
    boolean checkInput() {
        return guiManager.checkInput();
    }
    private void setToWhite() {
    	  guiManager.setToWhite();
    }


    
    public boolean checkForChanges() {
       
    	return guiManager.checkForChanges();
    }

}
