package bjad.swing.wizard;

import java.util.EventListener;

/**
 * Listener for when the Wizard completes
 * within the framework.
 *
 * @author 
 *   Ben Dougall
 * @param <T> 
 *    The data type for the object the wizard
 *    is producing.
 */
public interface WizardFinishedListener<T> extends EventListener
{
   /**
    * Method to implement to react to when the user
    * presses the finish button in the wizard.
    * 
    * @param data
    *    The data built from the wizard's execution.  
    */
   public void wizardComplete(T data);
}
