package hello.jdbc.connection;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;

@Slf4j
public class DBConnectionUtil {

    public static Connection getConnection() {
        try {
            // DriverManager : 라이브러리에 있는 데이터베이스 드라이버를 찾아서 해당 드라이버가 제공하는 커넥션을 반환해준다
            Connection connection = DriverManager.getConnection(ConnectionConst.URL, ConnectionConst.USERNAME, ConnectionConst.PASSWORD);
            log.info("get connection={}, class={},", connection, connection.getClass());
            return connection;
        }
        catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

}
