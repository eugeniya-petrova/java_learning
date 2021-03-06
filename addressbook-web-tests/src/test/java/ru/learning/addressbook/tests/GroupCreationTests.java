package ru.learning.addressbook.tests;

import org.testng.annotations.Test;
import ru.learning.addressbook.model.GroupData;
import ru.learning.addressbook.model.GroupSet;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase {

    @Test
    public void testGroupCreation() throws Exception {
        app.goTo().groupPage();
        GroupSet before = app.group().set();
        GroupData group = new GroupData().withName("Group 2802-02").withHeader("Группа 28 02").withFooter("Группа 28 02 2021");
        app.group().create(group);
		assertThat(app.group().count(), equalTo(before.size() + 1));// сравниваем количество групп до и после
        GroupSet after = app.group().set();

        //group.withId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
        assertThat(after, equalTo(
                before.withAdded(group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
    }

}
