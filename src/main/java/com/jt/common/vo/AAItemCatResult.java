package com.jt.common.vo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AAItemCatResult {

    @JsonProperty("data")	//json序列化时指定字段名称
    private List<AAItemCatData> itemCats = new ArrayList<AAItemCatData>();

    public List<AAItemCatData> getItemCats() {
        return itemCats;
    }

    public void setItemCats(List<AAItemCatData> itemCats) {
        this.itemCats = itemCats;
    }

}
