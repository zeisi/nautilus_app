package com.nautilus.omni.customviews.stickyheaders;

import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class StickyHeadersTouchListener implements RecyclerView.OnItemTouchListener {
    private final GestureDetector gestureDetector;
    /* access modifiers changed from: private */
    public final HeaderStore headerStore;
    /* access modifiers changed from: private */
    public OnHeaderClickListener listener;

    public StickyHeadersTouchListener(RecyclerView parent, HeaderStore headerStore2) {
        this.headerStore = headerStore2;
        this.gestureDetector = new GestureDetector(parent.getContext(), new SingleTapDetector(parent));
    }

    public boolean onInterceptTouchEvent(RecyclerView parent, MotionEvent e) {
        return this.listener != null && this.gestureDetector.onTouchEvent(e);
    }

    public void onTouchEvent(RecyclerView parent, MotionEvent e) {
    }

    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    public void setListener(OnHeaderClickListener listener2) {
        this.listener = listener2;
    }

    private class SingleTapDetector extends GestureDetector.SimpleOnGestureListener {
        private final RecyclerView parent;

        public SingleTapDetector(RecyclerView parent2) {
            this.parent = parent2;
        }

        public boolean onSingleTapUp(MotionEvent e) {
            return findItemHolderUnder(e.getX(), e.getY()) != null;
        }

        public boolean onSingleTapConfirmed(MotionEvent e) {
            RecyclerView.ViewHolder holder = findItemHolderUnder(e.getX(), e.getY());
            if (holder == null) {
                return false;
            }
            StickyHeadersTouchListener.this.listener.onHeaderClick(StickyHeadersTouchListener.this.headerStore.getHeaderViewByItem(holder), StickyHeadersTouchListener.this.headerStore.getHeaderId(holder.getPosition()));
            return true;
        }

        private RecyclerView.ViewHolder findItemHolderUnder(float x, float y) {
            RecyclerView.ViewHolder holder;
            for (int i = this.parent.getChildCount() - 1; i > 0; i--) {
                View item = this.parent.getChildAt(i);
                RecyclerView.ViewHolder holder2 = this.parent.getChildViewHolder(item);
                if (holder2 != null && StickyHeadersTouchListener.this.headerStore.isHeader(holder2) && y < ((float) item.getTop()) && ((float) (item.getTop() - StickyHeadersTouchListener.this.headerStore.getHeaderHeight(holder2))) < y) {
                    return holder2;
                }
            }
            View firstItem = this.parent.getChildAt(0);
            if (firstItem == null || (holder = this.parent.getChildViewHolder(firstItem)) == null || y >= ((float) StickyHeadersTouchListener.this.headerStore.getHeaderHeight(holder)) || (holder.getPosition() != 0 && !StickyHeadersTouchListener.this.headerStore.isSticky())) {
                return null;
            }
            return holder;
        }
    }
}
