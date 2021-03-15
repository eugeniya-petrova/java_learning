package ru.learning.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.learning.addressbook.model.ContactData;
import ru.learning.addressbook.model.ContactSet;
import ru.learning.addressbook.model.GroupData;
import ru.learning.addressbook.model.GroupSet;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactRemovalFromGroupTests extends TestBase {

    @BeforeMethod
    public void checkPreconditions() {
        if (app.db().groupSet().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("for remove"));
            app.goTo().homePage(); //переход на страницу с контактами
        }

        if (app.db().contactSet().size() == 0) {
            app.contact().create(new ContactData().withFirstName("for remove").inGroup(app.db().groupSet().iterator().next()), true);
            app.goTo().homePage();
        }

        //добавить ещё проверку, если контакт есть, но он не добавлен ни в какую группу - предварительно добавить
        //if (app.db().contactSet().iterator().next().getGroupSet().size() == 0) {app.contact().addToGroup(contact, someGroup)}
    }

    @Test(enabled = true)
    public void testContactRemovalFromGroup() {
        ContactData contact = app.db().contactSet().iterator().next();
        GroupSet groupSetBefore = contact.getGroupSet();
        GroupData parentGroup = groupSetBefore.iterator().next();
        ContactSet contactSetBefore = parentGroup.getContactSet();
        app.contact().removeFromGroup(contact, parentGroup);

        assertThat(app.contact().count(), equalTo(contactSetBefore.size() - 1)); //сравниваем количество контактов в конкретной группе до и после
        GroupSet groupSetAfter = app.db().contactById(contact.getId()).getGroupSet(); //по id получаем из бд контакт, который удаляли из группы, получаем его список групп
        ContactSet contactSetAfter = app.db().groupById(parentGroup.getId()).getContactSet(); //по id получаем из бд группу, из которой удалялся контакт, получаем её список контактов
        assertThat(groupSetAfter, equalTo(groupSetBefore.without(parentGroup)));
        assertThat(contactSetAfter, equalTo(contactSetBefore.without(contact)));
        //verifyContactListInUI();
    }
}
