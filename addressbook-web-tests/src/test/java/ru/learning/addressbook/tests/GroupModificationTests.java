package ru.learning.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.learning.addressbook.model.GroupData;
import ru.learning.addressbook.model.GroupSet;

import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupModificationTests extends TestBase {

    @BeforeMethod
    public void checkPreconditions() {
        app.goTo().groupPage();
        if (app.group().set().size() == 0) {
            app.group().create(new GroupData().withName("for update"));
        }
    }

    @Test(enabled = false)
    public void testGroupModification() {
        GroupSet before = app.group().set();
        GroupData modifiedGroup = before.iterator().next();
        GroupData group = new GroupData().withId(modifiedGroup.getId()).withName("Group модиф").withHeader("Group модиф");
        app.group().modify(group);
        assertThat(app.group().count(), equalTo(before.size()));// сравниваем количество групп до и после
        GroupSet after = app.group().set();
        assertThat(after, equalTo(before.without(modifiedGroup).withAdded(group)));
    }
}
