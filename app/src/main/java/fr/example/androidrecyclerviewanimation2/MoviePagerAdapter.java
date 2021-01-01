package fr.example.androidrecyclerviewanimation2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class MoviePagerAdapter extends FragmentStateAdapter {

    private List<Fragment> fragments = new ArrayList<>();
    private List<Movie> dataset;

    public MoviePagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = new MovieFragment();
        Bundle args = new Bundle();
        args.putParcelable("movie", dataset.get(position));
        fragment.setArguments(args);
        fragments.add(fragment);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public List<Fragment> getFragments() {
        return fragments;
    }

    public void setDataset(List<Movie> dataset) {
        this.dataset = dataset;
    }
}
