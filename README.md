## 七月社区
学习SpringBoot的第一个实战项目

## 资料
[Spring 文档](https://spring.io/guides)

[Spring Web](https://spring.io/guides/gs/serving-web-content/)

[es社区](https://elasticsearch.cn/explore)

[怎么在git中使用SSH key](https://blog.csdn.net/weixin_42105893/article/details/104189330)

[引入GitHub登录方式-GitHub OAuth 文档](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)

## 工具
[Git工具](https://git-scm.com/download)

[前端框架-Bootstrap](https://v3.bootcss.com/components/)

[Visual Paradigm](https://www.visual-paradigm.com)

[Flyway Migration管理数据库](https://flywaydb.org/getstarted/firststeps/maven)

## 脚本
```sql
create table tbl_user
(
    id            int auto_increment
        primary key,
    account_id    varchar(100) ,
    name          varchar(50)  ,
    token         char(36)     ,
    time_create   bigint       ,
    time_modified bigint       
);

```

```bash
mvn flyway:migrate
```

