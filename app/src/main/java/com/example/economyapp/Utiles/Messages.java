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

    public void showAletWithAction(WeakReference<Context> reference, Runnable taskToExecute) {
        alertDialog = new AlertDialog.Builder(reference.get())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Si", (dialogInterface, i) -> {
                    taskToExecute.run();
                })
                .setNegativeButton("No", (dialogInterface, i) -> {
                    alertDialog.dismiss();
                })
                .show();
    }

    public void showLoading(WeakReference<Context> reference) {
        progressDialog = new ProgressDialog(reference.get());
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
