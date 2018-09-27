package com.airbnb.epoxy;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * A version of {@link com.airbnb.epoxy.EpoxyModel} that allows you to use a view holder pattern
 * instead of a specific view when binding to your model.
 */
public abstract class EpoxyModelWithHolder<T extends EpoxyHolder> extends EpoxyModel<T> {

  public EpoxyModelWithHolder() {
  }

  public EpoxyModelWithHolder(long id) {
    super(id);
  }

  /** This should return a new instance of your {@link com.airbnb.epoxy.EpoxyHolder} class. */
  protected abstract T createNewHolder();

  @Override
  public void bind(@NonNull T holder) {
    super.bind(holder);
  }

  @Override
  public void bind(@NonNull T holder, @NonNull List<Object> payloads) {
    super.bind(holder, payloads);
  }

  @Override
  public void bind(@NonNull T holder, @NonNull EpoxyModel<?> previouslyBoundModel) {
    super.bind(holder, previouslyBoundModel);
  }

  @Override
  public void unbind(@NonNull T holder) {
    super.unbind(holder);
  }

  @Override
  public void visibilityFocusedVisible(@NonNull T view) {
    super.visibilityFocusedVisible(view);
  }

  @Override
  public void visibilityFullImpressionVisible(@NonNull T view) {
    super.visibilityFullImpressionVisible(view);
  }

  @Override
  public void visibilityInvisible(@NonNull T view) {
    super.visibilityInvisible(view);
  }

  @Override
  public void visibilityUnfocusedVisible(@NonNull T view) {
    super.visibilityUnfocusedVisible(view);
  }

  @Override
  public void visibilityVisible(@NonNull T view) {
    super.visibilityVisible(view);
  }

  @Override
  public void visibilityChanged(float visibleHeight, float visibleWidth, int percentVisibleHeight,
      int percentVisibleWidth, @NonNull T view) {
    super.visibilityChanged(visibleHeight, visibleWidth, percentVisibleHeight, percentVisibleWidth,
        view);
  }

  @Override
  public boolean onFailedToRecycleView(T holder) {
    return super.onFailedToRecycleView(holder);
  }

  @Override
  public void onViewAttachedToWindow(T holder) {
    super.onViewAttachedToWindow(holder);
  }

  @Override
  public void onViewDetachedFromWindow(T holder) {
    super.onViewDetachedFromWindow(holder);
  }
}
