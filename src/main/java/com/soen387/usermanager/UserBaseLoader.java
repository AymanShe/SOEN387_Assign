package com.soen387.usermanager;

import org.json.simple.JSONArray;

public interface UserBaseLoader {

    public JSONArray loadUserBase();

    public void saveUserBase(JSONArray userBaseJson);

}
