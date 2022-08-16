package com.ticktack.homey_test.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.datasource.DataSourceUtils;
import javax.sql.DataSource;
import java.sql.*;

import com.ticktack.homey_test.domain.Member;

public class JdbcMemberRepository implements MemberRepository{
	
	private final DataSource dataSource;

	public JdbcMemberRepository(DataSource datasource) {
		super();
		this.dataSource = datasource;
	}


	@Override
	public Member save(Member member) {
		String sql = "insert into member(name) values(?)";
		
		 Connection conn = null;
		 PreparedStatement pstmt = null;
		 ResultSet rs = null;
		 
		 try {
			 conn = getConnection();
			 pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			 
			 pstmt.setString(1, member.getName());
			 
			 pstmt.executeUpdate(); // DB 쿼리 실행
			 rs = pstmt.getGeneratedKeys(); // 생성한 id값 반환
			 
			 if (rs.next()) {
				 member.setId(rs.getLong(1));
			 } else {
				 throw new SQLException("id 조회 실패");
			 }
			 
			 return member;
			 
		 } catch (Exception e) {
			 throw new IllegalStateException(e);
		 } finally {
			 close(conn, pstmt, rs);
		 }
	}

	@Override
	public Optional<Member> findById(Long id) {
		
		String sql = "select * from member where id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		 
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery(); // 쿼리 조회
			
			if(rs.next()) {
				Member member = new Member();
				member.setId(rs.getLong("id"));
				member.setName(rs.getString("name"));
				return Optional.of(member);
			} else {
				return Optional.empty();
			}
		} catch (Exception e) {
			throw new IllegalStateException(e);
		} finally {
			close(conn, pstmt, rs);
		}
	}

	@Override
	public Optional<Member> findByName(String name) {
		String sql = "select * from member where name = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Member member = new Member();
				member.setId(rs.getLong("id"));
				member.setName(rs.getString("name"));
				return Optional.of(member);
			}
			return Optional.empty();
		} catch (Exception e) {
			throw new IllegalStateException(e);
		} finally {
			close(conn, pstmt, rs);
		}
	}

	@Override
	public List<Member> findAll() {
		String sql = "select * from member";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			List<Member> members = new ArrayList<>(); // 결과를 리스트로
			
			while(rs.next()) { // 조회 결과 여러 개라 반복문
				Member member = new Member();
				member.setId(rs.getLong("id"));
				member.setName(rs.getString("name"));
				members.add(member);
			}
			return members;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		} finally {
			close(conn, pstmt, rs);
		}
	}
	
	
	// 메소드에서 직접 getConnection() 시 매번 새로운 connection 생성됨
	// DataSourceUtils를 통해 호출하기 : DB 트랜잭션 관련 문제가 없도록 같은 connection 객체 유지시켜줌
	// connection 끊을때도 DataSourceUtils 통해서 release
	private Connection getConnection() {
		 return DataSourceUtils.getConnection(dataSource);
	}	
	
	
	 private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
		  try {
			  if (rs != null) {
				  rs.close();
			  }
		  } catch (SQLException e) {
			  e.printStackTrace();
		  }
		  
		  try {
			  if (pstmt != null) {
				  pstmt.close();
			  }
		  } catch (SQLException e) {
			  e.printStackTrace();
		  }
		  
		  try {
			  if (conn != null) {
				  close(conn);
			  }
		  } catch (SQLException e) {
			  e.printStackTrace();
		  }
	  }
	 
	  private void close(Connection conn) throws SQLException {
		  DataSourceUtils.releaseConnection(conn, dataSource);
	  }

}
