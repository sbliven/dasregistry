<%@ page import="org.biojava.services.das.registry.*" %>
<jsp:useBean id="register" class="dasregistry.registryBean" scope="session"/>
<jsp:setProperty name="register" property="*"/> 


<HTML>
<HEAD>
 <title>available DAS services</title>
<link rel="stylesheet" type="text/css" href="http://www.sanger.ac.uk/stylesheets/stylesheet.css" />

</HEAD>

<BODY>
<!-- the sanger default header -->
<%@ include file="sangerheader.jsp" %>




<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr><td class="h2bg"><img src="/gfx/blank.gif" height="8" alt="" /></td></tr>
  <tr valign="center">
    <td class="h2bg" width="100%" align="center">
      <span class="bannertext">available DAS services</span>
    </td>
  </tr>
  <tr><td class="h2bg"><img src="/gfx/blank.gif" height="8" alt="" /></td></tr>
</table>


<p>


<table border="0" cellspacing="0" cellpadding="0" align="center" class="violet1">
  <tr valign="top">
    <td class="grey1" rowspan="4"><img src="/icons/blank.gif" width="1" height="1" alt="" /></td>
    <td class="grey1" colspan="3"><img src="/icons/blank.gif" width="1" height="1" alt="" /></td>
    <td class="grey1" rowspan="4"><img src="/icons/blank.gif" width="1" height="1" alt="" /></td>
  </tr>

  <tr valign="top">
    <td><img src="/icons/blank.gif" width="10" height="9" alt="" /></td>
    <td><img src="/icons/blank.gif" width="10" height="9" alt="" /></td>
    <td><img src="/icons/blank.gif" width="10" height="9" alt="" /></td>
  </tr>

  <tr valign="top">
    <td><img src="/icons/blank.gif" width="10" height="10" alt="" /></td>
    <td>


  
    <% 
	    //String[][]s = register.listServices() ;
	    DasSource[] s = register.listServices() ;
	    
	    %>
    <table border="0" >
    <tr>
    <td><b>url</b></td>
    <td><b>adminemail</b></td>
    <td><b>description</b></td>
    <td><b>coordinateSystem</b></td>
    <td><b>capabilities</b></td>
    </tr>
    <%
  
	    int i=0;
	    for (i=0;i<=s.length -1; i++) {
	    	%>
	    	<tr>
	    	<%
		

		DasSource ds = s[i];
		%>
		
		 <td><%= ds.getUrl()       %></td>
		 <td><%= ds.getAdminemail()   %></td>
		 <td><%= ds.getDescription()  %></td>

		 <td><% String[] coordSys = ds.getCoordinateSystem();
		      for (int j=0;j<coordSys.length;j++){
		      out.println(coordSys[j]);
		      }

		 %></td>
		 <td><% String[] capabs = ds.getCapabilities();
		      for (int j=0;j<capabs.length;j++){
		      out.println(capabs[j]);
		      }

		 %></td>
		<%
		 
	    } 

   
    %> 
    </table>
   

   </td>
    <td><img src="/icons/blank.gif" width="10" height="10" alt="" /></td>
  </tr>

  <tr>
    <td colspan="3"><img src="/icons/blank.gif" width="10" height="10" alt="" /></td>
  </tr>

  <tr>
    <td class="grey1" colspan="5"><img src="/icons/blank.gif" width="1" height="1" alt="" /></td>
  </tr>
</table>


<p>


<%@ include file="sangerfooter.jsp" %>


  </body>
</html>


