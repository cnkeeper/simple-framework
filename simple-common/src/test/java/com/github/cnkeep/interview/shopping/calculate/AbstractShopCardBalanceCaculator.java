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
public abstract class AbstractShopCardBalanceCaculator implements ShopCardBalanceCaculator {
    @Override
    public int caculate(ShopCard shopCard, List<Coupon> coupons) {
        // 计算商品总额
        doCalAllProductPrice(shopCard);

        // 计算使用优惠券的情况
        doCalWithCoupon(shopCard,coupons);
        return 0;
    }

    protected abstract void doCalWithCoupon(ShopCard shopCard, List<Coupon> coupons);

    private void doCalAllProductPrice(ShopCard shopCard){
        // TODO
    }

    @Override
    public CouponType acceptType() {
        return null;
    }
}
