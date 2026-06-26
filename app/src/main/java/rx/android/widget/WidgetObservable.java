package rx.android.widget;

import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.TextView;
import rx.Observable;
import rx.android.view.OnCheckedChangeEvent;

/* JADX INFO: loaded from: classes.dex */
public final class WidgetObservable {
    private WidgetObservable() {
        throw new AssertionError("No instances");
    }

    public static Observable<OnTextChangeEvent> text(TextView input) {
        return text(input, false);
    }

    public static Observable<OnTextChangeEvent> text(TextView input, boolean emitInitialValue) {
        return Observable.create(new OnSubscribeTextViewInput(input, emitInitialValue));
    }

    public static Observable<OnCheckedChangeEvent> input(CompoundButton button) {
        return input(button, false);
    }

    public static Observable<OnCheckedChangeEvent> input(CompoundButton button, boolean emitInitialValue) {
        return Observable.create(new OnSubscribeCompoundButtonInput(button, emitInitialValue));
    }

    public static Observable<OnItemClickEvent> itemClicks(AdapterView<?> adapterView) {
        return Observable.create(new OnSubscribeAdapterViewOnItemClick(adapterView));
    }

    public static Observable<OnListViewScrollEvent> listScrollEvents(AbsListView listView) {
        return Observable.create(new OnSubscribeListViewScroll(listView));
    }
}
