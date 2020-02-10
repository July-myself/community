create table tbl_question
(
	id int auto_increment,
	title varchar(50) null,
	description text null,
	time_create bigint null,
	time_modified bigint null,
	creator int null comment '问题创建人id',
	comment_count int default 0 null comment '评论数',
	view_count int default 0 null comment '阅读数',
	like_count int default 0 null comment '点赞数',
	tag varchar(256) null,
	constraint tbl_question_pk
		primary key (id)
);

