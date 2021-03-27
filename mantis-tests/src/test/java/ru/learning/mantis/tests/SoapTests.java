package ru.learning.mantis.tests;

import org.testng.annotations.Test;
import ru.learning.mantis.model.Project;
import ru.learning.mantis.model.Issue;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class SoapTests extends TestBase{
	
	@Test
	public void testGetProjects() throws MalformedURLException, ServiceException, RemoteException {
		Set<Project> projectSet = app.soap().getProjects();
		System.out.println(projectSet.size());
		for (Project project : projectSet) {
			System.out.println(project.getName());
		}
	}
	
	@Test
	public void testCreateIssue() throws MalformedURLException, ServiceException, RemoteException {
		Set<Project> projectSet = app.soap().getProjects();
		Issue issue = new Issue().withSummary("test summary")
                .withDescription("test descr").withProject(projectSet.iterator().next());
		Issue created = app.soap().addIssue(issue);
		assertEquals(issue.getSummary(), created.getSummary());
	}
}
