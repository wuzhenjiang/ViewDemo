package com.okay.demo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.okay.demo.R;


public class CommonDialog extends Dialog {

    private DialogViewHelper mDialogViewHelper;

    private CommonDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public <T extends View> T findView(int viewId) {
        return (T)mDialogViewHelper.findViewById(viewId);
    }

    private void setViewHelper(DialogViewHelper dialogViewHelper) {
        this.mDialogViewHelper = dialogViewHelper;
    }

    public static class Builder {
        private Context mContext;
        private int mThemeResId;
        // 宽度
        private int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 高度
        public int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 动画
        public int mAnimations = 0;
        // 位置
        public int mGravity = Gravity.CENTER;
        // 点击空白区域是否能取消
        public boolean mCancelable = true;
        //取消监听
        public OnCancelListener mOnCancelListener;
        //消失监听
        private OnDismissListener mOnDismissListener;
        //按键监听
        private OnKeyListener mOnKeyListener;
        // 布局View
        public View mView;
        // 布局layout id
        public int mViewLayoutResId;

        private int maxY = 0;
        private int maxX = 0;

        public SparseArray<View.OnClickListener> mClickArray = new SparseArray<>();
        public SparseArray<CharSequence> mTextArray = new SparseArray<>();
        public SparseArray<Integer> mImageArray = new SparseArray<>();

        public Builder(Context context) {
            this(context, R.style.dialog_style);
        }

        public Builder(Context context, int themeResId) {
            this.mContext = context;
            this.mThemeResId = themeResId;
        }

        /**
         * 设置布局View
         *
         * @param view
         * @return
         */
        public Builder setContentView(View view) {
            this.mView = view;
            return this;
        }

        public Builder setContentView(int layoutId) {
            this.mViewLayoutResId = layoutId;
            return this;
        }


        // 配置一些万能的参数
        public Builder fullWidth() {
            mWidth = ViewGroup.LayoutParams.MATCH_PARENT;
            return this;
        }


        /**
         * 从底部弹出
         *
         * @param isAnimation 是否有动画
         * @return
         */
        public Builder formBottom(boolean isAnimation) {
            if (isAnimation) {
                mAnimations = R.style.dialog_from_bottom_anim;
            }
            mGravity = Gravity.BOTTOM;
            return this;
        }

        public Builder gravity(int gravity){
            mGravity = gravity;
            return this;
        }

        /**
         * 设置Dialog的宽高
         *
         * @param width
         * @param height
         * @return
         */
        public Builder setWidthAndHeight(int width, int height) {
            this.mWidth = width;
            this.mHeight = height;
            return this;
        }

        /**
         * 添加默认动画
         *
         * @return
         */
        public Builder addDefaultAnimation() {
            this.mAnimations = R.style.dialog_scale_anim;
            return this;
        }

        /**
         * 是否能取消
         *
         * @param cancelable
         * @return
         */
        public Builder setCancelable(boolean cancelable) {
            this.mCancelable = cancelable;
            return this;
        }

        /**
         * 取消监听
         *
         * @param onCancelListener
         * @return
         */
        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            this.mOnCancelListener = onCancelListener;
            return this;
        }

        /**
         * 消失监听
         *
         * @param onDismissListener
         * @return
         */
        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            this.mOnDismissListener = onDismissListener;
            return this;
        }

        /**
         * 按键监听
         *
         * @param onKeyListener
         * @return
         */
        public Builder setOnKeyListener(OnKeyListener onKeyListener) {
            this.mOnKeyListener = onKeyListener;
            return this;
        }

        public Builder setOnClickListener(int id, View.OnClickListener listener) {
            mClickArray.put(id, listener);
            return this;
        }

        public Builder setText(int id, CharSequence charSequence) {
            mTextArray.put(id, charSequence);
            return this;
        }

        public Builder setImage(int id, int resId) {
            mImageArray.put(id, resId);
            return this;
        }

        public CommonDialog create() {
            // Context has already been wrapped with the appropriate theme.
            final CommonDialog dialog = new CommonDialog(mContext, mThemeResId);
            if (mViewLayoutResId != 0) {
                mView = LayoutInflater.from(mContext).inflate(mViewLayoutResId, null);
            }
            dialog.setContentView(mView);
            DialogViewHelper dialogViewHelper = new DialogViewHelper(mView);
            dialog.setViewHelper(dialogViewHelper);

            dialog.setCancelable(mCancelable);
            if (mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            dialog.setOnCancelListener(mOnCancelListener);
            dialog.setOnDismissListener(mOnDismissListener);
            dialog.setOnKeyListener(mOnKeyListener);

            int size = mClickArray.size();
            for (int i=0;i<size;i++){
                dialogViewHelper.setOnClickListener(mClickArray.keyAt(i),mClickArray.valueAt(i));
            }

            int setTextSize = mTextArray.size();
            for(int i=0;i<setTextSize;i++){
                dialogViewHelper.setTextView(mTextArray.keyAt(i),mTextArray.valueAt(i));
            }

            int imageSize = mImageArray.size();
            for(int i=0;i<imageSize;i++){
                dialogViewHelper.setImageView(mImageArray.keyAt(i),mImageArray.valueAt(i));
            }

            // 4.配置自定义的效果  全屏  从底部弹出    默认动画
            Window window = dialog.getWindow();
            // 设置位置
            window.setGravity(mGravity);

            // 设置动画
            if (mAnimations != 0) {
                window.setWindowAnimations(mAnimations);
            }

            // 设置宽高
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = mWidth;
            params.height = mHeight;

            if(maxY!=0){
                params.y = maxY;
            }
            if(maxX!=0){
                params.x = maxX;
            }
            window.setAttributes(params);
            return dialog;
        }

        public Builder setLoacitonTop(int maginX,int maginY,int gravity) {
            mGravity = gravity;
            this.maxY = maginY;
            this.maxX = maginX;
            return this;
        }

        public CommonDialog show() {
            final CommonDialog dialog = create();
            dialog.show();
            return dialog;
        }
    }
}