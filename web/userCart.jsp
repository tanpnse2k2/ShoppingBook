<%-- 
    Document   : userCart
    Created on : Oct 14, 2022, 9:05:24 AM
    Author     : Nhat Tan
--%>

<%@page import="java.util.Map"%>
<%@page import="tanpn.cart.CartObject"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Web Mart</title>
    </head>
    <body>
        <h1>Web Mart</h1>
        <c:set var="Errors_Cart" value="${requestScope.ERRORS_CART}"/>
        <c:if test="${not empty Errors_Cart}">
            <font color ="red">
                ${Errors_Cart}
            </font>
            </br>
            
        </c:if>
        <!--1. Cust goes to his/her cart placement-->
        <c:if test="${not empty sessionScope}">
           <!--2. Cust take his/her cart-->
           <c:set var="cart" value="${sessionScope.CART}"/>
           <c:if test="${not empty cart}">
               
               <c:set var="nameOfProduct" value="${sessionScope.PRODUCTNAME}"/>
               <!--3. Cust take items-->
               <c:set var="itemList" value="${cart.items}"/>
               <c:if test="${not empty itemList}">
                   <!--4. Traverse the list-->
                   <!--Set value that Item List is Existed-->
                   <c:set var = "IsItemListExisted" value = "1" />
                    
                    <table border="1">
                        <thead>
                            <tr>
                                <th>No</th>
                                <th>Name</th>
                                <th>Quantity</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <form action="removeItemController">

                                    <c:forEach var ="item" items="${itemList}"  varStatus="counter">

                                            <tr>
                                                <td>${counter.count}</td>
                                                <td>${nameOfProduct[item.key]}</td>
                                                <td>${item.value}</td>
                                                <td>
                                                    <input type="checkbox" name="chkItem" value="${item.key}" />

                                                </td>
                                            </tr>

                                    </c:forEach>
                                        <tr >
                                            <td td colspan = "3">
                                                <a href ="loadProductPage" >Add more items to Your Cart</a> 
                                            </td>

                                            <td>
                                                <input type="submit" value="Remove Selected Action" name="btAction" />
                                            </td>
                                        </tr>

                               
                           </form>
                       </tbody>

                    </table>
                    
                    <form action="checkOutController">
                        <input type="submit" value="CheckOut" name="btAction" />
                    </form>     
                   
               </c:if>
              
            
            <!--end of traverse the List of Cart-->
               
           </c:if>
            <!--end of check validate Cart-->
          
            
            
        </c:if> 
        <!--end of check validate Session-->
        
        
        <c:if test = "${empty IsItemListExisted}">
                <h2>
                    <font color = "red">
                        No Items Had Selected Yet!!
                    </font>
                </h2>
                <a href ="loadProductPage" >Add more items to Your Cart</a> 
        </c:if>
                
                </br> <a href ="loginPage"> Back to Login Page </a>
        
        <%--
        <%
            
            //1. Cust goes to his/her cart placement
            //Session o day da co san roi, vi` day la Implicit Object
            if (session != null) {
            
            //2. Cust takes his/her cart
            Map <String, String> nameofProduct = (Map<String, String>) session.getAttribute("PRODUCTNAME");
            CartObject cart = (CartObject) session.getAttribute("CART");
            if (cart != null) {
                //3. Cust takes items
                Map<String, Integer> items = cart.getItems();
                if (items != null) {
                    //4. System traverses items to show
                    %>
                    <form action="DispatchController">
                   
                    <table border="1">
                        <thead>
                            <tr>
                                <!--Ctrl + Shift to renew-->
                                <th>No.</th>
                                <th>Name</th>
                                <th>Quantity</th>
                                <th>Action</th>
                                
                            </tr>
                        </thead>
                       
                        
                        <tbody>
                            
                           <%
                               //Muon hien ten thi Ket noi product DAO, get Attribute List ra, sau do key item = sku of productList
                               int count = 0;
                               for (String key: items.keySet()) {
                                   
                                   int value = items.get(key);
                                   %>
                                   <tr>
                                       <td>
                                           <%= ++count %>
                                           
                                       </td>
                                       <td>
                                           <%= nameofProduct.get(key) %>
                                           
                                       </td>
                                       <td>
                                           <%=value%>
                                           
                                       </td>
                                       <td>
                                           <!--Viet Value truyen len co SPACE la di xa -> Vi truyen du 1 dau SPACE -> SO SANH CHUOI KO HOP LE-->
                                           <input type="checkbox" name="chkItem" value="<%=key%>" />
                                       </td>
                                       
                                       
                                       
                                   </tr>
                            
                            
                            <%
                               
                               
                               }
                           %>
                           
                           <tr>
                               <td colspan = "3">
                                   <a href ="DispatchController?btAction=View Product">Add More Items to Your Cart </a>
                                   <!--<input type="submit" value="View Product" name="btAction" />-->
                                   
                                   
                               </td>
                               <td> 
                                   <input type="submit" value="Remove Selected Action" name="btAction" />
                                   
                               </td>
                               
                               
                           </tr>
                        </tbody>
                      
                    </table>
                           
                           <!-- Item != null -> Check out-->
                  
                        <input type="submit" value="CheckOut" name="btAction" />
                    </form>
        
        <%
        %>

                        
        
        
        <%    
            
                }
                
                
                return;
            } //end cart has existed
           
                return;
            } //end session has existed
        
        %>
        
        <h1>
            No cart is existed!!!
        </h1>
        
      
                      --%>
        
    </body>
</html>
