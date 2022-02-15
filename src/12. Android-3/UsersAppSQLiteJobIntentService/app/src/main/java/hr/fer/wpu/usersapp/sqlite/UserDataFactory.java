package hr.fer.wpu.usersapp.sqlite;

import java.util.UUID;

public class UserDataFactory {
    public static UserData[] getData() {
        UserData[] data = new UserData[5];

        data[0]=(createUserData(UUID.randomUUID().toString(),"Pero", "Perić", true, "DAILY", "Unska 3", 10000, "Zagreb"));
        data[1]=(createUserData(UUID.randomUUID().toString(),"Ana", "Štefok", true, "MONTHLY", "Jadranska 65", 10000, "Zagreb"));
        data[2]=(createUserData(UUID.randomUUID().toString(),"Maja", "Brlek", false, null, "Slavonska 34", 21000, "Split"));
        data[3]=(createUserData(UUID.randomUUID().toString(),"Ivan", "Babić", true, "WEEKLY", "Zagorska 32", 31000, "Osijek"));
        data[4]=(createUserData(UUID.randomUUID().toString(),"Petra", "Horvat", false, null, "Dubrovačka 212",  51000, "Rijeka"));

        return data;
    }

    private static UserData createUserData(String uuid,
            String firstName, String lastName,
            boolean receiveEmails, String emailType, String street,
            int postalCode, String city) {
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
