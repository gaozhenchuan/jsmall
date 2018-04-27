package org.jsmall.web.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

public class LoginControllerTest {
	
    @Test
    public void multiplicationOfZeroIntegersShouldReturnZero() {
    	LoginController tester = new LoginController(); // MyClass is tested

    	HttpServletRequest request = mock(HttpServletRequest.class);
    	HttpServletResponse response = mock(HttpServletResponse.class); 
        when(request.getParameter("password")).thenReturn("secret");
        when(request.getParameter("username")).thenReturn("me");
    	tester.login(request, response);
    }
}
