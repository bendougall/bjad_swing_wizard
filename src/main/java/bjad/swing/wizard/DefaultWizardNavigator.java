package bjad.swing.wizard;

import java.util.List;

/**
 * Default Wizard Navigator, setting up the navigator to
 * go between the pages one page at a time with no special
 * logic making the wizard skip pages. 
 *
 *
 * @author 
 *    Ben Dougall
 * @param <T>
 *    The datatype for the data bean the wizard
 *    will build while showing.  
 */
public class DefaultWizardNavigator<T> extends AbstractWizardNavigator<T>
{
   /**
    * Constructor setting the pages to show within the wizard.  
    * 
    * @param pages
    *    The pages to show in the wizard.
    * @throws IllegalArgumentException
    *    Thrown if the page list is null or empty.
    */
   public DefaultWizardNavigator(List<AbstractWizardPage<T>> pages) throws IllegalArgumentException
   {
      super(pages);
   }

   @Override
   public void previousPage()
   {
      this.currentPageIndex--;
      this.currentPage = pages.get(currentPageIndex);
   }

   @Override
   public void nextPage()
   {
      this.currentPageIndex++;
      this.currentPage = pages.get(currentPageIndex);
   }

   @Override
   public boolean isFirstPage()
   {      
      return this.currentPageIndex == 0;
   }

   @Override
   public boolean isLastPage()
   {
      return this.currentPageIndex == (pages.size() - 1);
   }
}
