## 跟着麻将社区做的项目源码
没什么，想看源码可以去 github 上的 community 仓库。

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

## 工具
[GIt](https://git-scm.com/downloads)  
[visual-paradigm](https://online.visual-paradigm.com/cn/)(主要用于画一些时序图、ER、UML等等，当然功能还有很多)  
[H2 数据库](https://www.h2database.com/html/quickstart.html)(发现不是很好用，就换了 MySQL)  
[Flyway 数据库版本管理工具](https://flywaydb.org/)  
[Lombok 开发效率工具](https://projectlombok.org/)(用过你就知道)  


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

## 首页帖子分页过程（2019.09.23 16:16）
1. 首页传递两个参数： `page`--页码，`size`--一页容量,分页数据量。  
2. 将 `page` 和 `size` 传递到 `QuestionService`。  
3. 通过 `QuestionMapper` 取到数据总数（总共有多少条数据）。  
4. 根据数据总数和 `size` 计算页码数和分页位置，以及是否显示 上/下一页，第一页/最尾页 等参数。  
5. 将查询、计算得到的所有参数和结果封装成 `PaginationDTO` 对象，传回给前端页面。  
6. 在页面中调用 `Model` 注入的 `PaginationDTO` 属性，*Bootstrap* 的分页样式和 *Thymeleaf* 标签完成分页显示。  


## 个人帖子板块发现的问题（2019.09.24）
1. 数据库表的设计有问题，*question* 库表 `creator` 应该关联 *user* 库表的 `account_id` ，而不是 `id`。  
2. 登录并记录的功能设计有问题，暂时还没想到怎么修复。  
3. 帖子的查询方式有问题（修改了库表之后课可正常工作）。  
接下去要找个时间修复登录并记录的功能。  


## 拦截器源码阅读 -- 静态资源加载问题（还没看懂。。。）


## wangEditor -- 富文本编辑器引入问题（2019.09.25）
尝试引入wangEditor代替输入框，看起来稍微有那么一点样子，但是不知道怎么整 container 的属性，尝试了一下，网页的标签好像去不掉。。。。  
问题多多。  


## 关于资源文件加载问题(2019.09.26)
报错：Error resolving template [index], template might not exist ......  
解决方案：在 `pom.xml` 中添加 `<resources>` 标签，配置资源文件路径（就是 `resources` 目录下的所有文件）。  
参考文章：[Springboot+thymeleaf+mybatis 报Error resolving template [index], template might not exist的异常](https://blog.csdn.net/fengzyf/article/details/83341479)  


## 关于引入 `Mybatis Generator` 产生的其中一个问题 -- 重复映射(2019.09.26)
报错信息：The alias 'xxxx' is already mapped to the value 'xxxxxx'  
debug 报错信息：The alias 'GeneratedCriteria' is already mapped to the value 'xxxxxx'  
解决方案：将 `mybatis-spring-boot-starter` 的版本改为 2.0.0 或者 2.1.0。  
具体原因看讨论：https://github.com/mybatis/generator/issues/461  