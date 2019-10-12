package kr.co.itcen.jblog.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.co.itcen.jblog.exception.FileUploadException;
import kr.co.itcen.jblog.repository.BlogDao;
import kr.co.itcen.jblog.repository.CategoryDao;
import kr.co.itcen.jblog.repository.PostDao;
import kr.co.itcen.jblog.vo.BlogVo;
import kr.co.itcen.jblog.vo.CategoryVo;
import kr.co.itcen.jblog.vo.PostVo;

@Service
public class BlogService {

	@Autowired
	private BlogDao blogDao;

	@Autowired
	private CategoryDao categoryDao;

	@Autowired
	private PostDao postDao;

	public List<CategoryVo> getList(String id) {
		return categoryDao.getList(id);
	}

	public List<PostVo> getList(Long categoryno) {
		return postDao.getList(categoryno);
	}

	public PostVo getPost(Long postno, Long categoryno) {
		return postDao.getPost(postno, categoryno);
	}

	public BlogVo get(String id) {
		return blogDao.get(id);
	}

	public List<CategoryVo> getCategoryList(String id) {
		return categoryDao.getCategoryList(id);
	}
	// +이미지 추가해야함(나중)

	public List<CategoryVo> getCategorytitle(String id) {
		List<CategoryVo> list = categoryDao.getCategorytitle(id);
		return list;
	}

	public Boolean insertPost(PostVo postvo) {

		return postDao.insert(postvo);

	}

	// 로고 이미지 업로드
	private static final String SAVE_PATH = "/uploads";
	private static final String URL_PREFIX = "/images";

	public Boolean update(MultipartFile multipartFile, BlogVo vo) {
		String url = "";
		if (multipartFile == null)
			return false;

		try {
			String originalFilename = multipartFile.getOriginalFilename();
			String extName = originalFilename.substring(originalFilename.lastIndexOf('.') + 1);
			String saveFileName = generateSaveFilename(extName);
			// long fileSize = multipartFile.getSize();
			System.out.println("##################################" + originalFilename);
			System.out.println("##################################" + saveFileName);
			byte[] fileData = multipartFile.getBytes();
			OutputStream os = new FileOutputStream(SAVE_PATH + "/" + saveFileName);
			os.write(fileData);
			os.close();
			url = URL_PREFIX + "/" + saveFileName;
		} catch (IOException e) {
			throw new FileUploadException();
		}
		vo.setLogo(url);
		return blogDao.update(vo);
	}

	private String generateSaveFilename(String extName) {// 파일이름을 시간으로 저장하면 파일명 중복이 잘 일어나지않음ㄴ
		String filename = "";
		Calendar calendar = Calendar.getInstance();
		filename += calendar.get(Calendar.YEAR);
		filename += calendar.get(Calendar.MONTH);
		filename += calendar.get(Calendar.DATE);
		filename += calendar.get(Calendar.HOUR);
		filename += calendar.get(Calendar.MINUTE);
		filename += calendar.get(Calendar.SECOND);
		filename += calendar.get(Calendar.MILLISECOND);
		filename += (".") + extName;
		return filename;
	}

	public Boolean ajaxremoveCategory(Long catNo) {
		postDao.ajaxdeletePost(catNo);
		return 1 == categoryDao.ajaxdeleteCategory(catNo);
	}

	public Long ajaxcreateCategory(CategoryVo catVo) {
		Long catNo = categoryDao.ajaxinsertCategory(catVo);
		return catNo;
	}

}
