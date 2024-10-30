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
  private static final long serialVersionUID = 1L;
  private ArrayList<String> theProductNumList = new ArrayList<String>();

  /**
   * Add a product to the BetterBasket.
   * Product is appended to the end, and then sort by product number
   * @param pr A product to be added to the basket
   * @return true if successfully adds the product
   */
  @Override
  public boolean add( Product pr )
  {
    // search product
    int idx = findIndex(pr.getProductNum());
    if(idx != -1)
      ((Product)get(idx)).increaseQuantity();
    else {
      super.add( pr );     // Call add in ArrayList
      // sort products in basket by product no
      this.sort((Comparator<? super Product>) pr);
    }

    theProductNumList.add(pr.getProductNum());
    return true;
  }

  @Override
  public Product remove()
  {
    if(this.isEmpty())
      return null;
    // get last added product
    int lastIdx = theProductNumList.size()-1;
    String lastPrNum = theProductNumList.get(lastIdx);
    int idx = findIndex(lastPrNum);
    if(idx == -1)
      return null;
    theProductNumList.remove(lastIdx);
    Product pr = this.get(idx);
    // if quantity is 0, remove the product
    if(((Product)get(idx)).decreaseQuantity() != true)
      this.remove(idx);

    return new Product(pr.getProductNum(), pr.getDescription(), pr.getPrice(), 1);
  }

  private int findIndex(String prNum)
  {
    // search product
    int idx = 0;
    boolean exist = false;
    for(Product p : this) {
      if(prNum.compareTo(p.getProductNum()) == 0) {
        exist = true;
        break;
      }
      idx++;
    }
    if(exist == true)
      return idx;
    else
      return -1;
  }
}