/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package items;

/**
 *
 * @author anahit
 */
  import java.util.HashMap;

  public class ShoppingCart{
  	HashMap<Integer, Integer> items;
  	public ShoppingCart(){
  		items = new HashMap<>();
  	}
  	public HashMap getItems(){
  		return items;
  	}
  	public void addItem(int itemId, int amount){
            if(items.containsKey(itemId)){
                items.put(itemId, items.get(itemId)+1);
            }
            else{
                items.put(itemId, amount);
            }
  	}

  }
