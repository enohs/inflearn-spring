package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 * JdbcTemplate 사용
 */
@Slf4j
public class MemberRepositoryV5 implements MemberRepository {

  private final JdbcTemplate template;

  public MemberRepositoryV5(DataSource dataSource) {
    this.template = new JdbcTemplate(dataSource);
  }

  @Override
  public Member save(Member member) {
    String sql = "insert into member(member_id, money) values (?, ?)";

    // 커넥션 받아오고, 스테이트먼트에 파라미터 넣어주고, 예외처리하는 것까지 전부 알아서 해줌
    template.update(sql, member.getMemberId(), member.getMoney());
    return member;
  }

  @Override
  public Member findById(String memberId) {
    String sql = "select * from member where member_id = ?";

    // 디비에서 하나 조회할 때는 ForObject
    return template.queryForObject(sql, memberRowMapper(), memberId);
  }

  // 쿼리 결과로 어떻게 결과를 만들 건지에 대한 매퍼 정보
  private RowMapper<Member> memberRowMapper() {
    return (rs, rowNum) -> {
      Member member = new Member();
      member.setMemberId(rs.getString("member_id"));
      member.setMoney(rs.getInt("money"));
      return member;
    };
  }

  @Override
  public void update(String memberId, int money) {
    String sql = "update member set money=? where member_id=?";
    template.update(sql, money, memberId);
  }

  @Override
  public void delete(String memberId) {
    String sql = "delete from member where member_id=?";
    template.update(sql, memberId);
  }

}
