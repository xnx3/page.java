
## 代码使用

#### 1. pom.xml 引入 

````
<dependency>
	<groupId>cn.zvo.page</groupId>
	<artifactId>page</artifactId>
	<version>1.0</version>
</dependency>
````

#### 1. 快速使用

````
Page page = new Page(80, 10, 8); 	//共80条、每页10条 ，当前第3页 的分页数据
page.setPathAndQuery("/admin/user/list.jsp", "title=管雷鸣&age=22&currentPage=4");	//这个完全可以不用设置，如果不设置，那么输出的不会带有跳转地址
System.out.println(page.toJsonString());
````

输出：

````
{
	"lastPage": "/admin/user/list.jsp?title=管雷鸣&age=22&currentPage=8",
	"firstPage": "/admin/user/list.jsp?title=管雷鸣&age=22&currentPage=1",
	"nextPageNumber": 8,
	"nextPage": "/admin/user/list.jsp?title=管雷鸣&age=22&currentPage=8",
	"haveNextPage": false,
	"upPageNumber": 7,
	"haveUpPage": false,
	"upPage": "/admin/user/list.jsp?title=管雷鸣&age=22&currentPage=7",
	"url": "/admin/user/list.jsp?title=管雷鸣&age=22",
	"limitStart": 70,
	"allRecordNumber": 80,
	"currentPageNumber": 8,
	"everyNumber": 10,
	"lastPageNumber": 8,
	"currentLastPage": true,
	"currentFirstPage": false,
	"nextList": [
		[]
	],
	"upList": [
		[{
			"href": "/admin/user/list.jsp?title=管雷鸣&age=22&currentPage=5",
			"pageNumber": 5,
			"title": "5"
		}, {
			"href": "/admin/user/list.jsp?title=管雷鸣&age=22&currentPage=6",
			"pageNumber": 6,
			"title": "6"
		}, {
			"href": "/admin/user/list.jsp?title=管雷鸣&age=22&currentPage=7",
			"pageNumber": 7,
			"title": "7"
		}]
	]
}
````
