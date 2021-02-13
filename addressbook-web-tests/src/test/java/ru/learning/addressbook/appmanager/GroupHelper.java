package ru.learning.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.learning.addressbook.model.GroupData;

public class GroupHelper extends HelperBase {

    public GroupHelper(WebDriver wd) {
        super(wd);
    }

    public void returnToGroupPage() {
        click(By.linkText("group page"));
    }

    public void submitGroupCreation() {
        click(By.name("submit"));
    }

    public void fillGroupForm(boolean update, GroupData groupData, String modif) {
        if (update) {
            type(By.name("group_name"), getLocatorText(By.name("group_name")) + modif);
            type(By.name("group_header"), getLocatorText(By.name("group_header")) + modif);
            type(By.name("group_footer"), getLocatorText(By.name("group_footer")) + modif);
        } else {
            type(By.name("group_name"), groupData.getName());
            type(By.name("group_header"), groupData.getHeader());
            type(By.name("group_footer"), groupData.getFooter());
        }
    }

    /*
    public void updateGroupForm(String modif) {
        type(By.name("group_name"), getLocatorText(By.name("group_name")) + modif);
        type(By.name("group_header"), getLocatorText(By.name("group_header")) + modif);
        type(By.name("group_footer"), getLocatorText(By.name("group_footer")) + modif);
    }
     */

    public void initGroupCreation() {
        click(By.name("new"));
    }

    public void deleteSelectedGroups() {
        click(By.name("delete"));
    }

    public void selectGroup() {
        click(By.name("selected[]"));
    }

    public void initGroupModification() {
        click(By.name("edit"));
    }

    public void submitGroupModification() {
        click(By.name("update"));
    }

    public boolean whereGroup() {
        return isElementPresent(By.name("selected[]"));
    }

    public void createGroup(boolean update, GroupData groupData, String modif) {
        initGroupCreation();
        fillGroupForm(update, groupData, modif);
        submitGroupCreation();
        returnToGroupPage();
    }
}
