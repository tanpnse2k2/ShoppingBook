<%-- 
    Document   : shopping
    Created on : Oct 19, 2022, 4:08:10 PM
    Author     : Nhat Tan
--%>

<%@page import="java.util.List"%>
<%@page import="tanpn.product.ProductDTO"%>
<%@page import="tanpn.product.ProductDAO"%>
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
<!--        Sẽ load dữ liệu từ 1 DTO lên
        DTO tạo ra thông qua kết nối của DAO
        Trình bày từng sản phẩm 1 
        Nếu ấn Add to Cart thì sản phẩm đó biến mất -> Update DTO, Reload lại Trang (easier version)
        hoặc 
        In sản phẩm ra 1 lần, nếu số lượng ấn add to cart = quantity - Tận dụng AddItemCartServlet, lấy quantity đã add ra
        -> Update DTO, Reload lại Trang
        Ko update Database bởi vì chưa thanh toán -> chưa xóa
        
        Last edition: 
            Cứ cho Customer add thoải mái, đến khi Check Out, quantity ko đủ hoặc status = false thì báo lỗi

-->
<c:set var ="productList" value = "${sessionScope.PRODUCTLIST}" scope="request"/>
            <c:if test = "${not empty productList}">
        <table border="1">
            <thead>
                <tr>
                    <th>No</th>
                    <th>Name</th>
                    <th>Price</th>
                    <th>Quantity</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                
                <c:set var = "count" value = "0"/>
                <c:forEach var="product" items="${requestScope.productList}" varStatus="counter">
                    <form action="addItemController" method="POST">
                        <tr>
                            

                            <%--<c:set var = "count" value = "${count + 1}"/>--%>
                            <td>
                               ${counter.count}
                            </td>

                            <td>${product.name}</td>
                            <td>${product.price}</td>
                            <td>
                                <input type="number" name="quantity" value=""  min="1" step="1"/>
                                
                            </td>
                            <td>
                                <input type="hidden" name="cboBook" value="${product.sku}" />
                                <input type="submit" value="Add book to Your Cart" name="btAction" />
                            </td>

                        </tr>
                    </form>
                </c:forEach>
                
            </tbody>
        </table>
     </c:if>
            
    </br>
    <%--<c:set var="urlRewriting" value = "viewCartPage" />--%>
    <c:url var="urlRewriting" value="viewCartPage">
    </c:url>
    <a href ="${urlRewriting}">View Your Cart</a>
    
    </br> <a href ="loginPage"> Back to Login Page </a>
    
        
<%--
        <table border="1">
            <thead>
                <tr>
                    <th colspan = "2">No</th>
                    <th colspan = "5">Name</th>
                    <th colspan = "5">Quantity</th>
                    <th colspan = "2">Price</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
            <%
                ProductDAO dao = new ProductDAO();
                List<ProductDTO> productList = (List<ProductDTO>) request.getAttribute("PRODUCTLIST");
                if (productList != null) {
                    
                    int count = 0;
                    for (ProductDTO product: productList) {
                        
                        
                   
                
            %>
            <form action="DispatchController">
            
                <tr>
                    <!--Gui username, btAction Add to Cart ve DispatchController-->
                    <input type="hidden" name="cboBook" value="<%= product.getSku() %>" />
                    <td colspan = "2"><%= ++count %></td>
                    <td colspan = "5"><%= product.getName() %></td>
                    <td colspan = "5">
                        <input type="text" name="quantity" value="" />
                        
                    </td>
                    <td colspan = "2"><%= product.getPrice() %></td>
                    <td> <input type="submit" value="Add book to Your Cart" name="btAction" /> </td>

                </tr>
            </form>
            </tbody>
            <%  
                     } //end of Show Product List
               
                } //end of check/ find ProductList
            else  {
                %>
            <h2>No Product is Available</h2> </br>
            
            <%
            } //end of Show If no Record have
                
            %>
        </table>
        
        <form action="DispatchController">
            <input type="submit" value="View Your Cart" name="btAction" />
        </form>
        --%>
    </body>
</html>
