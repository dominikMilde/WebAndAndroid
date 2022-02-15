package hr.fer.wpu.usersapp.sqlite;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDataDao {
    @Query("SELECT * FROM userdatas ORDER BY uuid")
    List<UserData> getAll();

    @Query("SELECT * FROM userdatas WHERE uuid = :userUuid")
    UserData getByUuid(String userUuid);

    @Insert
    void insertAll(UserData... users);

    @Insert
    void insert(UserData users);

    @Query("DELETE FROM userdatas")
    void deleteAll();

    @Query("DELETE FROM userdatas WHERE uuid = :uuid")
    void deleteByUuid(String uuid);

    @Query("SELECT COUNT(*) FROM userdatas")
    int getCount();
}