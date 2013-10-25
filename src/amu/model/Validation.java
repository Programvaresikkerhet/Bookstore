package amu.model;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class Validation {
	
	private static String issues = "";
	
	private static int PASSWORD_LENGTH = 8;
	
	//Start: Validation for RegisterCustomer
	public static boolean validatePassword(String password){
		boolean isValid = true;
		if(password == "" || password == null || password.isEmpty()){
			isValid = false;
		}
		if(!hasCorrectLength(password)){
			issues += "Password must be at least " + PASSWORD_LENGTH + " characters long.\n";
			isValid = false;
		}
		if(!hasUpperCase(password)){
			issues += "Password must contain at least one uppercase letter.\n";
			isValid = false;
		}
		if(!hasDigit(password)){
			issues += "Password must contain at least one digit.\n";
			isValid = false;
		}
		if(password.length() > 40){
			issues += "Password must contain a maximum of 40 characters.";
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
		if(email.length() > 255){
			isValid = false;
		} else{
			try{
				InternetAddress emailAddress = new InternetAddress(email);
				emailAddress.validate();
			} catch (AddressException e){
				isValid = false;
			}
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
	//End: Validation for RegisterCustomer
	
	//Start: Validation for all input
	public static String sanitizeInput(String input){
		if(input != null){
			input = input.replace('&', ' ');
			input = input.replace('\\', ' ');
			input = input.replace('/', ' ');
			input = input.replace('|', ' ');
			input = input.replace('%', ' ');
			input = input.replace('#', ' ');
			input = input.replace('{', ' ');
			input = input.replace('}', ' ');
			input = input.replace('*', ' ');
			input = input.replace('|', ' ');
			input = input.replace('$', ' ');
			input = input.replace('<', ' ');
			input = input.replace('>', ' ');
			return input;
		} else{
			return null;
		}
	}
	//End: Validation for all input
	
	//Start: String-length validation
	public static boolean validateStringLength(String input, int length){
		return input.length() <= length;
	}
}
