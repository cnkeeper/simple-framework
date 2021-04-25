package com.github.cnkeep.interview.shopping.enums;

/**
 * @description: 优惠券类型
 * @author: <a href="mailto:zhangleili@lizhi.fm">LeiLi.Zhang</a>
 * @date: 2021/4/14
 * @version:
 **/
public enum CouponType {
    DISCOUNT(1,"打折券");
    private int index;
    private String desc;

    CouponType(int index, String desc) {
        this.index = index;
        this.desc = desc;
    }

    public int getIndex() {
        return index;
    }

    public String getDesc() {
        return desc;
    }

    public static CouponType lookup(int index,CouponType defaultV){
        // TODO
        return defaultV;
    }
}
