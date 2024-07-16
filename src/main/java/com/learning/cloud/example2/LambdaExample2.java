package com.learning.cloud.example2;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;

public class LambdaExample2 implements RequestHandler<String, Integer>{

	@Override
	public Integer handleRequest(String input, Context context) {
		LambdaLogger logger = context.getLogger();
		Gson gson = new Gson();
		logger.log("event: " + gson.toJson(context));
		
		return input.length();
	}

}
