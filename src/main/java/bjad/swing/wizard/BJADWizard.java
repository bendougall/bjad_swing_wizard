package bjad.swing.wizard;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Main wizard class to setup the pages, controls, and
 * wire events between the consumer and the pages.
 *
 * @author 
 *   Ben Dougall
 * @param <T>
 *    The bean to pass between all the pages in the wizard.  
 */
public class BJADWizard<T> extends JPanel implements ActionListener
{
   /**
    * Property name for property listeners listening when the page 
    * index is changed.
    */
   public static final String PAGE_INDEX = "PageIndex";
   
   /**
    * Property name for property listeners listening when the page
    * is changed.
    */
   public static final String PAGE = "Page";
   
   private static final long serialVersionUID = 4238864705083061870L;
   
   /**
    * The data bean managed by the pages within the 
    * wizard. 
    */
   protected T dataBean; 
   
   /**
    * The pages to show within the wizard. 
    */
   protected List<AbstractWizardPage<T>> pages;
   
   /**
    * The panel for the previous and next buttons. 
    */
   protected JPanel buttonPanel;
   
   /**
    * The previous button displayed in the wizard. 
    */
   protected JButton previousButton = new JButton("Previous");
   
   /**
    * The next button displayed in the wizard.
    */
   protected JButton nextButton = new JButton("  Next  ");

   /**
    * The panel for the page being displayed in the wizard. 
    */
   protected JPanel pagePanel;
   
   /**
    * The navigator to use within the wizard. 
    */
   protected AbstractWizardNavigator<T> navigator; 
   
   /**
    * Constructor to create the double buffered wizard panel  
    * setting the initial data bean for the wizard to populate. 
    * 
    * @param dataBean
    *    The initial databean for the wizard to populate.
    *    Cannot be null. 
    * @param pages
    *    The list of pages to display in the wizard. Cannot
    *    be null. 
    * @throws IllegalArgumentException
    *    Thrown if the data bean object or the list of pages passed is null.
    */
   public BJADWizard(T dataBean, List<AbstractWizardPage<T>> pages) throws IllegalArgumentException
   {
      this(dataBean, pages, true);
   }
   
   /**
    * Constructor, setting the buffer level for the wizard panel
    * and the initial data bean for the wizard to populate. 
    * 
    * @param isDoubleBuffered
    *    True to make the panel double buffered.
    * @param dataBean
    *    The databean for the wizard to create. Cannot
    *    be null.
    * @param pages
    *    The list of pages to display in the wizard. Cannot
    *    be null. 
    * @throws IllegalArgumentException 
    *    Thrown if the data bean object or the list of pages passed is null.
    */
   @SuppressWarnings({ "unchecked", "rawtypes" })
   public BJADWizard(T dataBean, List<AbstractWizardPage<T>> pages, boolean isDoubleBuffered) throws IllegalArgumentException
   {
      super(new BorderLayout(0, 0), isDoubleBuffered);
      if (dataBean != null)
      {
         this.dataBean = dataBean;
      }
      else
      {
         throw new IllegalArgumentException("Initial Data bean cannot be null");
      }
      
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
      
      navigator = new DefaultWizardNavigator(pages);
            
      buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0), isDoubleBuffered);
      buttonPanel.add(previousButton);
      buttonPanel.add(nextButton);
      buttonPanel.setBorder(new EmptyBorder(5, 0, 5, 0));
      
      previousButton.addActionListener(this);
      nextButton.addActionListener(this);
      
      pagePanel = new JPanel(isDoubleBuffered);
      
      this.add(pagePanel, BorderLayout.CENTER);
      this.add(buttonPanel, BorderLayout.SOUTH);
      
      // Show the first page.
      changeActivePage(0);
   } 
   
   /**
    * Changes the active page on the wizard, removing 
    * the current screen if one is present, and displaying
    * the new page after triggering its setup from data and 
    * page displayed functions. 
    * 
    * @param index
    *    The index of the page to display.
    */
   private void changeActivePage(int index)
   {  
      // If a page is displayed, make sure the page 
      // is removed from the UI panel.
      for (int i = 0; i != this.pagePanel.getComponentCount(); ++i)
      {
         if (this.pagePanel.getComponent(i) instanceof AbstractWizardPage<?>)
         {
            this.pagePanel.remove(i);
            break;
         }
      }      
            
      // Set the active wizard control within the page
      // about to be shown.
      this.navigator.currentPage.setWizard(this);
      
      // Add the page to the page panel in the wizard.
      pagePanel.add(this.navigator.getCurrentPage());
      
      // Wipe out the drawing state of the page panel so
      // the new panel will be displayed 
      pagePanel.invalidate();
      pagePanel.repaint();
      pagePanel.validate();
      
      // Have the screen set up based on the data bean provided.
      this.navigator.currentPage.setupScreenFromData(this.dataBean);
      
      // Have the screen setup for being displayed. 
      this.navigator.currentPage.pageDisplayed();
      
      // Enable the previous button if the active page is 
      // not the first page 
      this.previousButton.setEnabled(false == this.navigator.isFirstPage());
      
      // Re-label the next button based on the page index. 
      this.nextButton.setText(this.navigator.isLastPage() ? "Finish" : "  Next  ");      
   }

   /**
    * Adds the wizard finished listener to the wizard. 
    * 
    * @param listener
    *    The listener to add
    */
   public void addWizardFinishedListener(WizardFinishedListener<T> listener)
   {
     listenerList.add(WizardFinishedListener.class, listener);
   }
   
   /**
    * Removes the wizard finished listener from the listener list.
    * @param listener
    *    The listener to remove. 
    */
   public void removeWizardFinishedListener(WizardFinishedListener<T> listener) 
   {
     listenerList.remove(WizardFinishedListener.class, listener);
   }
   
   /**
    * Returns a bean of the controls in the wizard.
    * 
    * @return  
    *    The bean with the controls added to it. 
    */
   public WizardControls controls()
   {
      return new WizardControls(pagePanel, buttonPanel, previousButton, nextButton);
   }

   /**
    * Reacts to the previous and next button being pressed. 
    * 
    * @param e
    *    The action event.
    */
   @Override
   public void actionPerformed(ActionEvent e)
   {
      if (previousButton.equals(e.getSource()))
      {
         previousPage();
      }
      else if (nextButton.equals(e.getSource()))
      {
         if (this.navigator.getCurrentPage().nextOrFinishPressed())
         {
            this.dataBean = this.navigator.getCurrentPage().updateWizardData(this.dataBean);
            if (this.navigator.isLastPage())
            {
               finishPressed();
            }
            else
            {
               nextPage();
            }
         }
      }
   }
   
   /**
    * Sets a custom navigator for the wizard to use. 
    * @param navigator
    *    The navigator to use.
    */
   public void setNavigator(AbstractWizardNavigator<T> navigator)
   {
      this.navigator = navigator;
      this.navigator.pages = this.pages;
   }

   /**
    * Moves the wizard to the previous screen
    * if the active screen is not the first screen.
    */
   private void previousPage()
   {
      // Store the active index and page for the 
      // property change event that will be fired 
      // once the page is updated.
      int tempIndex = this.navigator.currentPageIndex;
      AbstractWizardPage<T> tempPage = this.navigator.currentPage;
      
      navigator.previousPage();
      changeActivePage(navigator.getCurrentIndex());
      
      // Fire the property changed listener for the page and page index
      // properties now that the page has changed.
      firePropertyChange(PAGE, tempPage, navigator.currentPage);
      firePropertyChange(PAGE_INDEX, tempIndex, this.navigator.currentPageIndex);
   }

   /**
    * Moves the wizard to the next page if the active'
    * page is not the last page.
    */
   private void nextPage()
   {
      // Store the active index and page for the 
      // property change event that will be fired 
      // once the page is updated.
      int tempIndex = this.navigator.currentPageIndex;
      AbstractWizardPage<T> tempPage = this.navigator.currentPage;
      
      navigator.nextPage();
      changeActivePage(navigator.getCurrentIndex());
      
      // Fire the property changed listener for the page and page index
      // properties now that the page has changed.
      firePropertyChange(PAGE, tempPage, navigator.currentPage);
      firePropertyChange(PAGE_INDEX, tempIndex, this.navigator.currentPageIndex);
   }

   /**
    * Fires the wizard finished listeners now that the 
    * wizard is complete.
    */
   @SuppressWarnings("unchecked")
   private void finishPressed()
   {
      Object[] listeners = listenerList.getListenerList();
      for (int i = 0; i < listeners.length; i = i + 2)
      {
         if (listeners[i] == WizardFinishedListener.class)
         {
            ((WizardFinishedListener<T>) listeners[i + 1]).wizardComplete(this.dataBean);
         }
      }
   }
}
