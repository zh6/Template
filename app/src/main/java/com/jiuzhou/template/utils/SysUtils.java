package com.jiuzhou.template.utils;

import com.alibaba.fastjson.JSON;
import com.jiuzhou.template.common.Constants;
import com.jiuzhou.template.entity.SysButtonEntity;
import com.jiuzhou.template.entity.SysModuleEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class SysUtils {
    /**
     * 返回按钮是否有权限
     *
     * @param module 模块名字
     * @param button 按钮名字
     * @return
     */
    public static Boolean getAuthorityButton(String module, String button) {
        List<SysModuleEntity> sys = JSON.parseArray(SpUtils.USER.getString(Constants.SYS), SysModuleEntity.class);
        if (sys != null) {
            for (SysModuleEntity item : sys) {
                if (module.equals(item.getCaption())) {
                    for (SysButtonEntity btn : item.getModulButtonList()) {
                        if (button.equals(btn.getName())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static JSONObject SysJsonObjAuth(String module) {
        JSONObject jsonObject = new JSONObject();
        List<SysModuleEntity> sys = JSON.parseArray(SpUtils.USER.getString(Constants.SYS), SysModuleEntity.class);
        if (sys != null && sys.size() > 0) {
            for (SysModuleEntity item : sys) {
                if (module.equals(item.getCaption())) {
                    if (item.getModulButtonList() != null && item.getModulButtonList().size() > 0) {
                        for (SysButtonEntity btn : item.getModulButtonList()) {
                            try {
                                jsonObject.put(btn.getName(), true);
                            } catch (JSONException e) {
                            }
                        }

                    }

                }
            }
        }
        return jsonObject;
    }
}
