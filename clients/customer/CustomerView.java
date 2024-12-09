package clients.customer;

import catalogue.Basket;
import catalogue.BetterBasket;
import clients.Picture;
import middle.MiddleFactory;
import middle.StockReader;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Implements the Customer view.
 * @author  Mike Smith University of Brighton
 * @version 1.0
 */

public class CustomerView implements Observer
{
  class Name                              // Names of buttons
  {
    public static final String CHECK  = "Search";
    public static final String CLEAR  = "Clear";


  }

  private static final int H = 300;       // Height of window pixels
  private static final int W = 400;       // Width  of window pixels

  private final JLabel      theAction  = new JLabel();
  private final JTextField  theInput   = new JTextField();
  //private final JTextField  theInputNo = new JTextField();
  private final JTextArea   theOutput  = new JTextArea();
  private final JScrollPane theSP      = new JScrollPane();
  private final JButton     theBtCheck = new JButton( Name.CHECK );
  private final JButton     theBtClear = new JButton( Name.CLEAR );
  private final JButton     darkMode = new JButton( "☀" );
  private final JButton theBtCheckByName = new JButton("Name Search"); // Button for name check


  private Picture thePicture = new Picture(80,80);
  private StockReader theStock   = null;
  private CustomerController cont= null;

  /**
   * Construct the view
   * @param rpc   Window in which to construct
   * @param mf    Factor to deliver order and stock objects
   * @param x     x-cordinate of position of window on screen
   * @param y     y-cordinate of position of window on screen
   */

  public CustomerView( RootPaneContainer rpc, MiddleFactory mf, int x, int y )
  {
    AtomicBoolean DarkMode = new AtomicBoolean(false);
    try                                             //
    {
      theStock  = mf.makeStockReader();             // Database Access
    } catch ( Exception e )
    {
      System.out.println("Exception: " + e.getMessage() );
    }
    Container cp         = rpc.getContentPane();    // Content Pane
    Container rootWindow = (Container) rpc;         // Root Window
    cp.setLayout(null);                             // No layout manager
    rootWindow.setSize( W, H );                     // Size of Window
    rootWindow.setLocation( x, y );

    Font f = new Font("Monospaced",Font.PLAIN,12);  // Font f is

    theBtCheck.setBounds( 16, 25+60*0, 80, 40 );    // Check button
    theBtCheck.setBackground(Color.black);
    theBtCheck.addActionListener(                   // Call back code
            e -> cont.doCheck( theInput.getText() ) );
    cp.add( theBtCheck );                          //  Add to canvas

    //Check By Name Button
    theBtCheckByName.setBounds(16, 25 + 60 * 2, 80, 40); // Position for button
    theBtCheckByName.setBackground(Color.black);
    theBtCheckByName.addActionListener(       // Call back code
            e -> cont.doCheckByName(theInput.getText()));
    cp.add(theBtCheckByName);


    theBtClear.setBounds( 16, 25+60*1, 80, 40 );    // Clear button
    theBtClear.addActionListener(                   // Call back code
            e -> cont.doClear() );
    cp.add( theBtClear );                           //  Add to canvas
    // Add Input Field for Name
    theInput.setBounds(110, 100, 120, 40); // Position for name input
    theInput.setText("Name/Item Number:");
    theInput.setForeground(Color.GRAY); // Placeholder text color
    theInput.addFocusListener(new java.awt.event.FocusListener() {
      @Override
      public void focusGained(java.awt.event.FocusEvent e) {
        if (theInput.getText().equals("Name/Item Number:")) {
          theInput.setText("");
          theInput.setForeground(Color.BLACK);
        }
      }

      @Override
      public void focusLost(java.awt.event.FocusEvent e) {
        if (theInput.getText().isEmpty()) {
          theInput.setText("Name/Item Number:");
          theInput.setForeground(Color.GRAY);
        }
      }
    });
    cp.add(theInput);


    darkMode.setBounds(350, 0, 20, 20); 				//smaller button, in the corner
    darkMode.addActionListener(e -> {
      if (DarkMode.get()) {						//if dark mode is true
        //then it should switch to light mode
        rpc.getContentPane().setBackground(Color.WHITE);
        setButtonTheme(Color.WHITE);			//all the backgrounds
        setTextTheme(Color.BLACK);				//all the text

        darkMode.setText("☀");
      } else {
        //Switch to dark mode
        rpc.getContentPane().setBackground(Color.BLACK);
        setButtonTheme(Color.DARK_GRAY);
        setTextTheme(Color.WHITE);
        darkMode.setText("☾︎");
      }
      DarkMode.set(!DarkMode.get());
    });
    cp.add(darkMode);


    theAction.setBounds(110, 25, 270, 20);   // Message area
    theAction.setText("");                   // Blank
    cp.add(theAction);                       // Add to canvas

    theInput.setBounds(110, 50, 120, 40);    // Product number area
    theInput.setText("Name/Item Number:");      // Placeholder text
    theInput.setForeground(Color.GRAY);      // Placeholder text color

    // Add FocusListener for ghost text
    theInput.addFocusListener(new java.awt.event.FocusListener() {
      @Override
      public void focusGained(java.awt.event.FocusEvent e) {
        if (theInput.getText().equals("Name/Item Number:")) {
          theInput.setText("");         // Clear placeholder
          theInput.setForeground(Color.BLACK); // Set normal text color
        }
      }

      @Override
      public void focusLost(java.awt.event.FocusEvent e) {
        if (theInput.getText().isEmpty()) {
          theInput.setText("Name/Item Number:");  // Restore placeholder
          theInput.setForeground(Color.GRAY); // Set placeholder text color
        }
      }
    });
    cp.add(theInput); // Add to canvas

    theSP.setBounds(110, 100, 270, 160); // Scrolling pane
    theOutput.setText("");               // Blank
    theOutput.setFont(f);                // Uses font
    cp.add(theSP);                       // Add to canvas
    theSP.getViewport().add(theOutput);  // In TextArea

    thePicture.setBounds(16, 25 + 80 * 2, 80, 80); // Picture area
    cp.add(thePicture); // Add to canvas
    thePicture.clear();

    rootWindow.setVisible(true); // Make visible
    theInput.requestFocus();     // Focus is here
  }


  private void setTextTheme(Color color) {

    theAction.setForeground(color);
    theInput.setForeground(color);
    theOutput.setForeground(color);
    theSP.setForeground(color);
    theBtCheck.setForeground(color);
    theBtClear.setForeground(color);
    theBtCheckByName.setForeground(color);
    darkMode.setForeground(color);

  }


  private void setButtonTheme(Color color) {
    theAction.setBackground(color);
    theInput.setBackground(color);
    theOutput.setBackground(color);
    theSP.setBackground(color);
    theBtCheck.setBackground(color);
    theBtCheckByName.setBackground(color);
    theBtClear.setBackground(color);
    darkMode.setBackground(color);
  }


  /**
   * The controller object, used so that an interaction can be passed to the controller
   * @param c   The controller
   */

  public void setController( CustomerController c )
  {
    cont = c;
  }

  /**
   * Update the view
   * @param modelC   The observed model
   * @param arg      Specific args
   */




  public void update( Observable modelC, Object arg )
  {
    CustomerModel model  = (CustomerModel) modelC;
    String        message = (String) arg;
    theAction.setText( message );
    ImageIcon image = model.getPicture();  // Image of product
    if ( image == null )
    {
      thePicture.clear();                  // Clear picture
    } else {
      thePicture.set( image );             // Display picture
    }
    theOutput.setText( model.getBasket().getDetails() );
    theInput.requestFocus();               // Focus is here
  }

}