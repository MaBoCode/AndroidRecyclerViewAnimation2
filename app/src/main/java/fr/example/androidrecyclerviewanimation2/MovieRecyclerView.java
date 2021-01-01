package fr.example.androidrecyclerviewanimation2;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MovieRecyclerView extends RecyclerView {

    protected List<MovieViewHolder> viewHolders = new ArrayList<>();

    public MovieRecyclerView(@NonNull Context context) {
        super(context);
    }

    public MovieRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MovieRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void onAddViewHolder(MovieViewHolder viewHolder) {
        viewHolders.add(viewHolder);
    }

    public void onRemoveViewHolder(MovieViewHolder viewHolder) {
        viewHolders.remove(viewHolder);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        for (MovieViewHolder holder: viewHolders) {
            if (t > 0) {
                holder.animateTransition(MovieViewHolder.ANIMATED_TRANSLATION_AMOUNT, 0f, 0f, 1f);
            } else {
                holder.animateTransition(-MovieViewHolder.ANIMATED_TRANSLATION_AMOUNT, 0f, 0f, 1f);
            }
        }
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
    }
}
