
java分页工具，8KB大小，无三方依赖。

## 代码使用

#### 1. pom.xml 引入 

````
<!-- Page 分页 https://gitee.com/mail_osc/page.java -->
<dependency>
	<groupId>cn.zvo.page</groupId>
	<artifactId>page</artifactId>
	<version>1.0</version>
</dependency>
````

#### 2. 快速使用

````
Page page = new Page(80, 10, 3); 	//共80条、每页10条 ，当前第3页 的分页数据
page.setUrl("http://www.zvo.cn/admin/user/list.jsp?name=管雷鸣&age=23&currentPage=4");  //当前的url，这项可以不设置，不设置那输出时有链接的将不会有值。
System.out.println(page.toJsonString());
````

#### 3. 输出

````
{
	"lastPage": "/admin/user/list.jsp?name=管雷鸣&age=23&currentPage=4&currentPage=8",
	"firstPage": "/admin/user/list.jsp?name=管雷鸣&age=23&currentPage=4&currentPage=1",
	"nextPageNumber": 4,
	"nextPage": "/admin/user/list.jsp?name=管雷鸣&age=23&currentPage=4&currentPage=4",
	"haveNextPage": true,
	"upPageNumber": 2,
	"haveUpPage": true,
	"upPage": "/admin/user/list.jsp?name=管雷鸣&age=23&currentPage=4&currentPage=2",
	"url": "/admin/user/list.jsp?name=管雷鸣&age=23&currentPage=4",
	"limitStart": 20,
	"allRecordNumber": 80,
	"currentPageNumber": 3,
	"everyNumber": 10,
	"lastPageNumber": 8,
	"currentLastPage": false,
	"currentFirstPage": false,
	"nextList": [
		[{
			"href": "/admin/user/list.jsp?name=管雷鸣&age=23&currentPage=4&currentPage=4",
			"pageNumber": 4,
			"title": "4"
		}, {
			"href": "/admin/user/list.jsp?name=管雷鸣&age=23&currentPage=4&currentPage=5",
			"pageNumber": 5,
			"title": "5"
		}, {
			"href": "/admin/user/list.jsp?name=管雷鸣&age=23&currentPage=4&currentPage=6",
			"pageNumber": 6,
			"title": "6"
		}]
	],
	"upList": [
		[{
			"href": "/admin/user/list.jsp?name=管雷鸣&age=23&currentPage=4&currentPage=1",
			"pageNumber": 1,
			"title": "1"
		}, {
			"href": "/admin/user/list.jsp?name=管雷鸣&age=23&currentPage=4&currentPage=2",
			"pageNumber": 2,
			"title": "2"
		}]
	]
}
````

## 关于我们
作者：管雷鸣  
个人官网：[www.guanleiming.com](http://www.guanleiming.com)  
QQ群：726906889  
