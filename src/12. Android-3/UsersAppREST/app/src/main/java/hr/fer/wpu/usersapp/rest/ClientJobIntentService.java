package hr.fer.wpu.usersapp.rest;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.ArrayList;
import java.util.List;

public class ClientJobIntentService extends JobIntentService {
    static final int JOB_ID = 1234;

    static public void enqueueWork(Context context, Intent work) {
        enqueueWork(context, ClientJobIntentService.class, JOB_ID, work);
    }

    @Override
    protected void onHandleWork(@NonNull Intent work) {
        String action = work.getAction();

        Intent resultIntent = new Intent(Actions.BROADCAST);
        UserDataService service = RestAdapterSingleton.getInstance().create(UserDataService.class);
        switch (action) {
            case Actions.DELETE:
                String uuid = work.getStringExtra("uuid");
                service.deleteByUuid(uuid);
                break;

            case Actions.GET:
                //do nothing
                break;

            case Actions.INSERT:
                UserData userData = (UserData) work.getSerializableExtra("userData");
                service.insert(userData);
                break;

            case Actions.UPDATE:
                userData = (UserData) work.getSerializableExtra("userData");
                service.update(userData, userData.getUuid());
                break;
        }

        //linked list is serializable
        List<UserData> userDatas = service.getAll();
        resultIntent.putExtra("userDatas", (ArrayList) userDatas);

        LocalBroadcastManager.getInstance(this).sendBroadcast(resultIntent);
    }
}
