package io.capsulo.min808.features.listnote;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import io.capsulo.min808.R;

/**
 * Todo : Add class description
 */
public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {

  private final ListNoteAdapter mAdapter;
  private Drawable icon;
  private final ColorDrawable background;

  public SimpleItemTouchHelperCallback(ListNoteAdapter adapter, Context context) {
    mAdapter = adapter;
    icon = ContextCompat.getDrawable(context, R.drawable.ic_delete_white_24dp);
    background = new ColorDrawable(Color.BLACK);
  }

  @Override
  public boolean isLongPressDragEnabled() {
    return true;
  }

  @Override
  public boolean isItemViewSwipeEnabled() {
    return true;
  }

  @Override
  public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
    int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
    int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
    return makeMovementFlags(dragFlags, swipeFlags);
  }

  @Override
  public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                        RecyclerView.ViewHolder target) {
    mAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
    return true;
  }

  @Override
  public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
    mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
  }

  @Override
  public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

    View itemView = viewHolder.itemView;

    int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
    int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
    int iconBottom = iconTop + icon.getIntrinsicHeight();

    int backgroundCornerOffset = 20;

    // Swiping to the right
    if (dX > 0) {
      int iconLeft = itemView.getLeft() + iconMargin + icon.getIntrinsicWidth();
      int iconRight = itemView.getLeft() + iconMargin;
      icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

      background.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + ((int) dX) + backgroundCornerOffset, itemView.getBottom());
    }
    // Swiping to the left
    else if (dX < 0) {
      int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
      int iconRight = itemView.getRight() - iconMargin;
      icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

      background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset, itemView.getTop(), itemView.getRight(), itemView.getBottom());
    }
    // view is unSwiped
    else {

      background.setBounds(0, 0, 0, 0);
    }

    // Draw
    background.draw(c);
    icon.draw(c);
  }



}