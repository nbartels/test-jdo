/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mydomain.model.mapping;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import mydomain.model.Email;
import org.datanucleus.ClassConstants;
import org.datanucleus.ExecutionContext;
import org.datanucleus.state.ObjectProvider;
import org.datanucleus.store.rdbms.mapping.datastore.DatastoreMapping;
import org.datanucleus.store.rdbms.mapping.java.SingleFieldMapping;

/**
 *
 * @author viego2
 */
public class EmailMapping extends SingleFieldMapping {
    
    private final EmailConverter converter = new EmailConverter();

    @Override
    public Class getJavaType() {
        return Email.class;
    }

    @Override
    public String getJavaTypeForDatastoreMapping(int index) {
        return ClassConstants.JAVA_LANG_STRING.getName();
    }

    @Override
    public void setObject(ExecutionContext ec, PreparedStatement ps, int[] exprIndex, Object value) {
        System.out.println("setObject short");
        String valString = converter.toDatastoreType((Email)value);
        
        super.setString(ec, ps, exprIndex, valString);        
        //getDatastoreMapping(0).setObject(ps, exprIndex[0], valString);
    }

    @Override
    public void setString(ExecutionContext ec, PreparedStatement ps, int[] exprIndex, String value) {
        System.out.println("setString short");
        setObject(ec, ps, exprIndex, value);
    }
    
    @Override
    public void setObject(ExecutionContext ec, PreparedStatement ps, int[] exprIndex, Object value, ObjectProvider ownerOP, int ownerFieldNumber) {
        System.out.println("setObject long");
        String valString = converter.toDatastoreType((Email)value);
        super.setObject(ec, ps, exprIndex, valString, ownerOP, ownerFieldNumber); //To change body of generated methods, choose Tools | Templates.
    }
    
    

    @Override
    public Object getObject(ExecutionContext ec, ResultSet resultSet, int[] exprIndex) {
        System.out.println("getObject short");
        String value = (String) getDatastoreMapping(0).getObject(resultSet, exprIndex[0]);
        //String value = getDatastoreMapping(0).getString(resultSet, exprIndex[0]);
        //String value = (String)super.getObject(ec, resultSet, exprIndex);
        
        return converter.toMemberType(value);
        
    }

    @Override
    public Object getObject(ExecutionContext ec, ResultSet rs, int[] exprIndex, ObjectProvider ownerOP, int ownerFieldNumber) {
        System.out.println("getObject long");
        String value = (String) super.getObject(ec, rs, exprIndex, ownerOP, ownerFieldNumber); //To change body of generated methods, choose Tools | Templates.
        return converter.toMemberType(value);
    }
    
    
    
    
    
}
