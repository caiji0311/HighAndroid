package com.caiji.android.reflecandinocation.bean;

import java.util.List;

/**
 * Created by lj on 2016/6/28.
 */
public class Items {

    public String content_url;

    public String cover_image_url;

    public String cover_webp_url;

    public String created_at;

    public  String likes_count;

    public  String published_at;

    public String share_msg;

    public String title;

    public String updated_at;

    public String url;

    @Override
    public String toString() {
        return "Items{" +
                "content_url='" + content_url + '\'' +
                ", cover_image_url='" + cover_image_url + '\'' +
                ", cover_webp_url='" + cover_webp_url + '\'' +
                ", created_at='" + created_at + '\'' +
                ", likes_count='" + likes_count + '\'' +
                ", published_at='" + published_at + '\'' +
                ", share_msg='" + share_msg + '\'' +
                ", title='" + title + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public String getContent_url() {
        return content_url;
    }

    public void setContent_url(String content_url) {
        this.content_url = content_url;
    }

    public String getCover_image_url() {
        return cover_image_url;
    }

    public void setCover_image_url(String cover_image_url) {
        this.cover_image_url = cover_image_url;
    }

    public String getCover_webp_url() {
        return cover_webp_url;
    }

    public void setCover_webp_url(String cover_webp_url) {
        this.cover_webp_url = cover_webp_url;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getLikes_count() {
        return likes_count;
    }

    public void setLikes_count(String likes_count) {
        this.likes_count = likes_count;
    }

    public String getPublished_at() {
        return published_at;
    }

    public void setPublished_at(String published_at) {
        this.published_at = published_at;
    }

    public String getShare_msg() {
        return share_msg;
    }

    public void setShare_msg(String share_msg) {
        this.share_msg = share_msg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
