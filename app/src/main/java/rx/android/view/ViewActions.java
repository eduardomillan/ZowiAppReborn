package rx.android.view;

import android.view.View;
import android.widget.TextView;
import com.google.android.gms.analytics.ecommerce.Promotion;
import rx.android.internal.Preconditions;
import rx.functions.Action1;

/* JADX INFO: loaded from: classes.dex */
public final class ViewActions {
    private ViewActions() {
        throw new IllegalStateException("No instances!");
    }

    public static Action1<? super Boolean> setEnabled(View view) {
        Preconditions.checkNotNull(view, Promotion.ACTION_VIEW);
        return new ViewAction1<View, Boolean>(view) { // from class: rx.android.view.ViewActions.1
            @Override // rx.android.view.ViewAction1
            public void call(View view2, Boolean enabled) {
                view2.setEnabled(enabled.booleanValue());
            }
        };
    }

    public static Action1<? super Boolean> setActivated(View view) {
        Preconditions.checkNotNull(view, Promotion.ACTION_VIEW);
        return new ViewAction1<View, Boolean>(view) { // from class: rx.android.view.ViewActions.2
            @Override // rx.android.view.ViewAction1
            public void call(View view2, Boolean activated) {
                view2.setActivated(activated.booleanValue());
            }
        };
    }

    public static Action1<? super Boolean> setClickable(View view) {
        Preconditions.checkNotNull(view, Promotion.ACTION_VIEW);
        return new ViewAction1<View, Boolean>(view) { // from class: rx.android.view.ViewActions.3
            @Override // rx.android.view.ViewAction1
            public void call(View view2, Boolean clickable) {
                view2.setClickable(clickable.booleanValue());
            }
        };
    }

    public static Action1<? super Boolean> setFocusable(View view) {
        Preconditions.checkNotNull(view, Promotion.ACTION_VIEW);
        return new ViewAction1<View, Boolean>(view) { // from class: rx.android.view.ViewActions.4
            @Override // rx.android.view.ViewAction1
            public void call(View view2, Boolean focusable) {
                view2.setFocusable(focusable.booleanValue());
            }
        };
    }

    public static Action1<? super Boolean> setSelected(View view) {
        Preconditions.checkNotNull(view, Promotion.ACTION_VIEW);
        return new ViewAction1<View, Boolean>(view) { // from class: rx.android.view.ViewActions.5
            @Override // rx.android.view.ViewAction1
            public void call(View view2, Boolean selected) {
                view2.setSelected(selected.booleanValue());
            }
        };
    }

    public static Action1<? super Boolean> setVisibility(View view) {
        return setVisibility(view, 8);
    }

    public static Action1<? super Boolean> setVisibility(View view, final int visibilityOnFalse) {
        Preconditions.checkNotNull(view, Promotion.ACTION_VIEW);
        Preconditions.checkArgument(visibilityOnFalse != 0, "Binding false to VISIBLE has no effect and is thus disallowed.");
        if (visibilityOnFalse != 4 && visibilityOnFalse != 8) {
            throw new IllegalArgumentException(visibilityOnFalse + " is not a valid visibility value.");
        }
        return new ViewAction1<View, Boolean>(view) { // from class: rx.android.view.ViewActions.6
            @Override // rx.android.view.ViewAction1
            public void call(View view2, Boolean value) {
                int visibility = value.booleanValue() ? 0 : visibilityOnFalse;
                view2.setVisibility(visibility);
            }
        };
    }

    public static Action1<? super CharSequence> setText(TextView textView) {
        Preconditions.checkNotNull(textView, "textView");
        return new ViewAction1<TextView, CharSequence>(textView) { // from class: rx.android.view.ViewActions.7
            @Override // rx.android.view.ViewAction1
            public void call(TextView view, CharSequence text) {
                view.setText(text);
            }
        };
    }

    public static Action1<? super Integer> setTextResource(TextView textView) {
        Preconditions.checkNotNull(textView, "textView");
        return new ViewAction1<TextView, Integer>(textView) { // from class: rx.android.view.ViewActions.8
            @Override // rx.android.view.ViewAction1
            public void call(TextView view, Integer resId) {
                view.setText(resId.intValue());
            }
        };
    }
}
