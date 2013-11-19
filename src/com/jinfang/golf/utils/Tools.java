package com.jinfang.golf.utils;

import java.util.UUID;

public class Tools {
	public static String getUUID() {

		UUID uuid = UUID.randomUUID();

		String str = uuid.toString();

		String temp = str.substring(0, 7) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23)
				+ str.substring(24);

		return temp;
	}
}
