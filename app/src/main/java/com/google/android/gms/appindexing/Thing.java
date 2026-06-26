package com.google.android.gms.appindexing;

import android.net.Uri;
import android.os.Bundle;
import com.google.android.gms.common.internal.zzx;

/* JADX INFO: loaded from: classes.dex */
public class Thing {
    final Bundle zzRi;

    public static class Builder {
        final Bundle zzRj = new Bundle();

        public Thing build() {
            return new Thing(this.zzRj);
        }

        public Builder put(String key, Thing value) {
            zzx.zzw(key);
            if (value != null) {
                this.zzRj.putParcelable(key, value.zzRi);
            }
            return this;
        }

        public Builder put(String key, String value) {
            zzx.zzw(key);
            if (value != null) {
                this.zzRj.putString(key, value);
            }
            return this;
        }

        public Builder setDescription(String description) {
            put("description", description);
            return this;
        }

        public Builder setId(String id) {
            if (id != null) {
                put("id", id);
            }
            return this;
        }

        public Builder setName(String name) {
            zzx.zzw(name);
            put("name", name);
            return this;
        }

        public Builder setType(String type) {
            put("type", type);
            return this;
        }

        public Builder setUrl(Uri url) {
            zzx.zzw(url);
            put("url", url.toString());
            return this;
        }
    }

    Thing(Bundle propertyBundle) {
        this.zzRi = propertyBundle;
    }

    public Bundle zzlx() {
        return this.zzRi;
    }
}
