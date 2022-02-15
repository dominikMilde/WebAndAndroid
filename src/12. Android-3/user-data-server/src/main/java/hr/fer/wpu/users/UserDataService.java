/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.wpu.users;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

/**
 *
 * @author Krešimir Pripužić <kresimir.pripuzic@fer.hr>
 */
@Service
public class UserDataService {

    private List<UserData> userDatas = UserDataFactory.getData();

    public List<UserData> get() {
        Collections.sort(userDatas, Comparator.comparing(UserData::getUuid));
        return userDatas;
    }

    public void insert(UserData user) {
        user.setUuid(UUID.randomUUID().toString());
        userDatas.add(user);
    }

    public void delete(String uuid) {
        Iterator<UserData> iterator = userDatas.iterator();

        while (iterator.hasNext()) {
            if (iterator.next().getUuid().equals(uuid)) {
                iterator.remove();
            }
        }
    }

    public void update(UserData user, String uuid) {
        Iterator<UserData> iterator = userDatas.iterator();

        boolean removed = false;
        while (iterator.hasNext()) {
            if (iterator.next().getUuid().equals(uuid)) {
                iterator.remove();
                removed = true;
                break;
            }
        }
        if (removed)
            userDatas.add(user);
    }
}
