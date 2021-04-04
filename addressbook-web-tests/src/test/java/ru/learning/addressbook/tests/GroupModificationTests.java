package ru.learning.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.learning.addressbook.model.GroupData;
import ru.learning.addressbook.model.GroupSet;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupModificationTests extends TestBase {

    @BeforeMethod
    public void checkPreconditions() {
        if (app.db().groupSet().size() == 0) {
			app.goTo().groupPage();
            app.group().create(new GroupData().withName("for update"));
        }
    }

    @Test
    public void testGroupModification() {
        GroupSet before = app.db().groupSet();
        GroupData modifiedGroup = before.iterator().next();
        GroupData group = new GroupData().withId(modifiedGroup.getId()).withName("Group модиф").withHeader("Group модиф").withFooter("Group модиф");
		app.goTo().groupPage();
        app.group().modify(group);
        assertThat(app.group().count(), equalTo(before.size())); //сравниваем количество групп до и после
        GroupSet after = app.db().groupSet();
        assertThat(after, equalTo(before.without(modifiedGroup).withAdded(group)));
		verifyGroupListInUI();
    }
}
