package ru.learning.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.learning.addressbook.model.ContactData;
import ru.learning.addressbook.model.ContactSet;
import ru.learning.addressbook.model.GroupData;
import ru.learning.addressbook.model.GroupSet;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAdditionToGroupTests extends TestBase {

    ContactData contact = app.db().contactSet().iterator().next();

    @BeforeMethod
    public void checkPreconditions() {
        if (app.db().contactSet().size() == 0) {
            app.contact().create(new ContactData().withFirstName("for add"), true);
            app.goTo().homePage();
        }

        if (app.db().groupSet().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("for add"));
            app.goTo().homePage();
        }

        if (contact.getGroupSet().size() == (app.db().groupSet().size())) { //если контакт добавлен во все группы, существующие в бд
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("one else group")); //создаём ещё одну группу
            app.goTo().homePage();
        }

    }

    @Test(enabled = true)
    public void testContactAdditionToGroup() {
        GroupSet groupSetBefore = contact.getGroupSet();
       // GroupData targetGroup = app.db().groupSet().iterator().next();
        GroupData targetGroup = app.db().groupSet().stream().max((g1, g2) -> Integer.compare(g1.getId(), g2.getId())).get();
        ContactSet contactSetBefore = targetGroup.getContactSet();
        app.contact().addToGroup(contact, targetGroup);

        assertThat(app.contact().count(), equalTo(contactSetBefore.size() + 1)); //сравниваем количество контактов в конкретной группе до и после
        GroupSet groupSetAfter = app.db().contactById(contact.getId()).getGroupSet(); //по id получаем из бд контакт, добавленный в группу, получаем его список групп
        ContactSet contactSetAfter = app.db().groupById(targetGroup.getId()).getContactSet(); //по id получаем из бд группу, в которую добавили контакт, получаем её список контактов
        assertThat(groupSetAfter, equalTo(groupSetBefore.withAdded(targetGroup)));
        assertThat(contactSetAfter, equalTo(contactSetBefore.withAdded(contact)));
    }
}