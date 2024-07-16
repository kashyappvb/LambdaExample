package com.learning.cloud.example3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification.S3EventNotificationRecord;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.google.gson.Gson;

public class LambdaExample3 implements RequestHandler<S3Event, List<String>>{
	Gson gson = new Gson();
	AmazonS3 s3Client = AmazonS3Client.builder()
			.withCredentials(new DefaultAWSCredentialsProviderChain())
			.build();
	@Override
	public List<String> handleRequest(S3Event s3Event, Context context) {
		LambdaLogger logger = context.getLogger();
		logger.log("context: " + gson.toJson(context));
		logger.log("s3Event: " + gson.toJson(s3Event));
		List<String> data = new ArrayList<>();
		
		for(S3EventNotificationRecord record : s3Event.getRecords()) {
			String bucket = record.getS3().getBucket().getName();
			String key = record.getS3().getObject().getKey();
			
			GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, key);
			S3Object s3Object = s3Client.getObject(getObjectRequest);
			try {
				byte[] dataInBytes = s3Object.getObjectContent().readAllBytes();
				logger.log("Data :" + new String(dataInBytes));
				data.add(new String(dataInBytes));
			} catch (IOException e) {
				logger.log("IO Exception while reading content : " + e.getMessage());
			}
		}
		
		return data;
	}

}
