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

import java.util.ArrayList ;

/* All DasRegistry servers must provide these methods: */

public interface DasRegistry {
	
    /*
     * try to connect to the DAS services specified by url,
     * if successfull, register this service in DAS database 
     * 
     * return: in which provides an Exitcode.
     * 1 - service has been registered successfully
     * 0 - unknown error occured
     * 2 - server already in database, not registering a second time
     * 3 - server could not be contacted (checkDasServer failed)
     * 4 - database error -  could not add server to database
     * 5 - unknown capability - one or several of the capabilities are unknown.
     */
    public int registerService(String url, 
			       String adminemail, 
			       String description, 
			       String[] coordinateSystem,
			       String[] capabilities) ;

    /* 
     * return list of available DAS sources 
     * 
     */
    public DasSource[] listServices();
		

    /* retrieve all possible Capabilities of DAS services */
    public String[] getAllCapabilities();
    
}
