package com.ravel.backend.spacePro.utils;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EnvironmentsS3Config {

	// AmazonS3 Client, in this object you have all AWS API calls about S3.
	private AmazonS3 amazonS3;

	// Your bucket URL, this URL is https://{bucket-name}.s3-{region}.amazonaws.com/
	//    @Value("${amazon.s3.endpoint}")
	private String url = "https://ravel-environments.s3.eu-central-1.amazonaws.com/";

	// Your bucket name.
	//    @Value("${amazon.s3.bucket-name}")
	private String bucketName = "ravel-environments";

	// The IAM access key.
	@Value("${amazon.s3.access-key}")
	private String accessKey;

	// The IAM secret key.
	@Value("${amazon.s3.secret-key}")
	private String secretKey;

	// Getters for parents.
	protected AmazonS3 getClient() {
		return amazonS3;
	}

	protected String getUrl() {
		return url;
	}

	protected String getBucketName() {
		return bucketName;
	}

	// This method are called after Spring starts AmazonClientService into your container.
	@PostConstruct
	private void init() {
		// Init your AmazonS3 credentials using BasicAWSCredentials.
		BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

		// Start the client using AmazonS3ClientBuilder, here we goes to make a standard cliente, in the
		// region SA_EAST_1, and the basic credentials.
		this.amazonS3 =
			AmazonS3ClientBuilder
				.standard()
				.withRegion(Regions.EU_CENTRAL_1)
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.build();
	}
}
