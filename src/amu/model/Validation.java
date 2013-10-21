package amu.model;

public class Validation {
	
	private static int PASSWORD_LENGTH = 8;
	
	public boolean validate(String password){
		return (hasCorrectLength(password) && hasUpperCase(password) && hasDigit(password));
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
