package fr.example.androidrecyclerviewanimation2;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.navigation.Navigation;
import androidx.transition.Transition;
import androidx.transition.TransitionInflater;
import androidx.viewpager2.widget.ViewPager2;

import java.util.Arrays;
import java.util.List;

import fr.example.androidrecyclerviewanimation2.databinding.FrgMoviePagerBinding;

public class MoviePagerFragment extends Fragment {

    protected FrgMoviePagerBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FrgMoviePagerBinding.inflate(inflater, container, false);

        MoviePagerFragmentArgs args = MoviePagerFragmentArgs.fromBundle(getArguments());
        List<Movie> movies = Arrays.asList(args.getMovies());

        MoviePagerAdapter adapter = new MoviePagerAdapter(this);
        adapter.setDataset(movies);

        binding.movieViewPager.setAdapter(adapter);
        binding.movieViewPager.setCurrentItem((int) args.getCurrentItemId(), false);
        binding.movieViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int fragments = adapter.getFragments().size();
                MovieFragment fragment;

                if (fragments == 1) {
                    fragment = (MovieFragment) adapter.getFragments().get(0);
                } else if (position >= fragments){
                    fragment = (MovieFragment) adapter.getFragments().get(fragments - 1);
                } else {
                    fragment = (MovieFragment) adapter.getFragments().get(position);
                }
                Movie movie = movies.get(position);
                fragment.setToolbarColor(getContext(), binding, movie.poster);
                //fragment.animateTransition(MovieFragment.ANIMATED_TRANSLATION_AMOUNT, 0f, 0f, 1f);
            }
        });

        binding.tlbMovie.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigateUp();
            }
        });

        animateButtons();

        Transition transition = TransitionInflater.from(getContext()).inflateTransition(R.transition.movie_item_transition);
        setSharedElementEnterTransition(transition);

        return binding.getRoot();
    }

    public void animateButtons() {

        ObjectAnimator btnFavAnimator = ObjectAnimator.ofFloat(binding.btnFavorite, "translationY", 400f, 0f);
        ObjectAnimator btnLayersAnimator = ObjectAnimator.ofFloat(binding.btnLayers, "translationY", 400f, 0f);
        ObjectAnimator btnShareAnimator = ObjectAnimator.ofFloat(binding.btnShare, "translationY", 400f, 0f);

        AnimatorSet btnFavAnimatorSet = new AnimatorSet();
        btnFavAnimatorSet.playSequentially(btnFavAnimator);
        btnFavAnimatorSet.setDuration(300L);
        btnFavAnimatorSet.setInterpolator(new FastOutSlowInInterpolator());

        AnimatorSet btnLayersAnimatorSet = new AnimatorSet();
        btnLayersAnimatorSet.playSequentially(btnLayersAnimator);
        btnLayersAnimatorSet.setDuration(300L);
        btnLayersAnimatorSet.setStartDelay(100L);
        btnLayersAnimatorSet.setInterpolator(new FastOutSlowInInterpolator());

        AnimatorSet btnShareAnimatorSet = new AnimatorSet();
        btnShareAnimatorSet.playSequentially(btnShareAnimator);
        btnShareAnimatorSet.setDuration(300L);
        btnShareAnimatorSet.setStartDelay(200L);
        btnShareAnimatorSet.setInterpolator(new FastOutSlowInInterpolator());

        btnFavAnimatorSet.start();
        btnLayersAnimatorSet.start();
        btnShareAnimatorSet.start();
    }

}
