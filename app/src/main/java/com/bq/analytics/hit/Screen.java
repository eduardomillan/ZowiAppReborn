package com.bq.analytics.hit;

import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: classes.dex */
public class Screen {

    @Nullable
    public final Map<Integer, String> customDimensions;

    @NotNull
    public final String screen;

    public Screen(@NotNull String screen) {
        this(screen, null);
    }

    public Screen(@NotNull String screen, @Nullable Map<Integer, String> customDimensions) {
        this.screen = screen;
        this.customDimensions = customDimensions;
    }

    public static final class Builder {
        private Map<Integer, String> customDimensions;
        private String screen;

        public Builder screen(@NotNull String category) {
            this.screen = category;
            return this;
        }

        public Builder addCustomDimension(@NotNull Integer index, @NotNull String dimension) {
            if (this.customDimensions == null) {
                this.customDimensions = new HashMap();
            }
            this.customDimensions.put(index, dimension);
            return this;
        }

        @NotNull
        public Screen build() {
            return new Screen(this.screen, this.customDimensions);
        }
    }
}
