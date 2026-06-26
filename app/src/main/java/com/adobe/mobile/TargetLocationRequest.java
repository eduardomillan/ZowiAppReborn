package com.adobe.mobile;

import com.comscore.android.id.IdHelperAndroid;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class TargetLocationRequest {
    public static final String TARGET_PARAMETER_CATEGORY_ID = "categoryId";
    public static final String TARGET_PARAMETER_MBOX_3RDPARTY_ID = "mbox3rdPartyId";
    public static final String TARGET_PARAMETER_MBOX_HOST = "mboxHost";
    public static final String TARGET_PARAMETER_MBOX_PAGE_VALUE = "mboxPageValue";
    public static final String TARGET_PARAMETER_MBOX_PC = "mboxPC";
    public static final String TARGET_PARAMETER_MBOX_SESSION_ID = "mboxSession";
    public static final String TARGET_PARAMETER_ORDER_ID = "orderId";
    public static final String TARGET_PARAMETER_ORDER_TOTAL = "orderTotal";
    public static final String TARGET_PARAMETER_PRODUCT_PURCHASE_ID = "productPurchasedId";
    public String defaultContent;
    public String name;
    public HashMap<String, Object> parameters;

    protected TargetLocationRequest(String name, String defaultContent, Map<String, Object> parameters) {
        this.name = name;
        this.defaultContent = defaultContent;
        this.parameters = parameters != null ? new HashMap<>(parameters) : new HashMap<>();
    }

    protected static TargetLocationRequest createRequestWithOrderConfirm(String name, String orderID, String orderTotal, String productPurchaseID, Map<String, Object> params) {
        TargetLocationRequest me = new TargetLocationRequest(name, IdHelperAndroid.NO_ID_AVAILABLE, params);
        if (orderID != null) {
            me.parameters.put(TARGET_PARAMETER_ORDER_ID, orderID);
        }
        if (orderTotal != null) {
            me.parameters.put(TARGET_PARAMETER_ORDER_TOTAL, orderTotal);
        }
        if (productPurchaseID != null) {
            me.parameters.put(TARGET_PARAMETER_PRODUCT_PURCHASE_ID, productPurchaseID);
        }
        return me;
    }
}
