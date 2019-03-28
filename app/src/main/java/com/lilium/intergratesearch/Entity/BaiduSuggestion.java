package com.lilium.intergratesearch.Entity;

import java.util.List;

public class BaiduSuggestion {

    /**
     * q : from
     * p : false
     * g : [{"type":"sug","sa":"s_1","q":"from是什么意思"},{"type":"sug","sa":"s_2","q":"from time to time意思"},{"type":"sug","sa":"s_3","q":"from now on"},{"type":"sug","sa":"s_4","q":"from then on"},{"type":"sug","sa":"s_5","q":"from to"},{"type":"sug","sa":"s_6","q":"from怎么读音"},{"type":"sug","sa":"s_7","q":"from software"},{"type":"sug","sa":"s_8","q":"froment征"},{"type":"sug","sa":"s_9","q":"from head to toe"},{"type":"sug","sa":"s_10","q":"from scratch"}]
     */

    private String q;
    private boolean p;
    private List<GBean> g;

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

    public List<GBean> getG() {
        return g;
    }

    public void setG(List<GBean> g) {
        this.g = g;
    }

    public static class GBean {
        /**
         * type : sug
         * sa : s_1
         * q : from是什么意思
         */

        private String type;
        private String sa;
        private String q;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSa() {
            return sa;
        }

        public void setSa(String sa) {
            this.sa = sa;
        }

        public String getQ() {
            return q;
        }

        public void setQ(String q) {
            this.q = q;
        }
    }
}
