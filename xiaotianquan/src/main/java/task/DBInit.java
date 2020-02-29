

package task;

import util.DBUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;

public class DBInit {

    public static void init() {
        try {
            // 获取数据库初始化文件的输入流
            InputStream is = DBInit.class.getClassLoader()
                    .getResourceAsStream("init.sql");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(is, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                // 忽略注释--代码
                int idx = line.indexOf("--");
                if (idx != -1) {
                    line = line.substring(0, idx);
                }
                sb.append(line);
            }
            String[] sqls = sb.toString().split(";");
            Connection connection = null;
            Statement statement = null;
            try {
                for (String sql : sqls) {
                    connection = DBUtil.getConnection();
                    statement = connection.createStatement();
                    statement.executeUpdate(sql);
                }
            } finally {
                DBUtil.close(connection, statement,null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("数据库初始化任务错误");
        }
    }
}
