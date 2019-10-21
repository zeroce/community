alter table notification
    add notifier_name varchar(100) null comment '回复人昵称';
alter table notification
    add outer_title varchar(256) null comment '回复的标题';