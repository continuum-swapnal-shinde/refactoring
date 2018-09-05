package continuum.cucumber.Page;

import java.io.File;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class UploadObjectSingleOperation {
	
	private static String bucketName ="agentautotesting";
	
	public void uploadFile(AWSCredentials credentials,String uploadFileName,String keyName){
		AmazonS3 s3Client = new AmazonS3Client(credentials);
		try{
			
			System.out.println("Uploading a new object to S3 from a file\n");
			File file = new File(uploadFileName);
			s3Client.putObject(new PutObjectRequest(bucketName,
					keyName, file));
			
		}
		catch(AmazonServiceException ase){
			// The call was transmitted successfully, but Amazon S3 couldn't process 
            // it, so it returned an error response.
			System.out.println("Caught an AmazonServiceException, which "+
					"means your request made it "+
					"to Amazon S3, but was rejected with an error response"+
					"for some reason.");
			System.out.println("Error Message: "+ase.getMessage());
			System.out.println("HTTP Status Code: "+ase.getStatusCode());
			System.out.println("AWS Error Code: "+ase.getErrorCode());
			System.out.println("Error Type: "+ase.getErrorType());
			System.out.println("Request ID: "+ase.getRequestId());
		}
		catch(AmazonClientException ace){
			 // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
			System.out.println("Caught an AmazonClientException, which "+
					"means client encountered "+
					"an internal error while trying to "+
					"communicate with S3. "+
					"such as not being able to access the network.");
			System.out.println("Error Message: "+ace.getMessage());
			
		}
	}
	
	public void deletefile(AWSCredentials credentials,String keyName){
		AmazonS3 s3Client = new AmazonS3Client(credentials);
		try{
			s3Client.deleteObject(new DeleteObjectRequest(bucketName, keyName));
		}
		catch(AmazonServiceException ase){
			System.out.println("Caught an AmazonServiceException.");
			System.out.println("Error Message: "+ase.getMessage());
			System.out.println("HTTP Status Code: "+ase.getStatusCode());
			System.out.println("AWS Error Code: "+ase.getErrorCode());
			System.out.println("Error Type: "+ase.getErrorType());
			System.out.println("Request ID: "+ase.getRequestId());
	
		}
		catch(AmazonClientException ace){
			System.out.println("Caught an AmazonClientException.");
			System.out.println("Error Message: "+ace.getMessage());
		}	
	}
}
