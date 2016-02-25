package com.app.favcy.favcyutility;

import org.json.JSONObject;

public interface HttpCallListener {
    public void asyncComplete(JSONObject json);
}