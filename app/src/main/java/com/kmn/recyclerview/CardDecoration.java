package com.kmn.recyclerview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CardDecoration extends RecyclerView.ItemDecoration {

    /*
    Добавить разделительную линию мы можем двумя способами: легким и через декораторы.
     */

    private Paint mYellowPoint;
    private Paint mRedPoint;

    public CardDecoration(){
        mYellowPoint = new Paint();
        mYellowPoint.setStyle(Paint.Style.FILL); // полностью закрашена
        mYellowPoint.setAntiAlias(true); // сглаживание
        mYellowPoint.setColor(Color.YELLOW); // цвет

        mRedPoint = new Paint();
        mRedPoint.setStyle(Paint.Style.FILL);
        mRedPoint.setAntiAlias(true);
        mRedPoint.setColor(Color.RED);

    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);

        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int pixelOffset = parent.getContext().getResources().getDimensionPixelOffset(R.dimen.li_margin);

        int viewCount = parent.getChildCount();

        for(int i=0; i<viewCount; i++){
            View child = parent.getChildAt(i);

            int top = child.getTop() - pixelOffset / 2;
            int bottom = child.getBottom() + pixelOffset / 2;

            int childAdapterPosition = parent.getChildAdapterPosition(child);
            c.drawRect(left, top, right, bottom, childAdapterPosition % 2 == 0 ? mYellowPoint : mRedPoint);

        }
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        int pixelOffset = parent.getContext().getResources().getDimensionPixelOffset(R.dimen.li_margin);
        int viewCount = parent.getChildCount();
        int right = parent.getWidth() / 2;
        int left = right - pixelOffset * 2;

        for(int i=0; i<viewCount; i++){
            View child = parent.getChildAt(i);

            int top = child.getTop() + pixelOffset;
            int bottom = child.getBottom() - pixelOffset;

            int childAdapterPosition = parent.getChildAdapterPosition(child);
            c.drawRect(left, top, right, bottom, childAdapterPosition % 2 != 0 ? mYellowPoint : mRedPoint);
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int pixelOffset = parent.getContext().getResources().getDimensionPixelOffset(R.dimen.li_margin);

        outRect.left = pixelOffset;
        outRect.right = pixelOffset;
        outRect.top = pixelOffset / 2;
        outRect.bottom = pixelOffset / 2;

    }
}
