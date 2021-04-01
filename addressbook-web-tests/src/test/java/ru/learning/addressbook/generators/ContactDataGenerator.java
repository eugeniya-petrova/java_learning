package ru.learning.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.learning.addressbook.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.ArrayList;

public class ContactDataGenerator {

    @Parameter(names = "-c", description = "Contact count")
    public int count;

    @Parameter(names = "-f", description = "Target file")
    public String file;

    @Parameter(names = "-d", description = "Data format")
    public String format;

    public static void main(String[] args) throws IOException {
        ContactDataGenerator generator = new ContactDataGenerator();
        JCommander jCommander = new JCommander(generator);
        try {
            jCommander.parse(args);
        } catch (ParameterException ex) {
            jCommander.usage();
            return;
        }
        generator.run();
    }

    private void run() throws IOException {
        List<ContactData> contacts = generateContacts(count);
        if (format.equals("csv")) {
            saveAsCsv(contacts, new File(file));
        } else if (format.equals("json")) {
            saveAsJson(contacts, new File(file));
        } else {
            System.out.println("unrecognized format" + format);
        }

    }

    private void saveAsCsv(List<ContactData> contacts, File file) throws IOException {
        //System.out.println(new File(".").getAbsolutePath());
        try (Writer writer = new FileWriter(file)) {
            for (ContactData contact : contacts) {
                writer.write(String.format("%s;%s;%s;%s;%s;%s;%s\n", contact.getFirstName(), contact.getLastName(),
                        contact.getPhoto().getPath(), contact.getAddress(),
                        contact.getHomePhone(), contact.getEmail(), contact.getAddress2()));
            }
        }
    }

    private void saveAsJson(List<ContactData> contacts, File file) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(contacts);
        try (Writer writer = new FileWriter(file)) {
            writer.write(json);
        }
    }

    private List<ContactData> generateContacts(int count) {
        List<ContactData> contacts = new ArrayList<ContactData>();
        File photo = new File("src/test/resources/robot.png");
        for (int i = 0; i < count; i++) {
            contacts.add(new ContactData().withFirstName(String.format("имя %s", i)).withLastName(String.format("фамилия %s", i)).withPhoto(photo)
                    .withAddress(String.format("address 1-%s", i)).withHomePhone(String.format("%s-%s-%s", i, i, i))
                    .withEmail(String.format("test-%s@test.ru", i)).withAddress2(String.format("address 2-%s", i)));
        }
        return contacts;
    }
}