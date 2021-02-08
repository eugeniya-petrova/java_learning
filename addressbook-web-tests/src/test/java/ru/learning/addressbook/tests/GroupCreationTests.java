package ru.learning.addressbook.tests;

import org.testng.annotations.Test;
import ru.learning.addressbook.model.GroupData;

public class GroupCreationTests extends TestBase {

    @Test
    public void testGroupCreation() throws Exception {
        app.gotoGroupPage();
        app.initGroupCreation();
        app.fillGroupForm(new GroupData("Group 0802-01", "Группа 8 февраля", "Группа 8 февраля 2021"));
        app.submitGroupCreation();
        app.returnToGroupPage();
    }

}
