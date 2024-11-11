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
    for(Product prINList : this ) {
    if(prINList.getProductNum().equals(pr.getProductNum())) {
      int quantity = pr.getQuantity()+prINList.getQuantity();
      prINList.setQuantity(quantity);
      return (true);
    }

    }
    super.add( pr );     // Call add in ArrayList
    return (true);
    }

  }

