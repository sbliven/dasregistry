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

import java.util.ArrayList;
import java.util.HashMap ;
import java.sql.* ;


/* a standard DasRegistry service */

public class Simple_DasRegistry implements DasRegistry {

    private static Connection conn ;
    ArrayList all_capabilities;

    /**
     * 
     */
    public Simple_DasRegistry() {
	super();
	// TODO Auto-generated constructor stub

	try {
	    System.out.println("Simple_DasRegistry: init 1");
		
	    // Register MySQL driver
	    Class.forName("com.mysql.jdbc.Driver").newInstance();

	    //String url = new String("jdbc:mysql://192.168.5.21:3306/das_registry"); 
	    String url = new String("jdbc:mysql://81.96.73.32:3306/das_registry"); 
	    
	    String user = "myuserrw";
	    String password = "1canwrite";	    

	    conn = DriverManager.getConnection(url,user,password); 


	    all_capabilities = new ArrayList() ;
	    all_capabilities.add("sequence");
	    all_capabilities.add("structure"); 
	    all_capabilities.add("alignment");
	    all_capabilities.add("types");
	    all_capabilities.add("features"); 
	    all_capabilities.add("entry_points");
	    all_capabilities.add("error_segment");
	    all_capabilities.add("unknown_segment");
	    all_capabilities.add("unknown_feature");
	    all_capabilities.add("feature_by_id");
	    all_capabilities.add("component");
	    all_capabilities.add("supercomponent");
	    all_capabilities.add("dna");
	    all_capabilities.add("stylesheet");






	}
	catch (Exception ex) {
	    ex.printStackTrace();
	} 
    }
    
       
 
    /* check if new DAS service is already in Database
     */
    private boolean isInDatabase(String url) {
	try {
	    String cmd = "SELECT auto_id from registry where url = '" +url + "'" ;
	    PreparedStatement ps = conn.prepareStatement(cmd);
	    
	    ResultSet row = ps.executeQuery();

	    boolean hasNoRows = true;

	    while (row.next()) {
		hasNoRows = false;
		// retrieve data?
	    }
	    
	    if (! hasNoRows) {
		// something found in database!
		return true;
	    }
	    //System.out.println("getFetchSize "+i);
	    
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return false ;
    }





    /* see if a coordinate System is already known, if not, store in database 
       in both cases: 
       link coordinate system to  new DAS service ...
     */
    private void selectInsertCoordinateSystem(String coordsys, int auto_id){
	
	try {
	   
	    String sql ="SELECT coord_auto from coordinateSystems where coordinateSystem=?";
	    PreparedStatement ps =  conn.prepareStatement(sql); 
	    ps.setString(1,coordsys);
	    ResultSet row = ps.executeQuery();

	    // only one result expected ...
	    boolean found = row.next() ;
	    int coord_auto = -1 ;
	    if ( ! found) {
		
		sql = "INSERT INTO coordinateSystems (coordinateSystem) values (?)";
		ps.close() ;
		ps = conn.prepareStatement(sql); 

		ps.setString(1,coordsys);
		
		ps.executeUpdate();
		ps.executeUpdate();
		row = ps.getGeneratedKeys();
		coord_auto=-1 ;
		if ( row.next() ) {
		    coord_auto = row.getInt(1);
		}

	    }


    
	    if ( found) {
		coord_auto = row.getInt(1);
	    } 
	    
	    //and now store in registry2coordinateSystem
	    ps.close() ;
	    
	    sql = "INSERT INTO registry2coordinateSystem (auto_id,coord_auto) values (?,?);";
	    
	    ps = conn.prepareStatement(sql); 

	    ps.setInt(1,auto_id);
	    ps.setInt(2,coord_auto);
	    int r2c_auto = ps.executeUpdate();
	    
	    

	} catch (Exception e) {
	    e.printStackTrace();
	}

    }





    /* store  DAS service in database
     */
    private void storeInDatabase(
			 String url,
			 String adminemail,
			 String description,
			 String[] coordinateSystem,
			 String[] capabilities
			 ) {
	
	try {
	    String sql    = "INSERT INTO registry (url,adminemail,description" ;
	    String endsql = ") values (?,?,?" ;
	    for ( int i=0; i< capabilities.length;i++) {
		// potential security problem ??
		sql    += ","+capabilities[i];
		endsql += ",1";
	    }
	    
	    sql += endsql +")" ;
	    
	    PreparedStatement ps =  conn.prepareStatement(sql); 
	    ps.setString(1,url);
	    ps.setString(2,adminemail);
	    ps.setString(3,description);	    

	    ps.executeUpdate();
	    ResultSet rs = ps.getGeneratedKeys();
	    int auto_id = -1 ;
	    if (rs.next()) {
		auto_id= rs.getInt(1);
	    }
	    //System.out.println("register new server with auto_id "+auto_id);



	    for( int i=0; i<coordinateSystem.length;i++){

		String cs = coordinateSystem[i];
		selectInsertCoordinateSystem(cs,auto_id);
		    
	    }



	    

	} catch (Exception e) {
	    e.printStackTrace() ;
	}
	    
	return ;
    }


    /* check if a DAS server is online
     * at the moment not implemented, yet. Just returns true;
     */       
    private boolean checkDasServer(String url) {
	// do DAS - DSN request to server.
	return true ;
    }


    
    /* check that capabilities are valid */
    
    private boolean check_capabilities(String[] capabilities) {
	
	boolean all_ok = true ;
	for (int i=0; i < capabilities.length; i++){
	    String c = capabilities[i] ;
	    if ( ! all_capabilities.contains(c) ) {
		all_ok = false ;
	    }
	}
	return all_ok ;
    }


  

    /* helper function for get_capabilities */
    private boolean hasCapability(ResultSet row,int pos){
	try {
	    String cap = row.getString(pos);
	    
	    if ( cap != null) {
		return true ;
	    } else { return false ;}

	}
	catch (Exception e){
	    e.printStackTrace();
	}
	return false ;
	
    }


    

    /* returns the capabilities of service auto_id */
    private String[] get_capabilities(int auto_id){
	try {
	    ArrayList capabilities = new ArrayList();

	    String sql = "SELECT sequence, structure, alignment, types, features, entry_points, error_segment, unknown_segment, unknown_feature, feature_by_id, component, supercomponent, dna , stylesheet from registry where auto_id = ?";
	    //System.out.println(sql);
	    PreparedStatement ps = 
		conn.prepareStatement( sql);
	    ps.setInt(1,auto_id);
	    
	    ResultSet row =	ps.executeQuery() ;
	    
	    // only one result expected ...
	    row.next() ;
	    
	    
	    /* test which capabilities are supported by this service */
	    for (int i=0;i<all_capabilities.size();i++){
		if (hasCapability(row,i+1)) {
		    capabilities.add(all_capabilities.get(i));
		    //System.out.println("found capability:" + all_capabilities.get(i));
		}
	    }
	    
	    //System.out.println("cap size"+capabilities.size());
	    if (capabilities.size()==0) return null;
	    String[] str = new String[capabilities.size()];
	    for (int i=0;i<str.length;i++){
		str[i]=(String)capabilities.get(i);
	    }

 	    //System.out.println(str.length);
	    return str ;
	    
	    

	} catch (Exception e) {
	    e.printStackTrace() ;
	}
	return null ;
    }


    /* returns the number of available sources in registry */
    
    private int getNumberSources(){
	try {
	     PreparedStatement ps = 
		 conn.prepareStatement( "SELECT COUNT(*) FROM registry");
	     ResultSet row =	ps.executeQuery() ;
	     row.next();
	     int size = row.getInt(1);
	     return size ;

	} catch (Exception e) {
	    e.printStackTrace() ;
	}
	return 0 ;
    }

    /* retrieve all coordinate systems that are provided by service
       auto_id
    */
    private String[] getCoordinateSystems(int auto_id){

	try {
	    String sql = "select count(distinct(coordinateSystem)) from registry2coordinateSystem,coordinateSystems where registry2coordinateSystem.auto_id = ? and coordinateSystems.coord_auto = registry2coordinateSystem.coord_auto" ;
	    
	    PreparedStatement ps = 
		conn.prepareStatement( sql );
	    ps.setInt(1,auto_id);

	    ResultSet row =	ps.executeQuery() ;
	    row.next();
	    int size = row.getInt(1);
	    


	    String[] retarr = new String[size] ;
	
	    sql = "select coordinateSystem from registry2coordinateSystem,coordinateSystems where registry2coordinateSystem.auto_id = ? and coordinateSystems.coord_auto = registry2coordinateSystem.coord_auto" ; 
	
	    ps.close();
	    ps = conn.prepareStatement(sql) ; 
	    ps.setInt(1,auto_id);

	    row =	ps.executeQuery() ;
	    
	    int i = -1 ;
	    while (row.next()) { 
		i++ ;
		retarr[i] = row.getString(1);
		System.out.println(retarr[i]);
	    }
	
	    return retarr;
	}catch (Exception e) {
	    e.printStackTrace() ;
	}
	return null;
    }



    /* (non-Javadoc)
     * @see DasRegistry#registerService(java.lang.String, java.lang.String, java.lang.String)
     */
    public int registerService(
				   String url,
				   String adminemail,
				   String description,
				   String[] coordinateSystem,
				   String[] capabilities
				   ) {

	/* return: in which provides an Exitcode.
	 * 1 - service has been registered successfully
	 * 0 - unknown error occured
	 * 2 - server already in database, not registering a second time
	 * 3 - server could not be contacted (checkDasServer failed)
	 * 4 - database error -  could not add server to database
	 * 5 - unknown capability
	 */
	
	System.out.println("in RegisterService");

	boolean works = false ;
	// check if new DAS service is valid
	works = checkDasServer(url) ;
	if ( ! works) {
	    // 3 - server could not be contacted (checkDasServer failed)
	    System.err.println("server could not be contacted");
	    return 3 ;
	}

	boolean caps_ok = check_capabilities(capabilities);
	System.out.println("capabilities: "+caps_ok);
	if ( !caps_ok) {
	    System.err.println("unknown capability detected") ;
	    return 5 ;
		
	}
	

	// open connection to local database
	// check if service is already available
	boolean found = isInDatabase(url) ;
	if ( ! found ) {
	    try {
		storeInDatabase(url,adminemail,description,coordinateSystem,capabilities);
	    } catch (Exception e) {
		e.printStackTrace() ;
		// 4 - database error -  could not add server to database
		System.err.println("registerService: database error");
		return 4;
	    }
	} else {
	    // 2 - server already in database, not registering a second time
	    System.err.println("registerService: already in database");
	    return 2 ;
	}
	// if not enter
	
	// everything worked o.k.
	// 1 - service has been registered successfully
	System.out.println("successfully registered!");
	return 1;
    }



    /* (non-Javadoc)
     * @see DasRegistry#listServices()
     */

    public DasSource[] listServices() {
	// TODO Auto-generated method stub

	//ArrayList ret = new ArrayList() ;

	try {
	   
	    int size = getNumberSources();
	    //String[][] sources = new String[size][];
	    DasSource[] sources = new DasSource[size];

	    PreparedStatement ps = 
		conn.prepareStatement( "SELECT auto_id,url,adminemail,description from registry");
	    ResultSet row =	ps.executeQuery() ;
	
	    //ArrayList sources = new ArrayList();
	    int i = -1 ;
	    while (row.next()) {
		i += 1;
		int    auto_id          = row.getInt(1) ;
		String url              = row.getString(2) ;
		String adminemail       = row.getString(3) ;
		String description      = row.getString(4) ;
		
		System.out.println("get capabilities for "+auto_id);
		String[] capabilities = get_capabilities(auto_id);
		System.out.println("done!");
		int number_caps;
		if (capabilities != null) {
		    number_caps = capabilities.length ;
		} else {
		    number_caps = 0 ;
		}

		String[] coordinateSystems = getCoordinateSystems(auto_id) ;


		
		DasSource ds = new DasSource() ;
		ds.setUrl(url);
		ds.setAdminemail(adminemail);
		ds.setDescription(description);
		ds.setCoordinateSystem(coordinateSystems);
		ds.setCapabilities(capabilities);
		//System.out.println(ds);
		sources[i] = ds ;
		
		/*
		sources[i] = new String[4+number_caps] ;
		sources[i][0] = url ;
		sources[i][1] = adminemail ;
		sources[i][2] = description ;
		sources[i][3] = "" ;
		//sources[i][3] = capabilities ;
		for(int j=0; j<number_caps;j++){
		    sources[i][4+j] = capabilities[j];
		}
		//System.out.println("sources["+i+"]:"+url+", "+adminemail+", "+description);
		*/
	
	
	    }
	    
	    //System.out.println("returning"+sources[0] );
	    return sources ;
	    //DasSourceList dl = new DasSourceList() ;
	    //dl.setSources(sources);
	    //return dl ;
	    
	    

	    
	} catch (Exception e) {
	    e.printStackTrace() ;
	}
	return null;
    }

    /* returns all  capabilites that are known to the registry service */
    public String[] getAllCapabilities(){
	String[] retstr = new String[all_capabilities.size()] ;
	for (int i=0; i < all_capabilities.size(); i++){
	    retstr[i] = (String)all_capabilities.get(i) ;

	}
	return retstr;
    }

}
