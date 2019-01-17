package com.upsoft.demo.framework.dialog;

import java.io.Serializable;

/**
 * @author lh
 * @version 1.0.0
 * @filename ListBean
 * @description -------------------------------------------------------
 * @date 2017/6/28 14:06
 */
public class ListBean implements Serializable {
    private String id;
    private String value;

    public ListBean() {
    }

    public ListBean(String id, String value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
