package ru.learning.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.learning.addressbook.model.ContactData;
import ru.learning.addressbook.model.ContactSet;
import ru.learning.addressbook.model.GroupData;
import ru.learning.addressbook.model.GroupSet;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {
	
	@BeforeMethod
    public void checkPreconditions() {
        if (app.db().groupSet().size() == 0) {
			app.goTo().groupPage();
            app.group().create(new GroupData().withName("for contact add"));
			app.goTo().homePage(); //переход на страницу с контактами
        }
    }

    @DataProvider
    public Iterator<Object[]> validContactsFromCsv() throws IOException {
        List<Object[]> list = new ArrayList<Object[]>();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.csv")))) {
            String line = reader.readLine();
            // чтение первой строки не в цикле, потому что она может быть нулевая
            while (line != null) {
                String[] split = line.split(";");
                list.add(new Object[]{new ContactData()
                        .withFirstName(split[0]).withLastName(split[1]).withPhoto(new File(split[2])).withAddress(split[3])
                        .withHomePhone(split[4]).withEmail(split[5]).withAddress2(split[7])});
                line = reader.readLine();
            }
            return list.iterator();
        }
    }

    @DataProvider
    public Iterator<Object[]> validContactsFromJson() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")))) {
            String json = "";
            String line = reader.readLine();
            while (line != null) {
                json += line;
                line = reader.readLine();
            }
            Gson gson = new Gson();
            List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>() {
            }.getType());
            return contacts.stream().map((c) -> new Object[]{c}).collect(Collectors.toList()).iterator();
        }
    }

    @Test(dataProvider = "validContactsFromJson")
    public void testContactCreation(ContactData contact) throws Exception {
		GroupSet groupSet = app.db().groupSet();
        ContactSet before = app.db().contactSet();
        app.contact().create(contact.inGroup(groupSet.iterator().next()), true);
        app.goTo().homePage();
        assertThat(app.contact().count(), equalTo(before.size() + 1));// сравниваем количество контактов до и после
        ContactSet after = app.db().contactSet();

        //contact.withId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
        assertThat(after, equalTo(before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
		
		verifyContactListInUI();
    }

    @Test(enabled = false)
    public void testCurrentDir() {
        File currentDir = new File(".");
        System.out.println(currentDir.getAbsolutePath());
        File photo = new File("src/test/resources/robot.png");
        System.out.println(photo.getAbsolutePath());
        System.out.println(photo.exists());
    }

}