package com.github.cnkeep.interview.shopping.calculate;

import com.github.cnkeep.interview.shopping.Coupon;
import com.github.cnkeep.interview.shopping.ShopCard;
import com.github.cnkeep.interview.shopping.enums.CouponType;

import java.util.List;

/**
 * @description:
 * @author: <a href="mailto:zhangleili@lizhi.fm">LeiLi.Zhang</a>
 * @date: 2021/4/14
 * @version:
 **/
public interface ShopCardBalanceCaculator {

    /**
     *
     * @param shopCard
     * @param coupons
     * @return
     */
    int caculate(ShopCard shopCard, List<Coupon> coupons);

    CouponType acceptType();
}
