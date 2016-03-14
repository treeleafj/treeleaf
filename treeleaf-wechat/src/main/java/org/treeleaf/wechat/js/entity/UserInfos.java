package org.treeleaf.wechat.js.entity;

import java.util.List;

/**
 * Created by yaoshuhong on 2016/3/14.
 */
public class UserInfos extends WeChatBaseInfo {

    private List<UserInfo> user_info_list;

    public List<UserInfo> getUser_info_list() {
        return user_info_list;
    }

    public void setUser_info_list(List<UserInfo> user_info_list) {
        this.user_info_list = user_info_list;
    }
}
