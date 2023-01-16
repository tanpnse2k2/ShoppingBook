/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tanpn.cart;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import tanpn.product.ProductDTO;

/**
 *
 * @author Nhat Tan
 */

//Khi 1 object duoc khoi tao thi gia tri no = 0
//Cho nen phai kiem tra Object != null va Object Size > 0

//Class Ngan chua do
public class CartObject implements Serializable{
    private Map<String, Integer> items;

    //Khi lay gio nguoi khac bo vo gio minh` thi moi ton tai SET
    public Map<String, Integer> getItems() {
        return items;
    }
    
    //Co the dung de xoa so luong nhat dinh = cach truyen -quantity
    public void addItemToCart(String sku, int quantity) {
        if (sku == null) {
            return;
        
        }
        
        if (sku.trim().isEmpty()) {
            return;
        }
        
        if (quantity == 0) {
            return;
        }
        
        //Kiem tra gio ton tai chua
        if (this.items == null) {
            this.items = new HashMap<>();
        } // items have not existed
        
        //Gio ton tai thi tang so luong ngoai gio
        if (this.items.containsKey(sku)) {
            quantity += this.items.get(sku);
        } //end item has existed
        
        //Do tang ngoai gio cho nen update items
        this.items.put(sku, quantity);
        
    }
    
 
    
    
            
    
    public void removeItemFromCart (String sku) {
        if (sku == null) {
            return;
        }
        
        if (sku.trim().isEmpty()) {
            return;
        }
        
        if (this.items == null) {
            return;
        }
        
        if (this.items.containsKey(sku)) {
            this.items.remove(sku);
            //Khi object khong ton tai gi ca => Huy Object -> Muc tieu thuan tien
            if (this.items.isEmpty()) {
                this.items = null;
            }
            
        }
    
    }
    

    
}
