/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mydomain.model.mapping;

import mydomain.model.Email;
import org.datanucleus.store.types.converters.TypeConverter;

/**
 *
 * @author viego2
 */
public class EmailConverter implements TypeConverter<Email, String>{

    @Override
    public String toDatastoreType(Email x) {
       return x.getUsername() + "@" + x.getHostname(); 
    }

    @Override
    public Email toMemberType(String y) {
        String[] strResults = y.split("@");
        return new Email(strResults[0], strResults[1]);
    }
    
}
