package bjad.swing.wizard;

import java.awt.Color;
import java.awt.LayoutManager;

import javax.swing.JLabel;

/**
 * (Description)
 *
 *
 * @author 
 *   bendo 
 */
public class TestWizardPage extends AbstractWizardPage<String>
{
   private static final long serialVersionUID = 1057192896860950245L;
   static int pageCount = 0; 

   /**
    * 
    */
   public TestWizardPage()
   {
      super();
      add(new JLabel("Page " + ++pageCount));
      setBackground(Color.WHITE);
   }
   
   /**
    * @param isDoubleBuffered
    *    True to enable double buffering. 
    */
   public TestWizardPage(boolean isDoubleBuffered)
   {
      super(isDoubleBuffered);
   }

   /**
    * @param layout
    *    The layout manager to use within the page.
    * @param isDoubleBuffered
    *    True to enable double buffering.
    */
   public TestWizardPage(LayoutManager layout, boolean isDoubleBuffered)
   {
      super(layout, isDoubleBuffered);
   }

   /**
    * @param layout
    *    The layout manager to use within the page.
    */
   public TestWizardPage(LayoutManager layout)
   {
      super(layout);
   }
   
   @Override
   public void pageDisplayed()
   {
      ; // does nothing.
   }

   @Override
   public void setupScreenFromData(String data)
   {
      ; // does nothing
   }

   @Override
   public boolean nextOrFinishPressed()
   {
      return true;
   }

   @Override
   public String updateWizardData(String data)
   {
      return data + "T";
   }

}
