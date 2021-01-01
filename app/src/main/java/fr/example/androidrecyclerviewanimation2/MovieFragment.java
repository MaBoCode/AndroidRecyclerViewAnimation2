package fr.example.androidrecyclerviewanimation2;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.navigation.Navigation;
import androidx.palette.graphics.Palette;
import androidx.transition.Transition;
import androidx.transition.TransitionInflater;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.transition.MaterialContainerTransform;
import com.google.android.material.transition.MaterialElevationScale;

import fr.example.androidrecyclerviewanimation2.databinding.FrgMovieBinding;
import fr.example.androidrecyclerviewanimation2.databinding.FrgMoviePagerBinding;

public class MovieFragment extends Fragment {

    protected final static long ANIMATION_DURATION = 400L;
    protected final static float ANIMATED_TRANSLATION_AMOUNT = 450L;

    protected FrgMovieBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FrgMovieBinding.inflate(inflater, container, false);

        Movie movie = MovieFragmentArgs.fromBundle(getArguments()).getMovie();
        binding.setMovie(movie);

        Transition transition = TransitionInflater.from(getContext()).inflateTransition(R.transition.movie_item_transition);
        setSharedElementEnterTransition(transition);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        animateTransition(MovieFragment.ANIMATED_TRANSLATION_AMOUNT, 0f, 0f, 1f);
    }

    public void animateTransition(Float startTranslationValue, Float finalTranslationValue, Float startAlphaValue, Float finalAlphaValue) {

        ObjectAnimator titleTranslationAnimator = ObjectAnimator.ofFloat(binding.txtMovieTitle, "translationX", startTranslationValue, finalTranslationValue);
        ObjectAnimator titleAlphaAnimator = ObjectAnimator.ofFloat(binding.txtMovieTitle, "alpha", startAlphaValue, finalAlphaValue);

        ObjectAnimator typeTranslationAnimator = ObjectAnimator.ofFloat(binding.txtMovieType, "translationX", startTranslationValue, finalTranslationValue);
        ObjectAnimator typeAlphaAnimator = ObjectAnimator.ofFloat(binding.txtMovieType, "alpha", startAlphaValue, finalAlphaValue);

        AnimatorSet titleAnimator = new AnimatorSet();
        titleAnimator.playTogether(titleTranslationAnimator, titleAlphaAnimator);
        titleAnimator.setDuration(ANIMATION_DURATION);

        AnimatorSet typeAnimator = new AnimatorSet();
        typeAnimator.playTogether(typeTranslationAnimator, typeAlphaAnimator);
        typeAnimator.setDuration(ANIMATION_DURATION);
        typeAnimator.setStartDelay(ANIMATION_DURATION / 2);

        titleAnimator.start();
        typeAnimator.start();
    }

    public void setToolbarColor(Context context, FrgMoviePagerBinding pagerBinding, Drawable drawable) {

        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

        Palette p = Palette.from(bitmap).generate();
        Palette.Swatch swatch = p.getLightVibrantSwatch();

        MaterialToolbar tlbMovie = pagerBinding.tlbMovie;

        int backgroundColor = ContextCompat.getColor(context, R.color.white);

        if(swatch != null){
            backgroundColor = swatch.getRgb();
        }

        Drawable icon = ContextCompat.getDrawable(context, R.drawable.ic_back);
        icon.setTint(backgroundColor);

        tlbMovie.setNavigationIcon(icon);
    }
}
