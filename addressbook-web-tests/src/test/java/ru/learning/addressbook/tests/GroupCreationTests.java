package ru.learning.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.learning.addressbook.model.GroupData;

import java.util.Set;

public class GroupCreationTests extends TestBase {

    @Test(enabled = false)
    public void testGroupCreation() throws Exception {
        app.goTo().groupPage();
        Set<GroupData> before = app.group().set();
        GroupData group = new GroupData().withName("Group 2702-02").withHeader("Группа 27 02").withFooter("Группа 27 02 2021");
        app.group().create(group);
        Set<GroupData> after = app.group().set();
        Assert.assertEquals(after.size(), before.size() + 1);

        //group.withId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
        group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt());
        before.add(group);
        Assert.assertEquals(before, after);
    }

}
