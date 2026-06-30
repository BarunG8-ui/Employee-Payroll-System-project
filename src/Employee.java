public class Employee {

    private int empId;
    private String empName;
    private String department;
    private String designation;
    private double basicSalary;



public Employee(int empId, String empName, String department,
    String designation, double basicSalary) {

    this.empId = empId;
    this.empName = empName;
    this.department = department;
    this.designation = designation;
    this.basicSalary = basicSalary;
}


public int getEmpId() {
    return empId;
}

public String getEmpName() {
    return empName;
}

public String getDepartment() {
    return department;
}

public String getDesignation() {
    return designation;
}

public double getBasicSalary() {
    return basicSalary;
}

public void setEmpId(int empId) {
    this.empId = empId;
}

public void setEmpName(String empName) {
    this.empName = empName;
}

public void setDepartment(String department) {
    this.department = department;
}

public void setDesignation(String designation) {
    this.designation = designation;
}

public void setBasicSalary(double basicSalary) {
    this.basicSalary = basicSalary;
}
}