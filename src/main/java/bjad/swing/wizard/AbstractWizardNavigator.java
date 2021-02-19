package bjad.swing.wizard;

import java.util.List;

/**
 * Navigator interface for the wizard for implementing
 * users to use if they wish to make a custom page 
 * ordering logic. 
 *
 * @author 
 *   Ben Dougall
 * @param <T> 
 *    The data type of the bean being passed 
 *    within the wizard. 
 */
public abstract class AbstractWizardNavigator<T>
{
   /**
    * The pages within the wizard. 
    */
   protected List<AbstractWizardPage<T>> pages;
   /**
    * The current page shown in the wizard. 
    */
   protected AbstractWizardPage<T> currentPage;
   /**
    * The current index of the page showing in the wizard. 
    */
   protected int currentPageIndex = 0; 
   
   /**
    * Constructor for the implementations to implement
    * to set the pages within the navigator. 
    * 
    * @param pages
    *    The pages to show in the wizard.
    * @throws IllegalArgumentException
    *    Thrown if the page list is null or empty.
    */
   protected AbstractWizardNavigator(List<AbstractWizardPage<T>> pages) throws IllegalArgumentException
   {
      if (pages == null)
      {
         throw new IllegalArgumentException("Wizard pages list cannot be null");
      }
      else if (pages.isEmpty())
      {
         throw new IllegalArgumentException("Wizard pages list cannot be empty");
      }
      else
      {
         this.pages = pages;
      }
      this.pages = pages; 
      this.currentPage = pages.get(0);
      this.currentPageIndex = 0;
   }
   
   /**
    * Method to implement when the previous
    * page button is pressed. 
    */
   public abstract void previousPage();
   
   /**
    * Method to implement when the next 
    * page button is pressed.
    */
   public abstract void nextPage();
   
   /**
    * Marker for when the current page is the 
    * first page in the wizard (aka previous
    * is disabled)
    * 
    * @return
    *    True if the current page is the 
    *    first page in the wizard.
    */
   public abstract boolean isFirstPage();
   
   /**
    * Marker for when the current page is 
    * the last page in the wizard (aka next
    * becomes Finish).
    * 
    * @return
    *    True if the current page is the
    *    last page in the wizard.
    */
   public abstract boolean isLastPage();
   
   /**
    * Returns the current page the navigator
    * is sitting on. 
    * @return
    *    The current page.
    */
   public AbstractWizardPage<T> getCurrentPage()
   {
      return this.currentPage;
   }
   
   /**
    * Returns the page index for the page currently 
    * shown by the navigator.
    * @return
    *    The current page index
    */
   public int getCurrentIndex()
   {
      return this.currentPageIndex;
   }
   
   /**
    * Returns the total number of pages in the wizard.
    * @return
    *    The number of pages managed by the navigator
    */
   public int getPageCount()
   {
      return this.pages.size();
   }
}
