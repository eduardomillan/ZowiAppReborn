package com.bq.analytics.hit;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: classes.dex */
public class UserTiming {

    @NotNull
    public final String category;

    @Nullable
    public final String label;

    @Nullable
    public final String name;

    @NotNull
    public final Long value;

    public UserTiming(@NotNull String category, @NotNull Long value, @Nullable String name, @Nullable String label) {
        this.category = category;
        this.value = value;
        this.name = name;
        this.label = label;
    }

    public static final class Builder {
        private String category;
        private String label;
        private String name;
        private Long value;

        public Builder category(@NotNull String category) {
            this.category = category;
            return this;
        }

        public Builder value(@NotNull Long value) {
            this.value = value;
            return this;
        }

        public Builder name(@Nullable String name) {
            this.name = name;
            return this;
        }

        public Builder label(@Nullable String label) {
            this.label = label;
            return this;
        }

        @NotNull
        public UserTiming build() {
            return new UserTiming(this.category, this.value, this.name, this.label);
        }
    }
}
