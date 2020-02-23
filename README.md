## 七月社区
学习SpringBoot的第一个实战项目

## 资料
[Spring 文档](https://spring.io/guides)

[Spring Web](https://spring.io/guides/gs/serving-web-content/)

[es社区](https://elasticsearch.cn/explore)

[怎么在git中使用SSH key](https://blog.csdn.net/weixin_42105893/article/details/104189330)

[引入GitHub登录方式-GitHub OAuth 文档](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)

[Thymeleaf](https://www.thymeleaf.org/doc/tutprials/3.0/usingthymeleaf.html#setting-attribute-values)

[Mybatis Generator官方文档](https://mybatis.org/generator/)

## 工具
[Git工具](https://git-scm.com/download)

[前端框架-Bootstrap](https://v3.bootcss.com/components/)

[Visual Paradigm](https://www.visual-paradigm.com)

[Flyway Migration管理数据库](https://flywaydb.org/getstarted/firststeps/maven)

[lombok:自动生成getter&setter等](https://www.projectlombok.org)

[Postman:测试post接口使用]()

[实现Markdown编辑](http://editor.md.ipandao.com/)

[阿里云](https://homenew.console.aliyun.com/)

[图标库](https://www.iconfont.cn/)
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
执行数据库脚本
```bash
mvn flyway:migrate
```
执行mybatis配置
```bash
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
```
修改自增起始值
```sql
alter table tbl_question auto_increment = 12;
```

## 备注
手动改了头像的访问地址：https://profile.csdnimg.cn/D/1/6/3_weixin_46124214

## 部署
#### 需要准备
1. Git
2. JDK
3. Maven
4. MySql