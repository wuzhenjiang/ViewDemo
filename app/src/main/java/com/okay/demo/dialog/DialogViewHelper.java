package com.okay.demo.dialog;

import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * @author wzj
 * @date 5/3/21 12:54 PM
 */
class DialogViewHelper {
    private View mContentView;
    private SparseArray<WeakReference<View>> mArrayViews = new SparseArray<>();

    public DialogViewHelper(View contentView) {
        this.mContentView = contentView;
    }

    public View findViewById(int viewId) {
        WeakReference<View> viewWeakReference = mArrayViews.get(viewId);
        View targetView;
        if (null != viewWeakReference && null != viewWeakReference.get()) {
            targetView = viewWeakReference.get();
        } else {
            targetView = mContentView.findViewById(viewId);
            mArrayViews.put(viewId, new WeakReference(targetView));
        }
        return targetView;
    }

    public void setTextView(int viewId,CharSequence content){
        ((TextView)findViewById(viewId)).setText(content);
    }

    public void setOnClickListener(int id, View.OnClickListener listener) {
        findViewById(id).setOnClickListener(listener);
    }

    public void setImageView(int viewId, Integer resId) {
        ((ImageView)findViewById(viewId)).setImageResource(resId);
    }
}
