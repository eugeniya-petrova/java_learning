package ru.learning.addressbook.tests;

import org.testng.annotations.Test;
import ru.learning.addressbook.model.GroupData;

public class GroupModificationTests extends TestBase {

    @Test
    public void testGroupModification() {
        app.getNavigationHelper().gotoGroupPage();
        app.getGroupHelper().selectGroup();
        app.getGroupHelper().initGroupModification();
       // app.getGroupHelper().fillGroupForm(new GroupData("Group 0902 модиф", "Группа 9 февраля модиф", "Группа 9 февраля 2021 модиф"));
        app.getGroupHelper().updateGroupForm(" модифай");
        app.getGroupHelper().submitGroupModification();
        app.getGroupHelper().returnToGroupPage();
    }
}
