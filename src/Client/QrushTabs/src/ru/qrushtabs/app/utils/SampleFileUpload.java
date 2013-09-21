package ru.qrushtabs.app.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import ru.qrushtabs.app.profile.ProfileInfo;

public class SampleFileUpload {

	/**
	 * 024. A generic method to execute any type of Http Request and constructs
	 * a response object 025.
	 * 
	 * @param requestBase
	 *            the request that needs to be exeuted 026.
	 * @return server response as <code>String</code> 027.
	 */
	private static String executeRequest(HttpRequestBase requestBase) {
		String responseString = "";

		InputStream responseStream = null;
		HttpClient client = new DefaultHttpClient();
		try {
			HttpResponse response = client.execute(requestBase);
			if (response != null) {
				HttpEntity responseEntity = response.getEntity();

				if (responseEntity != null) {
					responseStream = responseEntity.getContent();
					if (responseStream != null) {
						BufferedReader br = new BufferedReader(
								new InputStreamReader(responseStream));
						String responseLine = br.readLine();
						String tempResponseString = "";
						while (responseLine != null) {
							tempResponseString = tempResponseString
									+ responseLine
									+ System.getProperty("line.separator");
							responseLine = br.readLine();
						}
						br.close();
						if (tempResponseString.length() > 0) {
							responseString = tempResponseString;
						}
					}
				}

			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (responseStream != null) {
				try {
					responseStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		client.getConnectionManager().shutdown();

		return responseString;
	}

	/**
	 * 078. Method that builds the multi-part form data request 079.
	 * 
	 * @param urlString
	 *            the urlString to which the file needs to be uploaded 080.
	 * @param file
	 *            the actual file instance that needs to be uploaded 081.
	 * @param fileName
	 *            name of the file, just to show how to add the usual form
	 *            parameters 082.
	 * @param fileDescription
	 *            some description for the file, just to show how to add the
	 *            usual form parameters 083.
	 * @return server response as <code>String</code> 084.
	 */
	public String executeMultiPartRequest(String urlString, File file,
			String fileName, String fileDescription) {

		HttpPost postRequest = new HttpPost(urlString);
		try {

			MultipartEntity multiPartEntity = new MultipartEntity();

			// The usual form parameters can be added this way

			multiPartEntity.addPart("fileDescription", new StringBody(
					fileDescription != null ? fileDescription : ""));
			multiPartEntity.addPart("fileName", new StringBody(
					fileName != null ? fileName : file.getName()));

			/*
			 * Need to construct a FileBody with the file that needs to be
			 * attached and specify the mime type of the file. Add the fileBody
			 * to the request as an another part. 097. This part will be
			 * considered as file part and the rest of them as usual form-data
			 * parts
			 */
			FileBody fileBody = new FileBody(file, "application/octect-stream");
			multiPartEntity.addPart("file", fileBody);
			multiPartEntity.addPart("username", new StringBody(ProfileInfo.username));
			multiPartEntity.addPart("atoken", new StringBody(ProfileInfo.userToken));
			postRequest.setEntity(multiPartEntity);
		} catch (UnsupportedEncodingException ex) {

			ex.printStackTrace();

		}

		return executeRequest(postRequest);

	}
}
