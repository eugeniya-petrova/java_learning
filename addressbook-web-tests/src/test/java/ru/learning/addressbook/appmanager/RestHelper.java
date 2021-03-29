package ru.learning.addressbook.appmanager;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import ru.learning.addressbook.model.Issue;

import java.io.IOException;
import java.util.Set;

import static com.google.gson.JsonParser.parseString;

public class RestHelper {

    private ApplicationManager app;

    public RestHelper(ApplicationManager app) {
        this.app = app;
    }

    public Set<Issue> getIssues() throws IOException {
        String json = getExecutor().execute(Request.Get(app.getProperty("bugifyURL") + "issues.json")).returnContent().asString();
        JsonElement parsed = parseString(json);
        JsonElement issues = parsed.getAsJsonObject().get("issues");
        return new Gson().fromJson(issues, new TypeToken<Set<Issue>>(){}.getType());
    }

    public int createIssue(Issue newIssue) throws IOException {
        String json = getExecutor().execute(Request.Post(app.getProperty("bugifyURL") + "issues.json")
                .bodyForm(new BasicNameValuePair("subject", newIssue.getSubject()), new BasicNameValuePair("description", newIssue.getDescription())))
                .returnContent().asString();
        JsonElement parsed = parseString(json);
        return parsed.getAsJsonObject().get("issue_id").getAsInt(); //возвращаем id созданной задачи
    }

    public String getIssueStateNameById(int issueId) throws IOException {
        String json = getExecutor().execute(Request.Get(app.getProperty("bugifyURL") + String.format("issues/%s.json", issueId)))
                .returnContent().asString();
        JsonElement parsed = parseString(json);
        JsonElement issues = parsed.getAsJsonObject().get("issues");
        JsonElement line = issues.getAsJsonArray().get(0);
        return line.getAsJsonObject().get("state_name").getAsString(); //возвращаем статус задачи
    }

    public Executor getExecutor() {
        return Executor.newInstance().auth(app.getProperty("bugifyKey"), "");
    }
}
