package com.ravindra.siit.chinkara.DataObj;

import java.sql.Timestamp;

public class NotificationData {
    long timestamp;
    String title;
    String body;
    String url;

    public NotificationData(long timestamp, String title, String body, String url) {
        this.timestamp = timestamp;
        this.title = title;
        this.body = body;
        this.url = url;
    }

    public NotificationData() {
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
