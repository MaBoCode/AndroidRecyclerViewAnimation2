package fr.example.androidrecyclerviewanimation2;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fr.example.androidrecyclerviewanimation2.databinding.ActivityMainBinding;
import fr.example.androidrecyclerviewanimation2.databinding.FrgMainBinding;
import fr.example.androidrecyclerviewanimation2.databinding.MovieListItemBinding;

public class MainFragment extends Fragment implements MovieAdapter.MovieItemClick {

    protected FrgMainBinding binding;

    protected List<Movie> data;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FrgMainBinding.inflate(inflater, container, false);

        initData();

        bindData();

        return binding.getRoot();
    }

    public void bindData() {
        MovieAdapter adapter = new MovieAdapter(new Movie.MovieDiff(), this);
        adapter.submitList(data);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.moviesRecyclerView.setHasFixedSize(true);
        binding.moviesRecyclerView.setLayoutManager(layoutManager);
        binding.moviesRecyclerView.setAdapter(adapter);
    }

    public void initData() {
        Resources resources = getResources();

        Drawable ghost = ResourcesCompat.getDrawable(resources, R.drawable.ghost_in_the_shell, null);
        Drawable pirates = ResourcesCompat.getDrawable(resources, R.drawable.pirates_of_the_carribean, null);
        Drawable game = ResourcesCompat.getDrawable(resources, R.drawable.game_of_thrones, null);
        Drawable alien = ResourcesCompat.getDrawable(resources, R.drawable.alien_covenant, null);

        data = new ArrayList<>();
        data.add(new Movie(Movie.MovieType.FILM, "Ghost In The Shell", ghost));
        data.add(new Movie(Movie.MovieType.FILM, "Pirates of The Caribbean", pirates));
        data.add(new Movie(Movie.MovieType.TV, "Game of Thrones", game));
        data.add(new Movie(Movie.MovieType.FILM, "Alien Covenant", alien));
    }

    @Override
    public void onItemClick(View view, Movie movie, long itemId) {
        MovieListItemBinding binding = DataBindingUtil.getBinding(view);

        MainFragmentDirections.ToMoviePagerFragment toMoviePagerFragment = MainFragmentDirections.toMoviePagerFragment(itemId, data.toArray(new Movie[data.size()]));

        binding.imgMoviePoster.setTransitionName("img_" + movie.title);
        binding.txtMovieType.setTransitionName("type_" + movie.title);
        binding.txtMovieTitle.setTransitionName("title_" + movie.title);

        FragmentNavigator.Extras extras = new FragmentNavigator.Extras.Builder()
                .addSharedElement(binding.imgMoviePoster, "movie_item_img_transition")
                .addSharedElement(binding.txtMovieType, "movie_item_type_transition")
                .addSharedElement(binding.txtMovieTitle, "movie_item_title_transition")
                .build();

        Navigation.findNavController(binding.getRoot()).navigate(toMoviePagerFragment, extras);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
