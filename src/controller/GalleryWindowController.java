package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


import model.Recipe;
import model.Recipes;
import view.CreateDialog;
import view.DetailDialog;
import view.GalleryWindow;

/**
 * Controller class of GalleryWindow, acts as an intermediary between view and model
 * defines what should happen on user interaction
 * 
 * @author Chang Chen
 */
public class GalleryWindowController implements MouseListener, ActionListener, KeyListener  {

	private Recipes model;
	private GalleryWindow view;
	private DatabaseController dbController;
	
	/**
	 * Constructor to create controller object
	 * 
	 * @param view view object of calculator which is managed by this controller
	 * @param model model object of calculator which is managed by this controller
	 */
	public GalleryWindowController(GalleryWindow view, Recipes model) {
		this.view = view;
		this.model = model;
	}
	/**
	 * Listeners, will be called whenever a key on the GalleryWindow is acted
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == view.btnCreate) showCreateDialog(); 
		if (e.getSource() == view.btnDisplay) showSearchResult();  
		if (e.getSource() == view.btnFavoriteButton) showFavoriteRecipes();
		if (e.getSource() == view.btnPrevious) showPreviousRecipes();
		if (e.getSource() == view.btnNext) showNextRecipes();
		if (e.getSource() == view.btnShowAll) showAllRecipes();

	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		for(int i=0;i<4;i++) {
			if (e.getSource() == view.galleryImages[i]) clickImageShowDetail(view.galleryImages[i]);
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		if(e.getSource() == view.txtSearch) {	
			String temp = view.txtSearch.getText();
			if(temp.equals("Searching...")) {
				view.txtSearch.setText("");
				view.txtSearch.setForeground(Color.BLACK);
			}
		}
	}
	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getSource() == view.txtSearch) {	
			String temp = view.txtSearch.getText();
			if(temp.equals("")) {
				view.txtSearch.setText("Searching...");
				view.txtSearch.setForeground(Color.GRAY);
			}
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getSource() == view.txtSearch) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				showSearchResult();
			}
		}
	}
	/**
	 *  when create button is clicked, show Create Dialog 
	 */
	public void showCreateDialog() {
		CreateDialog createDialog = new CreateDialog();
		createDialog.setLocationRelativeTo(null);
		createDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		createDialog.setVisible(true);
	}
	/**
	 * when showAll button is clicked, show all the recipes in the DB
	 */
	private void showAllRecipes() {
		try {
			dbController = new DatabaseController();
			model.recipes = dbController.searchRecipe("");
			System.out.println(model.recipes.size());
			view.curImageIndex = 0;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		view.updateGallery();
	}
	/**
	 * show the result user want to search
	 */
	public void showSearchResult() {
		try {
			dbController = new DatabaseController();
			model.recipes = dbController.searchRecipe(view.txtSearch.getText());
			System.out.println(model.recipes.size());
			view.curImageIndex = 0;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		view.updateGallery();
	}
	/**
	 * show user's favorite recipes
	 */
	public void showFavoriteRecipes() {
		try {
			dbController = new DatabaseController();
			model.recipes = dbController.FavouriteRecipe();
			view.curImageIndex = 0;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		view.updateGallery();
	}
	/**
	 * show the previous recipes
	 */
	public void showPreviousRecipes() {
		if(view.curImageIndex>0) {
			view.curImageIndex--;
			view.updateGallery();
		}else {
			JOptionPane.showMessageDialog(view, "No previous record.");
		}
	}
	/**
	 * show the recipes next
	 */
	public void showNextRecipes() {
		if(view.curImageIndex*4 + 3 < model.recipes.size()) {
			view.curImageIndex++;
			view.updateGallery();
		}else {
			JOptionPane.showMessageDialog(view, "No more record.");
		}
	}
	/**
	 * when user click on the image of specific recipe, pop up a detailed dialog
	 * @param galleryImage the image clicked
	 */
	public void clickImageShowDetail(JLabel galleryImage) {
		try {
			//display the recipe details
			dbController = new DatabaseController();
			Recipe recipe = dbController.detailedRecipe(view.galleryImagesMap.get(galleryImage).getRecipeId()) ;
			DetailDialog dialog = new DetailDialog(recipe);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(view, "No record");
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
