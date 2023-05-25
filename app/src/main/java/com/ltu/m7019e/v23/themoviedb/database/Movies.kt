package com.ltu.m7019e.v23.themoviedb.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ltu.m7019e.v23.themoviedb.model.Movie
import com.ltu.m7019e.v23.themoviedb.model.SavedMovie

@Database(entities = [Movie::class, SavedMovie::class], version = 2, exportSchema = false)
abstract class Movies : RoomDatabase() {

    abstract val moviesDao : MoviesDao

    companion object {
        @Volatile
        private var INSTANCE: Movies? = null

        fun getInstance(context: Context): Movies {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    Movies::class.java,
                    "cached_movies"
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
