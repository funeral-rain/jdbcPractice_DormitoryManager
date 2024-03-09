package org.example;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

class findData {
    final private static int[] format_AreaBuilding_table = new int[]{4, 10, 6};
    final private static int[] format_dorm_table = new int[]{13, 7, 6, 10, 6, 15, 10, 16};
    final private static int[] format_student_table = new int[]{15, 10, 13};

    public static void find_main() {
        System.out.println("查询子菜单\n");
        boolean IsContinue = true;
        String menu =
                "请输入选项前的序号，选择需要执行的项目，或键入quit回到主菜单\n\n" +
                        "1. 以学号查询学生数据\n" +
                        "2. 以（部分）姓名查询学生\n" +
                        "3. 按宿舍号查询宿舍中所有学生\n" +
                        "4. 按宿舍查询舍长\n" +
                        "5. 按宿舍号查询宿舍信息\n" +
                        "6. 按地区和楼栋查询宿舍\n" +
                        "7. 查询所有地区和楼栋\n";
        Scanner getInput = new Scanner(System.in);
        while (IsContinue) {
            System.out.println(menu);
            if (getInput.hasNextLine()) {
                String input = getInput.nextLine();
                switch (input) {
                    case "1":
                        System.out.println("以学号查找学生\n");
                        findStudentFromId();
                        break;
                    case "2":
                        System.out.println("以（部分）姓名查找学生\n");
                        findStudentFromName();
                        break;
                    case "3":
                        System.out.println("按宿舍号查询宿舍中所有学生\n");
                        findStudentsInDorm();
                        break;
                    case "4":
                        System.out.println("4. 按宿舍查询舍长\n");
                        findLeader();
                        break;
                    case "5":
                        System.out.println("按宿舍号查询宿舍信息\n");
                        findDormFromId();
                        break;
                    case "6":
                        System.out.println("按地区和楼栋查询宿舍\n");
                        findDormAreaBuilding();
                        break;
                    case "7":
                        System.out.println("查找楼栋与地区\n");
                        printAreaBuilding();
                        break;
                    case "quit":
                        IsContinue = false;
                        break;
                    default:
                        System.out.println("请输入正确的选项\n");
                }
            }
        }
    }

    private static void findStudentFromId() {
        long student_id = 0;
        String input;
        Scanner getInput = new Scanner(System.in);
        boolean IsContinue = true;
        while (IsContinue) {
            IsContinue = false;
            while (true) {
                System.out.println("请输入需查询的学生的学号:");
                if (getInput.hasNextLine()) {
                    input = getInput.nextLine();
                    if (Menu.isNumeric(input)) {
                        student_id = Long.parseLong(input);
                        break;
                    }
                    System.out.println("学号应是一串数字，请重新输入");
                }
            }
            printStudentData(student_id);
            System.out.println("输入r可继续查询，否则回到子菜单");
            if (getInput.hasNextLine())
                if (getInput.nextLine().equals("r"))
                    IsContinue = true;
        }
    }

    public static void printStudentData(long student_id) {
        if (!Student.IsExisting(student_id))
            System.out.println("表中无该学生数据");
        else {
            Student student = new Student(student_id);
            Menu.printBoard(student.getData(), format_student_table);
        }
    }

    private static void findStudentFromName() {
        String student_name = "";
        Scanner getInput = new Scanner(System.in);
        System.out.println("请输入需查询的学生的（部分）姓名:");
        if (getInput.hasNextLine())
            student_name = getInput.nextLine();
        System.out.println("符合条件的学生如下：");
        Menu.printBoard(Student.findStudents(student_name), format_student_table);
        System.out.println("按回车返回");
        if (getInput.hasNextLine())
            getInput.nextLine();
    }

    private static void findStudentsInDorm() {
        int DormId;
        String input;
        Scanner getInput = new Scanner(System.in);
        while (true) {
            System.out.println("请输入需查询的宿舍的宿舍号:");
            if (getInput.hasNextLine()) {
                input = getInput.nextLine();
                if (Menu.isNumeric(input)) {
                    DormId = Integer.parseInt(input);
                    break;
                }
                System.out.println("宿舍号应是一串数字，请重新输入");
            }
        }
        printStudentsInDorm(DormId);
        System.out.println("按回车返回");
        if (getInput.hasNextLine())
            getInput.nextLine();
    }

    public static void printStudentsInDorm(int DormId) {
        if (DormitoryManager.IsExistingDorm(DormId))
            Menu.printBoard(Student.findStudents(DormId), format_student_table);
        else
            System.out.println("表中无该宿舍");
    }

    public static void findLeader() {
        int DormId;
        String input;
        Scanner getInput = new Scanner(System.in);
        while (true) {
            System.out.println("请输入需查询舍长的宿舍的宿舍号:");
            if (getInput.hasNextLine()) {
                input = getInput.nextLine();
                if (Menu.isNumeric(input)) {
                    DormId = Integer.parseInt(input);
                    break;
                }
                System.out.println("宿舍号应是一串数字，请重新输入");
            }
        }
        if (DormitoryManager.IsExistingDorm(DormId))
            printStudentData(DormitoryManager.findLeader(DormId));
        else
            System.out.println("表中无该宿舍");
        System.out.println("按回车返回");
        if (getInput.hasNextLine())
            getInput.nextLine();
    }

    private static void findDormFromId() {
        int DormId = 0;
        String input;
        Scanner getInput = new Scanner(System.in);
        boolean IsContinue = true;
        while (IsContinue) {
            IsContinue = false;
            while (true) {
                System.out.println("请输入需查询的宿舍的宿舍号:");
                if (getInput.hasNextLine()) {
                    input = getInput.nextLine();
                    if (Menu.isNumeric(input)) {
                        DormId = Integer.parseInt(input);
                        break;
                    }
                    System.out.println("宿舍号应是一串数字，请重新输入");
                }
            }
            printDormData(DormId);
            printStudentsInDorm(DormId);
            System.out.println("输入r可继续查询，否则回到子菜单");
            if (getInput.hasNextLine())
                if (getInput.nextLine().equals("r"))
                    IsContinue = true;
        }
    }

    public static void printDormData(int DormId) {
        if (DormitoryManager.IsExistingDorm(DormId)) {
            Menu.printBoard(DormitoryManager.findDorm(DormId), format_dorm_table);
        } else
            System.out.println("表中无该宿舍");
    }

    private static void findDormAreaBuilding() {
        String area_name = "", building_name = "";
        Scanner getInput = new Scanner(System.in);
        System.out.println("请输入宿舍所处地区（若不限定则请输入*）:");
        if (getInput.hasNextLine())
            area_name = getInput.nextLine();
        System.out.println("请输入宿舍所处楼栋（若不限定则请输入*）:");
        if (getInput.hasNextLine())
            building_name = getInput.nextLine();
        System.out.println("查询结果如下：");
        if (area_name.equals("*") && building_name.equals("*"))
            printAllDorm();
        else if (area_name.equals("*"))
            printDormInBuilding(building_name);
        else if (building_name.equals("*"))
            printDormInArea(area_name);
        else
            printDormAreaBuilding(area_name, building_name);
        System.out.println("按回车返回");
        if (getInput.hasNextLine())
            getInput.nextLine();
    }

    public static void printDormInArea(String area_name) {
        if (DormitoryManager.IsExistingArea(area_name))
            Menu.printBoard(DormitoryManager.findDormInArea(area_name), format_dorm_table);
        else
            System.out.println("表中无该地区");
    }

    public static void printDormInBuilding(String building_name) {
        if (DormitoryManager.IsExistingBuilding(building_name))
            Menu.printBoard(DormitoryManager.findDormInBuilding(building_name), format_dorm_table);
        else
            System.out.println("表中无该楼栋");
    }

    public static void printDormAreaBuilding(String area_name, String building_name) {
        if (DormitoryManager.IsExistingArea(area_name) && DormitoryManager.IsExistingBuilding(building_name))
            Menu.printBoard(DormitoryManager.findDorm(area_name, building_name), format_dorm_table);
        else
            System.out.println("表中无该地区或无该宿舍");
    }

    public static void printAllDorm() {
        Menu.printBoard(DormitoryManager.findAllDorm(), format_dorm_table);
    }

    public static void printAreaBuilding() {
        Menu.printBoard(DormitoryManager.findDic(), format_AreaBuilding_table);
        Scanner wait = new Scanner(System.in);
        System.out.println("按回车返回");
        if (wait.hasNextLine())
            wait.nextLine();
    }
}
class inputData{
    public static void input_main(){
        System.out.println("添加/修改子菜单\n");
        boolean IsContinue = true;
        String menu =
                "请输入选项前的序号，选择需要执行的项目，或键入quit回到主菜单\n\n" +
                        "1. 添加/修改学生数据\n" +
                        "2. 单个添加宿舍\n" +
                        "3. 批量添加宿舍\n" +
                        "4. 添加楼栋\n" +
                        "5. 添加地区\n" +
                        "6. 交换两个学生所住宿舍\n" +
                        "7. 交换两个宿舍所住人员\n" +
                        "8. 修改宿舍容量\n" +
                        "9. 修改宿舍舍长\n" +
                        "10. 重置宿舍舍长\n" +
                        "11. 修改地区名称\n" +
                        "12. 修改楼栋名称\n";
        Scanner getInput = new Scanner(System.in);
        while (IsContinue) {
            System.out.println(menu);
            if (getInput.hasNextLine()) {
                String input = getInput.nextLine();
                switch (input) {
                    case "1":
                        System.out.println("添加/更改学生数据\n");
                        alterStudent();
                        break;
                    //添加宿舍这个房号不能超过100不然会有问题
                    //原本想着用正则匹配，但是这堆代码本来就很多了我不想增加工作量了寄寄寄
                    case "2":
                        System.out.println("单个添加宿舍\n");
                        InsertDorm();
                        break;
                    case "3":
                        System.out.println("批量添加宿舍\n");
                        InsertDorms();
                        break;
                    case "4":
                        System.out.println("添加楼栋\n");
                        addBuilding();
                        break;
                    case "5":
                        System.out.println("添加地区\n");
                        addArea();
                        break;
                    case "6":
                        System.out.println("交换两个学生所住宿舍\n");
                        transferStudent();
                        break;
                    case "7":
                        System.out.println("交换两个宿舍所住人员\n");
                        transferDorm();
                        break;
                    case "8":
                        System.out.println("修改宿舍容量\n");
                        UpdateDormCapacity();
                        break;
                    case "9":
                        System.out.println("修改宿舍舍长\n");
                        setLeader();
                        break;
                    case "10":
                        System.out.println("重置宿舍舍长\n");
                        resetLeader();
                        break;
                    case "11":
                        System.out.println("修改地区名称\n");
                        renameArea();
                        break;
                    case "12":
                        System.out.println("修改楼栋名称\n");
                        renameBuilding();
                        break;
                    case "quit":
                        IsContinue = false;
                        break;
                    default:
                        System.out.println("请输入正确的选项\n");
                }
            }
        }
    }
    private static void alterStudent(){
        int DormId = 0;
        long student_id = 0;
        String name = "", rawInput;
        String[] input;
        Scanner getInput = new Scanner(System.in);
        boolean IsContinue = true, IsUpdate;
        while (IsContinue){
            IsContinue = true;
            IsUpdate = true;
            System.out.println("请输入需添加/修改的学生的信息: （格式：学号 名字 宿舍号, 以空格分隔）");
            if (getInput.hasNextLine()) {
                rawInput = getInput.nextLine();
                input = rawInput.split(" ");
                if (input.length != 3)
                    System.out.println("输入格式不正确");
                else {
                    if (!Menu.isNumeric(input[0])) {
                        System.out.println("学号应是一串数字，请重新输入");
                        IsUpdate = false;
                    } else
                        student_id = Long.parseLong(input[0]);
                    if (!Menu.isNumeric(input[2])) {
                        System.out.println("宿舍号应是一串数字，请重新输入");
                        IsUpdate = false;
                    } else
                        DormId = Integer.parseInt(input[2]);
                    if (!DormitoryManager.IsExistingDorm(DormId)) {
                        System.out.println("指定的宿舍不存在，请重新输入");
                        IsUpdate = false;
                    }
                    if (DormitoryManager.findDormCapacity(DormId) < DormitoryManager.findDormCurrentOccupancy(DormId) + 1){
                        System.out.println("指定的宿舍已满员");
                        IsUpdate = false;
                    }
                    if (IsUpdate) {
                        name = input[1];
                        new Student(student_id, name, DormId);
                    }
                }
            }
            System.out.println("按下回车继续添加/更改，输入q回到子菜单");
            if (getInput.hasNextLine())
                if (getInput.nextLine().equals("q"))
                    IsContinue = false;
        }
    }
    //添加宿舍的这两个函数有点偷懒，如果房号大于100会出问题，但是懒得特判了））
    //最好是正则匹配来筛掉
    private static void InsertDorm(){
        System.out.println("已有的楼栋名和地区名如下：");
        findData.printAreaBuilding();
        String area_name = "", building_name = "", rawInput;
        int RoomId = 0, capacity = 1;
        String[] input;
        Scanner getInput = new Scanner(System.in);
        boolean IsContinue = true, IsInsert;
        while (IsContinue){
            IsContinue = true;
            IsInsert = true;
            System.out.println("请输入需添加的宿舍的信息: （格式：房号 楼栋 地区 容量, 以空格分隔）");
            if (getInput.hasNextLine()) {
                rawInput = getInput.nextLine();
                input = rawInput.split(" ");
                if (input.length != 4)
                    System.out.println("输入格式不正确");
                else {
                    if (!Menu.isNumeric(input[0])) {
                        System.out.println("房号应是一串数字，请重新输入");
                        IsInsert = false;
                    } else{
                        RoomId = Integer.parseInt(input[0]);
                        if (RoomId <= 0 && (RoomId % 100) == 0){
                            System.out.println("房号不正确");
                            IsInsert = false;
                        }
                    }


                    if (!(DormitoryManager.IsExistingBuilding(input[1]) && DormitoryManager.IsExistingArea(input[2])))
                    {
                        System.out.println("楼栋或地区不存在");
                        IsInsert = false;
                    } else {
                        building_name = input[1];
                        area_name = input[2];
                        if (DormitoryManager.IsExistingDorm(RoomId, building_name, area_name)) {
                            System.out.println("指定的宿舍已存在");
                            IsInsert = false;
                        }
                    }

                    if (!Menu.isNumeric(input[3])) {
                        System.out.println("宿舍容量应是一个数字，请重新输入");
                        IsInsert = false;
                    } else
                        capacity = Integer.parseInt(input[3]);

                    if (IsInsert)
                        DormitoryManager.InsertDorm(RoomId, building_name, area_name, capacity);
                }
            }
            System.out.println("按下回车继续添加/更改，输入q回到子菜单");
            if (getInput.hasNextLine())
                if (getInput.nextLine().equals("q"))
                    IsContinue = false;
        }
    }
    //对于已存在的宿舍，该函数将不做更改
    private static void InsertDorms() {
        System.out.println("已有的楼栋名和地区名如下：");
        findData.printAreaBuilding();
        String area_name = "", building_name = "", rawInput;
        int minFloor = 1, maxFloor = 1, minRoom = 1, maxRoom = 1, capacity = 1;
        String[] input;
        Scanner getInput = new Scanner(System.in);
        boolean IsContinue = true, IsInsert;
        while (IsContinue) {
            IsContinue = true;
            IsInsert = true;
            System.out.println("请输入需批量添加的宿舍的信息: （格式：楼栋 地区 容量, 以空格分隔）");
            if (getInput.hasNextLine()) {
                rawInput = getInput.nextLine();
                input = rawInput.split(" ");
                if (input.length != 3){
                    System.out.println("输入格式不正确");
                    IsInsert = false;
                }
                else {
                    if (!(DormitoryManager.IsExistingBuilding(input[0]) && DormitoryManager.IsExistingArea(input[1]))) {
                        System.out.println("楼栋或地区不存在");
                        IsInsert = false;
                    } else {
                        building_name = input[0];
                        area_name = input[1];
                    }

                    if (!Menu.isNumeric(input[2])) {
                        System.out.println("宿舍容量应是一个数字，请重新输入");
                        IsInsert = false;
                    } else
                        capacity = Integer.parseInt(input[2]);
                }
            }

            if (IsInsert) {
                System.out.println("请输入楼层范围和房间号范围: （格式：最低楼层 最高楼层 最小房间号 最大房间号, 以空格分隔）");
                if (getInput.hasNextLine()) {
                    rawInput = getInput.nextLine();
                    input = rawInput.split(" ");
                    if (input.length != 4)
                        System.out.println("输入格式不正确");
                    else {
                        if (!(Menu.isNumeric(input[0]) &&
                                Menu.isNumeric(input[1]) &&
                                Menu.isNumeric(input[2]) &&
                                Menu.isNumeric(input[3]))) {
                            System.out.println("输入数据中存在非数字字符");
                            IsInsert = false;
                        } else {
                            minFloor = Integer.parseInt(input[0]);
                            maxFloor = Integer.parseInt(input[1]);
                            minRoom = Integer.parseInt(input[2]);
                            maxRoom = Integer.parseInt(input[3]);
                        }
                        if (minFloor <= 0 && minRoom <= 0 && minFloor > maxFloor && minRoom > maxRoom) {
                            System.out.println("楼层数或房号的数值不正确");
                            IsInsert = false;
                        }
                        if (IsInsert)
                            for (int i = minFloor; i <= maxFloor; i++)
                                for (int j = minRoom; j <= maxRoom; j++) {
                                    int RoomId = i * 100 + j;
                                    //宿舍不存在时才添加宿舍
                                    if (!DormitoryManager.IsExistingDorm(RoomId, building_name, area_name))
                                        DormitoryManager.InsertDorm(RoomId, building_name, area_name, capacity);
                                }
                    }
                }
            }
            System.out.println("按下回车继续添加/更改，输入q回到子菜单");
            if (getInput.hasNextLine())
                if (getInput.nextLine().equals("q"))
                    IsContinue = false;
        }

    }
    private static void addBuilding(){
        System.out.println("已有的楼栋名和地区名如下：");
        findData.printAreaBuilding();
        String building_name;
        Scanner getInput = new Scanner(System.in);
        boolean IsContinue = true, IsInsert;
        while (IsContinue){
            IsContinue = true;
            IsInsert = true;
            System.out.println("请输入需添加的楼栋名:");
            if (getInput.hasNextLine()) {
                building_name = getInput.nextLine();
                if (building_name == null || building_name.isEmpty()){
                    System.out.println("楼栋名不合法");
                    IsInsert = false;
                }
                if (DormitoryManager.IsExistingBuilding(building_name)){
                    System.out.println("楼栋已存在");
                    IsInsert = false;
                }
                if (IsInsert) {
                    DormitoryManager.InsertBuilding(building_name);
                    System.out.println("修改后的楼栋表如下");
                    findData.printAreaBuilding();
                }
            }

            System.out.println("按下回车继续添加/更改，输入q回到子菜单");
            if (getInput.hasNextLine())
                if (getInput.nextLine().equals("q"))
                    IsContinue = false;
        }
    }
    private static void addArea() {
        System.out.println("已有的楼栋名和地区名如下：");
        findData.printAreaBuilding();
        String area_name, rawInput;
        int newId = 0;
        String[] input;
        Scanner getInput = new Scanner(System.in);
        boolean IsContinue = true, IsInsert;
        while (IsContinue) {
            IsContinue = true;
            IsInsert = true;
            System.out.println("请输入需添加的地区名: （格式：地区名 id，以空格分隔）");
            if (getInput.hasNextLine()) {
                rawInput = getInput.nextLine();
                input = rawInput.split(" ");
                if (input.length != 2)
                    System.out.println("输入格式不正确");
                else {
                    if (input[0].isEmpty()) {
                        System.out.println("地区名不合法");
                        IsInsert = false;
                    }
                    if (DormitoryManager.IsExistingArea(input[0])) {
                        System.out.println("地区已存在");
                        IsInsert = false;
                    }
                    if (!Menu.isNumeric(input[1])) {
                        System.out.println("id应为数字");
                        IsInsert = false;
                    } else {
                        newId = Integer.parseInt(input[1]);
                        if (DormitoryManager.countDicRows() < newId) {
                            System.out.println("id超出范围，请扩充楼栋后再尝试增加地区");
                            IsInsert = false;
                        }else if (!DormitoryManager.IsEmptyForArea(newId)) {
                            System.out.println("id已被占用");
                            IsInsert = false;
                        }
                    }
                    if (IsInsert) {
                        area_name = input[0];
                        DormitoryManager.alterArea(newId, area_name);
                        System.out.println("修改后的楼栋表如下");
                        findData.printAreaBuilding();
                    }
                }
            }
            System.out.println("按下回车继续添加/更改，输入q回到子菜单");
            if (getInput.hasNextLine())
                if (getInput.nextLine().equals("q"))
                    IsContinue = false;
        }
    }
    private static void transferStudent(){
        long student1Id = 0, student2Id = 0;
        String rawInput;
        String[] input;
        Scanner getInput = new Scanner(System.in);
        boolean IsContinue = true, IsUpdate;
        while (IsContinue){
            IsContinue = true;
            IsUpdate = true;
            System.out.println("请输入需交换宿舍的两个学生: （格式：学生1学号 学生2学号, 以空格分隔）");
            if (getInput.hasNextLine()) {
                rawInput = getInput.nextLine();
                input = rawInput.split(" ");
                if (input.length != 2)
                    System.out.println("输入格式不正确");
                else {
                    if (!(Menu.isNumeric(input[0]) && Menu.isNumeric(input[1]))) {
                        System.out.println("学号应是一串数字，请重新输入");
                        IsUpdate = false;
                    } else{
                        student1Id = Long.parseLong(input[0]);
                        student2Id = Long.parseLong(input[1]);
                        if (!(Student.IsExisting(student1Id) && Student.IsExisting(student2Id))){
                            System.out.println("表中无该学生");
                            IsUpdate = false;
                        }
                    }
                    if (IsUpdate) {
                        Student student1 = new Student(student1Id);
                        Student student2 = new Student(student2Id);
                        Student.TransferDorm(student1, student2);
                    }
                }
            }
            System.out.println("按下回车继续添加/更改，输入q回到子菜单");
            if (getInput.hasNextLine())
                if (getInput.nextLine().equals("q"))
                    IsContinue = false;
        }
    }
    private static void transferDorm(){
        int DormId1 = 0, DormId2 = 0;
        String rawInput;
        String[] input;
        Scanner getInput = new Scanner(System.in);
        boolean IsContinue = true, IsUpdate;
        while (IsContinue){
            IsContinue = true;
            IsUpdate = true;
            System.out.println("请输入需交换的两个宿舍: （格式：宿舍号1 宿舍号2, 以空格分隔）");
            if (getInput.hasNextLine()) {
                rawInput = getInput.nextLine();
                input = rawInput.split(" ");
                if (input.length != 2)
                    System.out.println("输入格式不正确");
                else {
                    if (!(Menu.isNumeric(input[0]) && Menu.isNumeric(input[1]))) {
                        System.out.println("宿舍号应是一串数字，请重新输入");
                        IsUpdate = false;
                    } else{
                        DormId1 = Integer.parseInt(input[0]);
                        DormId2 = Integer.parseInt(input[1]);
                        if (!(DormitoryManager.IsExistingDorm(DormId1) && DormitoryManager.IsExistingDorm(DormId2))){
                            System.out.println("表中无该宿舍");
                            IsUpdate = false;
                        }
                        if (DormitoryManager.findDormCurrentOccupancy(DormId1) > DormitoryManager.findDormCapacity(DormId2) &&
                                DormitoryManager.findDormCurrentOccupancy(DormId2) > DormitoryManager.findDormCapacity(DormId1)){
                            System.out.println("至少有一间宿舍，宿舍容量不足以容纳另一间宿舍的当前人数");
                            IsUpdate = false;
                        }
                    }
                    if (IsUpdate)
                        Student.TransferDorm(DormId1, DormId2);
                }
            }
            System.out.println("按下回车继续添加/更改，输入q回到子菜单");
            if (getInput.hasNextLine())
                if (getInput.nextLine().equals("q"))
                    IsContinue = false;
        }
    }
    private static void UpdateDormCapacity(){
        int DormId = 0, new_capacity = 0;
        String rawInput;
        String[] input;
        Scanner getInput = new Scanner(System.in);
        boolean IsContinue = true, IsUpdate;
        while (IsContinue){
            IsContinue = true;
            IsUpdate = true;
            System.out.println("请输入需修改的宿舍与修改后容量: （格式：宿舍号 容量, 以空格分隔）");
            if (getInput.hasNextLine()) {
                rawInput = getInput.nextLine();
                input = rawInput.split(" ");
                if (input.length != 2)
                    System.out.println("输入格式不正确");
                else {
                    if (!Menu.isNumeric(input[0])) {
                        System.out.println("宿舍号应是一串数字，请重新输入");
                        IsUpdate = false;
                    } else
                        DormId = Integer.parseInt(input[0]);
                    if (!Menu.isNumeric(input[1])) {
                        System.out.println("容量应是一个数字，请重新输入");
                        IsUpdate = false;
                    } else
                        new_capacity = Integer.parseInt(input[1]);
                    if (!DormitoryManager.IsExistingDorm(DormId)) {
                        System.out.println("指定的宿舍不存在，请重新输入");
                        IsUpdate = false;
                    }
                    if (new_capacity < DormitoryManager.findDormCurrentOccupancy(DormId)){
                        System.out.println("指定的宿舍现有居住人数大于修改后容量");
                        IsUpdate = false;
                    }
                    if (IsUpdate) {
                        DormitoryManager.UpdateCapacity(DormId, new_capacity);
                    }
                }
            }
            System.out.println("按下回车继续添加/更改，输入q回到子菜单");
            if (getInput.hasNextLine())
                if (getInput.nextLine().equals("q"))
                    IsContinue = false;
        }
    }
    private static void setLeader(){
        long studentId = 0;
        int DormId = 0;
        String rawInput;
        Scanner getInput = new Scanner(System.in);
        boolean IsContinue = true, IsUpdate;
        while (IsContinue){
            IsContinue = true;
            IsUpdate = true;
            System.out.println("请输入需设置舍长的宿舍的宿舍号:");
            if (getInput.hasNextLine()) {
                rawInput = getInput.nextLine();
                if (!Menu.isNumeric(rawInput)) {
                    System.out.println("宿舍号应是一串数字，请重新输入");
                    IsUpdate = false;
                } else{
                    DormId = Integer.parseInt(rawInput);
                    if (!DormitoryManager.IsExistingDorm(DormId)){
                        System.out.println("宿舍不存在");
                        IsUpdate = false;
                    }
                }
            }
            if (IsUpdate){
                System.out.println("宿舍中的学生如下");
                findData.printStudentsInDorm(DormId);
                System.out.println("请输入新舍长的学号");
                if (getInput.hasNextLine()){
                    rawInput = getInput.nextLine();
                    if (!Menu.isNumeric(rawInput))
                        System.out.println("学号应是一串数字，请重新输入");
                    else {
                        studentId = Long.parseLong(rawInput);
                        if (!Student.IsExisting(studentId))
                            System.out.println("学生不存在");
                        else {
                            Student student = new Student(studentId);
                            if (student.getDormitoryId() != DormId)
                                System.out.println("学生不在该宿舍内");
                            else
                                student.setLeader();
                        }
                    }
                }
            }

            System.out.println("按下回车继续添加/更改，输入q回到子菜单");
            if (getInput.hasNextLine())
                if (getInput.nextLine().equals("q"))
                    IsContinue = false;
        }
    }
    private static void resetLeader(){
        int DormId = 0;
        String rawInput;
        Scanner getInput = new Scanner(System.in);
        boolean IsContinue = true, IsUpdate;
        while (IsContinue){
            IsContinue = true;
            IsUpdate = true;
            System.out.println("请输入需清除舍长的宿舍的宿舍号:");
            if (getInput.hasNextLine()) {
                rawInput = getInput.nextLine();
                if (!Menu.isNumeric(rawInput)) {
                    System.out.println("宿舍号应是一串数字，请重新输入");
                    IsUpdate = false;
                } else{
                    DormId = Integer.parseInt(rawInput);
                    if (!DormitoryManager.IsExistingDorm(DormId)){
                        System.out.println("宿舍不存在");
                        IsUpdate = false;
                    }
                }
            }
            if (IsUpdate)
                DormitoryManager.ResetLeader(DormId);
            System.out.println("按下回车继续添加/更改，输入q回到子菜单");
            if (getInput.hasNextLine())
                if (getInput.nextLine().equals("q"))
                    IsContinue = false;
        }
    }
    private static void renameArea() {
        System.out.println("已有的楼栋名和地区名如下：");
        findData.printAreaBuilding();
        String old_area_name, new_area_name, rawInput;
        String[] input;
        Scanner getInput = new Scanner(System.in);
        boolean IsContinue = true, IsUpdate;
        while (IsContinue){
            IsContinue = true;
            IsUpdate = true;
            System.out.println("请输入需修改的地区名和修改后的地区名: （格式：修改前的地区 修改后的地区, 以空格分隔）");
            if (getInput.hasNextLine()) {
                rawInput = getInput.nextLine();
                input = rawInput.split(" ");
                if (input.length != 2){
                    System.out.println("输入格式不正确");
                    IsUpdate = false;
                }else {
                    if (!DormitoryManager.IsExistingArea(input[0])) {
                        System.out.println("被修改的地区不存在");
                        IsUpdate = false;
                    }
                    if (DormitoryManager.IsExistingArea(input[1])) {
                        System.out.println("修改后的地区名已存在");
                        IsUpdate = false;
                    }
                }

                if (IsUpdate) {
                    old_area_name = input[0];
                    new_area_name = input[1];
                    DormitoryManager.renameArea(old_area_name, new_area_name);
                    System.out.println("修改后的楼栋表如下");
                    findData.printAreaBuilding();
                }
            }

            System.out.println("按下回车继续添加/更改，输入q回到子菜单");
            if (getInput.hasNextLine())
                if (getInput.nextLine().equals("q"))
                    IsContinue = false;
        }
    }
    private static void renameBuilding(){
        System.out.println("已有的楼栋名和地区名如下：");
        findData.printAreaBuilding();
        String old_building_name, new_building_name, rawInput;
        String[] input;
        Scanner getInput = new Scanner(System.in);
        boolean IsContinue = true, IsUpdate;
        while (IsContinue){
            IsContinue = true;
            IsUpdate = true;
            System.out.println("请输入需修改的楼栋名和修改后的楼栋名: （格式：修改前的楼栋名 修改后的楼栋名, 以空格分隔）");
            if (getInput.hasNextLine()) {
                rawInput = getInput.nextLine();
                input = rawInput.split(" ");
                if (input.length != 2){
                    System.out.println("输入格式不正确");
                    IsUpdate = false;
                }else {
                    if (!DormitoryManager.IsExistingBuilding(input[0])) {
                        System.out.println("被修改的楼栋不存在");
                        IsUpdate = false;
                    }
                    if (DormitoryManager.IsExistingBuilding(input[1])) {
                        System.out.println("修改后的楼栋名已存在");
                        IsUpdate = false;
                    }
                }

                if (IsUpdate) {
                    old_building_name = input[0];
                    new_building_name = input[1];
                    DormitoryManager.renameBuilding(old_building_name, new_building_name);
                    System.out.println("修改后的楼栋表如下");
                    findData.printAreaBuilding();
                }
            }

            System.out.println("按下回车继续添加/更改，输入q回到子菜单");
            if (getInput.hasNextLine())
                if (getInput.nextLine().equals("q"))
                    IsContinue = false;
        }
    }
}
class deleteData{
    public static void delete_main(){
        System.out.println("删除子菜单\n");
        boolean IsContinue = true;
        String menu =
                "请输入选项前的序号，选择需要执行的项目，或键入quit表示退出\n\n" +
                        "1. 删除学生数据\n" +
                        "2. 删除宿舍连带学生数据\n" +
                        "3. 清空楼栋和地区\n";
        Scanner getInput = new Scanner(System.in);
        while (IsContinue){
            System.out.println(menu);
            if (getInput.hasNextLine()){
                String input = getInput.nextLine();
                switch (input){
                    case "1":
                        DeleteStudent();
                        System.out.println("删除学生数据\n");
                        break;
                    case "2":
                        System.out.println("删除宿舍连带学生数据\n");
                        DeleteDorm();
                        break;
                    case "3":
                        System.out.println("清空楼栋\n");
                        DeleteAreaBuilding();
                        break;
                    case "quit":
                        IsContinue = false;
                        break;
                    default:
                        System.out.println("请输入正确的选项\n");
                }
            }
        }
    }
    private static void DeleteStudent(){
        long student_id = 0;
        String input;
        Scanner getInput = new Scanner(System.in);
        boolean IsContinue = true;
        while (IsContinue) {
            IsContinue = false;
            while (true) {
                System.out.println("请输入需删除的学生的学号:");
                if (getInput.hasNextLine()) {
                    input = getInput.nextLine();
                    if (Menu.isNumeric(input)) {
                        student_id = Long.parseLong(input);
                        if (Student.IsExisting(student_id))
                            break;
                        else
                            System.out.println("表中不存在该学生");
                    } else
                        System.out.println("学号应是一串数字，请重新输入");
                }
            }
            System.out.println("确认删除以下学生吗，若确认则请输入'Y'");
            findData.printStudentData(student_id);
            if (getInput.hasNextLine())
                if (getInput.nextLine().equals("Y")){
                    Student student = new Student(student_id);
                    student.DeleteDB();
                }
            System.out.println("输入r可继续删除，否则回到子菜单");
            if (getInput.hasNextLine())
                if (getInput.nextLine().equals("r"))
                    IsContinue = true;
        }
    }
    private static void DeleteDorm(){
        int DormId = 0;
        String input;
        Scanner getInput = new Scanner(System.in);
        boolean IsContinue = true;
        while (IsContinue) {
            IsContinue = false;
            while (true) {
                System.out.println("请输入需删除的宿舍的宿舍号:");
                if (getInput.hasNextLine()) {
                    input = getInput.nextLine();
                    if (Menu.isNumeric(input)) {
                        DormId = Integer.parseInt(input);
                        if (DormitoryManager.IsExistingDorm(DormId))
                            break;
                        else
                            System.out.println("表中不存在该宿舍");
                    } else
                        System.out.println("宿舍号应是一串数字，请重新输入");
                }
            }
            System.out.println("待删除的宿舍信息如下：");
            findData.printDormData(DormId);
            findData.printStudentsInDorm(DormId);
            System.out.println("删除宿舍时将连带删除这些学生，若确认则请输入'Y'");
            if (getInput.hasNextLine())
                if (getInput.nextLine().equals("Y"))
                    DormitoryManager.DeleteDorm(DormId);
            System.out.println("输入r可继续删除，否则回到子菜单");
            if (getInput.hasNextLine())
                if (getInput.nextLine().equals("r"))
                    IsContinue = true;
        }
    }
    private static void DeleteAreaBuilding() {
        String area_name = "", building_name = "";
        Scanner getInput = new Scanner(System.in);
        while (true) {
            System.out.println("请输入宿舍所处地区（若不限定则请输入*）:");
            if (getInput.hasNextLine()) {
                area_name = getInput.nextLine();
                if (area_name.equals("*") || DormitoryManager.IsExistingArea(area_name))
                    break;
                System.out.println("请输入正确的地区名");
            }
        }
        while (true) {
            System.out.println("请输入宿舍所处楼栋（若不限定则请输入*）:");
            if (getInput.hasNextLine())
                building_name = getInput.nextLine();
            if (building_name.equals("*") || DormitoryManager.IsExistingBuilding(building_name))
                break;
            System.out.println("请输入正确的楼栋名");
        }

        if (area_name.equals("*") && building_name.equals("*")){
            System.out.println("确定清空所有宿舍吗，此操作不可撤销,确认请输入'Y'");
            if (getInput.hasNextLine() && Objects.equals(getInput.nextLine(), "Y"))
                DormitoryManager.DeleteAllDorm();
        }
        else if (area_name.equals("*"))
        {
            System.out.println("以下为将删除的宿舍");
            findData.printDormInBuilding(building_name);
            System.out.println("确定删除这些宿舍吗，此操作不可撤销,确认请输入'Y'");
            if (getInput.hasNextLine() && Objects.equals(getInput.nextLine(), "Y"))
                DormitoryManager.DeleteDormBuilding(area_name);
        }
        else if (building_name.equals("*"))
        {
            System.out.println("以下为将删除的宿舍");
            findData.printDormInArea(area_name);
            System.out.println("确定删除这些宿舍吗，此操作不可撤销,确认请输入'Y'");
            if (getInput.hasNextLine() && Objects.equals(getInput.nextLine(), "Y"))
                DormitoryManager.DeleteDormArea(area_name);
        }
        else
        {
            System.out.println("以下为将删除的宿舍");
            findData.printDormAreaBuilding(area_name, building_name);
            System.out.println("确定删除这些宿舍吗，此操作不可撤销,确认请输入'Y'");
            if (getInput.hasNextLine() && Objects.equals(getInput.nextLine(), "Y"))
                DormitoryManager.DeleteDorm(area_name, building_name);
        }
        System.out.println("按回车返回");
        if (getInput.hasNextLine())
            getInput.nextLine();
    }
}
public class Menu {
    public static void main(String[] args) {
        System.out.println("宿舍管理工具\n");
        boolean IsContinue = true;
        String menu =
                "请输入选项前的序号，选择需要执行的项目，或键入quit表示退出\n\n" +
                "1. 查询宿舍/学生数据\n" +
                "2. 添加/修改宿舍/学生数据\n" +
                "3. 删除宿舍/学生数据\n";
        Scanner getInput = new Scanner(System.in);
        while (IsContinue){
            System.out.println(menu);
            if (getInput.hasNextLine()){
                String input = getInput.nextLine();
                switch (input){
                    case "1":
                        findData.find_main();
                        break;
                    case "2":
                        inputData.input_main();
                        break;
                    case "3":
                        deleteData.delete_main();
                        break;
                    case "quit":
                        IsContinue = false;
                        break;
                    default:
                        System.out.println("请输入正确的选项\n");
                }
            }
        }
        dbConnection.closeConn();
    }
    public static void printBoard(ArrayList<String[]> board, int... lengths){
        StringBuilder fString = new StringBuilder();
        String outputString;
        for (int len : lengths)
            fString.append("%-").append(len).append("s ");

        for (String[] row : board) {
            outputString = String.format(fString.toString(), row);
            System.out.println(outputString);
        }
        System.out.print("\n");
    }
    public static boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[0-9]+$");
        return pattern.matcher(str).matches();
    }
}