/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tanpn.utils;

/**
 *
 * @author Nhat Tan
 */
public class MyApplicationConstants {
    public class DispatchFeature {
        public static final String LOGIN_PAGE = "loginPage";
        public static final String ERROR_PAGE = "errorPage";
        public static final String LOGIN_CONTROLLER = "loginController";
        public static final String SEARCH_CONTROLLER = "searchLastNameController";
        public static final String DELETE_CONTROLLER = "deleteAccountController";
        public static final String UPDATE_CONTROLLER = "updateController";
        public static final String VIEW_PRODUCT_PAGE = "loadProductPage";
        public static final String VIEW_CART_CONTROLLER = "viewCartPage";
        public static final String ADD_ITEM_TO_CART_CONTROLLER = "addItemController";
        public static final String REMOVE_ITEM_TO_CART_CONTROLLER = "removeItemController";
        public static final String LOG_OUT_FROM_SEARCH_PAGE = "logOutController";
        public static final String CHECK_OUT_SERVLET = "checkOutController";
        public static final String CREATE_NEW_ACCOUNT_CONTROLLER = "createNewAccountController";
        public static final String FIRST_REQUEST_CONTROLLER = "";
        //public static final String
    }
    
    public class LoginFeature {
        public static final String SEARCH_PAGE = "searchPage";
        public static final String INVALID_PAGE = "invalidPage";
    }
    
    public class SearchFeature {
    
    }
    
    public class ViewProductFeature {
        public static final String SHOPPING_PAGE = "shoppingPage";
    }
    
    public class CheckOutFeature {
        public static final String TOO_MUCH_QUANTITY_PAGE = "tooMuchQuantityPage";
    }
    
    public class CreateNewAccountFeature {
        public static final String ERRORS_CREATE_ACCOUNT_PAGE = "errorsCreateNewAccountPage";
    }
    
    
}
