package com.bq.zowi.presenters;

import org.jetbrains.annotations.NotNull;
import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subscriptions.CompositeSubscription;

/* JADX INFO: loaded from: classes.dex */
public abstract class BasePresenterImpl<V, W> implements BasePresenter<V, W> {
    protected CompositeSubscription subscriptions;
    private V view;
    private final PublishSubject<Void> viewBoundSubject = PublishSubject.create();
    private final PublishSubject<Void> viewUnBoundSubject = PublishSubject.create();
    private W wireframe;

    public Observable<Void> getViewBoundObservable() {
        return this.viewBoundSubject.asObservable();
    }

    public Observable<Void> getViewUnboundObservable() {
        return this.viewUnBoundSubject.asObservable();
    }

    @Override // com.bq.zowi.presenters.BasePresenter
    public void onCreateView() {
        this.subscriptions = new CompositeSubscription();
    }

    @Override // com.bq.zowi.presenters.BasePresenter
    public void onDestroyView() throws Throwable {
        if (this.subscriptions != null && !this.subscriptions.isUnsubscribed()) {
            this.subscriptions.unsubscribe();
        }
    }

    @Override // com.bq.zowi.presenters.BasePresenter
    public void bindViewAndWireframe(V view, W wireframe) {
        this.view = view;
        this.wireframe = wireframe;
        this.viewBoundSubject.onNext(null);
    }

    @Override // com.bq.zowi.presenters.BasePresenter
    public void unBindViewAndWireframe() {
        this.view = null;
        this.wireframe = null;
        this.viewUnBoundSubject.onNext(null);
    }

    @Override // com.bq.zowi.presenters.BasePresenter
    @NotNull
    public V getView() {
        if (this.view == null) {
            throw new NullPointerException("View is not ready yet");
        }
        return this.view;
    }

    @Override // com.bq.zowi.presenters.BasePresenter
    @NotNull
    public W getWireframe() {
        if (this.wireframe == null) {
            throw new NullPointerException("Wireframe is not ready yet");
        }
        return this.wireframe;
    }
}
