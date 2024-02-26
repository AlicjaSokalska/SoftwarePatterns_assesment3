package Answer2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventHandler implements ActionListener {
    private EmployeeDetails employeeDetails;

    public EventHandler(EmployeeDetails employeeDetails) {
        this.employeeDetails = employeeDetails;
    }

    public void actionPerformed(ActionEvent e) {
        employeeDetails.actionPerformed(e);
    }

    
}
   