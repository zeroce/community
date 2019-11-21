create table tag_field
(
	id bigint auto_increment,
	name varchar(256) not null comment '领域名',
	gmt_create bigint default 0 not null comment '创建时间',
	gmt_modified bigint default 0 not null comment '修改时间',
	constraint tag_field_pk
		primary key (id)
)
comment '标签领域';