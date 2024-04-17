package com.example.adambackend.common;

public class CommonUtil {
	public static boolean isNotNull(Object obj) {
		if (null == obj || obj.equals("")) {
			return false;
		}
		return true;
	}
}
