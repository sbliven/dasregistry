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

import org.apache.axis.encoding.ser.BeanSerializerFactory;
import org.apache.axis.encoding.ser.BeanDeserializerFactory ;
//import javax.xml.rpc.Call;
import org.apache.axis.client.Call ;


import javax.xml.namespace.QName ;

import  org.biojava.services.das.registry.* ;

public class registryBean {

    int numbcoordinateentries ; 
    String dasurl      ;
    String adminemail  ;
    String description ;
    String[] coordinateSystem;
    String[] capabilities ;


    public registryBean() {
	coordinateSystem = new String[0];
	capabilities     = new String[0];
    }

    private String getRegistryEndpoint(){
	String s = "http://127.0.0.1:8080/axis/services/das_registry" ;
	return s;
    }
    
    private URL getRegistryUrl() {
	String u = getRegistryEndpoint();
	try {
	    URL url =new URL (u);
	    return url;
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return null;
    }


    public void setNumbcoordinateentries(int i) {
	if ( i <1) { i = 1;}

	numbcoordinateentries = i ;
    }

    public int getNumbcoordinateentries() {
	if (numbcoordinateentries==0) {
	    numbcoordinateentries=1 ;
	}
	return numbcoordinateentries ;
    }


    public void setCoordinateSystem(String[] s) {
	//System.out.println("set coordinate System");
	coordinateSystem = s ;	
    }
    
    public String[] getCoordinateSystem(){
	//System.out.println("gett coordinate System");
	return coordinateSystem ;
    }

    public String getCoordinateSystem(int position){
	return coordinateSystem[position] ;
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

    public String getDasurl() { 
	return dasurl; 
    }

    public String getAdminemail() { return adminemail; }

    public String getDescription() { return description; }


    public boolean hasAlignmentCapability() {
	boolean hasalignmentcap = false ;
	for (int i=0; i<capabilities.length;i++) {
	    System.out.println("capa:"+capabilities[i]);
	    if ( capabilities[i].equals("alignment") ) {
		hasalignmentcap = true ;
		break ;
	    }
	}
	return hasalignmentcap ;
    }


    
    /* contact to DAS registry server and retrieve all possible capabilities */
    public String[] getAllCapabilities() {
	try{
	    Service  service = new Service();
	    Call     call    = (Call) service.createCall();
	
	    URL url = getRegistryUrl(); 
	    String u = getRegistryEndpoint();
	    call.setTargetEndpointAddress(u);
	    call.setOperationName(new QName("das:das_directory", "getAllCapabilities") );
	    call.setReturnType(XMLType.SOAP_ARRAY);
	
	    System.out.println("registryBean: call.invoke "+url);
	
	
	    String[] all_capabilities = (String[]) call.invoke(new Object [] {});
	
	    return all_capabilities;
	} catch(Exception e) {
	    e.printStackTrace();
	    System.out.println("an exception occured...");
	    
	}  
	return null;
    }



    /* list available DAS services */
    public DasSource[] listServices() {
	
	//System.out.println("registryBean: listing services");

	try{

	    Service  service = new Service();
            Call     call    = (Call) service.createCall();	    

	    QName qn = new QName( "http://localhost/das_directory", "dassource" );

	    // register serialization 

	    call.registerTypeMapping(DasSource.class,  
				     qn, 
				     new BeanSerializerFactory(DasSource.class, qn), 
				     new BeanDeserializerFactory(DasSource.class, qn)
				     ); 


	    URL url = getRegistryUrl();
	    String u = getRegistryEndpoint();
	    call.setTargetEndpointAddress(u);
	    call.setOperationName(new QName("das:das_directory", "listServices") );
	    call.setReturnType(XMLType.SOAP_ARRAY);

	    /*
	    QName qn = new QName( "urn:BeanService", "Order" );

	    call.registerTypeMapping(
				     Order.class, qn,
				     new org.apache.axis.encoding.ser.BeanSerializerFactory(Order.class, qn), 
				     new org.apache.axis.encoding.ser.BeanDeserializerFactory(Order.class, qn)); 
	    
	    */
	    

    


	    //String[][] sources = (String[][]) call.invoke(new Object [] {});
	    DasSource[] sources = (DasSource[]) call.invoke(new Object [] {});
	    	    
	   
	    return sources;
	    

	} catch(Exception e) {
	    e.printStackTrace();
	    System.out.println("an exception occured...");
	    
	}  
	return null;
    }


    /* register a new DAS service */
    public int registerMe() {

	try{
	    
	    Service  service = new Service();
            Call     call    = (Call) service.createCall();
	    
	    URL url = getRegistryUrl();
	    String u = getRegistryEndpoint();
	    System.out.println("registerMe");
	    call.setTargetEndpointAddress(u);
	    call.setOperationName(new QName("das:das_directory", "registerService") );
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
                    XMLType.SOAP_ARRAY,
		  javax.xml.rpc.ParameterMode.IN);
	    call.addParameter("capabilities", 
                    XMLType.SOAP_ARRAY,
		  javax.xml.rpc.ParameterMode.IN);
	    call.setReturnType(org.apache.axis.Constants.XSD_INT);

	    if (coordinateSystem==null){
		System.out.println("coordSystem = null!");
	    }
	   
	    System.out.println("invoke! coordSystem len:"+coordinateSystem.length);
	    //Response resp = (org.apache.soap.rpc.Response) call.invoke(new Object[] {dasurl,adminemail,description} );
	    Integer registry_status = (Integer) call.invoke(new Object[] {dasurl,adminemail,description,coordinateSystem,capabilities} );
	    System.out.println("returned");
	    
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
