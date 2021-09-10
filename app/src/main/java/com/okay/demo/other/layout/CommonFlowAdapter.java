package com.okay.demo.other.layout;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public abstract class CommonFlowAdapter<T> extends BaseAdapter {
    public List<T> mDatas;
    private int mLayoutId;
    private Context mContext;

    public CommonFlowAdapter(Context context, List<T> datas, int layoutId) {
        this.mContext = context;
        this.mDatas = datas;
        this.mLayoutId = layoutId;
    }

    public int getCounts() {
        return mDatas.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FlowHolder holder = new FlowHolder(mContext, parent, mLayoutId);
        convert(holder, mDatas.get(position), position);
        return holder.getConvertView();
    }

    public abstract void convert(FlowHolder holder, T item, int position);

    public class FlowHolder {
        private SparseArray<View> mViews;
        private View mConvertView;

        public FlowHolder(Context context, ViewGroup parent, int layoutId) {
            this.mViews = new SparseArray<View>();
            mConvertView = LayoutInflater.from(context).inflate(layoutId,
                    parent, false);
        }

        public FlowHolder setText(int viewId, CharSequence text) {
            TextView tv = getView(viewId);
            tv.setText(text);
            return this;
        }

        public <T extends View> T getView(int viewId) {
            View view = mViews.get(viewId);
            if (view == null) {
                view = mConvertView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }

        /**
         * 设置点击事件
         *
         * @return
         */
        public FlowHolder setOnClickListener(int viewId,
                                             View.OnClickListener clickListener) {
            getView(viewId).setOnClickListener(clickListener);
            return this;
        }

        /**
         * 设置条目的点击事件
         *
         * @return
         */
        public FlowHolder setItemClick(View.OnClickListener clickListener) {
            mConvertView.setOnClickListener(clickListener);
            return this;
        }

        public View getConvertView() {
            return mConvertView;
        }
    }
}

