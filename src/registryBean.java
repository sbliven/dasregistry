/*
 *                    BioJava development code
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  If you do not have a copy,
 * see:
 *
 *      http://www.gnu.org/copyleft/lesser.html
 *
 * Copyright for this code is held jointly by the individual
 * authors.  These should be listed in @author doc comments.
 *
 * For more information on the BioJava project and its aims,
 * or to join the biojava-l mailing list, visit the home page
 * at:
 *
 *      http://www.biojava.org/
 *
 * Created on 15.04.2004
 * @author Andreas Prlic
 *
 */



package dasregistry ;

import java.io.*;
import java.net.*;
import java.util.*;

import org.apache.axis.*;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.soap.SOAPException;
import org.apache.soap.rpc.Parameter;
import org.apache.soap.rpc.Response;
import org.apache.soap.Fault;
import org.apache.axis.encoding.TypeMappingRegistryImpl;
import org.apache.axis.encoding.ser.BeanSerializer;
import org.apache.axis.encoding.ser.ArraySerializer;

import javax.xml.rpc.Call;
import javax.xml.namespace.QName ;

import  org.biojava.services.das.registry.* ;

public class registryBean {
 
    String dasurl;
    String adminemail;
    String description;
    String coordinateSystem;
    String[] capabilities ;


    public void setCoordinateSystem(String s) {
	coordinateSystem = s ;	
    }
    
    public String getCoordinateSystem(){
	return coordinateSystem ;
    }
    public void setCapabilities (String[] s) {
	capabilities = s ;	
    }

    public String[] getCapabilities () {
	return capabilities ;
    }

    public void setDasurl( String value )
    {
        dasurl = value;
    }

    public void setAdminemail( String value )
    {
        adminemail = value;
    }

    public void setDescription( String value )
    {
        description = value;
    }

    public String getDasurl() { return dasurl; }

    public String getAdminemail() { return adminemail; }

    public String getDescription() { return description; }


    /* list available DAS services */
    public String[][] listServices() {
	
	System.out.println("registryBean: listing services");

	try{

	    Service  service = new Service();
            Call     call    = (Call) service.createCall();

	    String u = "http://127.0.0.1:8080/axis/services/das_registry" ;

	    URL url = new URL (u);

	    call.setTargetEndpointAddress(u);
	    call.setOperationName(new QName("das_registry", "listServices") );
	    call.setReturnType(XMLType.SOAP_ARRAY);

	    System.out.println("registryBean: call.invoke "+url);


	    String[][] sources = (String[][]) call.invoke(new Object [] {});
	    System.out.println("done!");

	    System.out.println(sources);
	    
	    /*
	    int i=0;
	    for (i=0;i<=sources.length -1; i++) {

		int j=0 ;
		for (j=0;j<=2; j++){		    
		    System.out.println("i,j"+i+", "+j+": "+sources[i][j]);		    
		} 
	    } 
	    */
	    return sources;
	    /* 
	    // Check the response.
	    if( !resp.generatedFault() ) {
		Parameter ret = resp.getReturnValue();
		Object value = ret.getValue();            
		System.out.println(value);
	    } else {
		Fault fault = resp.getFault();            
		System.err.println("Generated fault: ");
		System.out.println ("  Fault Code   = " + fault.getFaultCode());  
		System.out.println ("  Fault String = " + fault.getFaultString());
	    }
	    */
	    
 
	    // Check the response.
	    /*
	    if ( resp.generatedFault() ) {
 
		Fault fault = resp.getFault ();
		System.out.println("The call failed: ");
		System.out.println("Fault Code   = " + fault.getFaultCode());
		System.out.println("Fault String = " + fault.getFaultString());

	    }
	    else {
 
		Parameter result = resp.getReturnValue();
		arr = (ArrayList)result.getValue() ;
		System.out.println("list result"+arr);	       
		
		return arr ;
	    }
	    */
	    

	} catch(Exception e) {
	    e.printStackTrace();
	    System.out.println("an exception occured...");
	    
	}  
	return null;
    }


    /* register a new DAS service */
    public int registerMe() {

	try{
	    
	    //URL url = new URL ("http://localhost:8080/soap/servlet/rpcrouter");

	    Service  service = new Service();
            Call     call    = (Call) service.createCall();
	    String u = "http://127.0.0.1:8080/axis/services/das_registry" ;
	    URL url = new URL (u);
	    call.setTargetEndpointAddress(u);
	    call.setOperationName(new QName("das_registry", "registerService") );
	    call.addParameter("url", 
                    org.apache.axis.Constants.XSD_STRING,
                    javax.xml.rpc.ParameterMode.IN);
	    call.addParameter("adminemail", 
                    org.apache.axis.Constants.XSD_STRING,
                    javax.xml.rpc.ParameterMode.IN);
	    call.addParameter("description", 
                    org.apache.axis.Constants.XSD_STRING,
		  javax.xml.rpc.ParameterMode.IN);
	    call.addParameter("coordinateSystem", 
                    org.apache.axis.Constants.XSD_STRING,
		  javax.xml.rpc.ParameterMode.IN);
	    call.addParameter("capabilities", 
                    XMLType.SOAP_ARRAY,
		  javax.xml.rpc.ParameterMode.IN);
	    call.setReturnType(org.apache.axis.Constants.XSD_INT);

	   
	    //System.out.println("invoke!");
	    //Response resp = (org.apache.soap.rpc.Response) call.invoke(new Object[] {dasurl,adminemail,description} );
	    Integer registry_status = (Integer) call.invoke(new Object[] {dasurl,adminemail,description,coordinateSystem,capabilities} );
	    //System.out.println("returned");
	    
	    int status = registry_status.intValue();
	    //System.out.println("resulting value "+registry_status);
	    return status ;
	  
	    
	} catch(Exception e) {
	    e.printStackTrace();
	    System.out.println("an exception occured...");
	    return 0 ;
	}


	//System.out.println("arrived at end!");
	//return 1 ;

    }

 
}


  /*
    some old stuff ...

	    // Check the response.
	    if( !resp.generatedFault() ) {
		Parameter ret = resp.getReturnValue();
		Object value = ret.getValue();            
		System.out.println(value);
	    } else {
		Fault fault = resp.getFault();            
		System.err.println("Generated fault: ");
		System.out.println ("  Fault Code   = " + fault.getFaultCode());  
		System.out.println ("  Fault String = " + fault.getFaultString());
	    }
	    */
	    /* old apache sopa style:
	       
	    // Build the call.
	    Call call = new Call();
	    call.setTargetObjectURI("urn:das_registry");
	    call.setMethodName("registerService");
	    call.setEncodingStyleURI(Constants.NS_URI_SOAP_ENC);
	    Vector params = new Vector();
	    params.addElement(new Parameter("url",         String.class, dasurl, null));
	    params.addElement(new Parameter("adminemail",  String.class, adminemail, null));
	    params.addElement(new Parameter("description", String.class, description, null));
	    call.setParams (params);
 
	    // make the call: note that the action URI is empty because the
	    // XML-SOAP rpc router does not need this. This may change in the
	    // future.
	    Response resp = call.invoke(url, "" );
 
	    // Check the response.
	    if ( resp.generatedFault() ) {
 
		Fault fault = resp.getFault ();
		System.out.println("The call failed: ");
		System.out.println("Fault Code   = " + fault.getFaultCode());
		System.out.println("Fault String = " + fault.getFaultString());
		return 0;
	    }
	    else {
 
		Parameter result = resp.getReturnValue();
		Integer registry_status = (Integer)result.getValue() ;
		int status = registry_status.intValue();

				
		System.out.println("resulting value "+registry_status);
		return status ;
		
	    }

	    */
