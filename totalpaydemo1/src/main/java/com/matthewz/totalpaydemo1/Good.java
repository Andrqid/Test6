package com.matthewz.totalpaydemo1;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class Good implements MultiItemEntity {

    private int type = Constant.MAIN_TYPE_CONTENT;

    private String name;

    private String unitPrice;

    private String amount;

    public Good() {
    }

    public Good(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public int getItemType() {
        return type;
    }
}
