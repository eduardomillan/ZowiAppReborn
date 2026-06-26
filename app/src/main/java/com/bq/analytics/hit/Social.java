package com.bq.analytics.hit;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: classes.dex */
public class Social {

    @NotNull
    public final String action;

    @NotNull
    public final String network;

    @Nullable
    public final String target;

    public Social(@NotNull String network, @NotNull String action, @Nullable String target) {
        this.network = network;
        this.action = action;
        this.target = target;
    }

    public static final class Builder {
        private String action;
        private String network;
        private String target;

        public Builder network(@NotNull String category) {
            this.network = category;
            return this;
        }

        public Builder action(@NotNull String action) {
            this.action = action;
            return this;
        }

        public Builder target(@Nullable String label) {
            this.target = label;
            return this;
        }

        @NotNull
        public Social build() {
            return new Social(this.network, this.action, this.target);
        }
    }
}
