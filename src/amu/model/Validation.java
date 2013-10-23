package amu.model;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class Validation {
	
	private static String issues = "";
	
	private static int PASSWORD_LENGTH = 8;
	
	public static boolean validatePassword(String password){
		boolean isValid = true;
		if(password == "" || password == null || password.isEmpty()){
			isValid = false;
		} else if(!hasCorrectLength(password)){
			issues += "Password must be at least " + PASSWORD_LENGTH + " characters long. ";
			isValid = false;
		} else if(!hasUpperCase(password)){
			issues += "Password must contain at least one uppercase letter. ";
			isValid = false;
		} else if(!hasDigit(password)){
			issues += "Password must contain at least one digit. ";
			isValid = false;
		}
		return isValid;
	}
	
	public static String getIssues(){
		String _issues = issues;
		issues = "";
		return _issues;
	}
	
	
	public static boolean validateEmail(String email){
		boolean isValid = true;
		try{
			InternetAddress emailAddress = new InternetAddress(email);
			emailAddress.validate();
		} catch (AddressException e){
			isValid = false;
		}
		return isValid;
		
	}
	
	public static boolean hasCorrectLength(String password){
		return (password.length() >= PASSWORD_LENGTH);
	}
	
	public static boolean hasUpperCase(String password){
		for(int i = 0; i < password.length(); i++){
			if(Character.isUpperCase(password.charAt(i))){
				return true;
			}
		}
		return false;
	}
	
	public static boolean hasDigit(String password){
		for(int i = 0; i < password.length(); i++){
			if(Character.isDigit(password.charAt(i))){
				return true;
			}
		}
		return false;
	}

}
