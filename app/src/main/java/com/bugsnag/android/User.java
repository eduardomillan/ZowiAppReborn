package com.bugsnag.android;

import com.bugsnag.android.JsonStream;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
final class User implements JsonStream.Streamable {
    private String email;
    private String id;
    private String name;

    User() {
    }

    User(String id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    User(User u) {
        this(u.id, u.email, u.name);
    }

    @Override // com.bugsnag.android.JsonStream.Streamable
    public final void toStream(JsonStream writer) throws IOException {
        writer.beginObject();
        if (this.id != null) {
            writer.name("id").value(this.id);
        }
        if (this.email != null) {
            writer.name("email").value(this.email);
        }
        if (this.name != null) {
            writer.name("name").value(this.name);
        }
        writer.endObject();
    }

    public final void setId(String id) {
        this.id = id;
    }

    public final void setEmail(String email) {
        this.email = email;
    }

    public final void setName(String name) {
        this.name = name;
    }
}
