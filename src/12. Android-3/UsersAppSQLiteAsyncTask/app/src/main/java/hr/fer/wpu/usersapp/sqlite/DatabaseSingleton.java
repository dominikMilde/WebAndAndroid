package hr.fer.wpu.usersapp.sqlite;

import android.content.Context;
import androidx.room.Room;

public class DatabaseSingleton {
    private static DatabaseSingleton instance;
    private UserDataDatabase db;

    private DatabaseSingleton(Context context) {
        db = Room.databaseBuilder(context, UserDataDatabase.class, "userdatas").build();

        if (db.userDataDao().getCount() == 0) {
            db.userDataDao().insertAll(UserDataFactory.getData());
        }
    }

    //synchronized due to multiple accessing threads
    public static synchronized DatabaseSingleton getInstance(Context context) {
        if (instance == null || !instance.db.isOpen()) {
            instance = new DatabaseSingleton(context);
        }
        return instance;
    }

    public UserDataDatabase getDB() {
        return db;
    }
}
