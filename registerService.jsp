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

<form method="post" action="registerAction.jsp">

	<table border="0">
	<tr><td>DAS url     </td><td>  <input type="input" name="dasurl" size="20" value="http://"> </td></tr>
	<tr><td valign="top">coordinate system </td><td> <textarea COLS="20" ROWS="4" WRAP="hard" NAME="coordinateSystem"></textarea></td></tr>
	<tr><td>admin email </td><td> <input type="input" name="adminemail" size="20"></td></tr>
	<tr><td valign="top">description </td><td> <textarea COLS="20" ROWS="4" WRAP="hard" NAME="description"></textarea></td></tr>


	<tr><td valign="top">DAS capabilities</td> 

	<td> 
		 <SELECT NAME="capabilities" size="7" multiple>
		 <OPTION VALUE="sequence"       >sequence</OPTION>
		 <OPTION VALUE="structure"      >structure</OPTION>
		 <OPTION VALUE="alignment"      >alignment</OPTION>
		 <OPTION VALUE="types"          >types</OPTION>
		 <OPTION VALUE="features"       >features</OPTION>
		 <OPTION VALUE="entry_points"   >entry_points</OPTION>
		 <OPTION VALUE="feature_by_id"  >feature_by_id</OPTION>		 
		 <OPTION VALUE="group_by_id"    >group_by_id</OPTION>
		 <OPTION VALUE="component"      >component</OPTION>
		 <OPTION VALUE="supercomponent" >supercomponent</OPTION>
 		 <OPTION VALUE="dna"            >dna</OPTION>
		 <OPTION VALUE="stylesheet"     >stylesheet</OPTION>
		 <OPTION VALUE="error_segment"  >error_segment</OPTION>
		 <OPTION VALUE="unknown_segment">unknown_segment</OPTION>
		 <OPTION VALUE="unknown_feature">unknown_features</OPTION>
		 </SELECT>



	</td>
	</tr>
	</table>


	
 

	
	<input type="submit">
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


