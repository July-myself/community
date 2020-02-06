package com.july.community.provider;

import com.alibaba.fastjson.JSON;
import com.july.community.dto.AccessTokenDTO;
import com.july.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    //POST方式获取token
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            //System.out.println(string); //access_token=ddb77cc54a220aae1c6525aec227419b9fc6390d&scope=user&token_type=bearer
            //拆分获得需要的access_token值
            String[] split = string.split("&");
            String token = split[0].split("=")[1];
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //GET方式获得User
    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+ accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);//将string解析成GitHub对象
            return githubUser;
        } catch (IOException e) {
            return null;
        }

    }
}
