package bjad.swing.wizard;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Quick wrapper for the wizard controls that can 
 * be accessed by the consumer of the wizard framework
 *
 * @author 
 *   Ben Dougall
 */
public class WizardControls
{
   private JPanel pagePanel;
   private JPanel buttonPanel;
   private JButton previousButton;
   private JButton nextButton;
   
   /**
    * Creates the bean with the controls.
    * @param pagePanel
    *    The page panel in the wizard.
    * @param buttonPanel
    *    The button panel in the wizard
    * @param previousButton
    *    The previous button in the wizard.
    * @param nextButton
    *    The next button in the wizard.
    */
   protected WizardControls(JPanel pagePanel, JPanel buttonPanel, JButton previousButton, JButton nextButton)
   {
      this.pagePanel = pagePanel;
      this.buttonPanel = buttonPanel;
      this.previousButton = previousButton;
      this.nextButton = nextButton;
   }

   /**
    * Returns the value of the WizardControls instance's 
    * pagePanel property.
    *
    * @return 
    *   The value of pagePanel
    */
   public JPanel getPagePanel()
   {
      return this.pagePanel;
   }

   /**
    * Returns the value of the WizardControls instance's 
    * buttonPanel property.
    *
    * @return 
    *   The value of buttonPanel
    */
   public JPanel getButtonPanel()
   {
      return this.buttonPanel;
   }

   /**
    * Returns the value of the WizardControls instance's 
    * previousButton property.
    *
    * @return 
    *   The value of previousButton
    */
   public JButton getPreviousButton()
   {
      return this.previousButton;
   }

   /**
    * Returns the value of the WizardControls instance's 
    * nextButton property.
    *
    * @return 
    *   The value of nextButton
    */
   public JButton getNextButton()
   {
      return this.nextButton;
   }
   
   /**
    * Quick wrapper to disable the buttons in the wizard
    * in the case work is being done. 
    */
   public void disableButtons()
   {
      this.previousButton.setEnabled(false);
      this.nextButton.setEnabled(false);
   }
   
   /**
    * Quick wrapper to enable the buttons in the wizard
    * once a worker or other activity is complete.
    */
   public void enableButtons()
   {
      this.previousButton.setEnabled(true);
      this.nextButton.setEnabled(true);
   }
}