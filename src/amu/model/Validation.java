package amu.model;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class Validation {
	
	private String issues = "";
	
	private static int PASSWORD_LENGTH = 8;
	
	public boolean validatePassword(String password){
		boolean isValid = true;
		if(password == "" || password == null || password.isEmpty()){
			isValid = false;
		} else{
			if((password.length() < PASSWORD_LENGTH)){
				issues += "The password must be at least " + PASSWORD_LENGTH + " characters long.";
				isValid = false;
			}
			if(!hasUpperCase(password)){
				issues += "\nThe password must have at least one uppercase letter.";
				isValid = false;
			}
			if(!hasDigit(password)){
				issues += "\nThe password must have at least one digit.";
				isValid = false;
			}
		}
		return isValid;
	}
	
	
	public boolean validateEmail(String email){
		boolean isValid = true;
		try{
			InternetAddress emailAddress = new InternetAddress(email);
			emailAddress.validate();
		} catch (AddressException e){
			isValid = false;
		}
		return isValid;
		
	}
	
	
	public String getIssues(){
		return this.issues;
	}
	
	public boolean hasCorrectLength(String password){
		return (password.length() >= PASSWORD_LENGTH);
	}
	
	public boolean hasUpperCase(String password){
		for(int i = 0; i < password.length(); i++){
			if(Character.isUpperCase(password.charAt(i))){
				return true;
			}
		}
		return false;
	}
	
	public boolean hasDigit(String password){
		for(int i = 0; i < password.length(); i++){
			if(Character.isDigit(password.charAt(i))){
				return true;
			}
		}
		return false;
	}

}
