/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package homework_jdbc;

/**
 *
 * @author sarun
 */
public class employee {
    
    private int ID;
    private String name;
    private double GPA;
    public employee() {
    }
    public employee(int ID, String name, double GPA) {
        this.ID = ID;
        this.name = name;
        this.GPA = GPA;
    }
    public double getGPA() {
        return GPA;
    }

    public void setGPA(double GPA) {
        this.GPA= GPA;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

}
