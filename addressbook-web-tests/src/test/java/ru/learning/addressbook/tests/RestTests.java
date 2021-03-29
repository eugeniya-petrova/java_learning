package ru.learning.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Set;

import ru.learning.addressbook.model.Issue;

import static org.testng.Assert.assertEquals;

public class RestTests extends TestBase{

    @Test
    public void testCreateIssue() throws IOException {
        Set<Issue> issueSetBefore = app.rest().getIssues();
        Issue newIssue = new Issue().withSubject("test subj").withDescription("test descr");
        int issueId = app.rest().createIssue(newIssue);
        Set<Issue> issueSetAfter = app.rest().getIssues();
        issueSetBefore.add(newIssue.withId(issueId));
        assertEquals(issueSetAfter, issueSetBefore);
    }

    @Test
    public void testGetIssueStateNameById() throws IOException {
        String stateName = app.rest().getIssueStateNameById(761);
        System.out.println(stateName);
    }



}
