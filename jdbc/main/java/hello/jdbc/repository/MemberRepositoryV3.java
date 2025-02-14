package hello.jdbc.repository;


import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.NoSuchElementException;

/**
 *
 * 트랜잭션 - 트랙잭션 매니저
 * DataSourceUtils.getConnection()
 * DataSourceUtils.releaseConnection()
 *
 */
@Slf4j
public class MemberRepositoryV3 implements MemeberRepositoryEx {
    private final DataSource dataSource;

    public MemberRepositoryV3(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Member save(Member member) throws SQLException {
        String sql = "insert into member(member_id, money) values (?, ?)";

        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, member.getMemberId());
            pstmt.setInt(2, member.getMoney());
            pstmt.executeUpdate();
            return member;
        }
        catch (SQLException e) {
            log.error("db error", e);
            throw e;
        }
        finally {
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

        // 주의! 트랜잭션 동기화를 사용하려면 DataSourceUtils 를 사용해야 한다
        DataSourceUtils.releaseConnection(connection, dataSource);

    }

    private Connection getConnection() throws SQLException {
        // 주의! 트랜잭션 동기화를 사용하려면 DataSourceUtils 를 사용해야 한다
        Connection con = DataSourceUtils.getConnection(dataSource);
        log.info("get connection={}, class={}", con, con.getClass());
        return con;
    }

}
