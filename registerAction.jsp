<jsp:useBean id="register" class="dasregistry.registryBean" scope="session"/>
<jsp:setProperty name="register" property="*"/>

<HTML>
<HEAD>
 <title>DAS registration server</title>
<link rel="stylesheet" type="text/css" href="http://www.sanger.ac.uk/stylesheets/stylesheet.css" />

</HEAD>

<BODY>
<!-- the sanger default header -->
<%@ include file="sangerheader.jsp" %>



<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr><td class="h2bg"><img src="/gfx/blank.gif" height="8" alt="" /></td></tr>
  <tr valign="center">
    <td class="h2bg" width="100%" align="center">
      <span class="bannertext"> register new DAS service</span>
    </td>
  </tr>
  <tr><td class="h2bg"><img src="/gfx/blank.gif" height="8" alt="" /></td></tr>
</table>

 



<%
	int status = register.registerMe();
	if (status == 1) { 
		out.println("registering service ... o.k.!");
	} else if (status == 0) {
		out.println("registering service ... unknown error occured.");
	} else if (status == 2) {
		out.println("server already in database, not registering a second time.");
	} else if (status == 3) {
		out.println("server could not be contacted");
	} else if (status == 4) {
		out.println("database error -  could not add server to database");
	}


%>


<%@ include file="sangerfooter.jsp" %>


  </body>
</html>

