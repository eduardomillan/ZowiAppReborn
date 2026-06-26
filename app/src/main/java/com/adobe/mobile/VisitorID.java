package com.adobe.mobile;

/* JADX INFO: loaded from: classes.dex */
public class VisitorID {
    public VisitorIDAuthenticationState authenticationState;
    public final String id;
    public final String idOrigin;
    public final String idType;

    public enum VisitorIDAuthenticationState {
        VISITOR_ID_AUTHENTICATION_STATE_UNKNOWN(0),
        VISITOR_ID_AUTHENTICATION_STATE_AUTHENTICATED(1),
        VISITOR_ID_AUTHENTICATION_STATE_LOGGED_OUT(2);

        private final int value;

        VisitorIDAuthenticationState(int value) {
            this.value = value;
        }

        protected int getValue() {
            return this.value;
        }
    }

    protected VisitorID(String idOrigin, String idType, String id, VisitorIDAuthenticationState authenticationState) throws IllegalStateException {
        this.authenticationState = VisitorIDAuthenticationState.VISITOR_ID_AUTHENTICATION_STATE_UNKNOWN;
        String cleanIdType = StaticMethods.cleanContextDataKey(idType);
        if (cleanIdType == null || cleanIdType.length() == 0) {
            throw new IllegalStateException("idType must not be null/empty");
        }
        this.idOrigin = idOrigin;
        this.idType = cleanIdType;
        this.id = id;
        this.authenticationState = authenticationState;
    }

    protected boolean isVisitorID(String compareIDType, String compareID) {
        if (!this.idType.equals(compareIDType)) {
            return false;
        }
        if (this.id == null) {
            return compareID == null;
        }
        return this.id.equals(compareID);
    }

    protected String serializeIdentifierKeyForAnalyticsID() {
        return this.idType + ".id";
    }

    protected String serializeAuthenticationKeyForAnalyticsID() {
        return this.idType + ".as";
    }
}
