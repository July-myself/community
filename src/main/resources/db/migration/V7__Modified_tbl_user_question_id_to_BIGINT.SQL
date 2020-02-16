alter table tbl_question modify id bigint auto_increment;
alter table tbl_user modify id bigint auto_increment;
alter table tbl_question modify creator bigint null comment '问题创建人id';

