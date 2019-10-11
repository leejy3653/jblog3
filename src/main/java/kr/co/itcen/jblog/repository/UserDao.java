package kr.co.itcen.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.itcen.jblog.vo.UserVo;

@Repository
public class UserDao {
	@Autowired
	private SqlSession sqlsession;

	public Object get(String id) {
		UserVo result = sqlsession.selectOne("user.getById", id);
		return result;
	}

	public Boolean insert(UserVo vo) {
		int count = sqlsession.insert("user.insert", vo);
		return count == 1;

	}

	public UserVo getUser(UserVo vo) {
		UserVo uservo = sqlsession.selectOne("user.getByidpassword", vo);
		return uservo;
	}

}
