package continuum.cucumber.Page;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.BuildWithDetails;
import com.offbytwo.jenkins.model.JobWithDetails;

public class JenkinsJobVersion {
	
	
	/**
	 * 
	 * @param jenkinsJob
	 * @param userName
	 * @param pwd
	 * @return
	 * 
	 * This method is used to fetch the MSI package Last Successfull build no from artifactory
	 */
	
	public String agentSetupDownload(String jenkinsJob,String userName, String pwd){
		JenkinsServer jenkins;
		String jenkinsBuildNo ="";
		try {
			jenkins = new JenkinsServer(new URI(jenkinsJob), userName, pwd);
			try {
				JobWithDetails smokeTestJob = jenkins.getJob("dev_platform-windows-agent-package");
				BuildWithDetails details = smokeTestJob.getLastSuccessfulBuild().details();
				jenkinsBuildNo = String.valueOf(details.getNumber());
				System.out.println("Jenkins Build Number is : " + details.getNumber());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jenkinsBuildNo;
	}
	

	/*public static void main(String[] args) {
		JenkinsServer jenkins;
		try {
			jenkins = new JenkinsServer(new URI("http://ci.corp.continuum.net:8080/"), "rakesh.karkare", "Rocku@12");
			try {
				JobWithDetails smokeTestJob = jenkins.getJob("dev_platform-windows-agent-package");
				BuildWithDetails details = smokeTestJob.getLastSuccessfulBuild().details();
				System.out.println("Jenkins Build Number is : " + details.getNumber());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}*/

}
