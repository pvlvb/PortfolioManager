package com.example.portfoliomanager.Model.RemoteDataSource.NewsConverter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Source {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("domain")
    @Expose
    private String domain;
    @SerializedName("path")
    @Expose
    private Object path;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Object getPath() {
        return path;
    }

    public void setPath(Object path) {
        this.path = path;
    }

}

