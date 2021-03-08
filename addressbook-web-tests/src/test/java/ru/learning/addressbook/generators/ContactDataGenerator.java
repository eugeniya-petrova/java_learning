package ru.learning.addressbook.generators;

import ru.learning.addressbook.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.ArrayList;

public class ContactDataGenerator {

    public static void main(String[] args) throws IOException {
        int count = Integer.parseInt(args[0]);
        File file = new File(args[1]);

        List<ContactData> contactSet = generateContacts(count);
        save(contactSet, file);
    }

    private static void save(List<ContactData> contactSet, File file) throws IOException {
        //System.out.println(new File(".").getAbsolutePath());
        try (Writer writer = new FileWriter(file)) {
            for (ContactData contact : contactSet) {
                writer.write(String.format("%s;%s;%s;%s;%s;%s;%s;%s\n", contact.getFirstName(), contact.getLastName(),
                        contact.getPhoto().getPath(), contact.getAddress(),
                        contact.getHomePhone(), contact.getEmail(), contact.getGroup(), contact.getAddress2()));
            }
        }
    }

    private static List<ContactData> generateContacts(int count) {
        List<ContactData> contactSet = new ArrayList<ContactData>();
        File photo = new File("src/test/resources/robot.png");
        for (int i = 0; i < count; i++) {
            contactSet.add(new ContactData().withFirstName(String.format("имя %s", i)).withLastName(String.format("фамилия %s", i)).withPhoto(photo)
                    .withAddress(String.format("address 1-%s", i)).withHomePhone(String.format("%s-%s-%s", i, i, i))
                    .withEmail(String.format("test-%s@test.ru", i)).withGroup("Group модиф").withAddress2(String.format("address 2-%s", i)));
        }
        return contactSet;
    }
}
