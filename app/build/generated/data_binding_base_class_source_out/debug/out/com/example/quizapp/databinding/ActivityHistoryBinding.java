// Generated by view binder compiler. Do not edit!
package com.example.quizapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.quizapp.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityHistoryBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final AppBarLayout appBarLayout;

  @NonNull
  public final RecyclerView recyclerViewHistory;

  @NonNull
  public final MaterialToolbar toolbar;

  @NonNull
  public final TextView tvEmptyState;

  @NonNull
  public final TextView tvHistoryHeader;

  private ActivityHistoryBinding(@NonNull ConstraintLayout rootView,
      @NonNull AppBarLayout appBarLayout, @NonNull RecyclerView recyclerViewHistory,
      @NonNull MaterialToolbar toolbar, @NonNull TextView tvEmptyState,
      @NonNull TextView tvHistoryHeader) {
    this.rootView = rootView;
    this.appBarLayout = appBarLayout;
    this.recyclerViewHistory = recyclerViewHistory;
    this.toolbar = toolbar;
    this.tvEmptyState = tvEmptyState;
    this.tvHistoryHeader = tvHistoryHeader;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityHistoryBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityHistoryBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_history, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityHistoryBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.appBarLayout;
      AppBarLayout appBarLayout = ViewBindings.findChildViewById(rootView, id);
      if (appBarLayout == null) {
        break missingId;
      }

      id = R.id.recyclerViewHistory;
      RecyclerView recyclerViewHistory = ViewBindings.findChildViewById(rootView, id);
      if (recyclerViewHistory == null) {
        break missingId;
      }

      id = R.id.toolbar;
      MaterialToolbar toolbar = ViewBindings.findChildViewById(rootView, id);
      if (toolbar == null) {
        break missingId;
      }

      id = R.id.tvEmptyState;
      TextView tvEmptyState = ViewBindings.findChildViewById(rootView, id);
      if (tvEmptyState == null) {
        break missingId;
      }

      id = R.id.tvHistoryHeader;
      TextView tvHistoryHeader = ViewBindings.findChildViewById(rootView, id);
      if (tvHistoryHeader == null) {
        break missingId;
      }

      return new ActivityHistoryBinding((ConstraintLayout) rootView, appBarLayout,
          recyclerViewHistory, toolbar, tvEmptyState, tvHistoryHeader);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
