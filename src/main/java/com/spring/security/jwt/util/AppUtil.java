package com.spring.security.jwt.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public interface AppUtil {
	
	public static String getLogReport(Exception e) {
		StringWriter s=new StringWriter();
		PrintWriter p=new PrintWriter(s);
		e.printStackTrace(p);
		return s.toString();
	}

}
