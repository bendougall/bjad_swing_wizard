package bjad.swing.wizard.v2;

/**
 * Listener interface for wizard events, including the 
 * page being changed, the first page being shown, and 
 * the last page being shown.
 *
 * @param <DATA_MODEL>
 *    The data model class that the wizard is populating.
 *      
 * @param <PAGE_IMPL>
 *    The page implementation that is being displayed
 *    within the wizard. 
 *
 * @author 
 *   Ben Dougall
 */
public interface WizardListener<DATA_MODEL, PAGE_IMPL extends AbstractWizardPage<DATA_MODEL>>
{
   /**
    * Triggered when the first page is displayed
    * in the wizard framework.
    */
   public void firstPageDisplayed();
   
   /**
    * Triggered when the last page is displayed 
    * in the wizard framework.
    */
   public void lastPageDisplayed();
   
   /**
    * Triggered when the page has been displayed 
    * within the wizard framework.
    * 
    * @param pageDisplayed
    *    The page that has been displayed.
    * @param pageIndex
    *    The index of the page that was displayed.
    */
   public void pageChanged(PAGE_IMPL pageDisplayed, int pageIndex);
}
