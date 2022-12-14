package cn.zvo.page;

import java.util.ArrayList;
import java.util.List;
import cn.zvo.page.bean.TagA;

/**
 * 分页
 * @author 管雷鸣
 *
 */
public class Page{
	/**
	 * get传入要访问的列表的页面页数的名字
	 */
	public final static String CURRENTPAGENAME="currentPage";
	/**
	 * 默认向上列出3页跳转、向下列出3页跳转
	 * {@link #getUpList()} {@link #getNextList()}会用到
	 */
	private int listNumber = 3;
	
	private int limitStart;	//limit 的开始
	private String url;	//当前列表网址，后跟随除CURRENTPAGENAME外的其他所有参数
	private int allRecordNumber;	//总条数
	private int currentPageNumber;	//当前页面，当前第几页
	private int everyNumber;	//每页显示多少条
	private int lastPageNumber;	//最后一页是编号为几的页数
	private String nextPage;	//下一页URL
	private String upPage;		//上一页URL
	private String lastPage;	//尾页URL
	private String firstPage;	//首页，第一页URL
	private boolean haveNextPage;	//是否还有下一页
	private boolean haveUpPage;		//是否还有上一页
	private boolean currentLastPage;	//当前是否是最后一页
	private boolean currentFirstPage;	//当前是否是首页，第一页
	private int upPageNumber;	//上一页的页码
	private int nextPageNumber;	//下一页的页码
	private List<TagA> upList;	//向上的list分页标签
	private List<TagA> nextList;	//向下的分页list标签
	
	/**
	 * 传入当前列表页面的完整url（会自动过滤掉当前第几页的参数，以方便生成上一页、下一页等等链接）
	 * @param url 可以直接传入当前页面的url，图省事的话可以直接传入 request.getRequestURL() 即可。
	 * 	<p>传入格式分两种:
	 * 		<ul>
	 * 			<li>带协议的整个url，如 http://www.zvo.cn/admin/user/list.jsp?name=管雷鸣&age=23&currentPage=4</li>
	 * 			<li>不带协议跟域名的，如 /admin/user/list.jsp?name=管雷鸣&age=23&currentPage=4</li>
	 * 		</ul>
	 * 	</p>
	 */
	public void setUrl(String url) {
		if(url == null) {
			return;
		}
		
		url = url.trim();
		if(url.indexOf("http://") == 0 || url.indexOf("https://") == 0) {
			//带有协议的，将协议去掉，如 https://conference.cioe.cn/skin/gaofeng/css/reset.css?version=2018/6/1%2020:20:49
			int xieyiIndex = url.indexOf("://");
			
			//去掉协议的，如 conference.cioe.cn/skin/gaofeng/css/reset.css?version=2018/6/1%2020:20:49
			String quxieyiUrl = url.substring(xieyiIndex+3, url.length());
			
			//去掉域名的，如 /skin/gaofeng/css/reset.css?version=2018/6/1%2020:20:49
			int firstXiexian = quxieyiUrl.indexOf("/");
			url = quxieyiUrl.substring(firstXiexian, quxieyiUrl.length());
		}
		
		//如果有currentPage参数，那就将这个去掉
		if(url.indexOf(CURRENTPAGENAME) > 0){
			//传入的参数有当前页，需要吧currentPage这个参数给过滤掉
			String ur = subString(url, CURRENTPAGENAME, "&", 2);
			if(ur==null||ur.length()==0){
				ur=subString(url, CURRENTPAGENAME, null, 2);
			}
			if(ur==null||ur.length()==0){
				ur = url;
			}else{
				String cp = ur;
				ur = url.replace("?"+CURRENTPAGENAME+cp, "");
				ur = ur.replace("&"+CURRENTPAGENAME+cp, "");
			}
			url =ur;
		}
		
		this.url = url;
	}
	
	/**
	 * 共多少条数据
	 * <p>例子：比如总共100条数据，每页显示10条，这里的总条数100便是通过这里设置的</p>
	 * @param allRecordNumber 多少条，传入如 100
	 */
	public void setAllRecordNumber(int allRecordNumber) {
		this.allRecordNumber = allRecordNumber;
	}

	/**
	 * 传入当前页面的完整url（会自动过滤掉当前第几页的参数，以方便生成上一页、下一页等等链接）
	 * @param scheme 协议，传入如 http、https 
	 * @param host 主机，域名，传入如 www.zvo.cn 、 127.0.0.1 等格式
	 * @param port 端口号，传入如 80
	 * @param path 请求路径，传入如 /admin/list.jsp
	 * @param query get带的参数，传入格式如 a=1&b=2&c=3
	 * @deprecated 请使用 {@link #set(String)}
	 */
	private void setUrl(String scheme, String host, int port, String path, String query) {
		String url = scheme+"://" + host; //服务器地址
		if (port != 80) {
			url += ":"+port;
		}
		url += path;	//请求路径
		if (query==null||query.length()==0) {
		} else {
			url +="?"+query;
		}
		setUrlByStringUrl(url);
	}
	
	/**
	 * 创建Page对象，如果不是B/S，可以使用此创建
	 * <p>已过时不推荐使用，这个获取到的默认是第一页的数据。 如需要使用此，请使用 {@link #Page(int, int, int)} </p>
	 * @param allRecordNumber 共多少条
	 * @param everyNumber 每页多少条
	 * @deprecated
	 */
	public Page(int allRecordNumber ,int everyNumber){
		upList = new ArrayList<TagA>();
		nextList = new ArrayList<TagA>();
		this.allRecordNumber = allRecordNumber;
		this.everyNumber = everyNumber;
	}
	
	/**
	 * 设置每页显示多少条
	 * @param everyNumber
	 */
	public void setEveryNumber(int everyNumber) {
		this.everyNumber = everyNumber;
	}

	/**
	 * 需要配合以下几个才能使用，下面这三个是必须要设置的
	 * <ul>
	 * 		<li> {@link #setAllRecordNumber(int)} 设置共有多少条数据 </li>		
	 * 		<li> {@link #setEveryNumber(int)} 设置每页多少条 </li>		
	 * 		<li> {@link #setCurrentPageNumber(int)} 设置当前处于分页的第几页 </li>
	 * </ul>
	 */
	public Page() {
		upList = new ArrayList<TagA>();
		nextList = new ArrayList<TagA>();
	}
	
	/**
	 * 创建Page对象
	 * <p>已过时不推荐使用，如需要使用此，可使用 {@link #Page(int, int, int, String, String, int, String, String)} </p>
	 * @param allRecordNumber 共多少条
	 * @param everyNumber 每页多少条
	 * @param currentPage 当前第几页，传入如 1
	 */
	public Page(int allRecordNumber ,int everyNumber, int currentPage){
		upList = new ArrayList<TagA>();
		nextList = new ArrayList<TagA>();
		this.allRecordNumber = allRecordNumber;
		this.everyNumber = everyNumber;
		this.currentPageNumber = currentPage;
	}
	
	/**
	 * 获取最后一页的链接URL
	 * @return
	 */
	public String getLastPage() {
		this.lastPage=generateUrl(getLastPageNumber());	//生成尾页url
		return this.lastPage;
	}

	/**
	 * 获取第一页的链接URL
	 * @return
	 */
	public String getFirstPage(){
		this.firstPage = generateUrl(1);	//生成首页，第一页URL
		return this.firstPage;
	}
	
	/**
	 * 获取下一页的页码，如果没有下一页了，则返回的是最后一页的页码
	 * @return 下一页的页码，如 3
	 */
	public int getNextPageNumber() {
		//生成下一页的URL
		if(this.currentPageNumber < getLastPageNumber()){
			this.nextPageNumber = this.currentPageNumber + 1;
		}else{
			this.nextPageNumber = getLastPageNumber();
		}
		return nextPageNumber;
	}
	
	/**
	 * 获取下一页的链接URL,若没有下一页，返回的是尾页
	 * @return 
	 */
	public String getNextPage(){
		//生成下一页的URL
		this.nextPage=generateUrl(getNextPageNumber());
		return this.nextPage;
	}
	
	/**
	 * 是否还有下一页
	 * @return true:有下一页
	 */
	public boolean isHaveNextPage(){
		this.haveNextPage = getNextPageNumber() > this.currentPageNumber; 
		return this.haveNextPage;
	}

	/**
	 * 获取上一页的页码，如果没有上一页了，则返回的是第一页的页码
	 * @return 上一页的页码，如 3
	 */
	public int getUpPageNumber() {
		//上一页
		if(this.currentPageNumber > 1){
			this.upPageNumber = this.currentPageNumber - 1;
		}else{
			this.upPageNumber = 1;
		}
		return upPageNumber;
	}
	
	/**
	 * 是否还有上一页
	 * @return true:有上一页
	 */
	public boolean isHaveUpPage(){
		this.haveUpPage = getUpPageNumber() > 1; 
		return this.haveNextPage;
	}
	
	/**
	 * 获取上一页的链接URL，若是没有上一页，返回的是第一页
	 * @return
	 */
	public String getUpPage(){
		this.upPage = generateUrl(getUpPageNumber());
		return this.upPage;
	}
	
	public String getUrl() {
		if(this.url == null) {
			return "";
		}
		return this.url;
	}
	
	/**
	 * 组合url，获得跳转的链接地址
	 * @param pageNum 链接跳转的第几页
	 * @return
	 */
	private String generateUrl(int pageNum){
		if(getUrl() == null || getUrl().length() < 1) {
			return "";
		}
		return url+(this.url.indexOf("?")>0? "&":"?")+CURRENTPAGENAME+"="+pageNum;
	}
	
	/**
	 * 传入当前页面的完整url（会自动过滤掉当前第几页的参数，以方便生成上一页、下一页等等链接）
	 * <br/>若是bs应用，可直接使用 {@link #setUrl(HttpServletRequest)} 传入request，会全自动生成
	 * @param url 生成的首页、上一页等链接地址的URL前缀，如 http://aaa.xnx3.com/index.php?id=3&pageCurrent=5&status=4
	 * @deprecated 请使用 {@link #setUrl(String)}
	 */
	public void setUrlByStringUrl(String url) {
		String ur = null;
		if(url!=null&&url.indexOf(CURRENTPAGENAME)>0){
			//传入的参数有当前页，需要吧currentPage这个参数给过滤掉
			ur = subString(url, CURRENTPAGENAME, "&", 2);
			if(ur==null||ur.length()==0){
				ur=subString(url, CURRENTPAGENAME, null, 2);
			}
			if(ur==null||ur.length()==0){
				ur = url;
			}else{
				String cp = ur;
				ur = url.replace("?"+CURRENTPAGENAME+cp, "");
				ur = ur.replace("&"+CURRENTPAGENAME+cp, "");
			}
			url =ur;
		}
		this.url = url;
		
	}
	
	
	/**
	 * 获取limit查询开始的数字
	 * @return 比如第一页，那么这里就会返回 0
	 */
	public int getLimitStart() {
		this.limitStart = (this.currentPageNumber-1)*this.everyNumber;	//开始的limit
		return this.limitStart;
	}
	
	/**
	 * 获取总的记录数
	 */
	public int getAllRecordNumber() {
		return this.allRecordNumber;
	}
	
	/**
	 * 当前第几页
	 * @return
	 */
	public int getCurrentPageNumber() {
		return this.currentPageNumber;
	}
	/**
	 * 设置当前第几页
	 * 此必须是设置了 {@link #setUrlByStringUrl(String)}之后才可以调用此方法
	 * @return
	 */
	public void setCurrentPageNumber(int currentPageNumber) {
		this.currentPageNumber = currentPageNumber;
		if(this.currentPageNumber < 1){
			this.currentPageNumber = 1;
		}
	}
	
	/**
	 * 每页几条记录
	 * @return
	 */
	public int getEveryNumber() {
		return this.everyNumber;
	}

	public int getLastPageNumber() {
		//计算一共有多少页，最后一页是第几页
		if(getAllRecordNumber() == 0){
			this.lastPageNumber = 1;
		}else{
			this.lastPageNumber = (int) Math.ceil((float)getAllRecordNumber()/getEveryNumber());
		}
		return this.lastPageNumber;
	}
	
	/**
	 * 当前页面是否是最后一页
	 * @return
	 */
	public boolean isCurrentLastPage() {
		this.currentLastPage = getCurrentPageNumber() == getLastPageNumber();
		return this.currentLastPage;
	}

	/**
	 * 当前页面是否是第一页
	 * @return
	 */
	public boolean isCurrentFirstPage() {
		this.currentFirstPage = getCurrentPageNumber() == 1;		//当前页是否是第一页
		return this.currentFirstPage;
	}

	/**
	 * 向上的几页分页标签
	 * @return
	 */
	public List<TagA> getNextList() {
		this.nextList.clear();
		for (int i = 0; i < this.listNumber; i++) {
			int pageNum = getCurrentPageNumber()+i+1;
			if(pageNum <= getLastPageNumber()){
				TagA a = new TagA();
				a.setHref(generateUrl(pageNum));
				a.setTitle(pageNum+"");
				a.setPageNumber(pageNum);
				nextList.add(a);
			}else{
				break;
			}
		}
		
		return nextList;
	}

	/**
	 * 向下的几页分页标签
	 * @return
	 */
	public List<TagA> getUpList() {
		this.upList.clear();
		for (int i = 0; i < this.listNumber; i++) {
			int pageNum = getCurrentPageNumber()-i-1;
			if(pageNum>0){
				TagA a = new TagA();
				a.setHref(generateUrl(pageNum));
				a.setTitle(pageNum+"");
				a.setPageNumber(pageNum);
				upList.add(0,a);
			}else{
				break;
			}
		}
		
		return upList;
	}
	


	public static void main(String[] args) {
		Page page = new Page(110, 10);
		page.setUrlByStringUrl("");
		page.setCurrentPageNumber(6);
		
		System.out.println(page.toString());
	}

	/**
	 * 使用 {@link #getUpList()} 、 {@link #getNextList()} 获取上几页、下几页得跳转时用到，设置出现得上几页、下几页跳转按钮的个数。若不设置为默认向上列出3页跳转、向下列出3页跳转
	 * @param listNumber 设置出现得上几页、下几页跳转按钮的个数
	 */
	public void setListNumber(int listNumber) {
		this.listNumber = listNumber;
	}
	

	/**
	 * 从给定的字符串中截取想要的指定字符
	 * <p>来源于 https://gitee.com/leimingyun/xnx3_util/blob/master/src/main/java/com/xnx3/StringUtil.java</p>
	 * @param sourceString 源字符串，要切割的字符串
	 * @param startString 匹配的开始点字符
	 * 			<ul>
	 * 				<li>若为null或者""表示从头开始匹配</li>
	 * 				<li>若是没找到开始点字符串，默认为从最开始匹配</li>
	 *			</ul>
	 * @param endString 匹配的结束点字符
	 * 			<ul>
	 * 				<li>若为null或者""表示匹配到末尾</li>
	 * 				<li>若是没找到结束点字符串，默认为匹配到最末尾</li>
	 *			</ul>
	 * @param matchType 此项是针对结尾的匹配,可传入：
	 * 				<ul>
	 * 					<li>1:开始匹配找到的第一个，结束匹配找到的最后一个。</li>
	 * 					<li>2:开始匹配找到的第一个，结束匹配：找到的开始位置往后的第一个。</li>
	 * 					<li>3.开始匹配找到的最后一个，结束匹配找到的最后一个。</li>
	 * 					<li>4:开始匹配找到的最后一个，结束匹配：找到的开始位置往后的第一个。</li>
	 * 				</ul>
	 * @return 截取的字符串,若是传入了但是没找到开始或者结束字符则返回null
	 */
	private static String subString(String sourceString,String startString,String endString,int matchType){
		//开始点
		int start=0;
		if(!(startString==null||startString.length()==0)){
			if(matchType==1||matchType==2){
				start=sourceString.indexOf(startString);
			}else{
				start=sourceString.lastIndexOf(startString);
			}
			
			if(start<0){
				//没有找到，则定为0，从最开始处截取
				start=0;
			}else{
				//不截取传入的字符，从其后开始截取
				start=start+startString.length();
			}
		}
		
		//结束点
		int end=0;
		if(!(endString==null||endString.length()==0)){
			if(matchType==1||matchType==3){
				end=sourceString.lastIndexOf(endString);
				if(end<0){
					//没有找到，则定为－1，方法返回null
					end=-1;
				}
			}else{
				String xnx3_string;
				if(start>-1){
					xnx3_string=sourceString.substring(start);
				}else{
					xnx3_string = sourceString;
				}
				
				end=xnx3_string.indexOf(endString);
				if(end<0){
					end=0;
				}
				end=end+start;
			}
		}else{
			end=sourceString.length();
		}

		if(start==-1||end==-1){
			return null;
		}else{
			return sourceString.substring(start,end);
		}
	}
	
	/**
	 * 转化为json格式输出
	 * @return 格式如： {"lastPage":"", "firstPage":"", "nextPageNumber":0, "nextPage":"", "haveNextPage":false, "upPageNumber":2, "haveUpPage":false, "upPage":"", "url":"", "limitStart":20, "allRecordNumber":80, "currentPageNumber":3, "everyNumber":10, "lastPageNumber":8, "currentLastPage":false, "currentFirstPage":false, "nextList":[[{"href":"", "pageNumber":4, "title":"4"}, {"href":"", "pageNumber":5, "title":"5"}, {"href":"", "pageNumber":6, "title":"6"}]], "upList":[[{"href":"", "pageNumber":1, "title":"1"}, {"href":"", "pageNumber":2, "title":"2"}]]}
	 */
	public String toJsonString() {
		return toString();
	}
	
	@Override
	public String toString() {
		return "{"
				+ "\"lastPage\":\"" + getLastPage() + "\", "
				+ "\"firstPage\":\"" + getFirstPage() + "\", "
				+ "\"nextPageNumber\":"+ getNextPageNumber() + ", "
				+ "\"nextPage\":\"" + getNextPage() + "\", "
				+ "\"haveNextPage\":" + isHaveNextPage()+ ", "
				+ "\"upPageNumber\":" + getUpPageNumber() + ", "
				+ "\"haveUpPage\":" + isHaveUpPage() + ", "
				+ "\"upPage\":\""+ getUpPage() + "\", "
				+ "\"url\":\"" + getUrl() + "\", "
				+ "\"limitStart\":" + getLimitStart()+ ", "
				+ "\"allRecordNumber\":" + getAllRecordNumber() + ", "
				+ "\"currentPageNumber\":"+ getCurrentPageNumber() + ", "
				+ "\"everyNumber\":" + getEveryNumber() + ", "
				+ "\"lastPageNumber\":"+ getLastPageNumber() + ", "
				+ "\"currentLastPage\":" + isCurrentLastPage() + ", "
				+ "\"currentFirstPage\":"+ isCurrentFirstPage() + ", "
				+ "\"nextList\":[" + getNextList() + "], "
				+ "\"upList\":[" + getUpList() + "]"+
			"}";
	}
	
}
