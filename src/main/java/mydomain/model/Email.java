/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mydomain.model;

import javax.jdo.annotations.PersistenceCapable;

public class Email {
    
    private String username;
    
    private String hostname;

    public Email(String username, String hostname) {
        this.username = username;
        this.hostname = hostname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }
    
}
