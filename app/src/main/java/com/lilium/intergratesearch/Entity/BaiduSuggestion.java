package com.lilium.intergratesearch.Entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BaiduSuggestion {


    /**
     * q : rxjava
     * p : false
     * bs :
     * csor : 0
     * status : 0
     * g : [{"q":"rxandroid retrofit","t":"n","st":{"q":"rxandroid retrofit","new":0}},{"q":"rxandroid 定时任务","t":"n","st":{"q":"rxandroid 定时任务","new":0}},{"q":"rxandroid retrofit okhttp","t":"n","st":{"q":"rxandroid retrofit okhttp","new":0}}]
     * s : ["rxandroid retrofit","rxandroid 定时任务","rxandroid retrofit okhttp"]
     */

    private String q;
    private boolean p;
    private String bs;
    private String csor;
    private int status;
    private List<GBean> g;
    private List<String> s;

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public boolean isP() {
        return p;
    }

    public void setP(boolean p) {
        this.p = p;
    }

    public String getBs() {
        return bs;
    }

    public void setBs(String bs) {
        this.bs = bs;
    }

    public String getCsor() {
        return csor;
    }

    public void setCsor(String csor) {
        this.csor = csor;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<GBean> getG() {
        return g;
    }

    public void setG(List<GBean> g) {
        this.g = g;
    }

    public List<String> getS() {
        return s;
    }

    public void setS(List<String> s) {
        this.s = s;
    }

    public static class GBean {
        /**
         * q : rxandroid retrofit
         * t : n
         * st : {"q":"rxandroid retrofit","new":0}
         */

        private String q;
        private String t;
        private StBean st;

        public String getQ() {
            return q;
        }

        public void setQ(String q) {
            this.q = q;
        }

        public String getT() {
            return t;
        }

        public void setT(String t) {
            this.t = t;
        }

        public StBean getSt() {
            return st;
        }

        public void setSt(StBean st) {
            this.st = st;
        }

        public static class StBean {
            /**
             * q : rxandroid retrofit
             * new : 0
             */

            private String q;
            @SerializedName("new")
            private int newX;

            public String getQ() {
                return q;
            }

            public void setQ(String q) {
                this.q = q;
            }

            public int getNewX() {
                return newX;
            }

            public void setNewX(int newX) {
                this.newX = newX;
            }
        }
    }
}
