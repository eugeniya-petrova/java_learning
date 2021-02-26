package ru.learning.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.learning.addressbook.model.GroupData;

import java.util.List;

public class GroupDeletionTests extends TestBase {

    @BeforeMethod
    public void checkPreconditions() {
        app.getNavigationHelper().gotoGroupPage();
        if (! app.getGroupHelper().whereGroup()) {
            app.getGroupHelper().createGroup(new GroupData("for delete", "for delete", null));
        }
    }

    @Test
    public void testGroupDeletion() throws Exception {
        List<GroupData> before = app.getGroupHelper().getGroupList();
        int index = before.size() - 1;
        app.getGroupHelper().deleteGroup(index);
        List<GroupData> after = app.getGroupHelper().getGroupList();
        Assert.assertEquals(after.size(), before.size() - 1);

        before.remove(index);
        Assert.assertEquals(before, after);
    }

}
