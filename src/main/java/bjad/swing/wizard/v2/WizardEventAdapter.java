package bjad.swing.wizard.v2;

/**
 * Adapter class for the wizard listener so the developer
 * can implement one of the functions instead of all
 * of them. (Similar to KeyAdapter vs KeyListener)
 *
 * @param <DATA_MODEL>
 *    The data model class that the wizard is populating.
 *      
 * @param <PAGE_IMPL>
 *    The page implementation that is being displayed
 *    within the wizard. 
 */
public class WizardEventAdapter<DATA_MODEL, PAGE_IMPL extends AbstractWizardPage<DATA_MODEL>> implements WizardListener<DATA_MODEL, PAGE_IMPL>
{
   @Override
   public void firstPageDisplayed()
   {
      ; // does nothing
   }

   @Override
   public void lastPageDisplayed()
   {
      ; // does nothing      
   }

   @Override
   public void pageChanged(PAGE_IMPL pageDisplayed, int pageIndex)
   {
      ; // does nothing
   }

}
