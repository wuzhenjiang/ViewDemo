package com.okay.demo.recyclerview.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.okay.demo.bean.GroupInfo;

/**
 * @author wuzhenjiang
 *
 * https://blog.csdn.net/briblue/article/details/70211942
 */
public class StickySectionDecoration extends RecyclerView.ItemDecoration {

    private int mHeaderHeight = 100;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int mDividerHeight = 1;
    private Context mContext;
    /**
     * 判断第一个头布局是否需要移动
     * <p>
     * 记录第二个头布局位置，通过第二个头布局的top和第一个头布局的bottom判断第一个头布局是否需要向上或者向下滑动
     */
    private Rect rect = new Rect();

    public StickySectionDecoration(Context context, GroupInfoCallback mCallback) {
        this.mContext = context;
        this.mCallback = mCallback;
        mPaint.setColor(Color.GRAY);
        mTextPaint.setColor(Color.RED);
        mTextPaint.setTextSize(spToPx(40));
    }

    private float spToPx(int sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, mContext.getResources().getDisplayMetrics());
    }

    private int getScreenWidth() {
        return mContext.getResources().getDisplayMetrics().widthPixels;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        int childCount = parent.getChildCount();
        Log.e("onDrawOver", "childCount = " + childCount);
        /**
         * 通过反相便利先画第二个再画第一个，通过第二个位置判断第一个头布局是否要向上或者下移动
         */
        for (int i = childCount - 1; i >= 0; i--) {
            View view = parent.getChildAt(i);
            int index = parent.getChildAdapterPosition(view);
            if (mCallback != null) {
                GroupInfo groupinfo = mCallback.getGroupInfo(index);
                int left = parent.getPaddingLeft();
                int right = parent.getWidth() - parent.getPaddingRight();
                //屏幕上第一个可见的 ItemView 时，i == 0;
                if (i != 0) {
                    //只有组内的第一个ItemView之上才绘制
                    if (groupinfo.isFirstViewInGroup()) {
                        int top = view.getTop() - mHeaderHeight;
                        int bottom = view.getTop();
                        drawHeaderRect(c, groupinfo, left, top, right, bottom);
                        rect.set(left, top, right, bottom);
                        Log.e("onDrawOver", "  i = " + i + ", bottom= " + bottom + ",top= " + top);
                    }
                } else {

                    //当 ItemView 是屏幕上第一个可见的View 时，不管它是不是组内第一个View
                    //它都需要绘制它对应的 StickyHeader。

                    // 还要判断当前的 ItemView 是不是它组内的最后一个 View

                    int top = parent.getPaddingTop();
                    int bottom = top + mHeaderHeight;

                    if (rect != null && rect.top != -1) {
                        if (rect.top < bottom) {
                            bottom = rect.top;
                            top = bottom - mHeaderHeight;
                        }
                    }


                    drawHeaderRect(c, groupinfo, left, top, right, bottom);
                    rect.set(-1, -1, 0, 0);
                    Log.e("onDrawOver", "  i = " + i + ",  bottom= " + bottom + ",top= " + top);
                }
            }
        }
    }

    private void drawHeaderRect(Canvas c, GroupInfo groupinfo, int left, int top, int right, int bottom) {
        //绘制Header
        c.drawRect(left, top, right, bottom, mPaint);
        Paint.FontMetricsInt mFontMetrics = mTextPaint.getFontMetricsInt();
        String title = groupinfo.getTitle();
        float titleWidth = mTextPaint.measureText(title);
        float titleX = getScreenWidth() / 2 - titleWidth / 2;
        float dy = (mFontMetrics.bottom - mFontMetrics.top) / 2 - mFontMetrics.bottom;
        float baseLine = top + (bottom - top) / 2 + dy;
        //绘制Title
        c.drawText(groupinfo.getTitle(), titleX, baseLine, mTextPaint);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view);

        if (mCallback != null) {
            GroupInfo groupInfo = mCallback.getGroupInfo(position);

            //如果是组内的第一个则将间距撑开为一个Header的高度，或者就是普通的分割线高度
            if (groupInfo != null && groupInfo.isFirstViewInGroup()) {
                outRect.top = mHeaderHeight;
            } else {
                outRect.top = mDividerHeight;
            }
        }
    }



    private GroupInfoCallback mCallback;

    public interface GroupInfoCallback {
        GroupInfo getGroupInfo(int position);
    }
}

