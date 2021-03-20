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

    @Test(enabled = true)
    public void testContactRemovalFromGroup() {
		
		ContactData contact; // контакт, который будем удалять
		GroupSet groupSetBefore; //набор групп этого контакта
		GroupData parentGroup; //группа, из которой будем удалять
		ContactSet contactSetBefore; //набор контактов этой группы
		
		ContactSet contactSet = app.db().contactSet(); //все контакты в бд
		ContactSet contactsInGroup = new ContactSet();
		
		//ищем контакты, которые уже есть в какой-нибудь группе
		for (ContactData c : contactSet) {
			if (c.getGroupSet().size() > 0) {
				contactsInGroup = contactsInGroup.withAdded(c);
			}
		}
		
		if (contactsInGroup.size() == 0) { //если нет ни одного контакта, входящего в какую-нибудь группу
		    contact = app.db().contactSet().iterator().next();
			parentGroup = app.db().groupSet().iterator().next();
            app.goTo().homePage();
            app.contact().addToGroup(contact, parentGroup); //предварительно добавляем любой контакт в любую группу
			groupSetBefore = app.db().contactById(contact.getId()).getGroupSet(); //по id заново получаем из бд контакт, получаем список его групп
			contactSetBefore = app.db().groupById(parentGroup.getId()).getContactSet(); //по id заново получаем из бд группу, получаем список её контактов
        } else {
			contact = contactsInGroup.iterator().next();
			groupSetBefore = contact.getGroupSet();
			parentGroup = groupSetBefore.iterator().next();
			contactSetBefore = parentGroup.getContactSet();
		}

        app.goTo().homePage();
        app.contact().removeFromGroup(contact, parentGroup);

        assertThat(app.contact().count(), equalTo(contactSetBefore.size() - 1)); //сравниваем количество контактов в конкретной группе до и после
        GroupSet groupSetAfter = app.db().contactById(contact.getId()).getGroupSet(); //по id получаем из бд контакт, который удаляли из группы, получаем его список групп
        ContactSet contactSetAfter = app.db().groupById(parentGroup.getId()).getContactSet(); //по id получаем из бд группу, из которой удалялся контакт, получаем её список контактов
        assertThat(groupSetAfter, equalTo(groupSetBefore.without(parentGroup)));
        assertThat(contactSetAfter, equalTo(contactSetBefore.without(contact)));
		
		verifyContactsInGroupUI(parentGroup);
    }
}