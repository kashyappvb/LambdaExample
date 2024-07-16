package com.learning.cloud.example1;

import java.util.Map;

public class LambdaExample1 {
	
	public String handlerString(String name) {
		return "Hello " + name;
	}
	
	public String handlerObject(User user) {
		return "user: " + user;
	}
	
	public String handlerMap(Map<String,String> map) {
		map.forEach((k,v) -> {
			System.out.println("key:" + k + " value:" + v + " datatype:" + v.getClass().getName());
		});
		return "Map: " + map;
	}
}