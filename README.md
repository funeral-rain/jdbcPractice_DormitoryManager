# 孩子不懂事乱糊的

### ==一个宿舍管理系统==

单纯的jdbc和mysql使用的练习，为了应付实验室面试整的

没用反射没用框架，纯手写jdbc，菜单是命令行，所有功能做了错误处理~~所以看上去很杂~~

全程防sql注入，代价是全是*prepareStatement()*

数据库用了很多触发器（trigger），主要作用是自动生成宿舍id，修改日志，自动变动宿舍人数，其备份放在 *./db_example* 下，叫 *dormitorydb*

==因此本项目也请务必配合那个数据库使用（倒==

宿舍也就是 *dormitory* 表，学生是 *student* 表，日志是 *logging* 表， *dormiddic* 表是记录楼栋和地区两栏的编号，以便于生成宿舍id

管理与数据库的连接专门做了一个类，也就是 *dbConnection* ，可以在里面修改整个数据库的连接URL，账户密码什么的

学生专门有一个类暂存信息，和进行修改查询操作，也就是 *Student* 类，里面有其对象结构和其对象的方法

宿舍更多来说是面向表格的，所以不会以对象的形式加载到程序里面，只有一个负责和表格交互的类 *DormitoryManager* 。宿舍id会自动生成，生成核心是函数 *CreateDormId()* ，规则是地区编号\*1000000加楼栋编号\*10000再加本身的 *roomid*

最后就是专门用于显示控制命令行菜单的 *Menu* 类， *findData* 类用于查询的菜单和显示来自数据库的数据，*inputData* 和 *deleteData* 也就是一个是负责修改添加数据的菜单，一个删除数据菜单

大概就介绍到这了，完成的时候是23年11月，但是我废物到24年3月才学会git然后上传这坨项目