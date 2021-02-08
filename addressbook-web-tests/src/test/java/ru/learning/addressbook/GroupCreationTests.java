package ru.learning.addressbook;

import org.testng.annotations.Test;

public class GroupCreationTests extends TestBase {

    @Test
    public void testGroupCreation() throws Exception {
        gotoGroupPage();
        initGroupCreation();
        fillGroupForm(new GroupData("Group 0802-01", "Группа 8 февраля", "Группа 8 февраля 2021"));
        submitGroupCreation();
        returnToGroupPage();
    }

}
