package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV1;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 트랜잭션 - 파라미터 연동, 풀을 고려한 종료
 */
@RequiredArgsConstructor
@Slf4j
public class MemberServiceV2 {

    private final DataSource dataSource;
    private final MemberRepositoryV1 memberRepository;

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        Connection connection = dataSource.getConnection();
        try {
            connection.setAutoCommit(false); // 트랜잭션 시작

            // 비즈니스 로직 실행
            bizLogic(connection, fromId, toId, money);

            connection.commit(); // 성공 시 커밋

        }
        catch(Exception e) {
            connection.rollback(); // 실패 시 롤백
            throw new IllegalStateException();
        }
        finally {
            release(connection);
        }

    }

    private void bizLogic(Connection connection, String fromId, String toId, int money) throws SQLException {
        Member fromMember = memberRepository.findById(connection, fromId);
        Member toMember = memberRepository.findById(connection, toId);

        memberRepository.update(connection, fromId, fromMember.getMoney() - money);
        validation(toMember);
        memberRepository.update(connection, toId, toMember.getMoney() + money);
    }

    private void release(Connection connection) {
        if(connection != null) {
            try {
                connection.setAutoCommit(true); // true 로 바꿔주고 커넥션 풀로 반환
                connection.close();
            }
            catch (Exception e) {
                log.info("error", e);
            }
        }
    }

    private void validation(Member toMember) {
        if(toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("이체중 예외 발생");
        }
    }



}
