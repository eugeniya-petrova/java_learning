package ru.learning.addressbook.tests;

import org.testng.annotations.Test;
import ru.learning.addressbook.model.GroupData;

public class GroupDeletionTests extends TestBase {

    @Test
    public void testGroupDeletion() throws Exception {
        app.getNavigationHelper().gotoGroupPage();

        if (! app.getGroupHelper().whereGroup()) {
            app.getGroupHelper().createGroup(new GroupData("for delete", "for delete", null));
        }

        app.getGroupHelper().selectGroup(0);
        app.getGroupHelper().deleteSelectedGroups();
        app.getGroupHelper().returnToGroupPage();
    }

}
