// Generated by view binder compiler. Do not edit!
package com.example.logisticaliot.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.logisticaliot.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityDataBinding implements ViewBinding {
  @NonNull
  private final ScrollView rootView;

  @NonNull
  public final Button ControlBtn;

  @NonNull
  public final Button DataBtn;

  @NonNull
  public final TextView DoamView;

  @NonNull
  public final Button HistoryBtn;

  @NonNull
  public final Button MapBtn;

  @NonNull
  public final TextView NhietdoView;

  @NonNull
  public final Button ReloadBtn;

  @NonNull
  public final TextView TTTV5;

  @NonNull
  public final TextView TTTV6;

  @NonNull
  public final TextView ThongtinView;

  private ActivityDataBinding(@NonNull ScrollView rootView, @NonNull Button ControlBtn,
      @NonNull Button DataBtn, @NonNull TextView DoamView, @NonNull Button HistoryBtn,
      @NonNull Button MapBtn, @NonNull TextView NhietdoView, @NonNull Button ReloadBtn,
      @NonNull TextView TTTV5, @NonNull TextView TTTV6, @NonNull TextView ThongtinView) {
    this.rootView = rootView;
    this.ControlBtn = ControlBtn;
    this.DataBtn = DataBtn;
    this.DoamView = DoamView;
    this.HistoryBtn = HistoryBtn;
    this.MapBtn = MapBtn;
    this.NhietdoView = NhietdoView;
    this.ReloadBtn = ReloadBtn;
    this.TTTV5 = TTTV5;
    this.TTTV6 = TTTV6;
    this.ThongtinView = ThongtinView;
  }

  @Override
  @NonNull
  public ScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityDataBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityDataBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_data, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityDataBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.Control_Btn;
      Button ControlBtn = ViewBindings.findChildViewById(rootView, id);
      if (ControlBtn == null) {
        break missingId;
      }

      id = R.id.Data_Btn;
      Button DataBtn = ViewBindings.findChildViewById(rootView, id);
      if (DataBtn == null) {
        break missingId;
      }

      id = R.id.DoamView;
      TextView DoamView = ViewBindings.findChildViewById(rootView, id);
      if (DoamView == null) {
        break missingId;
      }

      id = R.id.History_Btn;
      Button HistoryBtn = ViewBindings.findChildViewById(rootView, id);
      if (HistoryBtn == null) {
        break missingId;
      }

      id = R.id.Map_Btn;
      Button MapBtn = ViewBindings.findChildViewById(rootView, id);
      if (MapBtn == null) {
        break missingId;
      }

      id = R.id.NhietdoView;
      TextView NhietdoView = ViewBindings.findChildViewById(rootView, id);
      if (NhietdoView == null) {
        break missingId;
      }

      id = R.id.Reload_Btn;
      Button ReloadBtn = ViewBindings.findChildViewById(rootView, id);
      if (ReloadBtn == null) {
        break missingId;
      }

      id = R.id.TT_TV5;
      TextView TTTV5 = ViewBindings.findChildViewById(rootView, id);
      if (TTTV5 == null) {
        break missingId;
      }

      id = R.id.TT_TV6;
      TextView TTTV6 = ViewBindings.findChildViewById(rootView, id);
      if (TTTV6 == null) {
        break missingId;
      }

      id = R.id.ThongtinView;
      TextView ThongtinView = ViewBindings.findChildViewById(rootView, id);
      if (ThongtinView == null) {
        break missingId;
      }

      return new ActivityDataBinding((ScrollView) rootView, ControlBtn, DataBtn, DoamView,
          HistoryBtn, MapBtn, NhietdoView, ReloadBtn, TTTV5, TTTV6, ThongtinView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
