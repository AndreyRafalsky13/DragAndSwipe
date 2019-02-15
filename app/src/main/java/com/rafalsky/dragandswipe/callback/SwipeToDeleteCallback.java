package com.rafalsky.dragandswipe.callback;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.rafalsky.dragandswipe.R;
import com.rafalsky.dragandswipe.adapters.PersonAdapter;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

    private PersonAdapter adapter;

    private Drawable icon;
    private ColorDrawable background;

    public SwipeToDeleteCallback(Context context, PersonAdapter adapter){

        //встановити напрямки за замовчуванням
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);

        this.adapter = adapter;

        //ініціалізація іконки та фону, які будуть видимі при свайпі
        icon = ContextCompat.getDrawable(context, R.drawable.ic_delete);
        background = new ColorDrawable(Color.RED);
    }

    //встановити напрямок для перетягування та свайпа
    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    //чи можна при довгому зажатті елемента перетягувати та свайпати його
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    //чи буде виконана операція свайпа, якщо провести по елементу
    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        View itemView = viewHolder.itemView;
        int backgroundCornerOffset = 20;
        int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconBottom = iconTop + icon.getIntrinsicHeight();

        if (dX > 0) { // Swiping to the right
            int iconLeft = itemView.getLeft() + iconMargin + icon.getIntrinsicWidth();
            int iconRight = itemView.getLeft() + iconMargin;
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            background.setBounds(itemView.getLeft(), itemView.getTop(),
                    itemView.getLeft() + ((int) dX) + backgroundCornerOffset,
                    itemView.getBottom());
        } else if (dX < 0) { // Swiping to the left
            int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
            int iconRight = itemView.getRight() - iconMargin;
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
                    itemView.getTop(), itemView.getRight(), itemView.getBottom());
        } else { // view is unSwiped
            background.setBounds(0, 0, 0, 0);
        }

        background.draw(c);
        icon.draw(c);
    }


    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return adapter.onItemMove(viewHolder.getAdapterPosition(), viewHolder1.getAdapterPosition());
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        adapter.onItemDismiss(viewHolder.getAdapterPosition());
    }
}
