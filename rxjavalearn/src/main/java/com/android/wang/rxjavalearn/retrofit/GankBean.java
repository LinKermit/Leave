package com.android.wang.rxjavalearn.retrofit;

import java.util.List;

/**
 * Created by wang on 2016/11/24.
 */

public class GankBean {

    /*
     "_id": "58362e82421aa9721eb68ac1",
      "createdAt": "2016-11-24T08:04:18.98Z",
      "desc": "11-24",
      "publishedAt": "2016-11-24T11:40:53.615Z",
      "source": "chrome",
      "type": "\u798f\u5229",
      "url": "http://ww3.sinaimg.cn/large/610dc034jw1fa2vh33em9j20u00zmabz.jpg",
      "used": true,
      "who": "daimajia "
     */
    private String error;
    private List<GankResult> results;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<GankResult> getResults() {
        return results;
    }

    public void setResults(List<GankResult> results) {
        this.results = results;
    }

    public static class  GankResult
    {
        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private String used;
        private String who;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUsed() {
            return used;
        }

        public void setUsed(String used) {
            this.used = used;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }
    }
}
