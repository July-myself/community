package com.july.community.cache;

import com.july.community.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TagCache {
    public static List<TagDTO> getTagList(){
        List<TagDTO> tagDTOS = new ArrayList<>();
        TagDTO program = new TagDTO();
        program.setCategoryName("开发语言");
        program.setTagList(Arrays.asList("javaScript","php","java","css","html","node","python","c++","c",
                "golang","objective-c","typescript","shell","swift","c#","sass","ruby","bash","less","asp.net","lua","scala",
                "coffeeScript","actionScript","rust","erlang","perl"));
        tagDTOS.add(program);

        TagDTO framework = new TagDTO();
        framework.setCategoryName("平台架构");
        framework.setTagList(Arrays.asList("laravel","spring","express","django","flask","yii","ruby-on-rails","tornado","koa","struts"));
        tagDTOS.add(framework);

        TagDTO server= new TagDTO();
        server.setCategoryName("服务器");
        server.setTagList(Arrays.asList("linux","nginx","docker","apache","ubuntu","centos","tomcat","unix","hadoop","windows-server","缓存","负载均衡"));
        tagDTOS.add(server);

        TagDTO database = new TagDTO();
        database.setCategoryName("数据库与缓存");
        database.setTagList(Arrays.asList("mysql","redis","mongodb","sql","oracle","nosql","memcached","sqlserver","postgresql","sqlite"));
        tagDTOS.add(database);

        TagDTO tool = new TagDTO();
        tool.setCategoryName("开发工具");
        tool.setTagList(Arrays.asList("git","github","visual-studio-code","vim","sublime-text","xcode","intellij-idea","eclipse","maven","ide","svn","visual-studio","atom","emacs","textmate","hg"));
        tagDTOS.add(tool);

        TagDTO life = new TagDTO();
        life.setCategoryName("生活");
        life.setTagList(Arrays.asList("求职","天气"));
        tagDTOS.add(life);

        return tagDTOS;
    }

    //对输入的标签进行过滤，返回预定标签库中不存在的标签
    public static String filterInvalid(String tags){
        String[] split = StringUtils.split(tags, ",");
        List<TagDTO> tagDTOS = getTagList();
        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTagList().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(split).filter(t -> StringUtils.isBlank(t) || !tagList.contains(t)).collect(Collectors.joining(","));
        return invalid;

    }
}
