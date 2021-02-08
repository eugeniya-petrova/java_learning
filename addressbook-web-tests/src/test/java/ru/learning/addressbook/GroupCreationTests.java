package ru.learning.addressbook;

import org.testng.annotations.Test;

public class GroupCreationTests extends TestBase {

    @Test
    public void testGroupCreation() throws Exception {
        gotoGroupPage();
        initGroupCreation();
        fillGroupForm(new GroupData("Group 0702-06", "Группа 7 февраля", "Группа 7 февраля 2021"));
        submitGroupCreation();
        returnToGroupPage();
    }

}
