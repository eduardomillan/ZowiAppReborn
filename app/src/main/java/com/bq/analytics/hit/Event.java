package com.bq.analytics.hit;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: classes.dex */
public class Event {

    @NotNull
    public final String action;

    @NotNull
    public final String category;

    @Nullable
    public final String label;

    @Nullable
    public final Long value;

    public Event(@NotNull String category, @NotNull String action, @Nullable String label, @Nullable Long value) {
        this.category = category;
        this.action = action;
        this.label = label;
        this.value = value;
    }

    public static final class Builder {
        private String action;
        private String category;
        private String label;
        private Long value;

        public Builder category(@NotNull String category) {
            this.category = category;
            return this;
        }

        public Builder action(@NotNull String action) {
            this.action = action;
            return this;
        }

        public Builder label(@Nullable String label) {
            this.label = label;
            return this;
        }

        public Builder value(@Nullable Long value) {
            this.value = value;
            return this;
        }

        @NotNull
        public Event build() {
            return new Event(this.category, this.action, this.label, this.value);
        }
    }
}
