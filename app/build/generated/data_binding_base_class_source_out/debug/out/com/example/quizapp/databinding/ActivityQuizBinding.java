// Generated by view binder compiler. Do not edit!
package com.example.quizapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.quizapp.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityQuizBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final AppBarLayout appBarLayout;

  @NonNull
  public final MaterialButton btnSubmit;

  @NonNull
  public final MaterialCardView cardOption1;

  @NonNull
  public final MaterialCardView cardOption2;

  @NonNull
  public final MaterialCardView cardOption3;

  @NonNull
  public final MaterialCardView cardOption4;

  @NonNull
  public final CardView cardQuestion;

  @NonNull
  public final LinearLayout optionsContainer;

  @NonNull
  public final LinearProgressIndicator progressBar;

  @NonNull
  public final RadioButton rbOption1;

  @NonNull
  public final RadioButton rbOption2;

  @NonNull
  public final RadioButton rbOption3;

  @NonNull
  public final RadioButton rbOption4;

  @NonNull
  public final MaterialToolbar toolbar;

  @NonNull
  public final TextView tvQuestion;

  @NonNull
  public final TextView tvQuestionCounter;

  @NonNull
  public final TextView tvTimer;

  private ActivityQuizBinding(@NonNull ConstraintLayout rootView,
      @NonNull AppBarLayout appBarLayout, @NonNull MaterialButton btnSubmit,
      @NonNull MaterialCardView cardOption1, @NonNull MaterialCardView cardOption2,
      @NonNull MaterialCardView cardOption3, @NonNull MaterialCardView cardOption4,
      @NonNull CardView cardQuestion, @NonNull LinearLayout optionsContainer,
      @NonNull LinearProgressIndicator progressBar, @NonNull RadioButton rbOption1,
      @NonNull RadioButton rbOption2, @NonNull RadioButton rbOption3,
      @NonNull RadioButton rbOption4, @NonNull MaterialToolbar toolbar,
      @NonNull TextView tvQuestion, @NonNull TextView tvQuestionCounter,
      @NonNull TextView tvTimer) {
    this.rootView = rootView;
    this.appBarLayout = appBarLayout;
    this.btnSubmit = btnSubmit;
    this.cardOption1 = cardOption1;
    this.cardOption2 = cardOption2;
    this.cardOption3 = cardOption3;
    this.cardOption4 = cardOption4;
    this.cardQuestion = cardQuestion;
    this.optionsContainer = optionsContainer;
    this.progressBar = progressBar;
    this.rbOption1 = rbOption1;
    this.rbOption2 = rbOption2;
    this.rbOption3 = rbOption3;
    this.rbOption4 = rbOption4;
    this.toolbar = toolbar;
    this.tvQuestion = tvQuestion;
    this.tvQuestionCounter = tvQuestionCounter;
    this.tvTimer = tvTimer;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityQuizBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityQuizBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_quiz, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityQuizBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.appBarLayout;
      AppBarLayout appBarLayout = ViewBindings.findChildViewById(rootView, id);
      if (appBarLayout == null) {
        break missingId;
      }

      id = R.id.btnSubmit;
      MaterialButton btnSubmit = ViewBindings.findChildViewById(rootView, id);
      if (btnSubmit == null) {
        break missingId;
      }

      id = R.id.cardOption1;
      MaterialCardView cardOption1 = ViewBindings.findChildViewById(rootView, id);
      if (cardOption1 == null) {
        break missingId;
      }

      id = R.id.cardOption2;
      MaterialCardView cardOption2 = ViewBindings.findChildViewById(rootView, id);
      if (cardOption2 == null) {
        break missingId;
      }

      id = R.id.cardOption3;
      MaterialCardView cardOption3 = ViewBindings.findChildViewById(rootView, id);
      if (cardOption3 == null) {
        break missingId;
      }

      id = R.id.cardOption4;
      MaterialCardView cardOption4 = ViewBindings.findChildViewById(rootView, id);
      if (cardOption4 == null) {
        break missingId;
      }

      id = R.id.cardQuestion;
      CardView cardQuestion = ViewBindings.findChildViewById(rootView, id);
      if (cardQuestion == null) {
        break missingId;
      }

      id = R.id.optionsContainer;
      LinearLayout optionsContainer = ViewBindings.findChildViewById(rootView, id);
      if (optionsContainer == null) {
        break missingId;
      }

      id = R.id.progressBar;
      LinearProgressIndicator progressBar = ViewBindings.findChildViewById(rootView, id);
      if (progressBar == null) {
        break missingId;
      }

      id = R.id.rbOption1;
      RadioButton rbOption1 = ViewBindings.findChildViewById(rootView, id);
      if (rbOption1 == null) {
        break missingId;
      }

      id = R.id.rbOption2;
      RadioButton rbOption2 = ViewBindings.findChildViewById(rootView, id);
      if (rbOption2 == null) {
        break missingId;
      }

      id = R.id.rbOption3;
      RadioButton rbOption3 = ViewBindings.findChildViewById(rootView, id);
      if (rbOption3 == null) {
        break missingId;
      }

      id = R.id.rbOption4;
      RadioButton rbOption4 = ViewBindings.findChildViewById(rootView, id);
      if (rbOption4 == null) {
        break missingId;
      }

      id = R.id.toolbar;
      MaterialToolbar toolbar = ViewBindings.findChildViewById(rootView, id);
      if (toolbar == null) {
        break missingId;
      }

      id = R.id.tvQuestion;
      TextView tvQuestion = ViewBindings.findChildViewById(rootView, id);
      if (tvQuestion == null) {
        break missingId;
      }

      id = R.id.tvQuestionCounter;
      TextView tvQuestionCounter = ViewBindings.findChildViewById(rootView, id);
      if (tvQuestionCounter == null) {
        break missingId;
      }

      id = R.id.tvTimer;
      TextView tvTimer = ViewBindings.findChildViewById(rootView, id);
      if (tvTimer == null) {
        break missingId;
      }

      return new ActivityQuizBinding((ConstraintLayout) rootView, appBarLayout, btnSubmit,
          cardOption1, cardOption2, cardOption3, cardOption4, cardQuestion, optionsContainer,
          progressBar, rbOption1, rbOption2, rbOption3, rbOption4, toolbar, tvQuestion,
          tvQuestionCounter, tvTimer);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
