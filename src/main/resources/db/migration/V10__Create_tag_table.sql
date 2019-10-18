create table tag
(
	id bigint auto_increment,
	name varchar(256) not null comment '标签名',
	type int default 0 not null comment '领域',
	gmt_create bigint default 0 not null,
	gmt_modified bigint default 0 not null,
	constraint tag_pk
		primary key (id)
)
comment '标签';