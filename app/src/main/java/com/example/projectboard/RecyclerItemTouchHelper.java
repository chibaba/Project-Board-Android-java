package com.example.projectboard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectboard.Adapter.TodoAdapter;

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    private TodoAdapter adapter;

    public RecyclerItemTouchHelper(TodoAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapter = adapter;
    }
      @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
      }
      @Override
    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direct) {
        final int position = viewHolder.getAdapterPosition();
        if(direct == ItemTouchHelper.LEFT ) {
            AlertDialog.Builder builder = new AlertDialog.Builder(adapter.getContext());
            builder.setTitle("Delete Task");
            builder.setMessage("Are you sure you want to delete this task ?");
            builder.setPositiveButton("confirm",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            adapter.deleteItem(position);
                        }
                    });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else {
            adapter.editItem(position);
        }
      }

      @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        Drawable icon;
          ColorDrawable background;

          View itemView = viewHolder.itemView;
          int backgroundCornerOffset = 20;

          if(dX>0) {
              icon = ContextCompat.getDrawable(adapter.getContext(), R.drawable.ic_baseline_edit);
              background = new ColorDrawable(ContextCompat.getColor(adapter.getContext(), R.color.design_default_color_primary_variant));
          } else {
              icon = ContextCompat.getDrawable(adapter.getContext(), R.drawable.ic_baseline_delete_24);
              background = new ColorDrawable(Color.RED);
          }

          int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) /2;
          int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;

          int iconButtom = iconTop + icon.getIntrinsicHeight();

          if(dX > 0 ) {// swiping to the right
              int iconLeft = itemView.getLeft() + iconMargin;
              int iconRight =  itemView.getLeft() + iconMargin + icon.getIntrinsicWidth();
              icon.setBounds(iconLeft, iconTop, iconRight, iconButtom);

              background.setBounds(itemView.getLeft(), itemView.getTop(),
                      itemView.getLeft() + ((int)dX) + backgroundCornerOffset, itemView.getBottom());
      }
          else if (dX<0){
              int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
              int iconRight = itemView.getRight() - iconMargin;
              icon.setBounds(iconLeft, iconTop, iconRight, iconButtom);

              background.setBounds(itemView.getRight() +((int)dX) - backgroundCornerOffset,itemView.getTop(),
                        itemView.getLeft() + ((int)dX) + backgroundCornerOffset, itemView.getBottom());
          }
          else {
              background.setBounds(0,0,0,0);
          }
            background.draw(c);
            icon.draw(c);
          }

}
