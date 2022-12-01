package cn.zvo.page;

public class Demo {
	public static void main(String[] args) {
		Page page = new Page(80, 10, 3); 	//共80条、每页10条 ，当前第3页 的分页数据
		System.out.println(page.toJsonString());
	}
}
