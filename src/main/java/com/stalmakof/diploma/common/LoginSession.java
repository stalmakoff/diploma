package com.stalmakof.diploma.common;

import com.stalmakof.diploma.models.User;

public class LoginSession {
	protected static User loggedUser = null;

	public LoginSession() {
	}

	public static void createSession(User user) {
		loggedUser = user;
	}

	public static User getSession() {
		return loggedUser;
	}

	public static boolean checkSession() {
		if (loggedUser != null) {
			return true;
		} else {
			return false;
		}
	}

	public static void logout() {
		loggedUser = null;
	}
}
