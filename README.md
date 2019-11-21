## 跟着麻将社区做的项目源码
没什么，想看源码可以去 github 上的 community 仓库。

## 部署到服务器
- Git 
- JDK1.8
- Maven
- MySQL5.7

## 资料
[Spring 指导](https://spring.io/guides)  
[Spring Thymeleaf 文档](https://spring.io/guides/gs/serving-web-content/)  
[目标社区 -- es](https://elasticsearch.cn/explore)  
[用 GithubAPI 登录](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)  
[Bootstrap 文档](https://v3.bootcss.com/components/#navbar-buttons)  
[Spring Boot 中文文档](https://www.springcloud.cc/spring-boot.html#_learning_about_spring_boot_features)  
[Spring 官方文档2.0.0.RC1 版](https://docs.spring.io/spring-boot/docs/2.0.0.RC1/reference/htmlsingle/)  
[Flyway 文档](https://flywaydb.org/documentation/database/mysql)(根据使用的数据库来查看相关文档)  
[Flyway Maven 配置](https://flywaydb.org/getstarted/firststeps/maven)  
[Lombok Maven 配置说明](https://projectlombok.org/setup/maven)(不同 IDE 有不同的使用说明，IDEA 除了引入依赖还要安装 Lombok 插件)  
[Thymeleaf 文档](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html)  
[Mybatis Generator Core 文档](http://mybatis.org/generator/configreference/javaClientGenerator.html)  
[mybatis-spring-boot-starter 文档](http://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/)  
[UCloud Ufile 对象存储文档](https://docs.ucloud.cn/storage_cdn/ufile/index.html)  
[Java SDK for UCloud Ufile](https://github.com/ucloud/ufile-sdk-java)  

## 工具
[GIt](https://git-scm.com/downloads)  
[visual-paradigm](https://online.visual-paradigm.com/cn/)(主要用于画一些时序图、ER、UML等等，当然功能还有很多)  
[H2 数据库](https://www.h2database.com/html/quickstart.html)(发现不是很好用，就换了 MySQL)  
[Flyway 数据库版本管理工具](https://flywaydb.org/)  
[Lombok 开发效率工具](https://projectlombok.org/)(用过你就知道)  
[WangEditor 富文本编辑器官网](http://www.wangeditor.com/)
[WangEditor 富文本编辑器 github 项目地址](https://github.com/wangfupeng1988/wangEditor/)
[Editormd Markdown编辑器 github 项目地址](https://github.com/pandao/editor.md)
[Editormd 编辑器官网](http://editor.md.ipandao.com/)
[UCloud 官网](https://www.ucloud.cn/)

## 数据库脚本
```sql
create table user
(
	id int auto_increment
		primary key,
	account_id varchar(100) null,
	name varchar(100) null,
	token char(36) null,
	gmt_create bigint null,
	gmt_modified bigint null
);
```
其他的数据库脚本在 `db/migration` 目录中。可惜这个 *Flyway* 社区版不能使用 `Nx__` 之类的更改删除功能。  

## 脚本
```bash
# 更新数据库命令
mvn flyway:migrate
```
```maven
# Mybatis Generator plugin 执行命令
mvn mybatis-generator:generate
# 举例，中间可加参数配置
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
```

---

### 首页帖子分页过程（2019.09.23 16:16）
1. 首页传递两个参数： `page`--页码，`size`--一页容量,分页数据量。  
2. 将 `page` 和 `size` 传递到 `QuestionService`。  
3. 通过 `QuestionMapper` 取到数据总数（总共有多少条数据）。  
4. 根据数据总数和 `size` 计算页码数和分页位置，以及是否显示 上/下一页，第一页/最尾页 等参数。  
5. 将查询、计算得到的所有参数和结果封装成 `PaginationDTO` 对象，传回给前端页面。  
6. 在页面中调用 `Model` 注入的 `PaginationDTO` 属性，*Bootstrap* 的分页样式和 *Thymeleaf* 标签完成分页显示。  

### 个人帖子板块发现的问题（2019.09.24）
1. 数据库表的设计有问题，*question* 库表 `creator` 应该关联 *user* 库表的 `account_id` ，而不是 `id`。  
2. 登录并记录的功能设计有问题，暂时还没想到怎么修复。  
3. 帖子的查询方式有问题（修改了库表之后课可正常工作）。  
接下去要找个时间修复登录并记录的功能。  

### 拦截器源码阅读 -- 静态资源加载问题（还没看懂。。。）

### wangEditor -- 富文本编辑器引入问题（2019.09.25）
尝试引入 *wangEditor* 代替输入框，看起来稍微有那么一点样子，但是不知道怎么整 container 的属性，尝试了一下，网页的标签好像去不掉。。。。  
问题多多。  

### 关于资源文件加载问题(2019.09.26)
解决方案：在 `pom.xml` 中添加 `<resources>` 标签，配置资源文件路径（就是 `resources` 目录下的所有文件）。  
参考文章：[Springboot+thymeleaf+mybatis 报Error resolving template [index], template might not exist的异常](https://blog.csdn.net/fengzyf/article/details/83341479)  

### 关于引入 `Mybatis Generator` 产生的其中一个问题 -- 重复映射(2019.09.26)
报错信息：The alias 'xxxx' is already mapped to the value 'xxxxxx'  
debug 报错信息：The alias 'GeneratedCriteria' is already mapped to the value 'xxxxxx'  
解决方案：将 `mybatis-spring-boot-starter` 的版本改为 2.0.0 或者 2.1.0。  
具体原因看讨论：https://github.com/mybatis/generator/issues/461  

### 关于评论功能的实现 -- 1 -- 前后端分离（2019-09-28）
先稍微写了一点后台的处理逻辑，前台页面还没有设计，PostMan 插件我也暂时没法下载，等过段时间能下载就来测试一下，现在也暂时只能这样了。  

### 关于评论功能的实现 -- 1 -- 前后端分离（2019-10-10）
经过几天的努力总算能用上 Postman，不断尝试后成功完成数据的接收返回，页面的一级评论也做好了，还没有做提交回复的一级评论异步刷新。  
Postman 在模拟请求的时候，如果需要检验登录状态的话，需要在 Cookie 中添加响应的 JSESSIONID 的值。（或者其他的键值）  

### 关于评论功能 -- 2 -- 二级评论（2019-10-13）
二级评论主要是分步做，先将基本的页面效果绘出来，再根据一级评论的评论ID，找到所有 parentId 为 ID 的评论数据，返回到页面。  
现在刚刚将添加二级评论的样式写好，看起来也有点丑。。。。  

### 关于评论功能 -- 3 -- 二级评论（2019-10-15）
已经将评论的样式和功能都做好，还顺便添加了帖子详情页的相关问题推荐；有点羞耻的把QQ二维码放到 index 上。。。。。。暂时没使用到别的什么工具。  

### 关于标签库 -- 1 -- 简单标签库（2019-10-18）
目前的标签库是直接就复制[思否社区](https://segmentfault.com/)上提问给的标签库，按理说上面是动态的，可以自建新标签，我这里暂时没实现哈，而且，也没有把标签全部存起来。  
之后会找时间完善基本的标签库功能。  

### 关于社区消息通知功能 -- 1 -- （2019-10-18）
调整了一下导航栏的图标，新建一个表作为消息通知的库 *notification*。接下来会完善这个消息通知功能。  

### 关于社区消息通知功能 -- 2 -- （2019-10-21）
使用社区产品的用户，经常会见到各种各样的消息功能。消息通知功能的设计就是为了让用户得到该有的反馈，提高用户的交互体验。  
1. 在导航栏定义一个通知图标作为UI设计，点击通知图标则跳转到消息列表页面。
2. 在消息列表页面，每条消息主要记录 消息发起人、消息接收人、消息主题、消息目标、消息发起时间、消息已/未读状态 几个元素。
3. 点击消息目标可跳转到消息发生的地点，并将未读状态去掉，最新消息数相应减少。
4. 消息的主要业务是问题回复、评论回复、问题点赞等（暂时就前两个）。  

1. 问题回复的主要修改：    
- 回复问题时同时获取相应的消息信息，封装成 `NotificationDTO` 保存到 `notification` 表中。  
1. 评论回复的主要修改：
- 回复评论同时获取相应的消息信息，封装成 `NotificationDTO` 保存到 `notification` 表中。
1. 未读状态根据 `notification` 表中 `status == 0` 和 `notitier_name == userAccountId` 计数。

### Editormd -- 引入 Markdown 编辑器代替 wangEditor（2019-10-22）
之前引入 *wangEditor* 来丰富编辑器，现在引入 Editormd 来增加使用体验。项目本身在 **Github** 上有持续维护（现在），但是在码云上貌似很久没更新了（不过影响不大）。地址已经放在上面了。  
Editormd 编辑器的文档还是挺容易读的。  

### 图片上传和存储 -- UCloud Ufile 使用（2019-10-26）
引入 *Editormd* 之后，为了能够上传和存储图片，采用了 **UCloud** 云服务的 **Ufile** 对象存储。emmmm个人体验不错，认证审核很快，SDK 阅读也不难，关键是提供的免费服务很好666666。  
图片上传功能的思路（同步上传）：
1. 表单上传图片，由 `FileController` 借助 `request` 保存图片文件。  
2. 将 `HttpServletRequest` 转为 `MultipartHttpServletRequest.getFile(name)`，找到上传的图片文件。
3. 调用 `UCloudProvider` 的 `upload()` 方法，传入图片文件的初始命名，文件类型和文件流。
4. 给图片文件生成网上命名，并获得存储空间的公钥密钥，将文件上传到存储空间，并获得 `response`。
5. 根据 `response` 返回的状态码，若成功上传则生成图片文件的 **URL**，上传失败则返回失败异常（图片上传失败）。
6. 得到生成的图片文件的 **URL**，并封装成 `FileDTO` 返回给前端页面。

### 多人登录状态的遗留问题（2019-10-26）
按理说现在论坛的登录功能是支持多人同时登录的，但是有一个问题，就是问题详情页面，评论回复输入框的提示应该是当前登录用户，或者是提示未登录状态。因此修改显示用户的头像和信息，从 `session` 中获取 `user` 的信息。  
还有一个，当回复问题时，提交一级评论后，任何一条的一级评论的初始被评论数为1，应该修改为0。  

### 发现分页的一个**Bug**（2019-10-27）
当 `totalCount` 是0时，分页的结果不能显示出来，并返回服务器异常。需要修复一下。  

### 搜索功能（2019-10-27）
目前实现了一下简单的搜索功能，基本实现逻辑是根据提交的搜索内容进行标题正则匹配，将包含的结果都查出来，返回到搜索页。  
1. 首先提交搜索内容。
2. 根据内容生成响应的关键词数组。
3. 根据关键词数组进行问题查找，并返回查找到的记录和总记录数。
4. 根据总记录数进行分页（物理分页）。
5. 将分页结果和记录返回到前端。  

但是现在流行的搜索方式应该是采用 **Solr** 或者 **Elasticsearch** 等等，实现这次功能只是采用很简单的逻辑，并没有什么难度，且效率问题、准确度问题。。。  

### 点赞功能 -- 1 -- 简单实现（2019-10-28）
采用 ajax 异步刷新点赞按钮的状态，新建了一个点赞记录表 `favour`，但是还没有完成异步查询功能。  
看很多网站的点赞图标都是异步刷新的，还没整出来。。。

### 几个小功能（2019-10-29）
添加了页尾，内容跟的 ES 中文社区。  
添加了日志的几个基本配置，保留30天的日志，每个日志大小50MB。  

### 登录跳转问题（2019-10-30）
增加一个参数，将浏览页面的URL传到后台，然后重定向到URL。

### 点赞功能 -- 2 -- 异步刷新（2019-11-05）
还没有整出来。。。。。。我太菜了/大哭

### 引入 Spring Boot 定时器 -- 1 -- 热门话题

### 首页标签分类查询
分别有5种查询：最新、最热、一周热点、月度热点、零回复的所有帖子。在查询的过程增加筛选条件和排序条件。