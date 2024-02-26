package Answer2;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.miginfocom.swing.MigLayout;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowListener;
import java.text.DecimalFormat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUIManager implements ActionListener, DocumentListener, ItemListener, WindowListener {
	 private Controller controller;
	 private AddRecordDialog addRecordDialog;
	 private EventHandler eventHandler;
	    private EmployeeDetails employeeDetails;
	 boolean change = false;
    private JFrame frame;
    private JButton closeApp;
    private JButton open;
    private JButton save;
    private JButton saveAs;
    private JButton searchById;
    private JButton searchBySurname;
    private JTextField searchByIdField;
    private JTextField searchBySurnameField;
    private JButton searchId;
    private JButton searchSurname;
    private JButton saveChange;
    private JButton cancelChange;
    private JButton firstItem;
    private JButton prevItem;
    private JButton nextItem;
    private JButton lastItem;
    private JButton listAll;
    private JButton create;
    private JButton modify;
    private JButton delete;
    private JTextArea textArea;
	

    public GUIManager(Controller controller) {
        this.controller = controller;
      
        initializeComponents();
    }

  
    private void initializeComponents() {
       eventHandler= new EventHandler(null);
        addRecordDialog = new AddRecordDialog(null);
      employeeDetails = new EmployeeDetails();
    }
    public GUIManager() {
    	 initializeComponents();
        frame = new JFrame();
        closeApp = new JButton("Close");
        open = new JButton("Open");
        save = new JButton("Save");
        saveAs = new JButton("Save As");
        searchById = new JButton("Search by ID");
        searchBySurname = new JButton("Search by Surname");
        searchByIdField = new JTextField();
        searchBySurnameField = new JTextField();
        searchId = new JButton("Search ID");
        searchSurname = new JButton("Search Surname");
        saveChange = new JButton("Save Change");
        cancelChange = new JButton("Cancel Change");
        firstItem = new JButton("First");
        prevItem = new JButton("Previous");
        nextItem = new JButton("Next");
        lastItem = new JButton("Last");
        listAll = new JButton("List All");
        create = new JButton("Create");
        modify = new JButton("Modify");
        delete = new JButton("Delete");
        textArea = new JTextArea();

        // Set up frame and add components
        frame.setTitle("Employee Details");
        frame.setSize(760, 600);
        frame.setLocation(250, 200);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(this);

        JPanel topPanel = new JPanel();
        topPanel.add(closeApp);
        topPanel.add(open);
        topPanel.add(save);
        topPanel.add(saveAs);
        topPanel.add(searchById);
        topPanel.add(searchByIdField);
        topPanel.add(searchId);
        topPanel.add(searchBySurname);
        topPanel.add(searchBySurnameField);
        topPanel.add(searchSurname);
        topPanel.add(saveChange);
        topPanel.add(cancelChange);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(firstItem);
        bottomPanel.add(prevItem);
        bottomPanel.add(nextItem);
        bottomPanel.add(lastItem);
        bottomPanel.add(listAll);
        bottomPanel.add(create);
        bottomPanel.add(modify);
        bottomPanel.add(delete);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       
        eventHandler.actionPerformed(e);
    }


    @Override
    public void changedUpdate(DocumentEvent d) {
        if (d.getDocument() == searchByIdField.getDocument() || d.getDocument() == searchBySurnameField.getDocument()) {
            if (searchByIdField.getText().isEmpty() && searchBySurnameField.getText().isEmpty()) {
                // Both search fields are empty, disable the search buttons
                searchId.setEnabled(false);
                searchSurname.setEnabled(false);
            } else {
                // At least one search field has text, enable the search buttons
                searchId.setEnabled(true);
                searchSurname.setEnabled(true);
            }
        }
    }


    @Override
    public void insertUpdate(DocumentEvent d) {
         if (d.getDocument() == searchByIdField.getDocument() || d.getDocument() == searchBySurnameField.getDocument()) {
            if (searchByIdField.getText().length() > 0 || searchBySurnameField.getText().length() > 0) {
                // At least one search field has text, enable the search buttons
                searchId.setEnabled(true);
                searchSurname.setEnabled(true);
            } else {
                // Both search fields are empty, disable the search buttons
                searchId.setEnabled(false);
                searchSurname.setEnabled(false);
            }
        }
    }


    @Override
    public void removeUpdate(DocumentEvent d) {
        if (d.getDocument() == searchByIdField.getDocument() || d.getDocument() == searchBySurnameField.getDocument()) {
            if (searchByIdField.getText().length() > 0 || searchBySurnameField.getText().length() > 0) {
                // At least one search field still has text, keep the search buttons enabled
                searchId.setEnabled(true);
                searchSurname.setEnabled(true);
            } else {
                // Both search fields are empty, disable the search buttons
                searchId.setEnabled(false);
                searchSurname.setEnabled(false);
            }
        }
    }


    @Override
    public void itemStateChanged(ItemEvent e) {
       
        if (e.getSource() == listAll) {
            // If the "List All" button or item state changes
            if (e.getStateChange() == ItemEvent.SELECTED) {
                // If the button or item is selected
                controller.displayAllEmployees(); // Call a method in the controller to display all employees
            }
        }
    }

    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosing(WindowEvent e) {
        if (checkInput() && !checkForChanges()) {
            controller.exitApp();
        }
    }





	boolean checkForChanges() {
		 return employeeDetails.checkForChanges();
	}


	public void windowClosed(WindowEvent e) {}

    public void windowIconified(WindowEvent e) {}

    public void windowDeiconified(WindowEvent e) {}

    public void windowActivated(WindowEvent e) {}

    public void windowDeactivated(WindowEvent e) {}

    public void setEnabled(boolean b) {

        closeApp.setEnabled(b);
        open.setEnabled(b);
        save.setEnabled(b);
        saveAs.setEnabled(b);
        searchById.setEnabled(b);
        searchBySurname.setEnabled(b);
        searchByIdField.setEnabled(b);
        searchBySurnameField.setEnabled(b);
        searchId.setEnabled(b);
        searchSurname.setEnabled(b);
        saveChange.setEnabled(b);
        cancelChange.setEnabled(b);
        firstItem.setEnabled(b);
        prevItem.setEnabled(b);
        nextItem.setEnabled(b);
        lastItem.setEnabled(b);
        listAll.setEnabled(b);
        create.setEnabled(b);
        modify.setEnabled(b);
        delete.setEnabled(b);
    }
    public boolean checkInput() {
        boolean valid = true;
        
        // Check input fields in AddRecordDialog
        if (addRecordDialog != null) {
            valid = addRecordDialog.checkInput();
        } else {
           
            System.err.println("Error: AddRecordDialog is null or not initialized");
            valid = false; // Set valid to false as a fallback
        }
        
    
        
        return valid;
    }



	public void setToWhite() {
		addRecordDialog.setToWhite() ;
		
	}


	
	
}
