/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.wpu.users;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Krešimir Pripužić <kresimir.pripuzic@fer.hr>
 */
public class UserDataFactory {

    public static List<UserData> getData() {
        List<UserData> data = new LinkedList<>();

        data.add(createUserData(UUID.randomUUID().toString(), "Pero", "Perić", true, "DAILY", "Unska 3", 10000, "Zagreb"));
        data.add(createUserData(UUID.randomUUID().toString(), "Ana", "Štefok", true, "MONTHLY", "Jadranska 65", 10000, "Zagreb"));
        data.add(createUserData(UUID.randomUUID().toString(), "Maja", "Brlek", false, null, "Slavonska 34", 21000, "Split"));
        data.add(createUserData(UUID.randomUUID().toString(), "Ivan", "Babić", true, "WEEKLY", "Zagorska 32", 31000, "Osijek"));
        data.add(createUserData(UUID.randomUUID().toString(), "Petra", "Horvat", false, null, "Dubrovačka 212", 51000, "Rijeka"));

        return data;
    }

    private static UserData createUserData(String uuid, String firstName, String lastName, boolean receiveEmails, String emailType, String street, int postalCode, String city) {

        UserData userData = new UserData();

        userData.setUuid(uuid);
        userData.setFirstName(firstName);
        userData.setLastName(lastName);
        userData.setReceiveEmails(receiveEmails);
        if (receiveEmails) {
            userData.setEmailType(emailType);
        } else {
            userData.setEmailType(null);
        }
        userData.setStreet(street);
        userData.setPostalCode(postalCode);
        userData.setCity(city);

        return userData;
    }
}
