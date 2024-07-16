package com.learning.cloud.example5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.google.gson.Gson;
import com.learning.cloud.example1.UserException;

public class LambdaExample5 implements RequestHandler<S3Event, Map<String, Object>>  {

	Gson gson = new Gson();
	
	@Override
	public Map<String, Object> handleRequest(S3Event s3Event, Context context) {
		LambdaLogger logger = context.getLogger();
		logger.log("Event : " + gson.toJson(s3Event));
		logger.log("Context : " + gson.toJson(context));
		List<String> keys = new ArrayList<>();
		
		s3Event.getRecords().forEach(s3Record -> {
			String key = s3Record.getS3().getObject().getKey();
			keys.add(key);
		});
		
		logger.log("Keys" + Arrays.toString(keys.toArray()));
		
		try {
		keys.forEach(key -> {
			if(key.contains("ex")) {
				logger.log("Throwing Exception...");
				throw new RuntimeException("Key contains ex");
			}
			else if(key.contains("ab")) {
				logger.log("Throwing ab Exception...");
				throw new UserException("User exeption");
			}
			else if(key.contains("cd")) {
				throw new NullPointerException("Contains cd");
			}
		});
		} catch (NullPointerException e) {
			logger.log("Catching Nullpointer exception : " + e);
		}
		Map<String, Object> response = new HashMap<>();
		response.put("keys", keys);
		response.put("kas", "pvb");
		return response;
	}
	
	

}
