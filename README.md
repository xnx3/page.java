
## 代码使用

#### 1. pom.xml 引入 

````

````

#### 1. 快速使用

````
Page page = new Page(80, 10, 3); 	//共80条、每页10条 ，当前第3页 的分页数据
System.out.println(page.toJsonString());
````

输出：

````
{
    "lastPage":"",
    "firstPage":"",
    "nextPageNumber":0,
    "nextPage":"",
    "haveNextPage":false,
    "upPageNumber":2,
    "haveUpPage":false,
    "upPage":"",
    "url":"",
    "limitStart":20,
    "allRecordNumber":80,
    "currentPageNumber":3,
    "everyNumber":10,
    "lastPageNumber":8,
    "currentLastPage":false,
    "currentFirstPage":false,
    "nextList":[
        [
            {
                "href":"",
                "pageNumber":4,
                "title":"4"
            },
            {
                "href":"",
                "pageNumber":5,
                "title":"5"
            },
            {
                "href":"",
                "pageNumber":6,
                "title":"6"
            }
        ]
    ],
    "upList":[
        [
            {
                "href":"",
                "pageNumber":1,
                "title":"1"
            },
            {
                "href":"",
                "pageNumber":2,
                "title":"2"
            }
        ]
    ]
}
````