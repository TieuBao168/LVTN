// Generated by view binder compiler. Do not edit!
package com.example.logisticaliot.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.logisticaliot.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityHistoryListBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final TextView Doam;

  @NonNull
  public final ListView LvThongtin;

  @NonNull
  public final TextView Nhietdo;

  @NonNull
  public final TextView Thoigian;

  @NonNull
  public final Button btnBack;

  private ActivityHistoryListBinding(@NonNull RelativeLayout rootView, @NonNull TextView Doam,
      @NonNull ListView LvThongtin, @NonNull TextView Nhietdo, @NonNull TextView Thoigian,
      @NonNull Button btnBack) {
    this.rootView = rootView;
    this.Doam = Doam;
    this.LvThongtin = LvThongtin;
    this.Nhietdo = Nhietdo;
    this.Thoigian = Thoigian;
    this.btnBack = btnBack;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityHistoryListBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityHistoryListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_history_list, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityHistoryListBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.Doam;
      TextView Doam = ViewBindings.findChildViewById(rootView, id);
      if (Doam == null) {
        break missingId;
      }

      id = R.id.LvThongtin;
      ListView LvThongtin = ViewBindings.findChildViewById(rootView, id);
      if (LvThongtin == null) {
        break missingId;
      }

      id = R.id.Nhietdo;
      TextView Nhietdo = ViewBindings.findChildViewById(rootView, id);
      if (Nhietdo == null) {
        break missingId;
      }

      id = R.id.Thoigian;
      TextView Thoigian = ViewBindings.findChildViewById(rootView, id);
      if (Thoigian == null) {
        break missingId;
      }

      id = R.id.btn_back;
      Button btnBack = ViewBindings.findChildViewById(rootView, id);
      if (btnBack == null) {
        break missingId;
      }

      return new ActivityHistoryListBinding((RelativeLayout) rootView, Doam, LvThongtin, Nhietdo,
          Thoigian, btnBack);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}