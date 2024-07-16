package com.learning.cloud.example1;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;

public class LambdaExample1_2 implements RequestHandler<Object, Object>{
	Gson gson = new Gson();
	
	@Override
	public User handleRequest(Object request, Context context) {
		LambdaLogger logger = context.getLogger();
		logger.log("context : " + gson.toJson(context));
		logger.log("input : " + gson.toJson(request));
		logger.log("Request Type: " + request.getClass());
		User user = new User();
		user.id=1000;
		return user;
	}

}
