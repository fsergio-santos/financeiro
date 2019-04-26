package com.financeiro.model.dto;


public class ChangePassword {

    private String newPassword;
    
    private String confirmePassword;
    
    public ChangePassword() {
	}

	
	public ChangePassword(String newPassword, String confirmePassword) {
		this.newPassword = newPassword;
		this.confirmePassword = confirmePassword;
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
