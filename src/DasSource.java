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


package org.biojava.services.das.registry ;

/* a simple class to be returned via SOAP
 */

public class DasSource {
    String url ;
    String adminemail ;
    String description ;
    String[] coordinateSystem ;
    String[] capabilities ;

    public DasSource () {
	url              = "";
	adminemail       = "" ;
	description      = "" ;
	coordinateSystem = null;
	capabilities     = null ;
    }
    
    public String toString() {
	
	String str = "<source>\n\t<uri>"+url+"</uri>\n\t<description>"+description+"</description>\n\t<contact>"+adminemail+"</contact>\n" ;
	    
	for (int i=0;i<coordinateSystem.length;i++){

	    str+="\t<coordinateSystem>"+coordinateSystem[i]+"</coordinateSystem>\n" ;
	}
	   

	for (int i=0;i<capabilities.length;i++){
	    str+="\t<service>http://www.biodas.org/das1/"+capabilities[i]+"</service>\n" ;
	}
	str +="</source>\n";
	return str;

    }


    public void setUrl(String u) {
	url = u ;
    }

    public void setAdminemail (String u) {
	adminemail = u ;
    }
    
    public void setDescription (String u) {
	description = "";
    }
    
    public void setCoordinateSystem (String[] u){
	coordinateSystem=u ;
    }

    public void setCapabilities (String[] u){
	capabilities = u ;
    }
    

    public String getUrl(){return url;}
    public String getAdminemail(){return adminemail;}
    public String getDescription(){return description;}
    public String[] getCapabilities(){return capabilities;}
    public String[] getCoordinateSystem(){return coordinateSystem;}

}
