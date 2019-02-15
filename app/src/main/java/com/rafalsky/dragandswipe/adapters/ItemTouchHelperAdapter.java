package com.rafalsky.dragandswipe.adapters;

public interface ItemTouchHelperAdapter {
    boolean onItemMove(int from, int to);
    void onItemDismiss(int position);
}
