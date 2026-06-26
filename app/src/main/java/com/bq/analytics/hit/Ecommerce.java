package com.bq.analytics.hit;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: classes.dex */
public class Ecommerce {

    @Nullable
    public final String itemCategory;

    @NotNull
    public final String itemName;

    @NotNull
    public final Double itemPrice;

    @NotNull
    public final Long itemQuantity;

    @NotNull
    public final String itemSku;

    @NotNull
    public final String transactionAffiliation;

    @Nullable
    public final String transactionCurrencyCode;

    @NotNull
    public final String transactionId;

    @NotNull
    public final Double transactionRevenue;

    @NotNull
    public final Double transactionShipping;

    @NotNull
    public final Double transactionTax;

    public Ecommerce(@NotNull String transactionId, @NotNull String transactionAffiliation, @NotNull Double transactionRevenue, @NotNull Double transactionTax, @NotNull Double transactionShipping, @Nullable String transactionCurrencyCode, @NotNull String itemName, @NotNull String itemSku, @Nullable String itemCategory, @NotNull Double itemPrice, @NotNull Long itemQuantity) {
        this.transactionId = transactionId;
        this.transactionAffiliation = transactionAffiliation;
        this.transactionRevenue = transactionRevenue;
        this.transactionTax = transactionTax;
        this.transactionShipping = transactionShipping;
        this.transactionCurrencyCode = transactionCurrencyCode;
        this.itemName = itemName;
        this.itemSku = itemSku;
        this.itemCategory = itemCategory;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
    }

    public static final class Builder {
        private String itemCategory;
        private String itemName;
        private double itemPrice;
        private long itemQuantity;
        private String itemSku;
        private String transactionAffiliation;
        private String transactionCurrencyCode;
        private String transactionId;
        private double transactionRevenue;
        private double transactionShipping;
        private double transactionTax;

        public Builder transactionId(@NotNull String transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public Builder transactionAffiliation(@NotNull String transactionAffiliation) {
            this.transactionAffiliation = transactionAffiliation;
            return this;
        }

        public Builder transactionRevenue(@NotNull Double transactionRevenue) {
            this.transactionRevenue = transactionRevenue.doubleValue();
            return this;
        }

        public Builder transactionTax(@NotNull Double transactionTax) {
            this.transactionTax = transactionTax.doubleValue();
            return this;
        }

        public Builder transactionShipping(@NotNull Double transactionShipping) {
            this.transactionShipping = transactionShipping.doubleValue();
            return this;
        }

        public Builder transactionCurrencyCode(@Nullable String transactionCurrencyCode) {
            this.transactionCurrencyCode = transactionCurrencyCode;
            return this;
        }

        public Builder itemName(@NotNull String itemName) {
            this.itemName = itemName;
            return this;
        }

        public Builder itemSku(@NotNull String itemSku) {
            this.itemSku = itemSku;
            return this;
        }

        public Builder itemCategory(@Nullable String itemCategory) {
            this.itemCategory = itemCategory;
            return this;
        }

        public Builder itemPrice(@NotNull Double itemPrice) {
            this.itemPrice = itemPrice.doubleValue();
            return this;
        }

        public Builder itemQuantity(@NotNull Long itemQuantity) {
            this.itemQuantity = itemQuantity.longValue();
            return this;
        }

        @NotNull
        public Ecommerce build() {
            return new Ecommerce(this.transactionId, this.transactionAffiliation, Double.valueOf(this.transactionRevenue), Double.valueOf(this.transactionTax), Double.valueOf(this.transactionShipping), this.transactionCurrencyCode, this.itemName, this.itemSku, this.itemCategory, Double.valueOf(this.itemPrice), Long.valueOf(this.itemQuantity));
        }
    }
}
