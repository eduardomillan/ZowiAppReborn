package com.bq.zowi.presenters;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;
import org.jetbrains.annotations.NotNull;

public abstract class BasePresenterImpl<V, W> implements BasePresenter<V, W> {
    protected CompositeDisposable disposables;
    private V view;
    private final PublishSubject<Void> viewBoundSubject = PublishSubject.create();
    private final PublishSubject<Void> viewUnBoundSubject = PublishSubject.create();
    private W wireframe;

    public Observable<Void> getViewBoundObservable() {
        return this.viewBoundSubject;
    }

    public Observable<Void> getViewUnboundObservable() {
        return this.viewUnBoundSubject;
    }

    @Override
    public void onCreateView() {
        this.disposables = new CompositeDisposable();
    }

    @Override
    public void onDestroyView() {
        if (this.disposables != null && !this.disposables.isDisposed()) {
            this.disposables.dispose();
        }
    }

    @Override
    public void bindViewAndWireframe(V view, W wireframe) {
        this.view = view;
        this.wireframe = wireframe;
        this.viewBoundSubject.onNext(null);
    }

    @Override
    public void unBindViewAndWireframe() {
        this.view = null;
        this.wireframe = null;
        this.viewUnBoundSubject.onNext(null);
    }

    @Override
    @NotNull
    public V getView() {
        if (this.view == null) {
            throw new NullPointerException("View is not ready yet");
        }
        return this.view;
    }

    @Override
    @NotNull
    public W getWireframe() {
        if (this.wireframe == null) {
            throw new NullPointerException("Wireframe is not ready yet");
        }
        return this.wireframe;
    }
}
