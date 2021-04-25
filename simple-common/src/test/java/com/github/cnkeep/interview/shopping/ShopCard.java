package com.github.cnkeep.interview.shopping;

import java.math.BigDecimal;
import java.util.List;

/**
 * @description:
 * @author: <a href="mailto:zhangleili@lizhi.fm">LeiLi.Zhang</a>
 * @date: 2021/4/14
 * @version:
 **/
public class ShopCard {
    private Long userId;
    private List<Product> products;
    private BigDecimal totalBalance;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(BigDecimal totalBalance) {
        this.totalBalance = totalBalance;
    }

    public static void main(String[] args) {

    }
}
