create table notification
(
	id bigint auto_increment,
	notifier bigint not null comment '通知人',
	receiver bigint not null comment '接收人',
	outer_id bigint not null comment '外键',
	type int not null comment '通知的消息类型',
	gmt_create bigint not null comment '通知日期',
	status int default 0 not null comment '1-已读/0-未读',
	constraint notification_pk
		primary key (id)
)
comment '通知';