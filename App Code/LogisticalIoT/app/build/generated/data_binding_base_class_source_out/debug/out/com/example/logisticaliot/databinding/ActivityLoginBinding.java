// Generated by view binder compiler. Do not edit!
package com.example.logisticaliot.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public final class ActivityLoginBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final TextView ForgetPasswordbtn;

  @NonNull
  public final Button Loginbtn;

  @NonNull
  public final EditText Matkhautext;

  @NonNull
  public final Button Registerbtn;

  @NonNull
  public final EditText Taikhoantext;

  private ActivityLoginBinding(@NonNull RelativeLayout rootView,
      @NonNull TextView ForgetPasswordbtn, @NonNull Button Loginbtn, @NonNull EditText Matkhautext,
      @NonNull Button Registerbtn, @NonNull EditText Taikhoantext) {
    this.rootView = rootView;
    this.ForgetPasswordbtn = ForgetPasswordbtn;
    this.Loginbtn = Loginbtn;
    this.Matkhautext = Matkhautext;
    this.Registerbtn = Registerbtn;
    this.Taikhoantext = Taikhoantext;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityLoginBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityLoginBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_login, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityLoginBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.ForgetPasswordbtn;
      TextView ForgetPasswordbtn = ViewBindings.findChildViewById(rootView, id);
      if (ForgetPasswordbtn == null) {
        break missingId;
      }

      id = R.id.Loginbtn;
      Button Loginbtn = ViewBindings.findChildViewById(rootView, id);
      if (Loginbtn == null) {
        break missingId;
      }

      id = R.id.Matkhautext;
      EditText Matkhautext = ViewBindings.findChildViewById(rootView, id);
      if (Matkhautext == null) {
        break missingId;
      }

      id = R.id.Registerbtn;
      Button Registerbtn = ViewBindings.findChildViewById(rootView, id);
      if (Registerbtn == null) {
        break missingId;
      }

      id = R.id.Taikhoantext;
      EditText Taikhoantext = ViewBindings.findChildViewById(rootView, id);
      if (Taikhoantext == null) {
        break missingId;
      }

      return new ActivityLoginBinding((RelativeLayout) rootView, ForgetPasswordbtn, Loginbtn,
          Matkhautext, Registerbtn, Taikhoantext);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
