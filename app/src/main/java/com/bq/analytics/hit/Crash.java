package com.bq.analytics.hit;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: classes.dex */
public class Crash {

    @Nullable
    public final String description;

    @NotNull
    public final Boolean isFatal;

    public Crash(@Nullable String description, @NotNull Boolean isFatal) {
        this.description = description;
        this.isFatal = isFatal;
    }

    public static final class Builder {
        private String description;
        private Boolean isFatal;

        public Builder description(@Nullable String description) {
            this.description = description;
            return this;
        }

        public Builder fatal(@NotNull Boolean isFatal) {
            this.isFatal = isFatal;
            return this;
        }

        @NotNull
        public Crash build() {
            return new Crash(this.description, this.isFatal);
        }
    }
}
