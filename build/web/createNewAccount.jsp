<%-- 
    Document   : createNewAccount
    Created on : Oct 25, 2022, 7:32:49 AM
    Author     : Nhat Tan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!--import taglib-->
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New Account</title>
    </head>
    <body>
        <h1>Create New Account</h1>
        <form action="createNewAccountController" method = "POST">
            <c:set var ="errors" value = "${requestScope.CREATE_ERRORS}"/>
<!--            
            Username va Full Name giu~ tri. de nguoi dung` sua
            Password va Confirm ko giu tri. Vi neu view resource -> Hien thi password len 
            -->
            
            Username <input type="text" name="txtUsername" value="${param.txtUsername}" /> (6 - 20 chars) </br>
            <c:if test ="${not empty errors.usernameLengthError}">
                <font color = "red"> 
                    ${errors.usernameLengthError}

                </font>
                </br>
               
            </c:if>
            
            <c:if test ="${not empty errors.usernameIsExisted}">
                <font color = "red"> 
                    ${errors.usernameIsExisted}
                </font>
                </br>
            </c:if>
            
            Password <input type="password" name="txtPassword" value="" /> (6 - 30 chars) </br>
            <c:if test ="${not empty errors.passwordLengthError}">
                <font color = "red"> 
                    ${errors.passwordLengthError}
                </font>
                </br>
            </c:if>
            Confirm <input type="password" name="txtConfirm" value="" /> </br>
            <c:if test ="${not empty errors.confirmLengthError}">
                <font color = "red"> 
                    ${errors.confirmLengthError} 
                </font>
                </br>
            </c:if>
            Full name <input type="text" name="txtFullname" value="${param.txtFullName}" /> (2 - 50 chars) </br>
            <c:if test ="${not empty errors.fullNameLengthError}">
                <font color = "red"> 
                    ${errors.fullNameLengthError} 
                </font>
                </br>
            </c:if>
            <input type="submit" value="Create New Account" name="btAction" />
            <input type="reset" value="Reset" />
        </form>
            
            </br> <a href ="loginPage"> Back to Login Page </a>
    </body>
</html>
