package util;



import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Driver;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
    private static final String URL =  "jdbc:mysql://localhost:3306/cash";
    private static final String USERNAME = "root";
    private static final String PASSWORD ="";

    private static DataSource DATASOURCE = new MysqlDataSource();
    static {
        ((MysqlDataSource)DATASOURCE).setUrl(URL);
        ((MysqlDataSource)DATASOURCE).setUser(USERNAME);
        ((MysqlDataSource)DATASOURCE).setPassword(PASSWORD);
    }



    public static DataSource getDataSource(){
        return DATASOURCE;
    }

    public static Connection getConnection(boolean autoCommit) {

        try {
            Connection connection = (Connection) DATASOURCE.getConnection();
            connection.setAutoCommit(true);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取连接失败");
        }
    }


    public static void close(Connection connection,Statement statement,
                             ResultSet resultSet){
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException("数据库资源释放失败");
        }
    }
}

