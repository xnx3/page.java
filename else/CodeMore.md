代码中的更多使用

#### 大众化使用

````
Page page = new Page(80, 10, 3); 	//共80条、每页10条 ，当前第3页 的分页数据
page.setUrl("http://www.zvo.cn/admin/user/list.jsp?name=管雷鸣&age=23&currentPage=4");  //当前的url，这项可以不设置，不设置那输出时有链接的将不会有值。
System.out.println(page.toJsonString());
````

#### 被继承成扩展

````

````