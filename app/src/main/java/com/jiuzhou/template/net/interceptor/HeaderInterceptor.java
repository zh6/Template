package com.jiuzhou.template.net.interceptor;

import com.jiuzhou.template.common.Constants;
import com.jiuzhou.template.utils.SpUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        // 添加参数
        Request newRequest = addHeader(oldRequest);
        Response response = chain.proceed(newRequest);
//        String responseStr = response.body().string();
//        JSONObject jsonObject = JSON.parseObject(responseStr);
//        int state = jsonObject.getIntValue("state");
//        if (state == 97) {
//            // 获取新token
//            getToken();
//            Request newTokenRequest = addHeader(oldRequest);
//            response = chain.proceed(newTokenRequest);
//        } else {
//            // 这里值得注意。由于前面value.bytes()把响应流读完并关闭了，所以这里需要重新生成一个response，否则数据就无法正常解析了
//            response = response.newBuilder().body(ResponseBody.create(response.body().contentType(), responseStr)).build();
//        }
        return response;
    }

    /**
     * 添加公共参数
     *
     * @param request
     * @return
     */
    private Request addHeader(Request request) {
        String token = SpUtils.USER.getString(Constants.TOKEN);
        if (token.isEmpty() || String.valueOf(request.url()).contains("login")) {
            return request;
        }
        return request.newBuilder().addHeader("Authorization", token).build();
    }


    /**
     * 登录接口获取最新的Token
     *
     * @throws IOException
     */
//    private void getToken() throws IOException {
//        //重新调用登录接口 获取token
//        Map<String, String> map = new HashMap<>();
//        map.put("loginName", SPUtil.USER.getString("name", ""));
//        map.put("password", SPUtil.USER.getString("pwd", ""));
//        map.put("systemId", "1");
//        map.put("flag", "0");
//        map.put("authCode", "O0i9");
//        map.put("encrypt", "1");
//        AllService.getInstance().Login(map).subscribe(new BaseObserver<LoginRespEntity>(this) {
//            @Override
//            public void onSuccess(LoginRespEntity response) {
//                if (response.getState() == 0) {  //表示登录成功
//                    SPUtil.USER.putString("token", response.getToken()).commit();
//                }
//            }
//        });
//    }

}
