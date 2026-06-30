import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Statement;
import java.sql.ResultSet;

public class EmployeeGUI extends JFrame {

    JLabel lblId, lblName, lblDept, lblDesignation, lblSalary;

    JTextField txtId, txtName, txtDept, txtDesignation, txtSalary;

   JButton btnAdd, btnView, btnCalculate, btnClear, btnDelete, btnUpdate;

    JTextArea outputArea;

    public EmployeeGUI() {

        setTitle("Employee Payroll System");
        setSize(600, 500);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Labels
        lblId = new JLabel("Employee ID");
        lblName = new JLabel("Employee Name");
        lblDept = new JLabel("Department");
        lblDesignation = new JLabel("Designation");
        lblSalary = new JLabel("Basic Salary");

        // Text Fields
        txtId = new JTextField();
        txtName = new JTextField();
        txtDept = new JTextField();
        txtDesignation = new JTextField();
        txtSalary = new JTextField();

        // Buttons
        btnAdd = new JButton("Add Employee");
        btnView = new JButton("View Employees");
        btnCalculate = new JButton("Calculate Salary");
        btnClear = new JButton("Clear");
        btnDelete = new JButton("Delete Employee");
        btnUpdate = new JButton("Update Employee");


        // Text Area
        outputArea = new JTextArea();

        JScrollPane scrollPane = new JScrollPane(outputArea);

        // Set Positions
        lblId.setBounds(30, 30, 120, 25);
        txtId.setBounds(160, 30, 180, 25);

        lblName.setBounds(30, 70, 120, 25);
        txtName.setBounds(160, 70, 180, 25);

        lblDept.setBounds(30, 110, 120, 25);
        txtDept.setBounds(160, 110, 180, 25);

        lblDesignation.setBounds(30, 150, 120, 25);
        txtDesignation.setBounds(160, 150, 180, 25);

        lblSalary.setBounds(30, 190, 120, 25);
        txtSalary.setBounds(160, 190, 180, 25);

        btnAdd.setBounds(30, 240, 150, 30);
        btnView.setBounds(200, 240, 150, 30);
        btnCalculate.setBounds(370, 240, 170, 30);
        btnClear.setBounds(410, 280, 130, 30);
        btnDelete.setBounds(220, 280, 170, 30);
        btnUpdate.setBounds(30, 280, 170, 30);


        scrollPane.setBounds(30, 330, 510, 130);

        // Add Components
        add(lblId);
        add(txtId);

        add(lblName);
        add(txtName);

        add(lblDept);
        add(txtDept);

        add(lblDesignation);
        add(txtDesignation);

        add(lblSalary);
        add(txtSalary);

        add(btnAdd);
        add(btnView);
        add(btnCalculate);
        add(btnClear);
        add(btnDelete);
        add(btnUpdate);

        add(scrollPane);
        btnAdd.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        addEmployee();
    }
});

btnView.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        viewEmployees();
    }
});

btnCalculate.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        calculateSalary();
    }
});

btnClear.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        clearFields();
    }
});

btnUpdate.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        updateEmployee();
    }
});

btnDelete.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        deleteEmployee();
    }
});

        setVisible(true);
    }

private void addEmployee() {

    if (txtId.getText().trim().isEmpty() ||
        txtName.getText().trim().isEmpty() ||
        txtDept.getText().trim().isEmpty() ||
        txtDesignation.getText().trim().isEmpty() ||
        txtSalary.getText().trim().isEmpty()) {

        JOptionPane.showMessageDialog(this, "Please fill all fields!");
        return;
    }

    try {

        Connection con = DBConnection.getConnection();

        String sql = "INSERT INTO employee VALUES (?, ?, ?, ?, ?)";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, Integer.parseInt(txtId.getText()));
        ps.setString(2, txtName.getText());
        ps.setString(3, txtDept.getText());
        ps.setString(4, txtDesignation.getText());
        ps.setDouble(5, Double.parseDouble(txtSalary.getText()));

        int rows = ps.executeUpdate();

        if (rows > 0) {
            outputArea.setText("Employee Added Successfully!");
        } else {
            outputArea.setText("Failed to Add Employee!");
        }

        con.close();

    } catch (Exception ex) {
        outputArea.setText(ex.getMessage());
    }

}
private void viewEmployees() {

    try {

        Connection con = DBConnection.getConnection();

        Statement st = con.createStatement();

        ResultSet rs = st.executeQuery("SELECT * FROM employee");

        outputArea.setText("");

        while (rs.next()) {

            outputArea.append(
                    "ID : " + rs.getInt("empId")
                    + "\nName : " + rs.getString("empName")
                    + "\nDepartment : " + rs.getString("department")
                    + "\nDesignation : " + rs.getString("designation")
                    + "\nSalary : " + rs.getDouble("basicSalary")
                    + "\n-----------------------------\n"
            );

        }

        con.close();

    } catch (Exception ex) {

        outputArea.setText(ex.getMessage());

    }

}

    private void calculateSalary() {

    try {

        double basicSalary = Double.parseDouble(txtSalary.getText());

        double hra = basicSalary * 0.20;
        double da = basicSalary * 0.10;
        double grossSalary = basicSalary + hra + da;

        outputArea.setText("");

        outputArea.append("Salary Details\n");
        outputArea.append("-------------------------\n");
        outputArea.append("Employee Name : " + txtName.getText() + "\n");
        outputArea.append("Basic Salary : " + basicSalary + "\n");
        outputArea.append("HRA (20%) : " + hra + "\n");
        outputArea.append("DA (10%) : " + da + "\n");
        outputArea.append("-------------------------\n");
        outputArea.append("Gross Salary : " + grossSalary);

    } catch (Exception ex) {

        outputArea.setText("Please enter a valid salary.");

    }

}
    private void clearFields() {

    txtId.setText("");
    txtName.setText("");
    txtDept.setText("");
    txtDesignation.setText("");
    txtSalary.setText("");

    outputArea.setText("");

    txtId.requestFocus();

}

    private void updateEmployee() {

    if (txtId.getText().trim().isEmpty() ||
        txtName.getText().trim().isEmpty() ||
        txtDept.getText().trim().isEmpty() ||
        txtDesignation.getText().trim().isEmpty() ||
        txtSalary.getText().trim().isEmpty()) {

        JOptionPane.showMessageDialog(this, "Please fill all fields!");
        return;
    }

    try {

        Connection con = DBConnection.getConnection();

        String sql = "UPDATE employee SET empName=?, department=?, designation=?, basicSalary=? WHERE empId=?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, txtName.getText());
        ps.setString(2, txtDept.getText());
        ps.setString(3, txtDesignation.getText());
        ps.setDouble(4, Double.parseDouble(txtSalary.getText()));
        ps.setInt(5, Integer.parseInt(txtId.getText()));

        int rows = ps.executeUpdate();

        if(rows > 0){
            outputArea.setText("Employee Updated Successfully!");
        }else{
            outputArea.setText("Employee Not Found!");
        }

        con.close();

    } catch(Exception ex){

        outputArea.setText(ex.getMessage());

    }

}
private void deleteEmployee() {

    try {

        Connection con = DBConnection.getConnection();

        String sql = "DELETE FROM employee WHERE empId=?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, Integer.parseInt(txtId.getText()));

        int rows = ps.executeUpdate();

        if(rows > 0){
            outputArea.setText("Employee Deleted Successfully!");
        }else{
            outputArea.setText("Employee Not Found!");
        }

        con.close();

    } catch(Exception ex){

        outputArea.setText(ex.getMessage());

    }

}
}