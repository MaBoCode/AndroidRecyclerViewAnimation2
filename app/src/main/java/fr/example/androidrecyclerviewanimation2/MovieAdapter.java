package fr.example.androidrecyclerviewanimation2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import fr.example.androidrecyclerviewanimation2.databinding.MovieListItemBinding;

public class MovieAdapter extends ListAdapter<Movie, MovieViewHolder> {

    protected MovieRecyclerView recyclerView;

    protected MovieItemClick listener;

    protected MovieAdapter(@NonNull DiffUtil.ItemCallback<Movie> diffCallback, MovieItemClick listener) {
        super(diffCallback);
        this.listener = listener;
    }

    public interface MovieItemClick {
        void onItemClick(View view, Movie movie, long itemId);
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MovieListItemBinding binding = MovieListItemBinding.inflate(inflater, parent, false);
        return new MovieViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = getItem(position);
        holder.bind(movie, listener, position);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = (MovieRecyclerView) recyclerView;
    }

    @Override
    public void onViewAttachedToWindow(@NonNull MovieViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        recyclerView.onAddViewHolder(holder);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull MovieViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        recyclerView.onRemoveViewHolder(holder);
    }
}
