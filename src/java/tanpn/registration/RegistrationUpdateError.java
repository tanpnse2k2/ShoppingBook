/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tanpn.registration;

import java.io.Serializable;

/**
 *
 * @author Nhat Tan
 */
public class RegistrationUpdateError implements Serializable{
    private String passwordLengthError;
    private String usernameWithPasswordLengthError;
   

    public RegistrationUpdateError() {
    }

    
    /**
     * @return the passwordLengthError
     */
    public String getPasswordLengthError() {
        return passwordLengthError;
    }

    /**
     * @param passwordLengthError the passwordLengthError to set
     */
    public void setPasswordLengthError(String passwordLengthError) {
        this.passwordLengthError = passwordLengthError;
    }

    /**
     * @return the usernameWithPasswordLengthError
     */
    public String getUsernameWithPasswordLengthError() {
        return usernameWithPasswordLengthError;
    }

    /**
     * @param usernameWithPasswordLengthError the usernameWithPasswordLengthError to set
     */
    public void setUsernameWithPasswordLengthError(String usernameWithPasswordLengthError) {
        this.usernameWithPasswordLengthError = usernameWithPasswordLengthError;
    }

   
}
