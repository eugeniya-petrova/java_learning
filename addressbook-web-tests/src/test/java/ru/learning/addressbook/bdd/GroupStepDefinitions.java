package ru.learning.addressbook.bdd;

import java.io.IOException;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import ru.learning.addressbook.appmanager.ApplicationManager;
import ru.learning.addressbook.model.GroupData;
import ru.learning.addressbook.model.GroupSet;
import org.openqa.selenium.remote.BrowserType;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupStepDefinitions {
	
	private ApplicationManager app;
	private GroupSet groupSet;
	private GroupData newGroup;
	
	@Before //аннотация для Cucumber
	public void init() throws IOException {
		app = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));
		app.init();
	}
	
	@After //аннотация для Cucumber
	public void stop() {
		app.stop();
		app = null;
	}
	
	@Given("^a set of groups$") //проверяется точное соответствие текста
	public void loadGroups() {
		groupSet = app.db().groupSet();
	}
	
	@When("^I create a new group with name (.+), header (.+) and footer (.+)$") //(.+) - входные параметры метода
	public void createGroup(String name, String header, String footer) {
		newGroup = new GroupData().withName(name).withHeader(header).withFooter(footer);
		app.group().create(newGroup);
	}
	
	@Then("^the new set of groups is equal to the old set with added group$")
	public void verifyGroupCreation() {
		GroupSet newGroupSet = app.db().groupSet();
		assertThat(newGroupSet, equalTo(groupSet.withAdded(newGroup)));
	}
}
