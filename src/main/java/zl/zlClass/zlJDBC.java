package zl.zlClass;

import java.sql.*;

/*
 * @Description: JDBC数据库连接学习
 * @Param:
 * @Author: zl
 * @Date: 2019/4/12 15:01
 */
public class zlJDBC {
    //mysql
    private Connection conn=null;
    private Integer errorCode=0;
    private Long cost;
    private String charcterEncoding="utf8";
    public boolean ConnectionMysql(String userName, String password, String serverIp, Integer port, String dbName) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
            return false;
        }
        String user=userName;
        String pass=password;
        String url = String.format("jdbc:mysql://%s:%d/%s?useUnicode=true&characterEncoding=%s&serverTimezone=GMT",serverIp,port,dbName,charcterEncoding);
        System.out.println("正在连接数据库......");
        try {
             conn = DriverManager.getConnection(url,user,pass);
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean connectionSqlite(String userName, String password ,String dbFilename){
        if (conn!=null)
            try{
            conn.close();
            }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        try {
            Class.forName("org.sqlite.JDBC");

        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
            return false;
        }
        String user=userName;
        String pass=password;
        String url = String.format("jdbc:sqlite://%s",dbFilename);
        url="jdbc:sqlite://d:/data1.db";
        System.out.println("正在连接Sqlite数据库......");
        try {
            conn = DriverManager.getConnection(url);
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;

    }
    public boolean ExcuteSql(String sql){


        try {
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.executeUpdate(sql);

        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean BatchExcuteSql(String[] sqls,Integer maxSubmit){
        //批量执行 sql只允许插入与更新。
       return false;
    }
    public ResultSet QueryMysql(String sql){
        ResultSet rs=null;
        try {
            PreparedStatement pStmt = conn.prepareStatement(sql);
            rs = pStmt.executeQuery(sql);
        }
        catch (SQLException e){
            e.printStackTrace();
            return rs;
        }
        return rs;
    }
    public static void testMysql(){
        zlJDBC zjb= new zlJDBC();
        String userName="root";
        String password="123456";
        String dataName="dictdb";
        String serveriP="localhost";
        Integer port=3306;
        zjb.ConnectionMysql(userName,password,serveriP,port,dataName);
        String sql = "CREATE TABLE test(id int(11) not null);";
        zjb.ExcuteSql(sql);
        sql = "insert into test(id)value(12)";
        zjb.ExcuteSql(sql);
        sql="update test set id=123 where id=12";
        zjb.ExcuteSql(sql);
        sql="select * from test";
        ResultSet rs = zjb.QueryMysql(sql);
        try{
        while (rs.next()){
            System.out.println(rs.getString("id"));
        }}
        catch (SQLException e){
            e.printStackTrace();
        }
        sql = "drop table test;";
        //zjb.ExcuteSql(sql);

    }
    public static void testSqlite(){
        zlJDBC zjb= new zlJDBC();
        zjb.connectionSqlite("","","D:/data.db");
        String sql = "CREATE TABLE test(id INTEGER )";
        zjb.ExcuteSql(sql);
        sql = "insert into test(id)value(12)";
        zjb.ExcuteSql(sql);
        sql="update test set id=123 where id=12";
        zjb.ExcuteSql(sql);
        sql="select * from test";
        ResultSet rs = zjb.QueryMysql(sql);
        try{
            while (rs.next()){
                System.out.println(rs.getString("id"));
            }}
        catch (SQLException e){
            e.printStackTrace();
        }
        sql = "drop table test;";
    }
    public static void  testSqliteSample(){
        try {
            Class.forName("org.sqlite.JDBC");
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
            return;
        }


        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("drop table if exists person");
            statement.executeUpdate("create table person (id integer, name string)");
            statement.executeUpdate("insert into person values(1, 'leo')");
            statement.executeUpdate("insert into person values(2, 'yui')");
            ResultSet rs = statement.executeQuery("select * from person");
            while(rs.next())
            {
                // read the result set
                System.out.println("name = " + rs.getString("name"));
                System.out.println("id = " + rs.getInt("id"));
            }
        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
        finally
        {
            try
            {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e)
            {
                // connection close failed.
                System.err.println(e);
            }
        }
    }
    public static void main(String[] args) {
        testSqliteSample();
    }
}
