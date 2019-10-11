package kr.co.itcen.jblog.vo;

public class PostVo {
	private Long no;
	private String title;
	private String context;
	private String regdate;
	private Long category_no;

	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getRegdate() {
		return regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	public Long getCategory_no() {
		return category_no;
	}

	public void setCategory_no(Long category_no) {
		this.category_no = category_no;
	}

	@Override
	public String toString() {
		return "PostVo [no=" + no + ", title=" + title + ", context=" + context + ", regdate=" + regdate
				+ ", category_no=" + category_no + "]";
	}

}
