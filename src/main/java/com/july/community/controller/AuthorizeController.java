package com.july.community.controller;

import com.july.community.dto.AccessTokenDTO;
import com.july.community.dto.GithubUser;
import com.july.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录后回调
 */
@Controller
public class AuthorizeController {
    
    @Autowired
    private GithubProvider gitHubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);//进行doPost请求，获取access_token
        GithubUser user = gitHubProvider.getUser(accessToken);//使用accesstok获得user信息
        //登录成功后的操作
        if(user != null){
            //写cookie和session
            request.getSession().setAttribute("user",user); //把user信息作为一个属性写入session中
            return "redirect:/"; //重定向，跳转到首页(填写的是路径)
        }else{
            //登录失败，重新登录
            return "redirect:/";
        }
    }
}
