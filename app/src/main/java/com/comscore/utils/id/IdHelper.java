package com.comscore.utils.id;

import android.annotation.SuppressLint;
import android.content.Context;
import com.comscore.android.id.DeviceId;
import com.comscore.android.id.IdHelperAndroid;
import com.comscore.utils.Constants;
import com.comscore.utils.Storage;
import com.comscore.utils.Utils;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"NewApi"})
public class IdHelper {
    private String a;
    private String b;
    private Storage c;
    private Context d;
    private String e;
    private String f;
    private String g;
    private boolean h = false;
    private boolean i = false;
    private boolean j = false;
    private Boolean k;

    public IdHelper(Context context, Storage storage) {
        this.d = context;
        this.c = storage;
    }

    private void a() {
        DeviceId advertisingDeviceId = IdHelperAndroid.getAdvertisingDeviceId(this.d);
        if (advertisingDeviceId == null) {
            this.h = true;
            a(null, false);
        } else if (advertisingDeviceId.getCommonness() == 0 && advertisingDeviceId.getPersistency() == 0) {
            a(advertisingDeviceId.getId(), true);
        } else {
            a(advertisingDeviceId.getId(), false);
        }
    }

    private void a(String str, boolean z) {
        if (str == null) {
            this.a = null;
            this.g = null;
            return;
        }
        this.h = z;
        String str2 = this.c.get(Constants.MD5_RAW_CROSSPUBLISHER_ID_KEY);
        boolean z2 = this.g == null;
        this.g = Utils.md5(str);
        if (a(this.g)) {
            this.a = this.c.get(Constants.CROSS_PUBLISHER_ID_KEY);
            return;
        }
        if (this.h && str2 != null && !str2.isEmpty() && str2 != this.g) {
            this.i = true;
            this.j = z2;
        }
        this.a = b(str);
        this.c.set(Constants.CROSS_PUBLISHER_ID_KEY, this.a);
        this.c.set(Constants.MD5_RAW_CROSSPUBLISHER_ID_KEY, this.g);
    }

    private boolean a(String str) {
        String str2 = this.c.get(Constants.MD5_RAW_CROSSPUBLISHER_ID_KEY);
        return str2 != null && str2.equals(str);
    }

    private String b(String str) {
        if (str == null || str.length() <= 0) {
            return null;
        }
        try {
            return Utils.encrypt(str);
        } catch (Exception e) {
            return null;
        }
    }

    private void b() {
        if (this.c.has(Constants.VID_KEY).booleanValue()) {
            this.b = this.c.get(Constants.VID_KEY);
            return;
        }
        DeviceId deviceId = IdHelperAndroid.getDeviceId(this.d);
        String id = deviceId.getId();
        String str = "-cs" + deviceId.getSuffix();
        if (this.b == null) {
            this.b = Utils.md5(id + getPublisherSecret()) + str;
            this.c.set(Constants.VID_KEY, this.b);
        }
    }

    private boolean c() {
        boolean zIsAdvertisingIdEnabled = IdHelperAndroid.isAdvertisingIdEnabled(this.d);
        if (this.k == null) {
            this.k = Boolean.valueOf(zIsAdvertisingIdEnabled);
        } else if (this.k.booleanValue() != zIsAdvertisingIdEnabled) {
            a();
        }
        return zIsAdvertisingIdEnabled;
    }

    public void generateIds() {
        if (isPublisherSecretEmpty()) {
            return;
        }
        this.f = IdHelperAndroid.getDeviceId(this.d).getId();
        b();
        a();
    }

    public String getAndroidId() {
        return this.f;
    }

    public String getCrossPublisherId() {
        if (!this.h) {
            return this.a;
        }
        if (!c()) {
            return IdHelperAndroid.NO_ID_AVAILABLE;
        }
        a();
        return (!this.i || this.j) ? this.a : IdHelperAndroid.NO_ID_AVAILABLE;
    }

    public String getMD5AdvertisingId() {
        if (this.h && c()) {
            return this.g;
        }
        return null;
    }

    public String getPublisherSecret() {
        if (this.e == null) {
            this.e = "";
        }
        return this.e;
    }

    public String getVisitorId() {
        return this.b;
    }

    public boolean isIdChanged() {
        return this.i;
    }

    public boolean isPublisherSecretEmpty() {
        return this.e == null || this.e.length() == 0;
    }

    public void setPublisherSecret(String str) {
        this.e = str;
    }
}
