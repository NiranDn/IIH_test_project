package com.example.iihtestproject.activity;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.iihtestproject.R;

public abstract class BaseActivity extends AppCompatActivity {

    public Dialog mProgress;
    private Fragment mFragment;

    public void showWaiting() {
        if (mProgress == null) {
            mProgress = new Dialog(this, R.style.Progressbar);
            mProgress.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mProgress.setContentView(R.layout.custom_progress_spinner);
            mProgress.setCancelable(false);
        }

        if (!mProgress.isShowing()) {
            mProgress.show();
        }
    }

    public void dismissWaiting() {
        if (mProgress != null && mProgress.isShowing()) {
            mProgress.dismiss();
            mProgress = null;
        }
    }

    public void showMessage(String message) {
        if (message == null || message.isEmpty()) return;

        Toast.makeText(
                this,
                message,
                Toast.LENGTH_LONG
        ).show();
    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    protected void startFragment(int containerId, String tagId, Fragment fragment, boolean isBackStackEnable) {
        mFragment = fragment;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (isBackStackEnable) transaction.addToBackStack(null);
        transaction.replace(containerId, mFragment,tagId);
        transaction.commitAllowingStateLoss();
    }
}
