<%-- 
    Document   : search
    Created on : Oct 4, 2022, 8:14:18 AM
    Author     : Nhat Tan
--%>

<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%--
<%@page import="tanpn.account.AccountDTO"%>
<%@page import="java.util.List"%>
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!--Xoa dong nay thi co tieu de nhung ko hien ban-->
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Page</title>
    </head>
    <body>

        <font color = "green"> 
        
        Welcome ${sessionScope.USER.fullName}
        </font>
        
        <form action="searchLastNameController" method = "GET">
            Search Value <input type="text" name="txtSearchValue" 
                                value="${param.txtSearchValue}" /> <br/>
            Button Search <input type="submit" value="Search" name="btAction" />
        </form>
            </br>
         
<!--            Search Value-->
        <!--c:set la 1 the empty - no ko co than cho nen phai co dau /-->
        <c:set var = "searchValue" value = "${param.txtSearchValue}" />
        <c:if test = "${not empty searchValue}">
            <c:set var = "result" value="${requestScope.SEARCH_RESULT}" />
            
            <!--Neu Search co-->
            <c:if test = "${not empty result}">
                <!--GET ATTRIBUTE UPDATE ERRORS-->
                <c:set var = "updateErrors" value = "${sessionScope.UPDATEERRORS}"/>
                <!--GET ATTRIBUTE DELETE ERRORS-->
                <c:set var="deleteErrors" value = "${sessionScope.DELETEERRORS}"/>
                
                
                <!--If cung dc ma ko cung dc vi ko co thi ra chuoi trang - tuong tu nhu xoa, co thi xoa, ko thi thoi-->
                <c:if test = "${not empty deleteErrors}">
                    <font color = "red">
                         ${deleteErrors.deleteUserIsLoggingError}
                    </font>
                    
                    <!--After show remove for dont show in the next request-->
                    <c:remove var="DELETEERRORS" scope ="session"/>
                    
                </c:if>
               
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Username</th>
                            <th>Password</th>
                            <th>Full Name</th>
                            <th>Role</th>
                            <th>Delete</th>
                            <th>Update</th>
                            
                        </tr>
                    </thead>
                    <tbody>
                        <select name="txtDa">
                        <c:forEach var = "dto" items = "${result}" varStatus ="counter">
                                <option>${dto.fullName}</option>
                        </c:forEach>
                        </select>
                       
                        
                        <c:forEach var = "dto" items = "${result}" varStatus ="counter">
                        <form action="updateController" method = "POST">
                        
                            <tr>
                                <td>
                                    ${counter.count}
                                   
                                </td>
                                <td>
                                    ${dto.username}
                                     <input type="hidden" name="txtUsername" value="${dto.username}" />
                                    
                                </td>
                                <td>
                                    <%--${dto.password}--%>
                                    <input type="password" name="txtPassword" value="" />
                                    <%--<input type="hidden" name="txtPassword" value="${dto.password}" />--%>
                                    
                                    
                                </td>
                                <td>
                                    
                                        ${dto.fullName}
                                   
                                </td>
                                <td>
                                    <%--${dto.role}--%>
                                    
                                    <input type="checkbox" name="chkAdmin" value="ON" 
                                        <c:if test = "${dto.role}">
                                            checked = "checked"

                                        </c:if>
                                     />

                                </td>
                                <td>
                                    <%--<c:url var ="urlRewritting" value = "DispatchController">--%>
                                    <c:url var ="urlRewritting" value = "deleteAccountController">
                                        <%--<c:param name = "btAction" value = "delete" />--%>
                                        <c:param name = "pk" value = "${dto.username}" />
                                        <c:param name = "LastSearchValue" value = "${searchValue}" />
                                        
                                    </c:url>
                                    <a href ="${urlRewritting}">Delete</a>
                                </td>
                                <td>
                                    <input type="submit" value="Update" name="btAction" />
                                    <input type="hidden" name="lastSearchValue" value="${searchValue}" />
                                </td>
                            </tr>
                            
                        </form>    
                        </c:forEach>
                    </tbody>
                </table>
                    
                <!--If Update Password Length Errors-->                                
                <font color = "red">
                    ${updateErrors.passwordLengthError} 
                </font>               
                <!--Remove Update Password Length Errors Attribute-->
                <c:remove var = "UPDATEERRORS" scope="session" />
                
            </c:if>
<!--            Neu Seach Value ko co-->
            <c:if test = "${empty result}">
                <h2>
                    No record is matched
                    
                </h2>
                
            </c:if>
            
        </c:if>    


        <form action="logOutController">
            <input type="submit" value="Log Out" name="btAction" />
        </form>

            
<%--
        <font color = "green"> 
        
            <%
//              
                Cookie[] cookies = request.getCookies();
                for (int i = 0; i < cookies.length; i++) {
                    %>
                    <font> 
                        cookies[<%=i%>]: <%= cookies[i].getName() %>  <%= cookies[i].getValue() %>  <%= cookies[i].getMaxAge() %>
                    </font>        
                    </br>
                    
                            <%
                    }

                int decrease = 1;
                if (cookies[cookies.length - 1].getName().equals("JSESSIONID")) {

                     decrease++;
                }

                Cookie lastCookie = cookies[cookies.length - decrease];
               
                String username = lastCookie.getName();
//                Khi ma co tac dong len page -> Server se tu dong tao cookies
//                Khi do cookies vua dc cap nhat boi server (Chua dc response ve Client) se luu o End of Cookie Array
//                Se nam o vi tri tuy theo co du lieu hay chua
//                -> Chi can tru 1 khi name = JSESSIONID
            %>
            Welcome <%= username %>
        </font>
        <h1>Search Page</h1>
        <form action="DispatchController" method = "GET">
            Gia tri o search van con
            Khi go Ctrl + Space trong JSP khong bung la sai
            <%
                String searchValue = request.getParameter("txtSearchValue");
                String showSearchValue = searchValue;
                if (showSearchValue == null) {
                    showSearchValue = "";
                }
                
            %>
            Search Value <input type="text" name="txtSearchValue" 
                                value="<%= showSearchValue %>" /> <br/>
            
            Button Search <input type="submit" value="Search" name="btAction" />
            
        </form> <br/>
        
        Neu go thang duong truyen -> searchValue = null -> do ko co gia tri truyen ve phia server
        <%
        
            
            if (searchValue != null) {
                List<AccountDTO> result = 
                              (List<AccountDTO>) request.getAttribute("SEARCH_RESULT");
                if (result != null) {
        %>
        
        <table border="1">
            <thead>
                <tr>
                    Nhan Ctrl + Shift + Mui ten len
                    <th>No.</th>
                    <th>Username</th>
                    <th>Password</th>
                    <th>Full name</th>
                    <th>Role</th>
                    <th>Delete</th>
                    <th>Update</th>
                    
                    
                    
                    
                    
                    
                </tr>
            </thead>
            <tbody>
                Xu li tung phan tu cua Result -> dung  Scriptlet
                <%
                    int count = 0;
                    
                    for (AccountDTO dto : result) {
                        
//                      Viet code ben trong duong truyen ko co khoang trang 
//                      - Neu co %20, %f la trong dg truyen co khoang trang hoac tab
                        String urlRewriting = "DispatchController"
                                + "?btAction=delete"
                                + "&pk=" + dto.getUsername()
                                + "&LastSearchValue=" + searchValue; 
                    %>
            <form action="DispatchController" method="POST">
                
           
                <tr>
                   
                    <td>
                        <%= ++count %>
                        
                    </td>
                    <td>
                        <%= dto.getUsername() %>
                        <input type="hidden" name="txtUsername" value="<%= dto.getUsername() %>" />
                    </td>
                    
                    <td>
                        <%--<%= dto.getPassword() %>
                        <input type="text" name="txtPassword" value<%= dto.getPassword() %> />
                    </td>
                    
                    <td>
                    <%= dto.getFullName() %>
                    </td>
                    
                    <td>
                        <%= dto.isRole() %>
                        <input type="checkbox" name="chkAdmin" value="ON" 
                               <%
                                   if (dto.isRole()) {
                                       %>
                                       checked="checked"
                               <%
                                   } //end admn is true
                               %>
                               />
                    </td>
       
                    <td>
                    Khong bao gio dc chen link vao Java code
                        Han che ko mo Scripting Element
                        <a href="<%= urlRewriting %>">Delete</a>
                        
                    </td>
                    
                     <td>
                         
                         Update la Update password, chkAdmin
                         <input type="hidden" name="lastSearchValue" 
                                value="<%= searchValue %>" />
                         <input type="submit" value="Update" name="btAction" />
                    </td>
                </tr>
            </form>
                <%
                    
                    }// end travere result DTO List
                
                %>
            </tbody>
        </table>

        
        <%
                    
                
                } else { //no record
                        //Tao code HTML 
                    %>
                    <h2>
                        No record is matcher!!!
                        
                    </h2>
        
        
        <%
                       
                
                }
            
            }
        %>
        <form action="DispatchController">
            <input type="submit" value="Log Out" name="btAction" />
        </form>
       --%>
    </body>
</html>
