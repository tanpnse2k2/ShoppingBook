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
public class RegistrationDeleteError implements Serializable{
    private String deleteUserIsLoggingError;

    public RegistrationDeleteError() {
    }

    
    /**
     * @return the deleteUserIsLoggingError
     */
    public String getDeleteUserIsLoggingError() {
        return deleteUserIsLoggingError;
    }

    /**
     * @param DeleteUserIsLoggingError the deleteUserIsLoggingError to set
     */
    public void setDeleteUserIsLoggingError(String DeleteUserIsLoggingError) {
        this.deleteUserIsLoggingError = DeleteUserIsLoggingError;
    }
    
}
