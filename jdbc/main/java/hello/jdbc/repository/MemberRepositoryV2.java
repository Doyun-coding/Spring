package hello.jdbc.repository;


import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.NoSuchElementException;

/**
 * JDBC - DataSource 사용, JdbcUtils 사용
 */
@Slf4j
public class MemberRepositoryV2 {
    private final DataSource dataSource;

    public MemberRepositoryV2(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Member save(Member member) throws SQLException {
        String sql = "insert into member(member_id, money) values (?, ?)";

        Connection connection = null;
        // 데이터베이스에 쿼리를 날리는 코드
        PreparedStatement pstmt = null;

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, member.getMemberId());
            pstmt.setInt(2, member.getMoney());
            pstmt.executeUpdate(); // Statement 를 통해 준비된 SQL 을 커넥션을 통해 실제 데이터베이스에 전달한 // 영향받은 row 수 만큼 반환
            return member;
        }
        catch (SQLException e) {
            log.error("db error", e);
            throw e;
        }
        finally {
//            pstmt.close(); // 여기에서 Exception 이 일어나면 connection.close() 가 실행이 안된다
//            connection.close();
            close(connection, pstmt, null);
        }

    }

    public Member findById(String memberId) throws SQLException {
        String sql = "select * from member where member_id = ?";

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null; // ResultSet 안에는 Cursor 가 있고 Cursor 가 이동해서 데이터를 조회한다 : rs.next()

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, memberId);

            rs = pstmt.executeQuery();// 정보를 가지고 올 때에는 executeQuery() 함수를 사용
            if(rs.next()) { // 현재 커서에 데이터가 있으면 true, 없으면 false
                Member member = new Member();
                member.setMemberId(rs.getString("member_id")); // 현재 커서가 가리키고 있는 곳에 memeber 가 있으면 String 으로 반환
                member.setMoney(rs.getInt("money")); // 현재 커서에 money 가 있으면 int 형으로 반환
                return member;
            }
            else {
                throw new NoSuchElementException("member not found memberId=" + memberId);
            }

        }
        catch (SQLException e) {
            log.error("db error", e);
            throw e;
        }
        finally {
            close(connection, pstmt, rs);
        }

    }

    public Member findById(Connection connection, String memberId) throws SQLException {
        String sql = "select * from member where member_id = ?";

        PreparedStatement pstmt = null;
        ResultSet rs = null; // ResultSet 안에는 Cursor 가 있고 Cursor 가 이동해서 데이터를 조회한다 : rs.next()

        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, memberId);

            rs = pstmt.executeQuery();// 정보를 가지고 올 때에는 executeQuery() 함수를 사용
            if(rs.next()) { // 현재 커서에 데이터가 있으면 true, 없으면 false
                Member member = new Member();
                member.setMemberId(rs.getString("member_id")); // 현재 커서가 가리키고 있는 곳에 memeber 가 있으면 String 으로 반환
                member.setMoney(rs.getInt("money")); // 현재 커서에 money 가 있으면 int 형으로 반환
                return member;
            }
            else {
                throw new NoSuchElementException("member not found memberId=" + memberId);
            }

        }
        catch (SQLException e) {
            log.error("db error", e);
            throw e;
        }
        finally {
            // connection 은 여기서 닫지 않는다
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(pstmt);
            //JdbcUtils.closeConnection(connection); // 여기서 닫아버리면 큰일 난다
        }

    }

    public void update(String memberId, int money) throws SQLException {
        String sql = "update member set money=? where member_id=?";

        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, money);
            pstmt.setString(2, memberId);
            int resultSize = pstmt.executeUpdate();
            log.info("resultSize={}", resultSize);
        }
        catch(SQLException e) {
            log.error("db error", e);
            throw e;
        }
        finally {
            close(connection, pstmt, null);
        }

    }

    public void delete(String memberId) throws SQLException {
        String sql = "delete from member where member_id=?";

        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, memberId);
            pstmt.executeUpdate();
        }
        catch (SQLException e) {
            throw e;
        }
        finally {
            close(connection, pstmt, null);
        }

    }

    private void close(Connection connection, Statement stmt, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        JdbcUtils.closeConnection(connection);

    }

    private Connection getConnection() throws SQLException {
        Connection con = dataSource.getConnection();
        log.info("get connection={}, class={}", con, con.getClass());
        return con;
    }

}


