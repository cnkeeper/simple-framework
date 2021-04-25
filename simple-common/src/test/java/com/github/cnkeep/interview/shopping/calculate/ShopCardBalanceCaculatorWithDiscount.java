package com.github.cnkeep.interview.shopping.calculate;

import com.github.cnkeep.interview.shopping.Coupon;
import com.github.cnkeep.interview.shopping.ShopCard;
import com.github.cnkeep.interview.shopping.enums.CouponType;

import java.util.List;

/**
 * @description: 打折计算
 * @author: <a href="mailto:zhangleili@lizhi.fm">LeiLi.Zhang</a>
 * @date: 2021/4/14
 * @version:
 **/
public class ShopCardBalanceCaculatorWithDiscount extends AbstractShopCardBalanceCaculator implements ShopCardBalanceCaculator {
    @Override
    protected void doCalWithCoupon(ShopCard shopCard, List<Coupon> coupons) {
       //
    }

    @Override
    public CouponType acceptType() {
        return CouponType.DISCOUNT;
    }
}
