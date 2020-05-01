package com.example.moviesapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.moviesapp.Movie;
import com.example.moviesapp.R;
import com.example.moviesapp.UrlDataIncome;
import com.example.moviesapp.ui.gallery.GalleryViewModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ListView moviesListV;
    private GalleryViewModel galleryViewModel;
    private UrlDataIncome urlDataIncome;
    private ArrayList<Movie> arrayList;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        moviesListV = root.findViewById(R.id.moviesListView);

        urlDataIncome = new UrlDataIncome(getActivity(),R.layout.fragment_home,moviesListV);
        urlDataIncome.execute();

        return root;
    }
}
