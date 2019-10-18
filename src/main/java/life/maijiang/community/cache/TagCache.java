package life.maijiang.community.cache;

import life.maijiang.community.dto.TagDTO;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class TagCache {
    public static List<TagDTO> get() {
        List<TagDTO> tagDTOs = new ArrayList<>();
        TagDTO program = new TagDTO();
        program.setCatagoryName("开发语言");
        program.setTags(Arrays.asList("java", "c++", "c", "c#", "Javascript", "html", "css", "Ruby", "Python", "Go", "Shell", "objective-c", "PHP", "R", "Swift", "Matlab", "TypeScript", "Kotlin", "VBA", "Scala"));
        tagDTOs.add(program);

        TagDTO framework = new TagDTO();
        framework.setCatagoryName("平台框架");
        framework.setTags(Arrays.asList("laravel", "spring", "express", "django", "flask", "yii", "ruby-on-rails", "tornado", "koa", "struts"));
        tagDTOs.add(framework);

        TagDTO server = new TagDTO();
        server.setCatagoryName("服务器");
        server.setTags(Arrays.asList("linux", "nginx", "docker", "apache", "ubuntu", "centos", "缓存", "tomcat", "负载均衡", "unix", "hadoop", "windows-server"));
        tagDTOs.add(server);

        TagDTO database = new TagDTO();
        database.setCatagoryName("数据库");
        database.setTags(Arrays.asList("mysql", "redis", "mongodbsql", "oracle", "nosql", "memcached", "sqlserver", "postgresql", "sqlite"));
        tagDTOs.add(database);

        TagDTO developtool = new TagDTO();
        developtool.setCatagoryName("开发工具");
        developtool.setTags(Arrays.asList("git", "github", "visual-studio-code", "vim", "sublime-text", "xcode", "intellij-idea", "eclipse", "maven", "ide", "svn", "visual-studio", "atom", "emacs", "textmate", "hg"));
        tagDTOs.add(developtool);

        return tagDTOs;
    }

    public static String FilterIsValid(String tags) {
        String[] split = StringUtils.split(tags, ",");
        List<TagDTO> tagDTOS = get();

        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));
        return invalid;
    }

}
