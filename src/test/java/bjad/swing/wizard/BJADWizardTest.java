package bjad.swing.wizard;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;

/**
 * (Description)
 *
 *
 * @author 
 *   bendo
 */
public class BJADWizardTest implements WizardFinishedListener<String>, PropertyChangeListener
{
   BJADWizard<String> wizard;
   TestDialog dlg;
   boolean wizardComplete = false;
   boolean pageIndexEvent = false;
   boolean pageEvent = false;
   
   /**
    * Tests the constructor argument exceptions
    */
   @Test
   public void testArgExceptions()
   {
      try
      {
         new BJADWizard<String>(null, null);
         org.junit.jupiter.api.Assertions.fail("Null data bean should not be allowed");
      }
      catch (IllegalArgumentException ex)
      {
         assertThat("Exception contains \"Initial Data bean cannot be null\"", ex.getMessage(), is("Initial Data bean cannot be null"));
      }
      
      try
      {
         new BJADWizard<String>("", null);
         org.junit.jupiter.api.Assertions.fail("Null page list should not be allowed");
      }
      catch (IllegalArgumentException ex)
      {
         assertThat("Exception contains \"Wizard pages list cannot be null\"", ex.getMessage(), is("Wizard pages list cannot be null"));
      }
      
      try
      {
         new BJADWizard<String>("", new ArrayList<AbstractWizardPage<String>>());
         org.junit.jupiter.api.Assertions.fail("Empty page list should not be allowed");
      }
      catch (IllegalArgumentException ex)
      {
         assertThat("Exception contains \"Wizard pages list cannot be empty\"", ex.getMessage(), is("Wizard pages list cannot be empty"));
      }
      
      try
      {
         new DefaultWizardNavigator<String>(null);
         org.junit.jupiter.api.Assertions.fail("Null page list should not be allowed");
      }
      catch (IllegalArgumentException ex)
      {
         assertThat("Exception contains \"Wizard pages list cannot be null\"", ex.getMessage(), is("Wizard pages list cannot be null"));
      }
      
      try
      {
         new DefaultWizardNavigator<String>(new ArrayList<AbstractWizardPage<String>>());
         org.junit.jupiter.api.Assertions.fail("Empty page list should not be allowed");
      }
      catch (IllegalArgumentException ex)
      {
         assertThat("Exception contains \"Wizard pages list cannot be empty\"", ex.getMessage(), is("Wizard pages list cannot be empty"));
      }      
   }
   /**
    * Tests the wizard flow. 
    */
   @Test
   public void testWizard()
   {
      List<AbstractWizardPage<String>> pages = new ArrayList<AbstractWizardPage<String>>();
      pages.add(new TestWizardPage());
      pages.add(new TestWizardPage());
      pages.add(new TestWizardPage());
      
      wizard = new BJADWizard<String>("", pages);
      wizard.setNavigator(new DefaultWizardNavigator<String>(pages));
      
      wizard.addWizardFinishedListener(this);
      wizard.removeWizardFinishedListener(this);
      wizard.addWizardFinishedListener(this);
      
      wizard.addPropertyChangeListener(BJADWizard.PAGE, this);
      wizard.addPropertyChangeListener(BJADWizard.PAGE_INDEX, this);
      
      assertThat("3 Pages in the wizard", wizard.navigator.getPageCount(), is(3));
      assertThat("Button Panel is not null", wizard.controls().getButtonPanel() != null, is(true));
      assertThat("Page Panel is not null", wizard.controls().getPagePanel() != null, is(true));
      wizard.controls().disableButtons();
      assertThat("Previous Button is disabled", wizard.controls().getPreviousButton().isEnabled(), is(false));
      assertThat("Next Button is disabled", wizard.controls().getNextButton().isEnabled(), is(false));
      wizard.controls().enableButtons();
      assertThat("Previous Button is enabled", wizard.controls().getPreviousButton().isEnabled(), is(true));
      assertThat("Next Button is enabled", wizard.controls().getNextButton().isEnabled(), is(true));
      
      
      wizard.controls().getPagePanel().add(new JLabel("Coverage Label"));
      
      dlg = new TestDialog(wizard);
      dlg.setVisible(true);
      
      assertThat("Wizard's page has the wizard", wizard.navigator.currentPage.getWizard(), is(wizard));
      assertThat("Wizard complete was triggered", wizardComplete, is(true));
      assertThat("Wizard's page property change was triggered", pageEvent, is(true));
      assertThat("Wizard's page index property change was triggered", pageIndexEvent, is(true));
      assertThat("Wizard's data bean", wizard.dataBean, is("TTTT"));
     
   }

   @Override
   public void wizardComplete(String data)
   {
      dlg.setVisible(false);
      wizardComplete = true;
   }
   
   /**
    * Gets the coverage for the wizard page ctors not used in the 
    * main test.
    */
   @Test
   public void testOtherPageCtors()
   {
      TestWizardPage p = new TestWizardPage();
      assertThat("Double bufferring is enabled", p.isDoubleBuffered(), is(true));
      
      p = new TestWizardPage(false);
      assertThat("Double bufferring is disabled", p.isDoubleBuffered(), is(false));
      
      p = new TestWizardPage(new BorderLayout());
      assertThat("Layout is a border layout", p.getLayout() instanceof BorderLayout, is(true));
      
      p = new TestWizardPage(new GridLayout(), false);
      assertThat("Layout is a grid layout", p.getLayout() instanceof GridLayout, is(true));
      assertThat("Double bufferring is disabled", p.isDoubleBuffered(), is(false));
   }
   @Override
   public void propertyChange(PropertyChangeEvent evt)
   {
      if (evt.getSource() instanceof BJADWizard<?>)
      {
         switch (evt.getPropertyName())
         {
         case BJADWizard.PAGE:
            pageEvent = true;
            break;
         case BJADWizard.PAGE_INDEX:
            pageIndexEvent = true;
            break;
         }
      }
      
   }
}

class TestDialog extends JDialog implements Runnable
{
   private static final long serialVersionUID = 681719526306704982L;
   BJADWizard<String> wizard;

   /**
    * @param wizard
    *    The wizard to display in the window.
    */
   protected TestDialog(BJADWizard<String> wizard)
   {
      super();
      this.setTitle("Test App");
      this.setModal(true);
      this.setSize(400, 400);
      
      this.wizard = wizard;
      this.setContentPane(wizard);
   }

   /**
    * Returns the value of the TestFrame instance's 
    * wizard property.
    *
    * @return 
    *   The value of wizard
    */
   public BJADWizard<String> getWizard()
   {
      return this.wizard;
   }

   /**
    * Sets the value of the TestFrame instance's 
    * wizard property.
    *
    * @param wizard 
    *   The value to set within the instance's 
    *   wizard property
    */
   public void setWizard(BJADWizard<String> wizard)
   {
      this.wizard = wizard;
   }
   
   @Override 
   public void setVisible(boolean state)
   {
      if (state)
      {
         SwingUtilities.invokeLater(this);
      }
      super.setVisible(state);
   }
   
   @Override
   public void run()
   {      
      pressNext();
      pressPrevious();
      pressNext();
      pressNext();
      pressNext();
   }
   
   private void pressNext()
   {
      SwingUtilities.invokeLater(new Runnable()
      {         
         @Override
         public void run()
         {
            try { Thread.sleep(150L); } catch (Exception ex) {}
            wizard.controls().getNextButton().doClick();
            System.out.println(wizard.controls().getNextButton().getText() + " Pressed");
         }
      });
   }
   
   private void pressPrevious()
   {
      SwingUtilities.invokeLater(new Runnable()
      {         
         @Override
         public void run()
         {
            try { Thread.sleep(150L); } catch (Exception ex) {}
            wizard.controls().getPreviousButton().doClick();
            System.out.println(wizard.controls().getPreviousButton().getText() + " Pressed");
         }
      });
   }
}