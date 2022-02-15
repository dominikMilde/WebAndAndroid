package hr.fer.wpu.usersapp.sqlite;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {UserData.class}, version = 1)
public abstract class UserDataDatabase extends RoomDatabase {
    public abstract UserDataDao userDataDao();
}