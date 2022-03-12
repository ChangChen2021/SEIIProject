package controller;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import model.Recipe;
import view.DetailDialog;


 
/**
 * controller of detail dialog , interact between model and view
 * @author Rui Yang
 *
 */
public class DetailDialogController implements ActionListener, MouseListener, KeyListener {
	private DatabaseController dbController;
	private DetailDialog view;
	private Recipe model;
	private String path;

	public DetailDialogController(DetailDialog view, Recipe model) {
		this.view = view;
		this.model = model;
		path = view.clickedRecipe.getRecipeImage();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == view.choosePic) {
			uploadPic(view.re);
		}
		if (e.getSource() == view.confirmButton) {
			changeServe(view.clickedRecipe, view.re);
		}
		if (e.getSource() == view.favButton) {
			changeFav(view.clickedRecipe, view.re);
		}
		if (e.getSource() == view.confirmRate) {
			submitRate();
		}
		if (e.getSource() == view.editButton) {
			editRecipe();
		}
		if (e.getSource() == view.editDoneButton) {
			submitRecipe(view.clickedRecipe);
		}
		if (e.getSource() == view.deleteButton) {
			deleteRecipe(view.clickedRecipe);
		}
	}

	/**
	 * update chosen photo to database
	 * @param re
	 * @throws IOException 
	 */
	private void uploadPic(Recipe re){
		try {
			dbController = new DatabaseController();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		path = view.choosePicture();
		if(path!=null) {re.setRecipeImage(path);}
		else {
			path = view.clickedRecipe.getRecipeImage();
		}
		re.setRecipeImage(path);
		try {
			dbController.updateRecipe(re);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * update serve amount and ingredient amount to database.
	 * @param clickedRecipe
	 * @param re
	 */
	private void changeServe(Recipe clickedRecipe, Recipe re) {
//		try {
//			dbController = new DatabaseController();
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		re.setServe(view.updateServeAmount());
		re.setIngredients(view.updateIngredients());
//		try {
//			dbController.updateRecipe(re);
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
	}

	/**
	 * update favourite state to database
	 * @param clickedRecipe
	 * @param re
	 */
	private void changeFav(Recipe clickedRecipe, Recipe re) {
		try {
			dbController = new DatabaseController();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		re.setIsFavourite(view.updateFav());
		try {
			dbController.updateRecipe(re);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	/**
	 * update rate to database
	 */
	private void submitRate() {
		int i = JOptionPane.showConfirmDialog(view, "Do you want to submit your rate?");
		if (i == 0) {
			try {
				dbController = new DatabaseController();
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			view.re.setScore(new Integer(view.ratingSpinner.getValue().toString()));
			try {
				dbController.updateRecipe(view.re);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	/**
	 * update gui for edit
	 */
	private void editRecipe() {
		try {
			dbController = new DatabaseController();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		view.updateRecipe();
	}
	/**
	 * update the edit recipe to database
	 * @param clickedRecipe
	 */

	private void submitRecipe(Recipe clickedRecipe) {
		try {
			for(int i = 0; i<5;i++) {
			if(view.ingredientField[i][0].getText().isEmpty()) continue;
			double amount = Double.valueOf(view.ingredientField[i][0].getText());
			amount = (double)Math.round(amount*100)/100;
			view.ingredientField[i][0].setText(amount+"");
			}
			model = new Recipe(clickedRecipe.getRecipeId(), view.recipeNameTextField.getText(), path,
					new Integer(view.preTimeTextField.getText()), new Integer(view.cookTimeTextField.getText()),
					new Integer(view.serveTextField.getText()), view.setIngredientList(),
					view.instructionTextArea.getText(), new Integer(view.ratingSpinner.getValue().toString()),
					view.isFav);
			boolean isSuccessful = dbController.updateRecipe(model);
			if (isSuccessful) {
				JOptionPane.showMessageDialog(view, "Edit successfully");
				view.dispose();
				Recipe recipe;
				try {
					recipe = dbController.detailedRecipe(clickedRecipe.getRecipeId());
					DetailDialog dialog = new DetailDialog(recipe);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			} else {
				JOptionPane.showMessageDialog(view, "Edit fail");
			}
			view.updateSubmit();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * delete the clicked recipe and update to database.
	 * @param clickedRecipe
	 */
	private void deleteRecipe(Recipe clickedRecipe) {
		try {
			dbController = new DatabaseController();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			int i = JOptionPane.showConfirmDialog(view, "Are you sure to delete this recipe?");
			if (i == 0) {
				int isSuccess = dbController.deleteRecipe(clickedRecipe.getRecipeId());
				if (isSuccess > 0) {
					JOptionPane.showMessageDialog(view, "Delete successfully");
					view.dispose();
				} else {
					JOptionPane.showMessageDialog(view, "Delete fail");
				}
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
	public void mouseEntered(MouseEvent e) {
		if(e.getSource() == view.serveTextField) {
			String temp = view.serveTextField.getText();
			if(temp.equals("Please input an Integer...")) {
				view.serveTextField.setText("");
				view.serveTextField.setForeground(Color.BLACK);
			}
		}
		if(e.getSource() == view.preTimeTextField) {
			String temp = view.preTimeTextField.getText();
			if(temp.equals("Input Integer.")) {
				view.preTimeTextField.setText("");
				view.preTimeTextField.setForeground(Color.BLACK);
			}
		}
		if(e.getSource() == view.cookTimeTextField) {
			String temp = view.cookTimeTextField.getText();
			if(temp.equals("Input Integer.")) {
				view.cookTimeTextField.setText("");
				view.cookTimeTextField.setForeground(Color.BLACK);
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getSource() == view.serveTextField) {
			String temp = view.serveTextField.getText();
			if(temp.equals("")) {
				view.serveTextField.setText("Please input an Integer...");
				view.serveTextField.setForeground(Color.GRAY);
			}
		}
		if(e.getSource() == view.preTimeTextField) {
			String temp = view.preTimeTextField.getText();
			if(temp.equals("")) {
				view.preTimeTextField.setText("Input Integer.");
				view.preTimeTextField.setForeground(Color.GRAY);
			}
		}
		if(e.getSource() == view.cookTimeTextField) {
			String temp = view.cookTimeTextField.getText();
			if(temp.equals("")) {
				view.cookTimeTextField.setText("Input Integer.");
				view.cookTimeTextField.setForeground(Color.GRAY);
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		for(int i = 0; i < 8; i++) {
			if(e.getSource()==view.ingredientField[i][0]) {
				int keyChar = e.getKeyChar();				
				if(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9 || keyChar==KeyEvent.VK_PERIOD){
					
				}else{
					e.consume(); 
				}
			}
		}
		if(e.getSource()== view.serveTextField) {
			int keyChar = e.getKeyChar();				
			if(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9){
				
			}else{
				e.consume(); 
			}
		}
		if(e.getSource()== view.cookTimeTextField) {
			int keyChar = e.getKeyChar();				
			if(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9){
				
			}else{
				e.consume(); 
			}
		}
		if(e.getSource()== view.preTimeTextField) {
			int keyChar = e.getKeyChar();				
			if(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9){
				
			}else{
				e.consume(); 
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
