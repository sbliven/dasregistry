<jsp:useBean id="register" class="dasregistry.registryBean" scope="session"/>
<jsp:setProperty name="register" property="numbcoordinateentries"/>

<HTML>
<HEAD>
 <title>DAS registration server</title>
<link rel="stylesheet" type="text/css" href="http://www.sanger.ac.uk/stylesheets/stylesheet.css" />

</HEAD>

<script language="Javascript">
<!--
	function reloadPage() {
		document.count_form.submit();
		return false;
	}
// -->
</script> 




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

<!--
<form action="registerService.jsp"  name="count_form" method="post">
<input type=hidden name="" value="">
1. Select the number of coordinateSystems (usually one, can be multiple if an alignment service)
currently <%=register.getNumbcoordinateentries()%>
	<input type="hidden" name="" value="">
	
	<input type="submit" value="change">
</form>
-->

<table border="0">

	<form method="post" action="registerService.jsp" name="count_form">
	<tr><td valign="top">
	Number of coordinate systems supported by service
	</td>
	<td>
	<select name="numbcoordinateentries" onChange="reloadPage()">
		<% 
			int numentries = register.getNumbcoordinateentries(); 
			for( int i=1;i<=10;i++){
				%>
				<option <% if (i==numentries){out.print("selected");}%>
				value="<%=i%>"><%=i%></option>
				<%
			}
			%>
	</select>
	 
	<input type="submit" value="change...">
	</td></tr>
	</form>



<form method="post" action="registerAction.jsp" name="register_form">
	<% 
	
	for (int j=0; j<numentries;j++) {
		%>
		<tr><td valign="top">coordinate system <%=j+1%>:</td>  
		<td><input type="input" name="coordinateSystem" value=""> 
		</td></tr>
		
		<%		
	}
	%>
	
	<tr><td valign="top">DAS url     </td>
	<td><input type="input" name="dasurl" size="20" value="<%
	
				String dasurl = register.getDasurl();
				if (dasurl == null) { 
					out.print("http://");
				} else {
					out.print(dasurl);
				}

								%>"> </td></tr>
	<tr><td valign="top">admin email </td>
	<td> <input type="input" name="adminemail" size="20" value="<%

				String adminemail = register.getAdminemail();
				if (adminemail!=null) { out.print(adminemail);};


								%>"></td></tr>
	

	<tr><td valign="top">description </td>
	<td> <textarea COLS="20" ROWS="4" WRAP="hard" NAME="description" value="<%=register.getDescription()%>"></textarea></td></tr>


	<tr><td valign="top">DAS capabilities</td> 

	<td>    <SELECT NAME="capabilities" size="7" multiple>

		<% 
		  String all_capabilities[] = register.getAllCapabilities();
		  for (int i=0;i<all_capabilities.length;i++){
		  out.println("<OPTION VALUE=\""+all_capabilities[i]+"\">"+all_capabilities[i]+"</OPTION>");
		  }	
		  %>
		
		 </SELECT>



	</td>
	</tr>

	</table>
        
	<input type="hidden" name="numbcoordinateentries" value="2">
	<input type="submit" value="register" >

	

	
 

	

</form>



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


