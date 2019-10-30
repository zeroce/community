create table favour
(
	id bigint auto_increment,
	user_account_id varchar(100) not null comment '用户ID',
	type_id bigint not null comment '目标ID',
	type int default 0 not null comment '点赞目标类型，default 0，1/问题，2/评论',
	status int default 0 not null comment '点赞状态，default 0/取消，1/有效',
	constraint favour_pk
		primary key (id)
)
comment '点赞表';