package kr.co.itcen.jblog.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import kr.co.itcen.jblog.security.AuthUser;
import kr.co.itcen.jblog.service.BlogService;
import kr.co.itcen.jblog.vo.BlogVo;
import kr.co.itcen.jblog.vo.CategoryVo;
import kr.co.itcen.jblog.vo.PostVo;
import kr.co.itcen.jblog.vo.UserVo;

@RequestMapping("/blog/{id:(?!assets)(?!images).*}")
@Controller
public class BlogController {

	@Autowired
	private BlogService blogService;

	@RequestMapping({ "", "/{catno}", "/{pathno1}/{pathno2}" })
	public String blogmain(@PathVariable String id, @PathVariable Optional<Long> pathno1,
			@PathVariable Optional<Long> pathno2, @AuthUser UserVo vo, Model model) {
		List<CategoryVo> list = blogService.getList(id);
		model.addAttribute("list", list);

		Long catno = list.get(0).getNo();// 0L
		Long postno = 0L;

		if (pathno2.isPresent()) {// /pathno1/pathno2
			catno = pathno1.get();
			postno = pathno2.get();
		} else if (pathno1.isPresent()) {// /pathno1
			catno = pathno1.get();
		}

		List<PostVo> postList = blogService.getList(catno);
		model.addAttribute("postList", postList);
		PostVo postvo;
		if (postno == 0L && (!postList.isEmpty())) {
			postno = postList.get(0).getNo();
			postvo = blogService.getPost(postno, catno);
		} else {
			postvo = blogService.getPost(postno, catno);
		}

		BlogVo blogvo = blogService.get(id);
		model.addAttribute("blog", blogvo);
		model.addAttribute("post", postvo);
		model.addAttribute("id", id);
		if (id.equals(vo.getId()))
			model.addAttribute("own", true);
		return "blog/blog-main";
	}

	@RequestMapping("/admin/basic") // 블로그 기본 관리
	public String blogadmin(@PathVariable String id, Model model) {
		BlogVo vo = blogService.get(id);
		model.addAttribute("vo", vo);
		return "blog/blog-admin-basic";
	}

	// + 블로그 관리에서 파일업로드 추가해야함....

	@RequestMapping("/admin/category") // 카테고리 관리
	public String category(@PathVariable String id, Model model) {
		List<CategoryVo> list = blogService.getCategoryList(id);
		model.addAttribute("list", list);
		BlogVo blogvo = blogService.get(id);
		model.addAttribute("vo", blogvo);
		return "blog/blog-admin-category";
	}

	@RequestMapping("/admin/write")
	public String postwrite(@PathVariable String id, Model model) {
		List<CategoryVo> list = blogService.getCategorytitle(id);
		BlogVo blogvo = blogService.get(id);
		model.addAttribute("list", list);
		model.addAttribute("vo", blogvo);
		return "blog/blog-admin-write";
	}

	@RequestMapping(value = "/admin/write", method = RequestMethod.POST)
	public String postwrite(@PathVariable String id, PostVo postvo, @RequestParam("category") Long no) {
		postvo.setCategory_no(no);
		blogService.insertPost(postvo);
		return "redirect:/blog/" + id;
	}

	// 파일업로드
	@RequestMapping(value = "/admin/basic", method = RequestMethod.POST)
	public String blogadmin(@RequestParam(value = "logo-file", required = false) MultipartFile multipartFile,
			@PathVariable String id, BlogVo vo) {
		blogService.update(multipartFile, vo);
		return "redirect:/blog/" + id;
	}

}
