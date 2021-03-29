package ru.learning.mantis.appmanager;

import biz.futureware.mantis.rpc.soap.client.*;
import ru.learning.mantis.model.Project;
import ru.learning.mantis.model.Issue;

import javax.xml.rpc.ServiceException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class SoapHelper {

    private ApplicationManager app;

    public SoapHelper(ApplicationManager app) {
        this.app = app;
    }

    public Set<Project> getProjects() throws MalformedURLException, ServiceException, RemoteException {
        MantisConnectPortType mc = getMantisConnect();
        ProjectData[] projects = mc.mc_projects_get_user_accessible(app.getProperty("web.adminLogin"), app.getProperty("web.adminPassword")); //поиск проектов, доступных из-под учётки админа

        //преобразование полученных ProjectData в модельный объект типа Project
        return Arrays.asList(projects).stream()
                .map((p) -> new Project().withId(p.getId().intValue()).withName(p.getName()))
                .collect(Collectors.toSet());
    }

    public Issue addIssue(Issue issue) throws MalformedURLException, ServiceException, RemoteException {
        MantisConnectPortType mc = getMantisConnect();
        String[] categories = mc.mc_project_get_categories(app.getProperty("web.adminLogin"), app.getProperty("web.adminPassword"), BigInteger.valueOf(issue.getProject().getId()));

        //Из своего модельного объекта Issue строим объект IssueData, имеющий нужную структуру для API
        IssueData issueData = new IssueData();
        issueData.setSummary(issue.getSummary());
        issueData.setDescription(issue.getDescription());
        issueData.setProject(new ObjectRef(BigInteger.valueOf(issue.getProject().getId()), issue.getProject().getName()));
        issueData.setCategory(categories[0]);
        BigInteger issueId = mc.mc_issue_add(app.getProperty("web.adminLogin"), app.getProperty("web.adminPassword"), issueData);
        IssueData newIssueData = mc.mc_issue_get(app.getProperty("web.adminLogin"), app.getProperty("web.adminPassword"), issueId);

        //обратное преобразование в модельный объект Issue
        return new Issue().withId(newIssueData.getId().intValue())
                .withSummary(newIssueData.getSummary()).withDescription(newIssueData.getDescription())
                .withProject(new Project().withId(newIssueData.getProject().getId().intValue())
                        .withName(newIssueData.getProject().getName()));
    }

    public String getIssueStatus(int issueId) throws MalformedURLException, ServiceException, RemoteException {
        MantisConnectPortType mc = getMantisConnect();
        IssueData issueData = mc.mc_issue_get(app.getProperty("web.adminLogin"), app.getProperty("web.adminPassword"), BigInteger.valueOf(issueId));
        Issue issue = new Issue().withId(issueData.getId().intValue()).withStatus(issueData.getStatus().getName());
        return issue.getStatus();
    }

    //метод, создающий соединение с Мантисом
    private MantisConnectPortType getMantisConnect() throws MalformedURLException, ServiceException {
        return new MantisConnectLocator().getMantisConnectPort(new URL(app.getProperty("web.baseURL") + "api/soap/mantisconnect.php"));
    }
}
