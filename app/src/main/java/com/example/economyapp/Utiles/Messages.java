package com.example.economyapp.Utiles;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;

import java.lang.ref.WeakReference;

public class Messages {
    private AlertDialog alertDialog;
    private ProgressDialog progressDialog;
    private String title;
    private String message;

    public Messages(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public void showAlert(WeakReference<Context> reference) {
        alertDialog = new AlertDialog.Builder(reference.get())
                .setTitle(title)
                .setMessage(message)
                .show();
    }

    public void showLoading(WeakReference<Context> reference) {
        progressDialog = new ProgressDialog(reference.get());
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public void hideLoading() {
        if (progressDialog.isShowing() || progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
