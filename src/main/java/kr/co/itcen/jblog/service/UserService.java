package kr.co.itcen.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.itcen.jblog.repository.BlogDao;
import kr.co.itcen.jblog.repository.CategoryDao;
import kr.co.itcen.jblog.repository.UserDao;
import kr.co.itcen.jblog.vo.UserVo;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private BlogDao blogDao;

	@Autowired
	private CategoryDao categoryDao;

	public Boolean existUser(String id) {
		return userDao.get(id) != null;
	}

	public Boolean insert(UserVo vo) { // 회원가입과 동시에 dafault값으로 블로그와 카테고리 생성
		userDao.insert(vo);
		blogDao.insert(vo.getId());
		return categoryDao.insert(vo.getId());

	}

	public UserVo getUser(UserVo vo) {
		return userDao.getUser(vo);
	}

}
