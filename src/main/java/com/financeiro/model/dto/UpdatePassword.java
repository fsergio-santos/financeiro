package com.financeiro.model.dto;

public class UpdatePassword {
	
    private String oldPassword;
	
	private String newPassword;
    
    private String confirmePassword;
    
    public UpdatePassword() {
		super();
	}

	public UpdatePassword(String oldPassword, String newPassword, String confirmePassword) {
		super();
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
		this.confirmePassword = confirmePassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmePassword() {
		return confirmePassword;
	}

	public void setConfirmePassword(String confirmePassword) {
		this.confirmePassword = confirmePassword;
	}
    
    
	
	
    

}
