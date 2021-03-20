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

    }

    @Test(enabled = true)
    public void testContactAdditionToGroup() {

        ContactData contact; // контакт, который будем добавлять
		GroupSet groupSetBefore; //набор групп этого контакта
		GroupData targetGroup; //группа, в которую будем добавлять
		ContactSet contactSetBefore; //набор контактов этой группы
		
		ContactSet contactSet = app.db().contactSet(); //все контакты в бд
		ContactSet contactsNotInAllGroups = new ContactSet();
		
		//ищем контакты, которые добавлены не во все группы
		for (ContactData c : contactSet) {
			if (c.getGroupSet().size() < app.db().groupSet().size()) {
				contactsNotInAllGroups = contactsNotInAllGroups.withAdded(c);
			}
		}
		
		if (contactsNotInAllGroups.size() == 0) { //если все контакты добавлены во все группы, существующие в бд
		    contact = app.db().contactSet().iterator().next();
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("one else group")); //создаём ещё одну группу
            //app.goTo().homePage();
        } else {
			contact = contactsNotInAllGroups.iterator().next();
		}

        groupSetBefore = contact.getGroupSet();
		
        //получаем разницу между списком всех групп в бд и списком групп, в которые уже добавлен контакт - это те группы, в которые контакт можно добавить
        GroupSet groupSet = app.group().diffGroupSets(app.db().groupSet(), groupSetBefore);
        targetGroup = groupSet.iterator().next();
        contactSetBefore = targetGroup.getContactSet(); //получаем список контактов той группы, в которую будем добавлять
        app.goTo().homePage();
        app.contact().addToGroup(contact, targetGroup);

        assertThat(app.contact().count(), equalTo(contactSetBefore.size() + 1)); //сравниваем количество контактов в конкретной группе до и после
        GroupSet groupSetAfter = app.db().contactById(contact.getId()).getGroupSet(); //по id получаем из бд контакт, добавленный в группу, получаем его список групп
        ContactSet contactSetAfter = app.db().groupById(targetGroup.getId()).getContactSet(); //по id получаем из бд группу, в которую добавили контакт, получаем её список контактов
        assertThat(groupSetAfter, equalTo(groupSetBefore.withAdded(targetGroup)));
        assertThat(contactSetAfter, equalTo(contactSetBefore.withAdded(contact)));
		
		verifyContactsInGroupUI(targetGroup);
    }

}