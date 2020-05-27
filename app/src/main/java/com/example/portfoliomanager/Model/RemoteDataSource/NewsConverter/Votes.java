package com.example.portfoliomanager.Model.RemoteDataSource.NewsConverter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Votes {

    @SerializedName("negative")
    @Expose
    private Integer negative;
    @SerializedName("positive")
    @Expose
    private Integer positive;
    @SerializedName("important")
    @Expose
    private Integer important;
    @SerializedName("liked")
    @Expose
    private Integer liked;
    @SerializedName("disliked")
    @Expose
    private Integer disliked;
    @SerializedName("lol")
    @Expose
    private Integer lol;
    @SerializedName("toxic")
    @Expose
    private Integer toxic;
    @SerializedName("saved")
    @Expose
    private Integer saved;
    @SerializedName("comments")
    @Expose
    private Integer comments;

    public Integer getNegative() {
        return negative;
    }

    public void setNegative(Integer negative) {
        this.negative = negative;
    }

    public Integer getPositive() {
        return positive;
    }

    public void setPositive(Integer positive) {
        this.positive = positive;
    }

    public Integer getImportant() {
        return important;
    }

    public void setImportant(Integer important) {
        this.important = important;
    }

    public Integer getLiked() {
        return liked;
    }

    public void setLiked(Integer liked) {
        this.liked = liked;
    }

    public Integer getDisliked() {
        return disliked;
    }

    public void setDisliked(Integer disliked) {
        this.disliked = disliked;
    }

    public Integer getLol() {
        return lol;
    }

    public void setLol(Integer lol) {
        this.lol = lol;
    }

    public Integer getToxic() {
        return toxic;
    }

    public void setToxic(Integer toxic) {
        this.toxic = toxic;
    }

    public Integer getSaved() {
        return saved;
    }

    public void setSaved(Integer saved) {
        this.saved = saved;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

}