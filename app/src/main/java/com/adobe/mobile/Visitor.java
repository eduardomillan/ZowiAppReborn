package com.adobe.mobile;

import com.adobe.mobile.VisitorID;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class Visitor {
    public static String getMarketingCloudId() {
        return VisitorIDService.sharedInstance().getMarketingCloudID();
    }

    public static void syncIdentifier(String identifierType, String identifier, VisitorID.VisitorIDAuthenticationState authenticationState) {
        if (identifierType == null || identifierType.length() == 0) {
            StaticMethods.logWarningFormat("ID Service - Unable to sync VisitorID with id:%s, idType was nil/empty.", identifier);
            return;
        }
        HashMap<String, String> identifiers = new HashMap<>();
        identifiers.put(identifierType, identifier);
        VisitorIDService.sharedInstance().idSync(identifiers, authenticationState);
    }

    public static void syncIdentifiers(Map<String, String> identifiers) {
        VisitorIDService.sharedInstance().idSync(identifiers);
    }

    public static void syncIdentifiers(Map<String, String> identifiers, VisitorID.VisitorIDAuthenticationState authenticationState) {
        VisitorIDService.sharedInstance().idSync(identifiers, authenticationState);
    }

    public static List<VisitorID> getIdentifiers() {
        return VisitorIDService.sharedInstance().getIdentifiers();
    }
}
