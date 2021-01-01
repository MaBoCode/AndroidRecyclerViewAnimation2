package fr.example.androidrecyclerviewanimation2;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;

import androidx.recyclerview.widget.RecyclerView;

import fr.example.androidrecyclerviewanimation2.databinding.MovieListItemBinding;

public class MovieViewHolder extends RecyclerView.ViewHolder {

    protected final static long ANIMATION_DURATION = 150L;
    protected final static float ANIMATED_TRANSLATION_AMOUNT = 50L;

    private final MovieListItemBinding binding;

    public MovieViewHolder(MovieListItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Movie movie, MovieAdapter.MovieItemClick listener, long position) {
        binding.setMovie(movie);
        binding.setListener(listener);
        binding.setItemId(position);
        binding.executePendingBindings();
    }

    public void animateTransition(Float startTranslationValue, Float finalTranslationValue, Float startAlphaValue, Float finalAlphaValue) {
        ObjectAnimator titleTranslationAnimator = ObjectAnimator.ofFloat(binding.txtMovieTitle, "translationY", startTranslationValue, finalTranslationValue);
        ObjectAnimator titleAlphaAnimator = ObjectAnimator.ofFloat(binding.txtMovieTitle, "alpha", startAlphaValue, finalAlphaValue);

        ObjectAnimator typeTranslationAnimator = ObjectAnimator.ofFloat(binding.txtMovieType, "translationY", startTranslationValue, finalTranslationValue);
        ObjectAnimator typeAlphaAnimator = ObjectAnimator.ofFloat(binding.txtMovieType, "alpha", startAlphaValue, finalAlphaValue);

        AnimatorSet titleAnimator = new AnimatorSet();
        titleAnimator.playTogether(titleTranslationAnimator, titleAlphaAnimator);
        titleAnimator.setDuration(ANIMATION_DURATION);

        AnimatorSet typeAnimator = new AnimatorSet();
        typeAnimator.playTogether(typeTranslationAnimator, typeAlphaAnimator);
        typeAnimator.setDuration(ANIMATION_DURATION);
        typeAnimator.setStartDelay(ANIMATION_DURATION);

        titleAnimator.start();
        typeAnimator.start();
    }
}