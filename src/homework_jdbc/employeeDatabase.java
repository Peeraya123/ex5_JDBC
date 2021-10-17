/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homework_jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class employeeDatabase {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        
        /*String derbyEmbeddedDriver = "org.apache.derby.jdbc.EmbeddedDriver";
        String msAccessDriver = "sun.jdbc.odbc.JdbcOdbcDriver";
        String msSQlDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String oracleDriver = "oracle.jdbc.driver.OracleDriver";*/

        String derbyClientDriver = "org.apache.derby.jdbc.ClientDriver";
        //String mySqlDriver = "com.mysql.cj.jdbc.Driver";
        //load driver
        Class.forName(derbyClientDriver);   //โหลด driver
        //Class.forName(mySqlDriver);
        //create connection
        /*
         * String url="jdbc:mysql://server[:port]/databaseName"; //for mySQL
         * String url="jdbc:derby:databaseName"; //for DerbyEmbedded
         * String url= "jdbc:odbc:Driver=:datasourceNameOfODBC" //for MS Accces
         * String url= "jdbc:sqlserver://server[:port]:database="databaseName" //for MS SQL Server 
         * String url= "jdbc:oracle:thin:@server:port:databaseName" //for Oracle
         */
        String url = "jdbc:derby://localhost:1527/employee";
        //String url="jdbc:mysql://localhost:3306/employee?serverTimezone=UTC";
        String user = "app";
        //String user = "root";
        String passwd = "app";
        //String passwd = "root";

        Connection con = DriverManager.getConnection(url, user, passwd);
        //create statement
       Statement stmt = con.createStatement();
       employee emp1 = new employee(1, "John", 4.00);
       employee emp2 = new employee(2, "Marry", 3.50);
       insertEmployee(stmt, emp1);
       insertEmployee(stmt, emp2);
       //Employee emp = getEmployeeById(stmt, 1);
       //emp.setSalary(12346);
        //updateEmployeeSalary(stmt, emp);
        //emp.setName("Jack");
        //updateEmployeeName(stmt, emp);
        //deleteEmployee(stmt, emp);
        //Employee emp3 = new Employee(3,"Markus", 14578);
        //Employee emp4 = new Employee(4,"Mark", 24579);
        //insertEmployeePreparedStatement(con, emp1);
        //insertEmployeePreparedStatement(con, emp2);

        //Employee emp = getEmployeeByIdPreparedStatement(con, 1);
        //emp.setName("Jack");
        //emp.setSalary(98765);
        //updateEmployeeNamePreparedStatement(con, emp);
        //updateEmployeeSalaryPreparedStatement(con, emp);
        //deleteEmployeePreparedStatement(con, emp);

        //ArrayList<Employee> employeeList = getAllEmployee(con);
        //printAllEmployee(employeeList);
        //close connection
        stmt.close();
        con.close();
    }
    public static void printAllEmployee(ArrayList<employee> employeeList) {
        for(employee emp : employeeList) {
           System.out.print(emp.getID() + " ");
           System.out.print(emp.getName() + " ");
           System.out.println(emp.getGPA() + " ");
       }
    }
    
    public static ArrayList<employee> getAllEmployee (Connection con) throws SQLException {
        String sql = "select * from employee order by ID";
        PreparedStatement ps = con.prepareStatement(sql);
        ArrayList<employee> employeeList = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
           employee employee = new employee();
           employee.setID(rs.getInt("ID"));
           employee.setName(rs.getString("name"));
           employee.setGPA(rs.getDouble("GPA"));
           employeeList.add(employee);
       }
       rs.close();
       return employeeList;
       
    }
    
   public static employee getEmployeeById(Statement stmt, int ID) throws SQLException {
       employee emp = null;
       String sql = "select * from employee where ID = " + ID;
       ResultSet rs = stmt.executeQuery(sql);
       if (rs.next()) {
           emp = new employee();
           emp.setID(rs.getInt("ID"));
           emp.setName(rs.getString("name"));
           emp.setGPA(rs.getDouble("GPA"));
       }
       return emp;
   } 
   public static void insertEmployee(Statement stmt, employee emp) throws SQLException {
       /*String sql = "insert into employee (id, name, salary)" +
                     " values (5, 'Mark', 12345)";*/
        String sql = "insert into employee (ID, name, GPA)" +
                     " values (" + emp.getID() + "," + "'" + emp.getName() + "'" + "," + emp.getGPA() + ")";
        int result = stmt.executeUpdate(sql);
        System.out.println("Insert " + result + " row");
   } 
   public static void deleteEmployee(Statement stmt, employee emp) throws SQLException {
       String sql = "delete from employee where ID = " + emp.getID();
       int result = stmt.executeUpdate(sql);
        //display result
        System.out.println("delete " + result + " row");
   }
   public static void updateEmployeeSalary(Statement stmt, employee emp) throws SQLException {
       String sql = "update employee set GPA  = " + emp.getGPA() + 
               " where ID = " + emp.getID();
       int result = stmt.executeUpdate(sql);
        //display result
        System.out.println("update " + result + " row");
   }
   public static void updateEmployeeName(Statement stmt, employee emp) throws SQLException {
       String sql = "update employee set name  = '" + emp.getName() + "'" + 
               " where ID = " + emp.getID();
       int result = stmt.executeUpdate(sql);
        //display result
        System.out.println("update " + result + " row");
   }
   
   public static void insertEmployeePreparedStatement(Connection con, employee emp) throws SQLException {
       String sql = "insert into employee (ID, name, GPA)" + 
               " values (?,?,?)";
       PreparedStatement ps = con.prepareStatement(sql);
       ps.setInt(1, emp.getID());
       ps.setString(2, emp.getName());
       ps.setDouble(3, emp.getGPA());
       int result = ps.executeUpdate();
        //display result
        System.out.println("Insert " + result + " row");
   }
   public static void deleteEmployeePreparedStatement(Connection con, employee emp) throws SQLException {
       String sql ="delete from employee where ID = ?";
       PreparedStatement ps = con.prepareStatement(sql);
       ps.setInt(1, emp.getID());
       int result = ps.executeUpdate();
        //display result
        System.out.println("Delete " + result + " row");
   }
   public static void updateEmployeeSalaryPreparedStatement(Connection con, employee emp) throws SQLException {
       String sql = "update employee set GPA  = ? where ID = ? ";
       PreparedStatement ps = con.prepareStatement(sql);
       ps.setDouble(1, emp.getGPA());
       ps.setInt(2, emp.getID());
       int result = ps.executeUpdate();
        //display result
        System.out.println("update " + result + " row");
   }
   public static void updateEmployeeNamePreparedStatement(Connection con, employee emp) throws SQLException {
       String sql = "update employee set name  = ? where ID = ? ";
       PreparedStatement ps = con.prepareStatement(sql);
       ps.setString(1, emp.getName());
       ps.setInt(2, emp.getID());
       int result = ps.executeUpdate();
        //display result
        System.out.println("update " + result + " row");
   }
   public static employee getEmployeeByIdPreparedStatement(Connection con, int id) throws SQLException {
       employee emp = null;
       String sql = "select * from employee where ID = ?";
       PreparedStatement ps = con.prepareStatement(sql);
       ps.setInt(1, id);
       ResultSet rs = ps.executeQuery();
       if (rs.next()) {
           emp = new employee();
           emp.setID(rs.getInt("ID"));
           emp.setName(rs.getString("name"));
           emp.setGPA(rs.getDouble("GPA"));
       }
       return emp;
   }
}
