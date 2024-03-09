package org.example;


import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Student {
    private final long studentId;
    private String name;
    private int DormitoryId;
    final private static Connection dbConn = dbConnection.getconn();
    private static PreparedStatement student_table_find;
    private static PreparedStatement student_table_insert;
    private static PreparedStatement student_table_update;
    private static PreparedStatement student_table_delete;
    private static PreparedStatement student_table_exist;
    private static PreparedStatement set_leader;
    private static PreparedStatement find_leader;
    private static PreparedStatement transfer_dorm;
    private static PreparedStatement transfer_dorms;
    private static PreparedStatement transfer_leader;
    public int getDormitoryId(){
        return this.DormitoryId;
    }
    public Student(long _studentId, String _name, int _DormitoryId){
        this.studentId = _studentId;
        this.name = _name;
        this.DormitoryId = _DormitoryId;
        if (DormitoryManager.IsExistingDorm(this.DormitoryId)) {
            if (!IsExisting(this.studentId))
                this.InsertDB();
            else
                this.UpdateDB();
        }
    }
    public Student(long _studentId){
        this.studentId = _studentId;
        this.RenewDB();
    }
    public void InsertDB(){
        try {
            if (student_table_insert == null)
                student_table_insert = dbConn.prepareStatement("INSERT INTO student (name, StudentId, DormitoryId) VALUES (?, ?, ?); ");
            student_table_insert.setString(1, this.name);
            student_table_insert.setLong(2, this.studentId);
            student_table_insert.setInt(3, this.DormitoryId);
            student_table_insert.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Error: insert student data failed");
            e.printStackTrace();
            System.exit(1);
        }
    }
    public void UpdateDB(){
        try {
            if (student_table_update == null)
                student_table_update = dbConn.prepareStatement("UPDATE student SET DormitoryId = ?, name = ? WHERE StudentId = ?");
            student_table_update.setInt(1, this.DormitoryId);
            student_table_update.setString(2, this.name);
            student_table_update.setLong(3, this.studentId);
            student_table_update.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Error: update student data failed");
            e.printStackTrace();
            System.exit(1);
        }
    }
    public void DeleteDB(){
        try {
            if (student_table_delete == null)
                student_table_delete = dbConn.prepareStatement("DELETE FROM student WHERE StudentId = ?");
            if (this.IsLeader())
                DormitoryManager.ResetLeader(this.DormitoryId);
            student_table_delete.setLong(1, this.studentId);
            student_table_delete.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Error: delete student data failed");
            e.printStackTrace();
            System.exit(1);
        }
    }
    public ArrayList<String[]> getData(){
        String[] line = {"StudentId", "name", "DormitoryId"};
        ArrayList<String[]> res = new ArrayList<>();
        res.add(Arrays.copyOf(line, 3));
        line[0] = String.valueOf(this.studentId);
        line[1] = this.name;
        line[2] = String.valueOf(this.DormitoryId);
        res.add(Arrays.copyOf(line, 3));
        return res;
    }
    public void setLeader(){
        try {
            if (set_leader == null)
                set_leader = dbConn.prepareStatement("UPDATE dormitory SET LeaderId = ? WHERE DormitoryId = ?");
            set_leader.setLong(1, this.studentId);
            set_leader.setInt(2, this.DormitoryId);
            set_leader.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Error: update dorm data failed");
            e.printStackTrace();
            System.exit(1);
        }
    }
    public void RenewDB(){
        try{
            if (student_table_find == null)
                student_table_find = dbConn.prepareStatement("SELECT * FROM student WHERE StudentId = ?");
            student_table_find.setLong(1, this.studentId);
            ResultSet _stu = student_table_find.executeQuery();
            _stu.next();
            this.name = _stu.getString("name");
            this.DormitoryId = _stu.getInt("DormitoryId");
        }
        catch (SQLException e){
            System.out.println("Error: get student data failed");
            e.printStackTrace();
            System.exit(1);
        }
    }
    public boolean IsLeader(){
        try {
            if (find_leader == null)
                find_leader = dbConn.prepareStatement("SELECT LeaderId FROM dormitory WHERE DormitoryId = ?");
            find_leader.setInt(1,this.DormitoryId);
            ResultSet leader = find_leader.executeQuery();
            leader.next();
            return leader.getInt("LeaderId") == this.studentId;
        }
        catch (SQLException e){
            System.out.println("Error: get dormitory data failed");
            e.printStackTrace();
            System.exit(1);
            return false;
        }
    }
    public static boolean IsExisting(long studentId){
        try {
            if (student_table_exist == null)
                student_table_exist = dbConn.prepareStatement("SELECT COUNT(*) AS t FROM student WHERE StudentId = ?");
            student_table_exist.setLong(1,studentId);
            ResultSet count = student_table_exist.executeQuery();
            count.next();
            return count.getInt("t") == 1;
        }
        catch (SQLException e){
            System.out.println("Error: get student data failed");
            e.printStackTrace();
            System.exit(1);
            return false;
        }
    }
    public static void TransferDorm(Student stu1, Student stu2){
        try {
            if (transfer_dorm == null)
                transfer_dorm = dbConn.prepareStatement(
            "UPDATE student SET DormitoryId = CASE WHEN StudentId = ? THEN ? WHEN StudentId = ? THEN ? END WHERE StudentId IN (?, ?)");
            if (stu1.IsLeader())
                DormitoryManager.ResetLeader(stu1.DormitoryId);
            if (stu2.IsLeader())
                DormitoryManager.ResetLeader(stu2.DormitoryId);
            transfer_dorm.setLong(1, stu1.studentId);
            transfer_dorm.setInt(2, stu2.DormitoryId);
            transfer_dorm.setLong(3, stu2.studentId);
            transfer_dorm.setInt(4, stu1.DormitoryId);
            transfer_dorm.setLong(5, stu1.studentId);
            transfer_dorm.setLong(6, stu2.studentId);
            transfer_dorm.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Error: transfer student data failed");
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static void TransferDorm(int dorm1, int dorm2){
        long leader1 = DormitoryManager.findLeader(dorm1), leader2 = DormitoryManager.findLeader(dorm2);
        try {
            if (transfer_dorms == null)
                transfer_dorms = dbConn.prepareStatement(
            "UPDATE student SET DormitoryId = CASE WHEN DormitoryId = ? THEN ? WHEN DormitoryId = ? THEN ? END " +
                    "WHERE DormitoryId IN (?, ?)");
            if (transfer_leader == null)
                transfer_leader = dbConn.prepareStatement("UPDATE dormitory" +
                "SET LeaderId = CASE WHEN DormitoryId = ? THEN ? WHEN DormitoryId = ? THEN ? END WHERE DormitoryId IN (?, ?);");
            transfer_leader.setInt(1, dorm1);
            transfer_leader.setLong(2, leader2);
            transfer_leader.setInt(3, dorm2);
            transfer_leader.setLong(4, leader1);
            transfer_leader.setInt(5, dorm1);
            transfer_leader.setInt(6, dorm2);
            transfer_leader.executeUpdate();

            transfer_dorms.setInt(1, dorm1);
            transfer_dorms.setInt(2, dorm2);
            transfer_dorms.setInt(3, dorm2);
            transfer_dorms.setInt(4, dorm1);
            transfer_dorms.setInt(5, dorm1);
            transfer_dorms.setInt(6, dorm2);
            transfer_dorms.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Error: transfer student data failed");
            e.printStackTrace();
            System.exit(1);
        }
    }
    private static ArrayList<String[]> getRes(ResultSet _res) throws SQLException{
        String[] line = {"StudentId", "name", "DormitoryId"};
        ArrayList<String[]> res = new ArrayList<>();
        res.add(Arrays.copyOf(line, 3));
        while (_res.next()){
            line[0] = String.valueOf(_res.getLong("StudentId"));
            line[1] = _res.getString("name");
            line[2] = String.valueOf(_res.getInt("DormitoryId"));
            res.add(Arrays.copyOf(line, 3));
        }
        return res;
    }
    public static ArrayList<String[]> findStudents(String partial_name){
        try {
            PreparedStatement finder = dbConn.prepareStatement("SELECT * FROM student WHERE name LIKE ?");
            finder.setString(1, String.format("%%%s%%", partial_name));
            ResultSet _res = finder.executeQuery();
            ArrayList<String[]> res = getRes(_res);
            finder.close();
            return res;
        }
        catch (SQLException e){
            System.out.println("Error: search Students data failed");
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }
    public static ArrayList<String[]> findStudents(int _DormitoryId){
        try {
            PreparedStatement finder = dbConn.prepareStatement("SELECT * FROM student WHERE DormitoryId = ?");
            finder.setInt(1, _DormitoryId);
            ResultSet _res = finder.executeQuery();
            ArrayList<String[]> res = getRes(_res);
            finder.close();
            return res;
        }
        catch (SQLException e){
            System.out.println("Error: search Students data failed");
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }
    public static ArrayList<String[]> findStudents(String partial_name, int _DormitoryId){
        try {
            PreparedStatement finder = dbConn.prepareStatement("SELECT * FROM student WHERE DormitoryId = ? AND name LIKE ?");
            finder.setInt(1, _DormitoryId);
            finder.setString(2, String.format("%%%s%%", partial_name));
            ResultSet _res = finder.executeQuery();
            ArrayList<String[]> res = getRes(_res);
            finder.close();
            return res;
        }
        catch (SQLException e){
            System.out.println("Error: search Students data failed");
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }

}