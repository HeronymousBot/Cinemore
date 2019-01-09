package com.example.heronymousbot.cinemore.DatabaseUtils;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.heronymousbot.cinemore.DatabaseUtils.FavoriteMovie;

import java.util.List;

@Dao
public interface FavoriteMovieDao {

    @Insert
    void insert(FavoriteMovie favoriteMovie);

    @Update
    void update(FavoriteMovie favoriteMovie);

    @Delete
    void delete(FavoriteMovie favoriteMovie);

    @Query("DELETE FROM favorite_movies_table")
    void deleteAllMovies();

    @Query("DELETE FROM favorite_movies_table WHERE movieID = :movieId")
    void deleteById(String movieId);

    @Query("SELECT * FROM favorite_movies_table WHERE movieID = :movieId")
    List<FavoriteMovie> foundById(String movieId);


    @Query("SELECT * FROM favorite_movies_table ORDER BY movieTitle ASC")
    LiveData<List<FavoriteMovie>> getAllFavoriteMovies();


}
