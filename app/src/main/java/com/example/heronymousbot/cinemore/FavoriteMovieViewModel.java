package com.example.heronymousbot.cinemore;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.heronymousbot.cinemore.DatabaseUtils.FavoriteMovie;
import com.example.heronymousbot.cinemore.DatabaseUtils.FavoriteMovieRepository;

import java.util.List;

public class FavoriteMovieViewModel extends AndroidViewModel {

    private FavoriteMovieRepository repository;
    private LiveData<List<FavoriteMovie>> allFavoriteMovies;

    public FavoriteMovieViewModel(@NonNull Application application) {
        super(application);
        repository = new FavoriteMovieRepository(application);
        allFavoriteMovies = repository.getAllFavoriteMovies();
    }

    public void insert(FavoriteMovie favoriteMovie) {
        repository.insert(favoriteMovie);
    }

    public void deleteById(String movieId) {
        repository.deleteById(movieId);
    }

    public boolean foundById(String movieId) {
        return repository.foundById(movieId);
    }

    public void update(FavoriteMovie favoriteMovie) {
        repository.update(favoriteMovie);
    }

    public void delete(FavoriteMovie favoriteMovie) {
        repository.delete(favoriteMovie);
    }


    public void deleteAllFavoriteMovies() {
        repository.deleteAllFavoriteMovies();
    }

    public LiveData<List<FavoriteMovie>> getAllFavoriteMovies() {
        return repository.getAllFavoriteMovies();
    }
}
