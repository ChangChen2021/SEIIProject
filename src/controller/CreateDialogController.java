package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import model.Recipe;
import view.CreateDialog;
/**
 * Controller of Create dialog , connect the model and view.
 * @author Rui Yang
 *
 */
public class CreateDialogController implements ActionListener, MouseListener, KeyListener {
	private DatabaseController dbController;
	private CreateDialog view;
	private String path;
	private Recipe model;
	public CreateDialogController(CreateDialog view, Recipe model) {
		this.view = view;
        this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == view.choosePic) {
			uploadPic();
		}
		if (e.getSource() == view.confirmButton) {
			changeAmount();
		}
		if (e.getSource() == view.favButton) {
			changeFav();
		}
		if (e.getSource() == view.saveButton) {
			saveRecipe();
		}
		if (e.getSource() == view.returnButton) {
			closeDialog();
		}
	}
  
	private void uploadPic() {
		path = view.choosePic();
	}

	private void changeAmount() {
		view.updateIngredientAmount();
	}

	private void changeFav() {
		view.updateFav();
	}

	/**
	 * save the new created recipe to database
	 */
	private void saveRecipe() {
		try {
			dbController = new DatabaseController();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			if(view.recipeNameTextField.getText().equals("")) {
				JOptionPane.showMessageDialog(view,"Please input the recipe name!");
			}
			else if(view.serveTextField.getText().equals("Please input an Integer...")) {
				JOptionPane.showMessageDialog(view,"Please input the serve amount!");
			}
			else if(view.instructionTextArea.getText().equals("")) {
				JOptionPane.showMessageDialog(view,"Please input the instruction!");
			}
			else if(view.preTimeTextField.getText().equals("Input Integer.")) {
				JOptionPane.showMessageDialog(view,"Please input the prepare time!");
			}
			else if(view.cookTimeTextField.getText().equals("Input Integer.")) {
				JOptionPane.showMessageDialog(view,"Please input the cook time!");
			}
			else {
			view.updateIngredients();
		    model = new Recipe(view.recipeNameTextField.getText(), path,
					new Integer(view.preTimeTextField.getText()), new Integer(view.cookTimeTextField.getText()),
					new Integer(view.serveTextField.getText()), view.ingredients, view.instructionTextArea.getText(),
					new Integer(view.ratingSpinner.getValue().toString()), view.isFav);
			boolean isSuccessful = dbController.createRecipe(model);
			if (isSuccessful) {
				JOptionPane.showMessageDialog(view, "Create Recipe Successfully");

			} else {
				JOptionPane.showMessageDialog(view, "Create Recipe failed");
			}
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private void closeDialog() {
		view.closeDialog();
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
		for(int i = 0; i < 5; i++) {
			if(e.getSource()== view.ingredientField[i][0]) {
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
