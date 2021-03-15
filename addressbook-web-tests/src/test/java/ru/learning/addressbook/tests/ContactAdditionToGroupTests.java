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

        //добавить проверку - если контакт уже добавлен во все возможные группы - создать в этом случае новую группу
        //if (app.db().contactSet().iterator().next().getGroupSet().size() == (app.db().groupSet().size())) {
         //   app.goTo().groupPage();
         //   app.group().create(new GroupData().withName("one else group"));
         //   app.goTo().homePage();
       // }

    }

    @Test(enabled = true)
    public void testContactAdditionToGroup() {
        ContactData contact = app.db().contactSet().iterator().next();
        GroupSet groupSetBefore = contact.getGroupSet();
        GroupData targetGroup = app.db().groupSet().iterator().next();
        ContactSet contactSetBefore = targetGroup.getContactSet();
        app.contact().addToGroup(contact, targetGroup);

        assertThat(app.contact().count(), equalTo(contactSetBefore.size() + 1)); //сравниваем количество контактов в конкретной группе до и после
        GroupSet groupSetAfter = app.db().contactById(contact.getId()).getGroupSet(); //по id получаем из бд контакт, добавленный в группу, получаем его список групп
        ContactSet contactSetAfter = app.db().groupById(targetGroup.getId()).getContactSet(); //по id получаем из бд группу, в которую добавили контакт, получаем её список контактов
        assertThat(groupSetAfter, equalTo(groupSetBefore.withAdded(targetGroup)));
        assertThat(contactSetAfter, equalTo(contactSetBefore.withAdded(contact)));
        //verifyContactListInUI();
    }
}