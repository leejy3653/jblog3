package kr.co.itcen.jblog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.itcen.jblog.dto.JSONResult;
import kr.co.itcen.jblog.service.BlogService;
import kr.co.itcen.jblog.vo.CategoryVo;
import kr.co.itcen.jblog.vo.UserVo;

@Configuration("categoryAPIController")
@RequestMapping("category/api")
public class CategoryController {

	@Autowired
	private BlogService blogService;

	@ResponseBody
	@RequestMapping("/remove")
	public JSONResult remove(@RequestParam(value = "id", required = true, defaultValue = "-1") Long catNo) {

		Boolean result = blogService.ajaxremoveCategory(catNo);
		if (!result || catNo == -1) {
			return JSONResult.success(false);
		}
		return JSONResult.success(result);
	}

	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public JSONResult create(@ModelAttribute CategoryVo catVo, HttpSession session) {
		String blog_id = ((UserVo) session.getAttribute("authUser")).getId();
		catVo.setBlog_id(blog_id);
		Long result = blogService.ajaxcreateCategory(catVo);
		return JSONResult.success(result);
	}

}
