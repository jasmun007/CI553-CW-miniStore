package catalogue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Write a description of class BetterBasket here.
 *
 * @author  Your Name
 * @version 1.0
 */
public class BetterBasket extends Basket
{
  @Override
  public boolean add( Product pr )
  {
    for(Product PrINList : this ) {
    if(prInList.getProductNumi().equals(pr.getProductNum())) {
      int quantity = pr.getQuantity()+prInList.GetQuantity();
      prInList.setQuantity(quantity);
      return (true);
    }
    }
    }
    return super.add( pr );     // Call add in ArrayList
  }

  @Override
  public Product remove() {
    return null;
  }

  // You need to add code here
  // merge the items for same product,
  // or sort the item based on the product number
}