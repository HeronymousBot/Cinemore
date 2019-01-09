package com.example.heronymousbot.cinemore.DatabaseUtils;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.heronymousbot.cinemore.DatabaseUtils.FavoriteMovie;
import com.example.heronymousbot.cinemore.DatabaseUtils.FavoriteMovieDao;
import com.example.heronymousbot.cinemore.DatabaseUtils.FavoriteMovieDatabase;

import java.util.List;

public class FavoriteMovieRepository {

    private FavoriteMovieDao favoriteMovieDao;
    private LiveData<List<FavoriteMovie>> allFavoriteMovies;
    private String foundById;


    public FavoriteMovieRepository(Application application) {
        FavoriteMovieDatabase database = FavoriteMovieDatabase.getInstance(application);
        favoriteMovieDao = database.favoriteMovieDao();
        allFavoriteMovies = favoriteMovieDao.getAllFavoriteMovies();


    }

    public void insert(FavoriteMovie favoriteMovie) {
        new InsertMovieAsyncTask(favoriteMovieDao).execute(favoriteMovie);

    }

    public void update(FavoriteMovie favoriteMovie) {
        new UpdateMovieAsyncTask(favoriteMovieDao).execute(favoriteMovie);

    }


    public void deleteById(String movieId) {
        new DeleteMovieByIdAsyncTask(favoriteMovieDao).execute(movieId);
    }

    public boolean foundById(String movieId) {
        if (this.favoriteMovieDao.foundById(movieId).isEmpty()) {
            return false;
        } else {
            return true;
        }

    }

    public void delete(FavoriteMovie favoriteMovie) {
        new DeleteMovieAsyncTask(favoriteMovieDao).execute(favoriteMovie);
    }

    public void deleteAllFavoriteMovies() {
        new DeleteAllMoviesAsyncTask(favoriteMovieDao).execute();

    }

    public LiveData<List<FavoriteMovie>> getAllFavoriteMovies() {
        return allFavoriteMovies;
    }

    private static class InsertMovieAsyncTask extends AsyncTask<FavoriteMovie, Void, Void> {
        private FavoriteMovieDao favoriteMovieDao;

        private InsertMovieAsyncTask(FavoriteMovieDao favoriteMovieDao) {
            this.favoriteMovieDao = favoriteMovieDao;
        }

        @Override
        protected Void doInBackground(FavoriteMovie... favoriteMovies) {
            favoriteMovieDao.insert(favoriteMovies[0]);
            return null;
        }
    }


    private static class UpdateMovieAsyncTask extends AsyncTask<FavoriteMovie, Void, Void> {
        private FavoriteMovieDao favoriteMovieDao;

        private UpdateMovieAsyncTask(FavoriteMovieDao favoriteMovieDao) {
            this.favoriteMovieDao = favoriteMovieDao;
        }

        @Override
        protected Void doInBackground(FavoriteMovie... favoriteMovies) {
            favoriteMovieDao.update(favoriteMovies[0]);
            return null;
        }
    }

    private static class DeleteMovieAsyncTask extends AsyncTask<FavoriteMovie, Void, Void> {
        private FavoriteMovieDao favoriteMovieDao;

        private DeleteMovieAsyncTask(FavoriteMovieDao favoriteMovieDao) {
            this.favoriteMovieDao = favoriteMovieDao;
        }

        @Override
        protected Void doInBackground(FavoriteMovie... favoriteMovies) {
            favoriteMovieDao.delete(favoriteMovies[0]);
            return null;
        }
    }

    private static class DeleteMovieByIdAsyncTask extends AsyncTask<String, Void, Void> {
        private FavoriteMovieDao favoriteMovieDao;

        private DeleteMovieByIdAsyncTask(FavoriteMovieDao favoriteMovieDao) {
            this.favoriteMovieDao = favoriteMovieDao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            favoriteMovieDao.deleteById(strings[0]);
            return null;
        }
    }


    private static class DeleteAllMoviesAsyncTask extends AsyncTask<Void, Void, Void> {
        private FavoriteMovieDao favoriteMovieDao;

        private DeleteAllMoviesAsyncTask(FavoriteMovieDao favoriteMovieDao) {
            this.favoriteMovieDao = favoriteMovieDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            favoriteMovieDao.deleteAllMovies();
            return null;
        }
    }
}
