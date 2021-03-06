package com.example.heronymousbot.cinemore.DatabaseUtils;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = FavoriteMovie.class, version = 1, exportSchema = false)
public abstract class FavoriteMovieDatabase extends RoomDatabase {


    private static FavoriteMovieDatabase instance;

    public abstract FavoriteMovieDao favoriteMovieDao();

    public static synchronized FavoriteMovieDatabase getInstance(Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), FavoriteMovieDatabase.class, "favorite_movie_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }

        return instance;
    }


}
