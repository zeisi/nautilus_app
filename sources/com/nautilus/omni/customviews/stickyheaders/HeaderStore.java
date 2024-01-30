package com.nautilus.omni.customviews.stickyheaders;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerViewHelper;
import android.view.View;
import java.util.ArrayList;
import java.util.HashMap;

public class HeaderStore {
    private final StickyHeadersAdapter adapter;
    private final HashMap<Long, Integer> headersHeightsByItemsIds = new HashMap<>();
    private final HashMap<Long, View> headersViewByHeadersIds = new HashMap<>();
    private final ArrayList<Boolean> isHeaderByItemPosition = new ArrayList<>();
    private boolean isSticky;
    private final RecyclerView parent;
    private final HashMap<Long, Boolean> wasHeaderByItemId = new HashMap<>();

    public HeaderStore(RecyclerView parent2, StickyHeadersAdapter adapter2, boolean isSticky2) {
        this.parent = parent2;
        this.adapter = adapter2;
        this.isSticky = isSticky2;
    }

    public View getHeaderViewByItem(RecyclerView.ViewHolder itemHolder) {
        int itemPosition = RecyclerViewHelper.convertPreLayoutPositionToPostLayout(this.parent, itemHolder.getPosition());
        if (itemPosition == -1) {
            return null;
        }
        long headerId = this.adapter.getHeaderId(itemPosition);
        if (!this.headersViewByHeadersIds.containsKey(Long.valueOf(headerId))) {
            RecyclerView.ViewHolder headerViewHolder = this.adapter.onCreateViewHolder(this.parent);
            this.adapter.onBindViewHolder(headerViewHolder, itemPosition);
            layoutHeader(headerViewHolder.itemView);
            this.headersViewByHeadersIds.put(Long.valueOf(headerId), headerViewHolder.itemView);
        }
        return this.headersViewByHeadersIds.get(Long.valueOf(headerId));
    }

    public long getHeaderId(int itemPosition) {
        return this.adapter.getHeaderId(itemPosition);
    }

    public int getHeaderHeight(RecyclerView.ViewHolder itemHolder) {
        if (!this.headersHeightsByItemsIds.containsKey(Long.valueOf(itemHolder.getItemId()))) {
            View header = getHeaderViewByItem(itemHolder);
            this.headersHeightsByItemsIds.put(Long.valueOf(itemHolder.getItemId()), Integer.valueOf(header.getVisibility() == 8 ? 0 : header.getMeasuredHeight()));
        }
        return this.headersHeightsByItemsIds.get(Long.valueOf(itemHolder.getItemId())).intValue();
    }

    public boolean isHeader(RecyclerView.ViewHolder itemHolder) {
        int itemPosition = RecyclerViewHelper.convertPreLayoutPositionToPostLayout(this.parent, itemHolder.getPosition());
        if (this.isHeaderByItemPosition.size() <= itemPosition) {
            this.isHeaderByItemPosition.ensureCapacity(itemPosition + 1);
            for (int i = this.isHeaderByItemPosition.size(); i <= itemPosition; i++) {
                this.isHeaderByItemPosition.add((Object) null);
            }
        }
        if (this.isHeaderByItemPosition.get(itemPosition) == null) {
            this.isHeaderByItemPosition.set(itemPosition, Boolean.valueOf(itemPosition == 0 || this.adapter.getHeaderId(itemPosition) != this.adapter.getHeaderId(itemPosition + -1)));
        }
        return this.isHeaderByItemPosition.get(itemPosition).booleanValue();
    }

    public boolean wasHeader(RecyclerView.ViewHolder itemHolder) {
        boolean z = false;
        if (!this.wasHeaderByItemId.containsKey(Long.valueOf(itemHolder.getItemId()))) {
            int itemPosition = RecyclerViewHelper.convertPreLayoutPositionToPostLayout(this.parent, itemHolder.getPosition());
            if (itemPosition == -1) {
                return false;
            }
            HashMap<Long, Boolean> hashMap = this.wasHeaderByItemId;
            Long valueOf = Long.valueOf(itemHolder.getItemId());
            if (itemPosition == 0 || this.adapter.getHeaderId(itemPosition) != this.adapter.getHeaderId(itemPosition - 1)) {
                z = true;
            }
            hashMap.put(valueOf, Boolean.valueOf(z));
        }
        return this.wasHeaderByItemId.get(Long.valueOf(itemHolder.getItemId())).booleanValue();
    }

    public boolean isSticky() {
        return this.isSticky;
    }

    public void onItemRangeRemoved(int positionStart, int itemCount) {
        this.headersViewByHeadersIds.clear();
        if (this.isHeaderByItemPosition.size() > positionStart + itemCount) {
            for (int i = 0; i < itemCount; i++) {
                RecyclerView.ViewHolder holder = this.parent.findViewHolderForPosition(positionStart + i);
                if (holder != null) {
                    this.wasHeaderByItemId.put(Long.valueOf(holder.getItemId()), this.isHeaderByItemPosition.get(positionStart + i));
                }
            }
            this.isHeaderByItemPosition.set(positionStart + itemCount, (Object) null);
            for (int i2 = 0; i2 < itemCount; i2++) {
                this.isHeaderByItemPosition.remove(positionStart);
            }
        }
    }

    public void onItemRangeInserted(int positionStart, int itemCount) {
        this.headersViewByHeadersIds.clear();
        if (this.isHeaderByItemPosition.size() > positionStart) {
            for (int i = 0; i < itemCount; i++) {
                this.isHeaderByItemPosition.add(positionStart, (Object) null);
            }
        }
        if (this.isHeaderByItemPosition.size() > positionStart + itemCount) {
            this.isHeaderByItemPosition.set(positionStart + itemCount, (Object) null);
        }
    }

    public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
        boolean z;
        boolean z2;
        this.headersViewByHeadersIds.clear();
        int min = Math.min(fromPosition, toPosition);
        int max = Math.max(fromPosition, toPosition);
        for (int i = min; i <= max; i++) {
            if (i >= this.isHeaderByItemPosition.size()) {
                this.isHeaderByItemPosition.add((Object) null);
            }
        }
        if (fromPosition < toPosition) {
            if (fromPosition == 0) {
                this.isHeaderByItemPosition.set(0, 1);
            } else {
                long fromPositionId = this.adapter.getHeaderId(fromPosition);
                long beforeFromPositionId = this.adapter.getHeaderId(fromPosition - 1);
                long afterFromPositionId = this.adapter.getHeaderId(fromPosition + 1);
                this.isHeaderByItemPosition.set(fromPosition, Boolean.valueOf(fromPositionId != beforeFromPositionId));
                this.isHeaderByItemPosition.set(fromPosition + 1, Boolean.valueOf(fromPositionId != afterFromPositionId));
            }
            long toPositionId = this.adapter.getHeaderId(toPosition);
            this.isHeaderByItemPosition.set(toPosition, Boolean.valueOf(toPositionId != this.adapter.getHeaderId(toPosition + -1)));
            if (toPosition < this.isHeaderByItemPosition.size() - 1) {
                long afterToPositionId = this.adapter.getHeaderId(toPosition + 1);
                ArrayList<Boolean> arrayList = this.isHeaderByItemPosition;
                int i2 = toPosition + 1;
                if (toPositionId != afterToPositionId) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                arrayList.set(i2, Boolean.valueOf(z2));
            }
        } else if (fromPosition > toPosition) {
            if (toPosition == 0) {
                this.isHeaderByItemPosition.set(0, 1);
            } else {
                long toPositionId2 = this.adapter.getHeaderId(toPosition);
                long beforeToPositionId = this.adapter.getHeaderId(toPosition - 1);
                long afterToPositionId2 = this.adapter.getHeaderId(toPosition + 1);
                this.isHeaderByItemPosition.set(toPosition, Boolean.valueOf(toPositionId2 != beforeToPositionId));
                this.isHeaderByItemPosition.set(toPosition + 1, Boolean.valueOf(toPositionId2 != afterToPositionId2));
            }
            long fromPositionId2 = this.adapter.getHeaderId(fromPosition);
            this.isHeaderByItemPosition.set(fromPosition, Boolean.valueOf(fromPositionId2 != this.adapter.getHeaderId(fromPosition + -1)));
            if (fromPosition < this.isHeaderByItemPosition.size() - 1) {
                long afterFromPositionId2 = this.adapter.getHeaderId(fromPosition + 1);
                ArrayList<Boolean> arrayList2 = this.isHeaderByItemPosition;
                int i3 = fromPosition + 1;
                if (fromPositionId2 != afterFromPositionId2) {
                    z = true;
                } else {
                    z = false;
                }
                arrayList2.set(i3, Boolean.valueOf(z));
            }
        } else if (fromPosition == 0) {
            this.isHeaderByItemPosition.set(0, 1);
        } else {
            long fromPositionId3 = this.adapter.getHeaderId(fromPosition);
            this.isHeaderByItemPosition.set(fromPosition, Boolean.valueOf(fromPositionId3 != this.adapter.getHeaderId(fromPosition + -1)));
            if (fromPosition < this.isHeaderByItemPosition.size() - 1) {
                this.isHeaderByItemPosition.set(fromPosition + 1, Boolean.valueOf(fromPositionId3 != this.adapter.getHeaderId(fromPosition + 1)));
            }
        }
    }

    public void onItemRangeChanged(int startPosition, int itemCount) {
        this.headersViewByHeadersIds.clear();
        if (startPosition < this.isHeaderByItemPosition.size()) {
            int start = Math.min(startPosition, this.isHeaderByItemPosition.size());
            int end = Math.min(startPosition + itemCount + 1, this.isHeaderByItemPosition.size());
            for (int i = start; i < end; i++) {
                this.isHeaderByItemPosition.set(i, (Object) null);
            }
        }
    }

    public void clear() {
        this.headersViewByHeadersIds.clear();
        this.isHeaderByItemPosition.clear();
        this.wasHeaderByItemId.clear();
    }

    private void layoutHeader(View header) {
        header.measure(View.MeasureSpec.makeMeasureSpec(this.parent.getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(0, 0));
        header.layout(0, 0, header.getMeasuredWidth(), header.getMeasuredHeight());
    }
}
