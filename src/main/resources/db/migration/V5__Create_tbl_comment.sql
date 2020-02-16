create table tbl_comment
(
	id bigint auto_increment,
	parent_id bigint not null comment '父类id，父类可能是question，也可以同样是comment',
	type int not null comment '父类的类型，是问题或者评论',
	commentator int null comment '评论人id',
	time_create bigint not null,
	time_modified bigint not null,
	like_count bigint default 0 null,
	constraint tbl_comment_pk
		primary key (id)
);

