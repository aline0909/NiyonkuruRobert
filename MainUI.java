package ui;

import model.Student;
import service.StudentService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MainUI extends JFrame {

    // ======= Form Fields =======
    private JTextField txtFirstName, txtLastName, txtEmail, txtPhone, txtDepartment;
    private JButton btnInsert, btnUpdate, btnDelete, btnReset;
    private JTable table;
    private DefaultTableModel tableModel;

    // ======= Service =======
    private StudentService studentService;

    // ======= Constructor =======
    public MainUI() {
        studentService = new StudentService();
        initializeUI();
        loadTableData();
    }

    // ======= Initialize UI =======
    private void initializeUI() {
        setTitle("Student Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 245, 245)); // light gray background

        // ======= Left Navigation Panel =======
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new GridLayout(6, 1, 0, 15));
        navPanel.setBorder(new EmptyBorder(20, 10, 20, 10));
        navPanel.setBackground(new Color(25, 118, 210)); // blue panel
        navPanel.setOpaque(true);

        JLabel navTitle = new JLabel("Navigation");
        navTitle.setForeground(Color.WHITE);
        navTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        navTitle.setHorizontalAlignment(SwingConstants.CENTER);
        navPanel.add(navTitle);

        btnInsert = createNavButton("Insert Student");
        btnUpdate = createNavButton("Update Student");
        btnDelete = createNavButton("Delete Student");
        btnReset = createNavButton("Reset Fields");

        navPanel.add(btnInsert);
        navPanel.add(btnUpdate);
        navPanel.add(btnDelete);
        navPanel.add(btnReset);

        add(navPanel, BorderLayout.WEST);

        // ======= Form Panel =======
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(25, 118, 210), 2), "Student Details", 0, 0, new Font("Segoe UI", Font.BOLD, 14), new Color(25, 118, 210)));
        formPanel.setBackground(new Color(245, 245, 245));
        formPanel.setOpaque(true);

        formPanel.add(new JLabel("First Name:"));
        txtFirstName = new JTextField();
        formPanel.add(txtFirstName);

        formPanel.add(new JLabel("Last Name:"));
        txtLastName = new JTextField();
        formPanel.add(txtLastName);

        formPanel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        formPanel.add(txtEmail);

        formPanel.add(new JLabel("Phone:"));
        txtPhone = new JTextField();
        formPanel.add(txtPhone);

        formPanel.add(new JLabel("Department:"));
        txtDepartment = new JTextField();
        formPanel.add(txtDepartment);

        // ======= Table Panel =======
        tableModel = new DefaultTableModel(new String[]{"ID", "First Name", "Last Name", "Email", "Phone", "Department"}, 0);
        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setBackground(new Color(255, 255, 255)); // table background
        table.setForeground(Color.BLACK);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(25);
        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.getViewport().setBackground(new Color(245, 245, 245));

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(245, 245, 245));
        centerPanel.add(formPanel, BorderLayout.NORTH);
        centerPanel.add(tableScroll, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // ======= Footer =======
        JLabel footer = new JLabel("© 2025 All rights reserved – Vugatime", SwingConstants.CENTER);
        footer.setBorder(new EmptyBorder(10, 0, 10, 0));
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footer.setForeground(new Color(50, 50, 50));
        footer.setBackground(new Color(220, 220, 220));
        footer.setOpaque(true);
        add(footer, BorderLayout.SOUTH);

        // ======= Button Actions =======
        btnInsert.addActionListener(e -> insertStudent());
        btnUpdate.addActionListener(e -> updateStudent());
        btnDelete.addActionListener(e -> deleteStudent());
        btnReset.addActionListener(e -> resetFields());

        // ======= Table Row Click =======
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    txtFirstName.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    txtLastName.setText(tableModel.getValueAt(selectedRow, 2).toString());
                    txtEmail.setText(tableModel.getValueAt(selectedRow, 3).toString());
                    txtPhone.setText(tableModel.getValueAt(selectedRow, 4).toString());
                    txtDepartment.setText(tableModel.getValueAt(selectedRow, 5).toString());
                }
            }
        });
    }

    // ======= Navigation Button Factory =======
    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(new Color(30, 136, 229));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setOpaque(true);
        return button;
    }

    // ======= CRUD Methods =======
    private void insertStudent() {
        if (!validateFields()) return;

        Student s = new Student(
                txtFirstName.getText(),
                txtLastName.getText(),
                txtEmail.getText(),
                txtPhone.getText(),
                txtDepartment.getText()
        );

        if (studentService.addStudent(s)) {
            JOptionPane.showMessageDialog(this, "Student added successfully!");
            loadTableData();
            resetFields();
        } else {
            JOptionPane.showMessageDialog(this, "Error adding student!");
        }
    }

    private void updateStudent() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a student to update.");
            return;
        }
        if (!validateFields()) return;

        int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
        Student s = new Student(
                id,
                txtFirstName.getText(),
                txtLastName.getText(),
                txtEmail.getText(),
                txtPhone.getText(),
                txtDepartment.getText()
        );

        if (studentService.updateStudent(s)) {
            JOptionPane.showMessageDialog(this, "Student updated successfully!");
            loadTableData();
            resetFields();
        } else {
            JOptionPane.showMessageDialog(this, "Error updating student!");
        }
    }

    private void deleteStudent() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a student to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
        if (studentService.deleteStudent(id)) {
            JOptionPane.showMessageDialog(this, "Student deleted successfully!");
            loadTableData();
            resetFields();
        } else {
            JOptionPane.showMessageDialog(this, "Error deleting student!");
        }
    }

    private void resetFields() {
        txtFirstName.setText("");
        txtLastName.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        txtDepartment.setText("");
        table.clearSelection();
    }

    // ======= Field Validation =======
    private boolean validateFields() {
        if (txtFirstName.getText().isEmpty() || txtLastName.getText().isEmpty()
                || txtEmail.getText().isEmpty() || txtPhone.getText().isEmpty() || txtDepartment.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return false;
        }
        if (!txtEmail.getText().matches("^\\S+@\\S+\\.\\S+$")) {
            JOptionPane.showMessageDialog(this, "Invalid email format!");
            return false;
        }
        if (!txtPhone.getText().matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Phone must contain only digits!");
            return false;
        }
        return true;
    }

    // ======= Load Table Data =======
    private void loadTableData() {
        tableModel.setRowCount(0);
        List<Student> students = studentService.getAllStudents();
        for (Student s : students) {
            tableModel.addRow(new Object[]{
                    s.getId(),
                    s.getFirstName(),
                    s.getLastName(),
                    s.getEmail(),
                    s.getPhone(),
                    s.getCourse() // still maps to Student.getCourse(), shows as Department in table
            });
        }
    }

    // ======= Main Method =======
    public static void main(String[] args) {
        // Force CrossPlatform L&F for proper colors
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new MainUI().setVisible(true);
        });
    }
}
