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


## 工具
[GIt](https://git-scm.com/downloads)  
[visual-paradigm](https://online.visual-paradigm.com/cn/)  
[H2 数据库](https://www.h2database.com/html/quickstart.html)(发现不是很好用，就换了 MySQL)

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