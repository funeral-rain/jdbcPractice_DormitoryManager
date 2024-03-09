package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DormitoryManager {
    final private static Connection dbConn = dbConnection.getconn();
    private static final Map<String, Integer> IdAreaDic = new HashMap<>();
    private static final Map<String, Integer> IdBuildingDic = new HashMap<>();
    private static PreparedStatement update_area;
    private static PreparedStatement insert_building;
    private static PreparedStatement update_building;
    private static PreparedStatement insert_dorm;
    private static PreparedStatement delete_dorm;
    private static PreparedStatement delete_students_in_dorm;
    private static PreparedStatement reset_leader;
    private static PreparedStatement update_capacity;
    private static PreparedStatement find_leader;
    private static PreparedStatement find_dorm_capacity;
    private static PreparedStatement find_dorm_CurrentOccupancy;
    private static PreparedStatement find_dorm_InBuilding;
    private static PreparedStatement find_dorm_InArea;
    private static PreparedStatement find_all_dorm;
    private static PreparedStatement find_dorm_AreaBuilding;
    private static PreparedStatement dorm_table_count;
    private static PreparedStatement dorm_area_count;
    private static PreparedStatement dic_rows_count;
    private static PreparedStatement dorm_building_count;
    private static PreparedStatement dic_is_empty_for_area;

    public static void alterArea(int switchRes, String al_area){
        try {
            if (update_area == null)
                update_area = dbConn.prepareStatement("UPDATE dormiddic SET area = ? WHERE switchRes = ?");
            update_area.setString(1, al_area);
            update_area.setInt(2, switchRes);
            update_area.executeUpdate();
            renewDic();
        }
        catch (SQLException e){
            System.out.println("Error: update area data failed");
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static void renameArea(String old_area, String new_area){
        try {
            if (update_building == null)
                update_building = dbConn.prepareStatement("UPDATE dormiddic SET area = ? WHERE area = ?");
            update_building.setString(1, new_area);
            update_building.setString(2, old_area);
            update_building.executeUpdate();
            renewDic();
        }
        catch (SQLException e){
            System.out.println("Error: update area data failed");
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static void InsertBuilding(String building){
        try {
            if (insert_building == null)
                insert_building = dbConn.prepareStatement("INSERT INTO dormiddic (switchRes, building) VALUES (?, ?)");
            insert_building.setInt(1, countDicRows() + 1);
            insert_building.setString(2, building);
            insert_building.executeUpdate();
            renewDic();
        }
        catch (SQLException e){
            System.out.println("Error: insert building data failed");
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static void renameBuilding(String old_building, String new_building){
        try {
            if (update_building == null)
                update_building = dbConn.prepareStatement("UPDATE dormiddic SET building = ? WHERE building = ?");
            update_building.setString(1, new_building);
            update_building.setString(2, old_building);
            update_building.executeUpdate();
            renewDic();
        }
        catch (SQLException e){
            System.out.println("Error: update building data failed");
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static void InsertDorm(int RootId, String building, String area, int capacity){
        try {
            if (insert_dorm == null)
                insert_dorm = dbConn.prepareStatement("INSERT INTO dormitory (building, area, capacity, RoomId)" +
                        "VALUES (?, ?, ?, ?);");
            insert_dorm.setString(1, building);
            insert_dorm.setString(2, area);
            insert_dorm.setInt(3, capacity);
            insert_dorm.setInt(4, RootId);
            insert_dorm.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Error: insert dormitory data failed");
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static void DeleteDorm(int DormId){
        try {
            if (delete_dorm == null)
                delete_dorm = dbConn.prepareStatement("DELETE FROM dormitory WHERE DormitoryId = ?");
            if (delete_students_in_dorm == null)
                delete_students_in_dorm = dbConn.prepareStatement("DELETE FROM student WHERE DormitoryId = ?");
            delete_dorm.setInt(1, DormId);
            delete_students_in_dorm.setInt(1, DormId);
            delete_students_in_dorm.executeUpdate();
            delete_dorm.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Error: delete dormitory data failed");
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static void DeleteDorm(String area_name, String building_name){
        try {
            if (find_dorm_AreaBuilding == null)
                find_dorm_AreaBuilding = dbConn.prepareStatement("SELECT * FROM dormitory WHERE area = ? AND building = ?");
            find_dorm_AreaBuilding.setString(1, area_name);
            find_dorm_AreaBuilding.setString(2, building_name);
            ResultSet Dorm = find_dorm_AreaBuilding.executeQuery();
            while (Dorm.next())
                DeleteDorm(Dorm.getInt("DormitoryId"));
        }
        catch (SQLException e){
            System.out.println("Error: delete dormitory data failed");
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static void DeleteDormArea(String area_name){
        try {
            if (find_dorm_InArea == null)
                find_dorm_InArea = dbConn.prepareStatement("SELECT * FROM dormitory WHERE area = ?");
            find_dorm_InArea.setString(1, area_name);
            ResultSet Dorm = find_dorm_InArea.executeQuery();
            while (Dorm.next())
                DeleteDorm(Dorm.getInt("DormitoryId"));
        }
        catch (SQLException e){
            System.out.println("Error: delete dormitory data failed");
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static void DeleteDormBuilding(String building_name){
        try {
            if (find_dorm_InBuilding == null)
                find_dorm_InBuilding = dbConn.prepareStatement("SELECT * FROM dormitory WHERE building = ?");
            find_dorm_InBuilding.setString(1, building_name);
            ResultSet Dorm = find_dorm_InBuilding.executeQuery();
            while (Dorm.next())
                DeleteDorm(Dorm.getInt("DormitoryId"));
        }
        catch (SQLException e){
            System.out.println("Error: delete dormitory data failed");
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static void DeleteAllDorm(){
        try {
            if (find_all_dorm == null)
                find_all_dorm = dbConn.prepareStatement("SELECT * FROM dormitory");
            ResultSet Dorm = find_all_dorm.executeQuery();
            while (Dorm.next())
                DeleteDorm(Dorm.getInt("DormitoryId"));
        }
        catch (SQLException e){
            System.out.println("Error: delete dormitory data failed");
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static void ResetLeader(int DormId){
        try {
            if (reset_leader == null)
                reset_leader = dbConn.prepareStatement("UPDATE dormitory SET LeaderId = 0 WHERE DormitoryId = ?");
            reset_leader.setInt(1, DormId);
            reset_leader.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Error: reset leader failed");
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static void UpdateCapacity(int DormId, int new_capacity){
        try {
            if (update_capacity == null)
                update_capacity = dbConn.prepareStatement("UPDATE dormitory SET capacity = ? WHERE DormitoryId = ?");
            update_capacity.setInt(1, new_capacity);
            update_capacity.setInt(2, DormId);
            update_capacity.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Error: reset leader failed");
            e.printStackTrace();
            System.exit(1);
        }
    }
    private static void renewDic(){
        try {
            PreparedStatement upgrade = dbConn.prepareStatement("SELECT * FROM dormiddic");
            ResultSet Dic = upgrade.executeQuery();
            while (Dic.next()){
                IdAreaDic.put(Dic.getString("area"), Dic.getInt("switchRes"));
                IdBuildingDic.put(Dic.getString("building"), Dic.getInt("switchRes"));
            }
        }
        catch (SQLException e){
            System.out.println("Error: get dict failed");
            e.printStackTrace();
            System.exit(1);
        }
    }
    private static int CreateDormId(int RootId, String building, String area){
        return IdAreaDic.get(area) * 1000000 + IdBuildingDic.get(building) * 10000 + RootId;
    }
    public static boolean IsExistingArea(String area_name){
        if (area_name == null) return false;
        if (IdAreaDic.isEmpty()) renewDic();
        return IdAreaDic.containsKey(area_name);
    }
    public static boolean IsExistingBuilding(String building_name){
        if (building_name == null) return false;
        if (IdBuildingDic.isEmpty()) renewDic();
        return IdBuildingDic.containsKey(building_name);
    }
    public static boolean IsExistingDorm(int RootId, String building, String area){
        if (!IsExistingArea(area) || !IsExistingBuilding(building))
            return false;
        int DormId = CreateDormId(RootId, building, area);
        return IsExistingDorm(DormId);
    }
    public static boolean IsExistingDorm(int DormId){
        try {
            if (dorm_table_count == null)
                dorm_table_count = dbConn.prepareStatement("SELECT COUNT(*) AS t FROM dormitory WHERE DormitoryId = ?");
            dorm_table_count.setInt(1, DormId);
            ResultSet count = dorm_table_count.executeQuery();
            count.next();
            return count.getInt("t") == 1;
        }
        catch (SQLException e){
            System.out.println("Error: get dormitory data failed");
            e.printStackTrace();
            System.exit(1);
            return false;
        }
    }
    public static boolean IsEmptyForArea(int switchRes){
        try {
            if (dic_is_empty_for_area == null)
                dic_is_empty_for_area = dbConn.prepareStatement("SELECT area FROM dormiddic WHERE switchRes = ?");
            dic_is_empty_for_area.setInt(1, switchRes);
            ResultSet count = dic_is_empty_for_area.executeQuery();
            count.next();
            return count.getString("area") == null;
        }
        catch (SQLException e){
            System.out.println("Error: get dormitory data failed");
            e.printStackTrace();
            System.exit(1);
            return false;
        }
    }
    public static long findLeader(int DormId){
        try {
            if (find_leader == null)
                find_leader = dbConn.prepareStatement("SELECT LeaderId FROM dormitory WHERE DormitoryId = ?");
            find_leader.setInt(1, DormId);
            ResultSet count = find_leader.executeQuery();
            count.next();
            return count.getLong("LeaderId");
        }
        catch (SQLException e){
            System.out.println("Error: find dormitory data failed");
            e.printStackTrace();
            System.exit(1);
            return 0;
        }
    }
    public static int findDormCapacity(int DormId){
        try {
            if (find_dorm_capacity == null)
                find_dorm_capacity = dbConn.prepareStatement("SELECT capacity FROM dormitory WHERE DormitoryId = ?");
            find_dorm_capacity.setInt(1, DormId);
            ResultSet count = find_dorm_capacity.executeQuery();
            count.next();
            return count.getInt("capacity");
        }
        catch (SQLException e){
            System.out.println("Error: find dormitory data failed");
            e.printStackTrace();
            System.exit(1);
            return 0;
        }
    }
    public static int findDormCurrentOccupancy(int DormId){
        try {
            if (find_dorm_CurrentOccupancy == null)
                find_dorm_CurrentOccupancy = dbConn.prepareStatement("SELECT currentOccupancy FROM dormitory WHERE DormitoryId = ?");
            find_dorm_CurrentOccupancy.setInt(1, DormId);
            ResultSet count = find_dorm_CurrentOccupancy.executeQuery();
            count.next();
            return count.getInt("currentOccupancy");
        }
        catch (SQLException e){
            System.out.println("Error: find dormitory data failed");
            e.printStackTrace();
            System.exit(1);
            return 0;
        }
    }
    public static int countDicRows(){
        try {
            if (dic_rows_count == null)
                dic_rows_count = dbConn.prepareStatement("SELECT COUNT(*) AS t FROM dormiddic");
            ResultSet count = dic_rows_count.executeQuery();
            count.next();
            return count.getInt("t");
        }
        catch (SQLException e){
            System.out.println("Error: find DormidDic data failed");
            e.printStackTrace();
            System.exit(1);
            return 0;
        }
    }
    public static int countDormInArea(String area_name){
        try {
            if (dorm_area_count == null)
                dorm_area_count = dbConn.prepareStatement("SELECT COUNT(*) AS t FROM dormitory WHERE area = ?");
            dorm_area_count.setString(1, area_name);
            ResultSet count = dorm_area_count.executeQuery();
            count.next();
            return count.getInt("t");
        }
        catch (SQLException e){
            System.out.println("Error: find dormitory data failed");
            e.printStackTrace();
            System.exit(1);
            return 0;
        }
    }
    public static int countDormInBuilding(String building_name){
        try {
            if (dorm_building_count == null)
                dorm_building_count = dbConn.prepareStatement("SELECT COUNT(*) AS t FROM dormitory WHERE building = ?");
            dorm_building_count.setString(1, building_name);
            ResultSet count = dorm_building_count.executeQuery();
            count.next();
            return count.getInt("t");
        }
        catch (SQLException e){
            System.out.println("Error: find dormitory data failed");
            e.printStackTrace();
            System.exit(1);
            return 0;
        }
    }
    public static ArrayList<String[]> findDorm(int DormId){
        try {
            PreparedStatement finder = dbConn.prepareStatement("SELECT * FROM dormitory WHERE DormitoryId = ?");
            finder.setInt(1, DormId);
            ResultSet _res = finder.executeQuery();
            ArrayList<String[]> res = getDormRes(_res);
            finder.close();
            return res;
        }
        catch (SQLException e){
            System.out.println("Error: search Dorm data failed");
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }
    public static ArrayList<String[]> findDormInArea(String area_name){
        try {
            if (find_dorm_InArea == null)
                find_dorm_InArea = dbConn.prepareStatement("SELECT * FROM dormitory WHERE area = ?");
            find_dorm_InArea.setString(1, area_name);
            ResultSet _res = find_dorm_InArea.executeQuery();
            ArrayList<String[]> res = getDormRes(_res);
            return res;
        }
        catch (SQLException e){
            System.out.println("Error: search Dorm data failed");
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }
    public static ArrayList<String[]> findDormInBuilding(String building_name){
        try {
            if (find_dorm_InBuilding == null)
                find_dorm_InBuilding = dbConn.prepareStatement("SELECT * FROM dormitory WHERE building = ?");
            find_dorm_InBuilding.setString(1, building_name);
            ResultSet _res = find_dorm_InBuilding.executeQuery();
            ArrayList<String[]> res = getDormRes(_res);
            return res;
        }
        catch (SQLException e){
            System.out.println("Error: search Dorm data failed");
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }
    public static ArrayList<String[]> findDorm(String area_name, String building_name){
        try {
            if (find_dorm_AreaBuilding == null)
                find_dorm_AreaBuilding = dbConn.prepareStatement("SELECT * FROM dormitory WHERE area = ? AND building = ?");
            find_dorm_AreaBuilding.setString(1, area_name);
            find_dorm_AreaBuilding.setString(2, building_name);
            ResultSet _res = find_dorm_AreaBuilding.executeQuery();
            ArrayList<String[]> res = getDormRes(_res);
            return res;
        }
        catch (SQLException e){
            System.out.println("Error: search Dorm data failed");
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }
    public static ArrayList<String[]> findAllDorm(){
        try {
            if (find_all_dorm == null)
                find_all_dorm = dbConn.prepareStatement("SELECT * FROM dormitory");
            ResultSet _res = find_all_dorm.executeQuery();
            ArrayList<String[]> res = getDormRes(_res);
            return res;
        }
        catch (SQLException e){
            System.out.println("Error: search Dorm data failed");
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }
    public static ArrayList<String[]> findDic(){
        try {
            PreparedStatement finder = dbConn.prepareStatement("SELECT * FROM dormiddic");
            ResultSet _res = finder.executeQuery();
            ArrayList<String[]> res = getDicRes(_res);
            finder.close();
            return res;
        }
        catch (SQLException e){
            System.out.println("Error: search DormIdDic data failed");
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }
    private static ArrayList<String[]> getDormRes(ResultSet _res) throws SQLException{
        String[] line = {"DormitoryId", "RoomId", "floor", "building", "area", "LeaderId", "capacity", "currentOccupancy"};
        ArrayList<String[]> res = new ArrayList<>();
        res.add(Arrays.copyOf(line, 8));
        while (_res.next()){
            line[0] = String.valueOf(_res.getInt("DormitoryId"));
            line[1] = String.valueOf(_res.getInt("RoomId"));
            line[2] = String.valueOf(_res.getInt("floor"));
            line[3] = _res.getString("building");
            line[4] = _res.getString("area");
            line[5] = String.valueOf(_res.getLong("LeaderId"));
            line[6] = String.valueOf(_res.getInt("capacity"));
            line[7] = String.valueOf(_res.getInt("currentOccupancy"));
            res.add(Arrays.copyOf(line, 8));
        }
        return res;
    }
    private static ArrayList<String[]> getDicRes(ResultSet _res) throws SQLException{
        String[] line = {"id", "building", "area"};
        ArrayList<String[]> res = new ArrayList<>();
        res.add(Arrays.copyOf(line, 3));
        while (_res.next()){
            String temp = _res.getString("area");
            line[0] = String.valueOf(_res.getInt("switchRes"));
            line[1] = _res.getString("building");
            line[2] = temp == null ? "" : temp;
            res.add(Arrays.copyOf(line, 3));
        }
        return res;
    }
}