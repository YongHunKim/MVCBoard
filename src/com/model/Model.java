package com.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Model {
	public String handlerRequest(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
