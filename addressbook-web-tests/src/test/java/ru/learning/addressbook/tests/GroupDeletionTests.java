package ru.learning.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.learning.addressbook.model.GroupData;
import ru.learning.addressbook.model.GroupSet;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupDeletionTests extends TestBase {

    @BeforeMethod
    public void checkPreconditions() {
        if (app.db().groupSet().size() == 0) {
			app.goTo().groupPage();
            app.group().create(new GroupData().withName("for delete"));
        }
    }

    @Test(enabled = true)
    public void testGroupDeletion() throws Exception {
        GroupSet before = app.db().groupSet();
        GroupData deletedGroup = before.iterator().next();
		app.goTo().groupPage();
        app.group().delete(deletedGroup);
        assertThat(app.group().count(), equalTo(before.size() - 1)); //сравниваем количество групп до и после
        GroupSet after = app.db().groupSet();
        assertThat(after, equalTo(before.without(deletedGroup)));
		verifyGroupListInUI();
    }

}
