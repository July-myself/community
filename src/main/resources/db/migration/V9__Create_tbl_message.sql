create table tbl_message
(
	id bigint auto_increment,
	notifier bigint not null comment '发出消息的人',
	receiver bigint not null comment '接收消息的人',
	outerId bigint not null comment '信息所对应的Id，如评论的Id。做外键',
	type int not null comment '消息的类型：收到评论，点赞，系统通知等',
	time_create bigint not null,
	status int default 0 not null comment '0：未读；1：已读',
	constraint tbl_message_pk
		primary key (id)
);

