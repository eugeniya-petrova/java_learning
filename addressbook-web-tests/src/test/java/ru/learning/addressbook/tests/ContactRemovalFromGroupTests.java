package ru.learning.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.learning.addressbook.model.ContactData;
import ru.learning.addressbook.model.ContactSet;
import ru.learning.addressbook.model.GroupData;
import ru.learning.addressbook.model.GroupSet;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactRemovalFromGroupTests extends TestBase {

    @BeforeMethod
    //@DataProvider
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

    }

    @Test
    public void testContactRemovalFromGroup() {
		
		ContactData contact = app.db().contactSet().iterator().next();
		
		if (contact.getGroupSet().size() == 0) { //если контакт не связан ни с какой группой
            app.goTo().homePage();
            app.contact().addToGroup(contact, app.db().groupSet().iterator().next()); //предварительно добавляем контакт в любую группу
        }
		
        GroupSet groupSetBefore = app.db().contactById(contact.getId()).getGroupSet(); //по id заново получаем из бд контакт, получаем список его групп
        GroupData parentGroup = groupSetBefore.iterator().next();
        ContactSet contactSetBefore = parentGroup.getContactSet(); //получаем список контактов той группы, из которой будем удалять
		app.goTo().homePage();
        app.contact().removeFromGroup(contact, parentGroup);

        assertThat(app.contact().count(), equalTo(contactSetBefore.size() - 1)); //сравниваем количество контактов в конкретной группе до и после
        GroupSet groupSetAfter = app.db().contactById(contact.getId()).getGroupSet(); //по id получаем из бд контакт, который удаляли из группы, получаем его список групп
        ContactSet contactSetAfter = app.db().groupById(parentGroup.getId()).getContactSet(); //по id получаем из бд группу, из которой удалялся контакт, получаем её список контактов
        assertThat(groupSetAfter, equalTo(groupSetBefore.without(parentGroup)));
        assertThat(contactSetAfter, equalTo(contactSetBefore.without(contact)));
    }
}
