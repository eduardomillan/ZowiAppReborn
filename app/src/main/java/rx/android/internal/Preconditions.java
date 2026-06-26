package rx.android.internal;

/* JADX INFO: loaded from: classes.dex */
public final class Preconditions {
    private Preconditions() {
        throw new AssertionError("No instances");
    }

    public static <T> T checkNotNull(T value, String message) {
        if (value == null) {
            throw new NullPointerException(message);
        }
        return value;
    }

    public static void checkArgument(boolean check, String message) {
        if (!check) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void checkState(boolean check, String message) {
        if (!check) {
            throw new IllegalStateException(message);
        }
    }
}
