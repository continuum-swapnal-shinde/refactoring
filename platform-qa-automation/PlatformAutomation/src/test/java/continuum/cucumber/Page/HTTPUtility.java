package continuum.cucumber.Page;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;

import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.DatatypeConverter;

import org.json.JSONArray;
import org.json.JSONObject;

public class HTTPUtility {

	/**
	 * Represents an HTTP connection
	 */
	private static HttpURLConnection httpConn;

	/**
	 * 
	 * @param requestURL
	 *            the URL of the remote server
	 * @return An Httpconnection object
	 * @throws IOException
	 *             thrown if any I/O error occurred
	 */
	public static HttpURLConnection sendGetRequest(String requestURL) throws IOException {
		try {
		    SSLContext sc = SSLContext.getInstance("SSL");
		    sc.init(null, getTrustMgr(), new java.security.SecureRandom());
		    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
		}

		URL url = new URL(requestURL);
		httpConn = (HttpURLConnection) url.openConnection();
		httpConn.setUseCaches(false);
		httpConn.setDoInput(true);
		httpConn.setDoOutput(false);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return httpConn;
	}

	/**
	 * 
	 * @param requestURL
	 *            the URL of the remote server
	 * @param params
	 *            a map containing post data
	 * @return An Httpconnection object
	 * @throws IOException
	 *             thrown if any I/O error occurred
	 */
	public static HttpURLConnection sendPostRequest(String requestURL, Map<String, Object> params) throws IOException {
		try {
		    SSLContext sc = SSLContext.getInstance("SSL");
		    sc.init(null, getTrustMgr(), new java.security.SecureRandom());
		    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
		}

		URL url = new URL(requestURL);
		httpConn = (HttpURLConnection) url.openConnection();

		if (!setup_Connection_POST(httpConn)) {
			return httpConn;
		}

		StringBuilder postData = new StringBuilder();
		for (Map.Entry<String, Object> one_param : params.entrySet()) {
			if (postData.length() != 0)
				postData.append('&');
			postData.append(URLEncoder.encode(one_param.getKey(), "UTF-8"));
			postData.append('=');
			postData.append(URLEncoder.encode(String.valueOf(one_param.getValue()), "UTF-8"));
		}

		byte[] postDataBytes = postData.toString().getBytes(StandardCharsets.UTF_8);
		OutputStream ostream = httpConn.getOutputStream();
		ostream.write(postDataBytes);
		ostream.flush();
		ostream.close();

		return httpConn;
	}

	/**
	 * 
	 * @return a String of the server's response
	 * @throws IOException
	 *             thrown if any I/O error occurred
	 */
	public static String readSingleLineRespone() throws IOException {
		InputStream inputStream = null;
		if (httpConn != null) {
			inputStream = httpConn.getInputStream();
		} else {
			throw new IOException("Connection is not established.");
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

		String response = reader.readLine();
		reader.close();

		return response;
	}

	/**
	 * 
	 * @return an array of Strings of the server's response
	 * @throws IOException
	 *             thrown if any I/O error occurred
	 */
	public static String[] readMultipleLinesRespone() throws IOException {
		InputStream inputStream = null;
		if (httpConn != null) {
			inputStream = httpConn.getInputStream();
		} else {
			throw new IOException("Connection is not established.");
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		List<String> response = new ArrayList<String>();

		String line = "";
		while ((line = reader.readLine()) != null) {
			response.add(line);
		}
		reader.close();

		return (String[]) response.toArray(new String[0]);
	}

	/**
	 * Closes the connection if opened
	 */
	public static void disconnect() {
		if (httpConn != null) {
			httpConn.disconnect();
		}
	}

	public static boolean setup_Connection_POST_WITHHEADER(HttpURLConnection httpConn, JSONObject mainJson,String hashcode)
			throws NoSuchAlgorithmException {
		// httpConn.setRequestProperty("Content-Type",
		// MediaType.APPLICATION_FORM_URLENCODED);
		// create md5 and change key

		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(mainJson.toString().getBytes());
		byte[] digest = md.digest();
		String myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();

		if(hashcode.equalsIgnoreCase("Valid")) {
		httpConn.setRequestProperty("Content-MD5", myHash);}
		else {
			httpConn.setRequestProperty("Content-MD5", myHash+"invalid");
		}
		httpConn.setReadTimeout(10 * 1000);
		httpConn.setConnectTimeout(10 * 1000);
		httpConn.setDoOutput(true);
		try {
			httpConn.setRequestMethod("POST");
		} catch (ProtocolException e1) {
			e1.printStackTrace();
		}
		try {
			httpConn.connect();
		} catch (ConnectException e) {
			System.out.println("Application is not running.");
			return false;
		} catch (Exception e) {
			System.out.println("Unable to connet to Application. Following exception during hitting URL"
					+ e.getClass().getCanonicalName());
			return false;
		}
		return true;

	}

	public static boolean setup_Connection_POST(HttpURLConnection httpConn) {
		// httpConn.setRequestProperty("Content-Type",
		// MediaType.APPLICATION_FORM_URLENCODED);
		httpConn.setRequestProperty("Accept", MediaType.APPLICATION_JSON);
		httpConn.setReadTimeout(10 * 1000);
		httpConn.setConnectTimeout(10 * 1000);
		httpConn.setDoOutput(true);
		try {
			httpConn.setRequestMethod("POST");
		} catch (ProtocolException e1) {
			e1.printStackTrace();
		}
		try {
			httpConn.connect();
		} catch (ConnectException e) {
			System.out.println("Application is not running.");
			return false;
		} catch (Exception e) {
			System.out.println("Unable to connet to Application. Following exception during hitting URL"
					+ e.getClass().getCanonicalName());
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param requestURL
	 *            the URL of the remote server
	 * @param payload
	 *            string containing post data
	 * @return An Httpconnection object
	 * @throws IOException
	 *             thrown if any I/O error occurred
	 */

	public static HttpURLConnection sendPostReqWithPayload(String requestUrl, String payload) throws IOException {
		try {
		    SSLContext sc = SSLContext.getInstance("SSL");
		    sc.init(null, getTrustMgr(), new java.security.SecureRandom());
		    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
		}

		URL url = new URL(requestUrl);
		httpConn = (HttpURLConnection) url.openConnection();

		if (!setup_Connection_POST(httpConn)) {
			return httpConn;
		}

		OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream(), "UTF-8");
		writer.write(payload);
		writer.close();
		return httpConn;

	}

	/**
	 * 
	 * @param requestURL
	 *            the URL of the remote server
	 * @param mainJson
	 *            object containing post data
	 * @return An Httpconnection object
	 * @throws IOException
	 *             thrown if any I/O error occurred
	 */
	public static HttpURLConnection sendPostReqJSONObj(String requestUrl, JSONObject mainJson) throws IOException {
		
		try {
		    SSLContext sc = SSLContext.getInstance("SSL");
		    sc.init(null, getTrustMgr(), new java.security.SecureRandom());
		    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
		}

		URL url = new URL(requestUrl);
		httpConn = (HttpURLConnection) url.openConnection();

		if (!setup_Connection_POST(httpConn)) {
			return httpConn;
		}

		OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream(), "UTF-8");
		writer.write(mainJson.toString());
		writer.close();
		return httpConn;

	}
	
	   private static TrustManager[] getTrustMgr() {
	        
	        TrustManager[] trustAllCerts = new TrustManager[]{
	        	    new X509TrustManager() {
	        	        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	        	            return null;
	        	        }
	        	        public void checkClientTrusted(
	        	            java.security.cert.X509Certificate[] certs, String authType) {
	        	        }
	        	        public void checkServerTrusted(
	        	            java.security.cert.X509Certificate[] certs, String authType) {
	        	        }
	        	    }
	        	};
	        
	        return trustAllCerts;
	    }

	/**
	 * 
	 * @param requestURL
	 *            the URL of the remote server
	 * @param mainJson
	 *            object containing post data
	 * @return An Httpconnection object
	 * @throws IOException
	 *             thrown if any I/O error occurred
	 */
	public static HttpURLConnection sendPostWithHeaderReqJSONObj(String requestUrl, JSONObject mainJson,String hashcode)
			throws IOException {
		URL url = new URL(requestUrl);
		httpConn = (HttpURLConnection) url.openConnection();

		try {
			if (!setup_Connection_POST_WITHHEADER(httpConn, mainJson,hashcode)) {
				return httpConn;
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream(), "UTF-8");
		writer.write(mainJson.toString());
		writer.close();
		return httpConn;

	}

	public static boolean setup_Connection_General(HttpURLConnection conTemp) {
		// conTemp.setRequestProperty("Content-Type", MediaType.APPLICATION_JSON);
		// conTemp.setRequestProperty("Accept", MediaType.APPLICATION_JSON);
		conTemp.setRequestProperty("continuum-agent-os", "windows");
		conTemp.setReadTimeout(10 * 1000);
		conTemp.setConnectTimeout(10 * 1000);
		// conTemp.setDoOutput(true);
		try {
			httpConn.setRequestMethod("DELETE");
		} catch (ProtocolException e1) {
			e1.printStackTrace();
		}
		try {
			conTemp.connect();
		} catch (ConnectException e) {
			System.out.println("application is not running.");
			return false;
		} catch (Exception e) {
			System.out.println("Unable to connet to application. Following exception during hitting URL"
					+ e.getClass().getCanonicalName());
			return false;
		}
		return true;
	}

	public static HttpURLConnection sendDeleteReq(String requestUrl) throws IOException {
		URL url = new URL(requestUrl);
		httpConn = (HttpURLConnection) url.openConnection();

		if (!setup_Connection_General(httpConn)) {
			return httpConn;
		}
		return httpConn;

	}

	public static boolean setup_Connection_POST_header(HttpURLConnection httpConn) {
		// httpConn.setRequestProperty("Content-Type",
		// MediaType.APPLICATION_FORM_URLENCODED);
		httpConn.setRequestProperty("continuum-agent-os", "windows");
		httpConn.setRequestProperty("Accept", MediaType.APPLICATION_JSON);
		httpConn.setReadTimeout(10 * 1000);
		httpConn.setConnectTimeout(10 * 1000);
		httpConn.setDoOutput(true);
		try {
			httpConn.setRequestMethod("POST");
		} catch (ProtocolException e1) {
			e1.printStackTrace();
		}
		try {
			httpConn.connect();
		} catch (ConnectException e) {
			System.out.println("Application is not running.");
			return false;
		} catch (Exception e) {
			System.out.println("Unable to connet to Application. Following exception during hitting URL"
					+ e.getClass().getCanonicalName());
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param string
	 * @return boolean This method is used to check whether string is in UUID format
	 */
	public static boolean isUUID(String string) {
		try {
			UUID.fromString(string);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public static HttpURLConnection sendPostReqJSONObjwithheader(String requestUrl, JSONObject mainJson)
			throws IOException {
		URL url = new URL(requestUrl);
		httpConn = (HttpURLConnection) url.openConnection();

		if (!setup_Connection_POST_header(httpConn)) {
			return httpConn;
		}

		OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream(), "UTF-8");
		writer.write(mainJson.toString());
		writer.close();
		return httpConn;

	}

	public static HttpURLConnection sendPUTReqJSONObj(String requestUrl, org.json.simple.JSONObject mainJson)
			throws IOException {
		// TODO Auto-generated method stub
		URL url = new URL(requestUrl);
		httpConn = (HttpURLConnection) url.openConnection();

		if (!setup_Connection_PUT(httpConn)) {
			return httpConn;
		}

		OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream(), "UTF-8");
		writer.write(mainJson.toString());
		writer.close();
		return httpConn;
	}

	public static boolean setup_Connection_PUT(HttpURLConnection httpConn2) {
		// TODO Auto-generated method stub
		httpConn.setRequestProperty("Accept", MediaType.APPLICATION_JSON);
		httpConn.setReadTimeout(10 * 1000);
		httpConn.setConnectTimeout(10 * 1000);
		httpConn.setDoOutput(true);
		try {
			httpConn.setRequestMethod("PUT");
		} catch (ProtocolException e1) {
			e1.printStackTrace();
		}
		try {
			httpConn.connect();
		} catch (ConnectException e) {
			System.out.println("Application is not running.");
			return false;
		} catch (Exception e) {
			System.out.println("Unable to connect to Application. Following exception during hitting URL"
					+ e.getClass().getCanonicalName());
			return false;
		}
		return true;
	}

	public static HttpURLConnection sendPostReqJSONObj1(String requestUrl, JSONArray mainJson) throws IOException {
		URL url = new URL(requestUrl);
		httpConn = (HttpURLConnection) url.openConnection();

		if (!setup_Connection_POST(httpConn)) {
			return httpConn;
		}

		OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream(), "UTF-8");
		writer.write(mainJson.toString());
		writer.close();
		return httpConn;

	}

	public static HttpURLConnection sendGetRequestWithcontentEncoding(String requestURL) throws IOException {
		// TODO Auto-generated method stub
		URL url = new URL(requestURL);
		httpConn = (HttpURLConnection) url.openConnection();
		httpConn.setRequestProperty("Accept-Encoding", "gzip");
		httpConn.setUseCaches(false);
		httpConn.setDoInput(true);
		httpConn.setDoOutput(false);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return httpConn;
	}

	public static HttpURLConnection sendDeleteRequest(String requestURL) throws IOException {
		// TODO Auto-generated method stub
		URL url = new URL(requestURL);
		httpConn = (HttpURLConnection) url.openConnection();
		httpConn.setRequestMethod("DELETE");
		/*
		 * httpConn.setUseCaches(false); httpConn.setDoInput(true);
		 * httpConn.setDoOutput(false);
		 */

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return httpConn;
	}

	public static HttpURLConnection sendPostReqWithPayloadForAgentMapping(String requestUrl, org.json.simple.JSONObject mainJsonArray) throws IOException {
		// TODO Auto-generated method stub
		URL url = new URL(requestUrl);
		httpConn = (HttpURLConnection) url.openConnection();

		if (!setup_Connection_POST(httpConn)) {
			return httpConn;
		}

		OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream(), "UTF-8");
		writer.write("[".concat(mainJsonArray.toString()).concat("]"));
		System.out.println("PayLoad: "+ "[".concat(mainJsonArray.toString()).concat("]"));
		writer.close();
		return httpConn;
	}

}
