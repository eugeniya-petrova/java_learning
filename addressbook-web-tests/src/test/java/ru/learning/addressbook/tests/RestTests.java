package ru.learning.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
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

public class RestTests {
	
	@Test
	public void testCreateIssue() throws IOException {
		Set<Issue> issueSetBefore = getIssues();
		Issue newIssue = new Issue().withSubject("test subj").withDescription("test descr");
		int issueId = createIssue(newIssue);
		Set<Issue> issueSetAfter = getIssues();
		issueSetBefore.add(newIssue.withId(issueId));
		assertEquals(issueSetAfter, issueSetBefore);
	}
	
	private Set<Issue> getIssues() throws IOException {
		String json = getExecutor().execute(Request.Get("https://bugify.stqa.ru/api/issues.json")).returnContent().asString();
		JsonElement parsed = new JsonParser().parse(json);
		JsonElement issues = parsed.getAsJsonObject().get("issues");
		return new Gson().fromJson(issues, new TypeToken<Set<Issue>>(){}.getType());
	}
	
	private int createIssue(Issue newIssue) throws IOException {
		String json = getExecutor().execute(Request.Post("https://bugify.stqa.ru/api/issues.json")
				.bodyForm(new BasicNameValuePair("subject", newIssue.getSubject()), new BasicNameValuePair("description", newIssue.getDescription())))
				.returnContent().asString();
		JsonElement parsed = new JsonParser().parse(json);
		return parsed.getAsJsonObject().get("issue_id").getAsInt(); //возвращаем id созданной задачи
	}
	
	private String getIssueStateNameById(int issueId) throws IOException {
		String json = getExecutor().execute(Request.Get(String.format("https://bugify.stqa.ru/api/issues/%s.json", issueId)))
				.returnContent().asString();
		JsonElement parsed = new JsonParser().parse(json);
		return parsed.getAsJsonObject().get("state_name").getAsString(); //возвращаем статус задачи
	}
	
	private Executor getExecutor() {
		return Executor.newInstance().auth("288f44776e7bec4bf44fdfeb1e646490", "");
	}
}
