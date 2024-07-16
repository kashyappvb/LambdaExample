package com.learning.cloud.example4;

import java.time.LocalDateTime;

import org.apache.http.HttpStatus;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.amazonaws.services.lambda.runtime.logging.LogLevel;
import com.google.gson.Gson;
import com.learning.cloud.example1.User;
import com.learning.cloud.example1.UserException;

public class LambdaExample4 implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

	Gson gson = new Gson();
	
	@Override
	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
		LambdaLogger logger = context.getLogger();
		logger.log("context: " + gson.toJson(context));
		logger.log("input: " + gson.toJson(input));
		
		User user = gson.fromJson(input.getBody(), User.class);
		
		user.setName(user.getName() + " From Lambda");
		
		try {
			if(user.getName().contains("Sam")) {
				throw new UserException("User Not Allowed");
			} else if(user.getName().contains("Run")) {
				throw new RuntimeException("Runtime exception!");
			}
		} catch(UserException e) {
			throw e;
		} catch (RuntimeException e) {
			logger.log("Logging runtime exception : " + e);
		}
		
		APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
		response.setBody(user.toString());
		response.setStatusCode(HttpStatus.SC_OK);
		
		return response;
	}


}
