package hello.jdbc.repository;


import hello.jdbc.domain.Member;
import hello.jdbc.repository.ex.MyDbException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;

import javax.sql.DataSource;
import java.sql.*;
import java.util.NoSuchElementException;

/**
 *
 * SQLExceptionTranslator 추가
 */
@Slf4j
public class MemberRepositoryV4_2 implements MemberRepository {
    private final DataSource dataSource;
    private final SQLExceptionTranslator exTranslator;

    public MemberRepositoryV4_2(DataSource dataSource) {
        this.dataSource = dataSource;
        this.exTranslator = new SQLErrorCodeSQLExceptionTranslator(dataSource);
    }

    @Override
    public Member save(Member member) {
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
            throw exTranslator.translate("save", sql, e);
        }
        finally {
            close(connection, pstmt, null);
        }

    }

    @Override
    public Member findById(String memberId) {
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
            throw exTranslator.translate("findById", sql, e);
        }
        finally {
            close(connection, pstmt, rs);
        }

    }

    @Override
    public void update(String memberId, int money) {
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
            throw exTranslator.translate("update", sql, e);
        }
        finally {
            close(connection, pstmt, null);
        }

    }

    @Override
    public void delete(String memberId) {
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
            throw exTranslator.translate("delete", sql, e);
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
