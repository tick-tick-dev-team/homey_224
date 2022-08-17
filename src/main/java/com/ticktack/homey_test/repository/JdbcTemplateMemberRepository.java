package com.ticktack.homey_test.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.ticktack.homey_test.domain.Member;

public class JdbcTemplateMemberRepository implements MemberRepository{
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired // 생성자 하나일 땐 @Autowired 생략가능
	public JdbcTemplateMemberRepository(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Member save(Member member) {
		// SimpleJdbcInsert : 테이블명, 인덱스 컬럼명 받아서 insert query 알아서 생성
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);         
		jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");
		
        Map<String, Object> parameters = new HashMap<>();         
        parameters.put("name", member.getName());
        
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        member.setId(key.longValue());
        return member;
	}

	@Override
	public Optional<Member> findById(Long id) {
		// 결과가 List로 반환됨 (쿼리 문자열, 결과 매핑, 전달 변수)
		List<Member> result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), id);
		return result.stream().findAny(); // optional로 반환됨
	}

	@Override
	public Optional<Member> findByName(String name) {
		List<Member> result = jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name);
		return result.stream().findAny(); // optional로 반환됨
	}

	@Override
	public List<Member> findAll() {
		return jdbcTemplate.query("select * from member", memberRowMapper());
	}

	private RowMapper<Member> memberRowMapper () {
//		return new RowMapper<Member>() {
//		// 결과가 ResultSet rs로 넘어옴
//			@Override
//			public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
//				Member member = new Member();
//				member.setId(rs.getLong("id"));
//				member.setName(rs.getString("name"));
//				return member;
//			}
//		};
		// 람다식 변형
		return (rs, rowNum) -> {
			Member member = new Member();
			member.setId(rs.getLong("id"));
			member.setName(rs.getString("name"));
			return member;
		};
	}
}
