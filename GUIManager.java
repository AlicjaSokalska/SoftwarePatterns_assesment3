package Answer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.text.DecimalFormat;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.miginfocom.swing.MigLayout;import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GUIManager extends JFrame {
    private static final DecimalFormat format = new DecimalFormat("\u20ac ###,###,##0.00");
    private long currentByteStart = 0;
    private RandomFile application = new RandomFile();
    private static EmployeeDetails frame = new EmployeeDetails();

    private FileNameExtensionFilter datfilter = new FileNameExtensionFilter("dat files (*.dat)", "dat");
    private File file;
    private boolean change = false;
    boolean changesMade = false;
    private JMenuItem open, save, saveAs, create, modify, delete, firstItem, lastItem, nextItem, prevItem, searchById,
            searchBySurname, listAll, closeApp;
    private JButton first, previous, next, last, add, edit, deleteButton, displayAll, searchId, searchSurname,
            saveChange, cancelChange;
    private JComboBox<String> genderCombo, departmentCombo, fullTimeCombo;
    private JTextField idField, ppsField, surnameField, firstNameField, salaryField;

    Font font1 = new Font("SansSerif", Font.BOLD, 16);
    String generatedFileName;
    Employee currentEmployee;
    JTextField searchByIdField, searchBySurnameField;
    String[] gender = {"", "M", "F"};
    String[] department = {"", "Administration", "Production", "Transport", "Management"};
    String[] fullTime = {"", "Yes", "No"};

    public GUIManager() {
        createAndShowGUI();
    }

    private void createContentPane() {
        setTitle("Employee Details");
        createRandomFile();
        JPanel dialog = new JPanel(new MigLayout());

        setJMenuBar(menuBar());
        dialog.add(searchPanel(), "width 400:400:400, growx, pushx");
        dialog.add(navigPanel(), "width 150:150:150, wrap");
        dialog.add(buttonPanel(), "growx, pushx, span 2,wrap");
        dialog.add(detailsPanel(), "gap top 30, gap left 150, center");

        JScrollPane scrollPane = new JScrollPane(dialog);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        addWindowListener((WindowListener) this);
    }

    private void createRandomFile() {
		// TODO Auto-generated method stub
		
	}

	private static void createAndShowGUI() {
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.createContentPane();
        frame.setSize(760, 600);
        frame.setLocation(250, 200);
        frame.setVisible(true);
    }

    private JMenuBar menuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu, recordMenu, navigateMenu, closeMenu;

        fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        recordMenu = new JMenu("Records");
        recordMenu.setMnemonic(KeyEvent.VK_R);
        navigateMenu = new JMenu("Navigate");
        navigateMenu.setMnemonic(KeyEvent.VK_N);
        closeMenu = new JMenu("Exit");
        closeMenu.setMnemonic(KeyEvent.VK_E);

        menuBar.add(fileMenu);
        menuBar.add(recordMenu);
        menuBar.add(navigateMenu);
        menuBar.add(closeMenu);

        fileMenu.add(open = new JMenuItem("Open")).addActionListener((ActionListener) this);
        open.setMnemonic(KeyEvent.VK_O);
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        fileMenu.add(save = new JMenuItem("Save")).addActionListener((ActionListener) this);
        save.setMnemonic(KeyEvent.VK_S);
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        fileMenu.add(saveAs = new JMenuItem("Save As")).addActionListener((ActionListener) this);
        saveAs.setMnemonic(KeyEvent.VK_F2);
        saveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, ActionEvent.CTRL_MASK));

        recordMenu.add(create = new JMenuItem("Create new Record")).addActionListener((ActionListener) this);
        create.setMnemonic(KeyEvent.VK_N);
        create.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        recordMenu.add(modify = new JMenuItem("Modify Record")).addActionListener((ActionListener) this);
        modify.setMnemonic(KeyEvent.VK_E);
        modify.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        recordMenu.add(delete = new JMenuItem("Delete Record")).addActionListener((ActionListener) this);

        navigateMenu.add(firstItem = new JMenuItem("First"));
        firstItem.addActionListener((ActionListener) this);
        navigateMenu.add(prevItem = new JMenuItem("Previous"));
        prevItem.addActionListener((ActionListener) this);
        navigateMenu.add(nextItem = new JMenuItem("Next"));
        nextItem.addActionListener((ActionListener) this);
        navigateMenu.add(lastItem = new JMenuItem("Last"));
        lastItem.addActionListener((ActionListener) this);
        navigateMenu.addSeparator();
        navigateMenu.add(searchById = new JMenuItem("Search by ID")).addActionListener((ActionListener) this);
        navigateMenu.add(searchBySurname = new JMenuItem("Search by Surname")).addActionListener((ActionListener) this);
        navigateMenu.add(listAll = new JMenuItem("List all Records")).addActionListener((ActionListener) this);

        closeMenu.add(closeApp = new JMenuItem("Close")).addActionListener((ActionListener) this);
        closeApp.setMnemonic(KeyEvent.VK_F4);
        closeApp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.CTRL_MASK));

        return menuBar;
    }

    private JPanel searchPanel() {
        JPanel searchPanel = new JPanel(new MigLayout());

        searchPanel.setBorder(BorderFactory.createTitledBorder("Search"));
        searchPanel.add(new JLabel("Search by ID:"), "growx, pushx");
        searchPanel.add(searchByIdField = new JTextField(20), "width 200:200:200, growx, pushx");
        searchByIdField.addActionListener((ActionListener) this);
        searchByIdField.setDocument(new JTextFieldLimit(20));
        searchPanel.add(searchId = new JButton("Go"), "width 35:35:35, height 20:20:20, growx, pushx, wrap");
        searchId.addActionListener((ActionListener) this);
        searchId.setToolTipText("Search Employee By ID");

        searchPanel.add(new JLabel("Search by Surname:"), "growx, pushx");
        searchPanel.add(searchBySurnameField = new JTextField(20), "width 200:200:200, growx, pushx");
        searchBySurnameField.addActionListener((ActionListener) this);
        searchBySurnameField.setDocument(new JTextFieldLimit(20));
        searchPanel.add(searchSurname = new JButton("Go"), "width 35:35:35, height 20:20:20, growx, pushx, wrap");
        searchSurname.addActionListener((ActionListener) this);
        searchSurname.setToolTipText("Search Employee By Surname");

        return searchPanel;
    }

    private JPanel navigPanel() {
        JPanel navigPanel = new JPanel();

        navigPanel.setBorder(BorderFactory.createTitledBorder("Navigate"));
        navigPanel.add(first = new JButton(new ImageIcon(
                new ImageIcon("first.png").getImage().getScaledInstance(17, 17, java.awt.Image.SCALE_SMOOTH))));
        first.setPreferredSize(new Dimension(17, 17));
        first.addActionListener((ActionListener) this);
        first.setToolTipText("Display first Record");

        navigPanel.add(previous = new JButton(new ImageIcon(new ImageIcon("prev.png").getImage()
                .getScaledInstance(17, 17, java.awt.Image.SCALE_SMOOTH))));
        previous.setPreferredSize(new Dimension(17, 17));
        previous.addActionListener((ActionListener) this);
        previous.setToolTipText("Display next Record");

        navigPanel.add(next = new JButton(new ImageIcon(
                new ImageIcon("next.png").getImage().getScaledInstance(17, 17, java.awt.Image.SCALE_SMOOTH))));
        next.setPreferredSize(new Dimension(17, 17));
        next.addActionListener((ActionListener) this);
        next.setToolTipText("Display previous Record");

        navigPanel.add(last = new JButton(new ImageIcon(
                new ImageIcon("last.png").getImage().getScaledInstance(17, 17, java.awt.Image.SCALE_SMOOTH))));
        last.setPreferredSize(new Dimension(17, 17));
        last.addActionListener((ActionListener) this);
        last.setToolTipText("Display last Record");

        return navigPanel;
    }

    private JPanel buttonPanel() {
        JPanel buttonPanel = new JPanel();

        buttonPanel.add(add = new JButton("Add Record"), "growx, pushx");
        add.addActionListener((ActionListener) this);
        add.setToolTipText("Add new Employee Record");
        buttonPanel.add(edit = new JButton("Edit Record"), "growx, pushx");
        edit.addActionListener((ActionListener) this);
        edit.setToolTipText("Edit current Employee");
        buttonPanel.add(deleteButton = new JButton("Delete Record"), "growx, pushx, wrap");
        deleteButton.addActionListener((ActionListener) this);
        deleteButton.setToolTipText("Delete current Employee");
        buttonPanel.add(displayAll = new JButton("List all Records"), "growx, pushx");
        displayAll.addActionListener((ActionListener) this);
        displayAll.setToolTipText("List all Registered Employees");

        return buttonPanel;
    }

    private JPanel detailsPanel() {
        JPanel empDetails = new JPanel(new MigLayout());
        JPanel buttonPanel = new JPanel();
        JTextField field;

        empDetails.setBorder(BorderFactory.createTitledBorder("Employee Details"));

        empDetails.add(new JLabel("ID:"), "growx, pushx");
        empDetails.add(idField = new JTextField(20), "growx, pushx, wrap");
        idField.setEditable(false);

        empDetails.add(new JLabel("PPS Number:"), "growx, pushx");
        empDetails.add(ppsField = new JTextField(20), "growx, pushx, wrap");

        empDetails.add(new JLabel("Surname:"), "growx, pushx");
        empDetails.add(surnameField = new JTextField(20), "growx, pushx, wrap");

        empDetails.add(new JLabel("First Name:"), "growx, pushx");
        empDetails.add(firstNameField = new JTextField(20), "growx, pushx, wrap");

        empDetails.add(new JLabel("Gender:"), "growx, pushx");
        empDetails.add(genderCombo = new JComboBox<String>(gender), "growx, pushx, wrap");

        empDetails.add(new JLabel("Department:"), "growx, pushx");
        empDetails.add(departmentCombo = new JComboBox<String>(department), "growx, pushx, wrap");

        empDetails.add(new JLabel("Salary:"), "growx, pushx");
        empDetails.add(salaryField = new JTextField(20), "growx, pushx, wrap");

        empDetails.add(new JLabel("Full Time:"), "growx, pushx");
        empDetails.add(fullTimeCombo = new JComboBox<String>(fullTime), "growx, pushx, wrap");

        buttonPanel.add(saveChange = new JButton("Save"));
        saveChange.addActionListener((ActionListener) this);
        saveChange.setVisible(false);
        saveChange.setToolTipText("Save changes");
        buttonPanel.add(cancelChange = new JButton("Cancel"));
        cancelChange.addActionListener((ActionListener) this);
        cancelChange.setVisible(false);
        cancelChange.setToolTipText("Cancel edit");

        empDetails.add(buttonPanel, "span 2,growx, pushx,wrap");

        for (int i = 0; i < empDetails.getComponentCount(); i++) {
            empDetails.getComponent(i).setFont(font1);
            if (empDetails.getComponent(i) instanceof JTextField) {
                field = (JTextField) empDetails.getComponent(i);
                field.setEditable(false);
                if (field == ppsField)
                    field.setDocument(new JTextFieldLimit(9));
                else
                    field.setDocument(new JTextFieldLimit(20));
                field.getDocument().addDocumentListener((DocumentListener) this);
            } else if (empDetails.getComponent(i) instanceof JComboBox) {
                empDetails.getComponent(i).setBackground(Color.WHITE);
                empDetails.getComponent(i).setEnabled(false);
                ((JComboBox<String>) empDetails.getComponent(i)).addItemListener((ItemListener) this);
                ((JComboBox<String>) empDetails.getComponent(i)).setRenderer(new DefaultListCellRenderer() {
                    public void paint(Graphics g) {
                        setForeground(new Color(65, 65, 65));
                        super.paint(g);
                    }
                });
            }
        }
        return empDetails;
    }
}
