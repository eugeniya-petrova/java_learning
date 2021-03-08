package ru.learning.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
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
	
	private void run() throws IOException{
        List<ContactData> contactSet = generateContacts(count);
        save(contactSet, new File(file));
    }

    private void save(List<ContactData> contactSet, File file) throws IOException {
        //System.out.println(new File(".").getAbsolutePath());
        try (Writer writer = new FileWriter(file)) {
            for (ContactData contact : contactSet) {
                writer.write(String.format("%s;%s;%s;%s;%s;%s;%s;%s\n", contact.getFirstName(), contact.getLastName(),
                        contact.getPhoto().getPath(), contact.getAddress(),
                        contact.getHomePhone(), contact.getEmail(), contact.getGroup(), contact.getAddress2()));
            }
        }
    }

    private List<ContactData> generateContacts(int count) {
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