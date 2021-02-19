package bjad.swing.wizard;

import java.awt.LayoutManager;

import javax.swing.JPanel;

/**
 * Class representing a page within the wizard being 
 * built. Extends JPanel, and contains all the controls
 * for the page (outside of the next/prev/finish 
 * buttons the framework controls.
 *
 * @author 
 *   Ben Dougall
 * @param <T> 
 *   The type of object passed between all the pages. 
 */
public abstract class AbstractWizardPage<T> extends JPanel
{
   private static final long serialVersionUID = -1606699327948845907L;
   
   /** The wizard showing the page when its shown */
   private BJADWizard<T> wizard; 

   /**
    * Default constructor, setting up with the default
    * layout manager and default buffering setting.
    */
   public AbstractWizardPage()
   {
      super(true);
   }

   /**
    * @param isDoubleBuffered
    *    True to enable double buffering. 
    */
   public AbstractWizardPage(boolean isDoubleBuffered)
   {
      super(isDoubleBuffered);
   }

   /**
    * @param layout
    *    The layout manager to use within the page.
    * @param isDoubleBuffered
    *    True to enable double buffering.
    */
   public AbstractWizardPage(LayoutManager layout, boolean isDoubleBuffered)
   {
      super(layout, isDoubleBuffered);
   }

   /**
    * @param layout
    *    The layout manager to use within the page.
    */
   public AbstractWizardPage(LayoutManager layout)
   {
      super(layout);
   }

   /**
    * Method executed with the page is displayed 
    * within the wizard.
    */
   public abstract void pageDisplayed();
   
   /**
    * Sets up the screen with the data bean
    * the wizard is maintaining.
    * 
    * @param data
    *    The data bean passed between the pages 
    *    in the wizard. 
    */
   public abstract void setupScreenFromData(T data);
   
   /**
    * Method executed with the next or finish button
    * is pressed within the wizard, allowing for screen 
    * validation or additional processing.
    * 
    * @return 
    *    True if the next page or finished event should
    *    fire, false to suppress the event due to things
    *    like validation or other runtime errors. 
    */
   public abstract boolean nextOrFinishPressed();
   
   /**
    * Method executed after nextOrFinishedPressed() is executed
    * and true is returned from it, allowing to wizard pages 
    * to update the data bean passed between the pages. 
    * 
    * @param data
    *    The data bean to update
    * @return
    *    The updated bean.
    */
   public abstract T updateWizardData(T data);
   
   /**
    * Returns the wizard the page is contained within.
    * @return
    *    The wizard hosting the page.
    */
   public BJADWizard<T> getWizard()
   {
      return this.wizard;
   }
   
   /**
    * Sets the wizard containing the page. 
    * @param wizard
    *    The wizard.
    */
   protected void setWizard(BJADWizard<T> wizard)
   {
      this.wizard = wizard;
   }
}
