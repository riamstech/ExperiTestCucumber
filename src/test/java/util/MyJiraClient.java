package util;

import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.BasicVotes;
import com.atlassian.jira.rest.client.api.domain.Comment;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.input.IssueInput;
import com.atlassian.jira.rest.client.api.domain.input.IssueInputBuilder;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MyJiraClient {

	public String username;
	public String password;
	public String jiraUrl;
	public JiraRestClient restClient;

    public MyJiraClient(String username, String password, String jiraUrl) {
        this.username = username;
        this.password = password;
        this.jiraUrl = jiraUrl;
        this.restClient = getJiraRestClient();
    }
    
   
    
   public void RaiseJiraIssue(String scenarioName,String filePath)
   {
	    //MyJiraClient myJiraClient = new MyJiraClient("putturrasheed", "Riams@1988", "http://localhost:8082/");
	    final String issueKey =createIssue("IB", 10004L, "Failure occured in Screnario: "+scenarioName);
        updateIssueDescription(issueKey, "Description is not yet added");
        Issue issue = getIssue(issueKey);
      //  addAttachment(issue.getKey(), "Issue Screenshot",filePath);
        System.out.println("Jira ticket rasied :"+issue.getKey());
   }
   
   
   public void RaiseJiraIssue(String scenarioName,String stepsToReproduce,String errorMessage,String assignedTo,String screenshotPath)
   {
	    //MyJiraClient myJiraClient = new MyJiraClient("putturrasheed", "Riams@1988", "http://localhost:8082/");
	    final String issueKey =createIssue("IB", 10004L, "Failure occured in Screnario: "+scenarioName);
        updateIssueDescription(issueKey, "Description is not yet added");
        Issue issue = getIssue(issueKey);
        addAttachment(issue.getKey(), "Issue Screenshot",screenshotPath);
        updateIssueDescription(issueKey, "Steps To Reroduce:    \n\n\n"+stepsToReproduce +"    \n \n \n Error Message \n \n:"+errorMessage);
        assignedTo(issueKey,assignedTo);
        
        
        System.out.println("Jira ticket rasied :"+issue.getKey());
   }
   
   public void RaiseJiraIssue(String scenarioName)
   {
	   
   }

    public static void main(String[] args) throws IOException
    {

        MyJiraClient myJiraClient = new MyJiraClient("mahammad.rasheed", "Ocbc@2020", "http://localhost:8081/");
        
        myJiraClient.RaiseJiraIssue("Test","hello");
        
       // Issue issue=myJiraClient.getIssue("TES-3");
        
        //System.out.println(issue.getDescription());

      //  final String issueKey = myJiraClient.createIssue("TES", 10004L, "Issue created from Java code");
        //myJiraClient.updateIssueDescription(issueKey, "This is description from my Jira Client");
        //issue = myJiraClient.getIssue(issueKey);
        //System.out.println(issue.getDescription());

       // myJiraClient.voteForAnIssue(issue);

       // System.out.println(myJiraClient.getTotalVotesCount(issueKey));

        //myJiraClient.addComment(issue, "This is comment from my Jira Client");

		/*
		 * List<Comment> comments = myJiraClient.getAllComments(issueKey);
		 * comments.forEach(c -> System.out.println(c.getBody()));
		 */
        
        //myJiraClient.addAttachment(issue.getKey(), "Issue Found","C:\\screenshots\\clickMoreServicesButton05-10-2019_18-26.png");

     //  myJiraClient.deleteIssue(issueKey, true);

       // myJiraClient.restClient.close();
    }

    public String createIssue(String projectKey, Long issueType, String issueSummary) {

        IssueRestClient issueClient = restClient.getIssueClient();
         IssueInput newIssue = new IssueInputBuilder(projectKey, issueType, issueSummary).build();
        return issueClient.createIssue(newIssue).claim().getKey();
    }

    public Issue getIssue(String issueKey) {
        return restClient.getIssueClient().getIssue(issueKey).claim();
    }

    public void voteForAnIssue(Issue issue) {
        restClient.getIssueClient().vote(issue.getVotesUri()).claim();
    }

    public int getTotalVotesCount(String issueKey) {
        BasicVotes votes = getIssue(issueKey).getVotes();
        return votes == null ? 0 : votes.getVotes();
    }

    public void addComment(Issue issue, String commentBody) {
        restClient.getIssueClient().addComment(issue.getCommentsUri(), Comment.valueOf(commentBody));
    }

	/*
	 * public List<Comment> getAllComments(String issueKey) { return
	 * StreamSupport.stream(getIssue(issueKey).getComments().spliterator(), false)
	 * .collect(Collectors.toList()); }
	 */

    public void updateIssueDescription(String issueKey, String newDescription) {
        IssueInput input = new IssueInputBuilder().setDescription(newDescription).build();
        restClient.getIssueClient().updateIssue(issueKey, input).claim();
    }
    
    public void assignedTo(String issueKey, String assignedTo) {
        IssueInput input = new IssueInputBuilder().setAssigneeName(assignedTo).build();
        restClient.getIssueClient().updateIssue(issueKey, input).claim();
    }

    public void deleteIssue(String issueKey, boolean deleteSubtasks) {
        restClient.getIssueClient().deleteIssue(issueKey, deleteSubtasks).claim();
    }

    public JiraRestClient getJiraRestClient() {
        return new AsynchronousJiraRestClientFactory().createWithBasicHttpAuthentication(getJiraUri(), this.username, this.password);
    }

    public URI getJiraUri() {
        return URI.create(this.jiraUrl);
    }
    
    public void addAttachment(String issueKey, String fileName, String filePath) 
    {
    	InputStream in;
		try {
			in = new FileInputStream(new File(filePath));
			restClient.getIssueClient().addAttachment(getIssue(issueKey).getAttachmentsUri(), in, fileName).claim();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
