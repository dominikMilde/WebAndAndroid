package hr.fer.wpu.usersapp.sqlite;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.ArrayList;

public class DatabaseJobIntentService extends JobIntentService {
    static final int JOB_ID = 1234;

    static public void enqueueWork(Context context, Intent work) {
        enqueueWork(context, DatabaseJobIntentService.class, JOB_ID, work);
    }

    @Override
    protected void onHandleWork(@NonNull Intent work) {
        String action = work.getAction();

        Intent resultIntent = new Intent(Actions.BROADCAST);
        UserDataDatabase db = DatabaseSingleton.getInstance(this).getDB();
        switch (action) {
            case Actions.DELETE:
                String uuid = work.getStringExtra("uuid");
                db.userDataDao().deleteByUuid(uuid);
                break;

            case Actions.GET:
                //do nothing
                break;

            case Actions.INSERT:
                UserData userData = (UserData) work.getSerializableExtra("userData");
                db.userDataDao().insert(userData);
                break;

            case Actions.UPDATE:
                userData = (UserData) work.getSerializableExtra("userData");
                db.userDataDao().deleteByUuid(userData.getUuid());
                db.userDataDao().insert(userData);
                break;

            case Actions.CLOSE:
                if (db != null) {
                    if (db.isOpen()) {
                        db.close();
                    }
                    db = null;
                }
                //do not return the result
                return;
        }

        //linked list is serializable
        ArrayList<UserData> userDatas = (ArrayList) db.userDataDao().getAll();
        resultIntent.putExtra("userDatas", userDatas);

        LocalBroadcastManager.getInstance(this).sendBroadcast(resultIntent);
    }
}
