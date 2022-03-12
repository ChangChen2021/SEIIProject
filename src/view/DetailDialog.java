package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import controller.DetailDialogController;
import model.Ingredient;
import model.Recipe;
import java.awt.Color;

/**
 * Create Detail recipe window to show ,delete and edit clicked recipe
 * 
 * @author Rui Yang
 *
 */
public class DetailDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel recipeLabel;
	private JLabel serveLabel;
	private JLabel preTimeLabel;
	private JLabel unitLabel;
	private JLabel cookTimeLabel;
	private JLabel unitLabel2;
	private JLabel ingredientsLabel;
	private JLabel instructionsLabel;
	private JLabel amountLabel;
	private JLabel unitNameLabel;
	private JLabel materialLabel;
	private JLabel picLabel;
	public JTextField recipeNameTextField;
	public JTextField serveTextField;
	public JTextField preTimeTextField;
	public JTextField cookTimeTextField;
	private JLabel ratingLabel;
	public JButton favButton;
	public JTextArea instructionTextArea;
	public int isFav;
	private List<Ingredient> ingredients;
	public Recipe clickedRecipe;
	public Recipe re;
	public JButton confirmButton;
	private int serveAmount;
	public JButton choosePic;
	private JLabel realPicLabel;
	private String path;
	public JSpinner ratingSpinner;
	private JPanel detailPanel;
	public JTextField[][] ingredientField;
	private DetailDialogController controller;
	public JButton editButton;
	public JButton editDoneButton;
	public JButton confirmRate;
	public JButton deleteButton;
	private Recipe model;
	private JPanel buttonPanel;
	private JScrollPane scroll;
	public DetailDialog() {

	}


	public DetailDialog(Recipe clickedRecipe) {
		this.clickedRecipe = clickedRecipe;
		controller = new DetailDialogController(this,model);
		try {
			re = (Recipe) clickedRecipe.clone();
		} catch (CloneNotSupportedException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		;
		//set detail dialog layout
		setDialog();
		//set button panel layout
		buttonPanel = new JPanel();
		buttonPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		detailPanel = new JPanel();
		GroupLayout gl_contentPanel = setDetailPanel(buttonPanel);
		//set recipe name field layout and show recipe name
		recipeLabel = setName(clickedRecipe);
		recipeLabel.setFont(new Font("",Font.BOLD,15));
		//set picture field layout and show pic from database
		setPic(clickedRecipe);
		//set serve field layout and show serve from database
		setServe(clickedRecipe);
		//set prepare and cook time field layout and show time from database
		setRecipeTime(clickedRecipe);
		//set ingredient field layout and show ingredients from database
		setIngredients(clickedRecipe);
		//set instruction field layout and show instruction from database
		setInstruction(clickedRecipe);
		//add components to detail panel
		addToDetailPanel();
		// isFav = 0(favourite) isFav = 1(not favourited)
		setFav(clickedRecipe, buttonPanel);
		//set Rate field layout
		setRate(clickedRecipe, buttonPanel);
		editDoneButton = new JButton("Edit done");
		deleteButton = new JButton("Delete");
		editDoneButton.setVisible(false);
		editButton = new JButton("Edit");
		buttonPanel.add(editButton);
		setDownPanel(gl_contentPanel);
		//set detail dialog action listener
		setDialogListener();
	}

	/**
	 * set layout of button panel and detail panel
	 * @param buttonPanel
	 * @return
	 */
	private GroupLayout setDetailPanel(JPanel buttonPanel) {
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(buttonPanel, GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE)
				.addComponent(detailPanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE));
		gl_contentPanel.setVerticalGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup().addGap(0)
						.addComponent(buttonPanel, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(detailPanel, GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)));
		return gl_contentPanel;
	}

	/**
	 * set layout of delete button and edit-done button.
	 * @param gl_contentPanel
	 */
	private void setDownPanel(GroupLayout gl_contentPanel) {
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				buttonPane.add(editDoneButton);
				getRootPane().setDefaultButton(editDoneButton);
			}
			{
				buttonPane.add(deleteButton);
			}
		}
	}

	/**
	 * set layout and initialize attributes of rate field.
	 * @param clickedRecipe
	 * @param buttonPanel
	 */
	private void setRate(Recipe clickedRecipe, JPanel buttonPanel) {
		ratingLabel = new JLabel("Rating\uFF1A");
		buttonPanel.add(ratingLabel);
		ratingSpinner = new JSpinner();
		ratingSpinner.setModel(new SpinnerNumberModel(clickedRecipe.getScore(), 0, 5, 1));
		buttonPanel.add(ratingSpinner);
		confirmRate = new JButton("Confirm rating");
		buttonPanel.add(confirmRate);
	}

	/**
	 * set layout and initialize attributes of favButton field.
	 * @param clickedRecipe
	 * @param buttonPanel
	 */
	private void setFav(Recipe clickedRecipe, JPanel buttonPanel) {
		favButton = new JButton("not Favourite");
		favButton.setEnabled(true);
		if (clickedRecipe.getIsFavourite() == 0) {
			favButton.setText("  "+"Favourited"+"   ");
			favButton.setForeground(Color.BLACK);
			favButton.setBorder(BorderFactory.createRaisedBevelBorder());
		}
		if (clickedRecipe.getIsFavourite() == 1) {
			favButton.setText(" "+"Not Favourite"+" ");
			favButton.setForeground(Color.GRAY);
			favButton.setBorder(BorderFactory.createLoweredSoftBevelBorder());
		}
		favButton.setHorizontalAlignment(SwingConstants.LEFT);
		buttonPanel.add(favButton);
	}

	/**
	 * add conponents of detail panel to this panel.
	 */
	private void addToDetailPanel() {
		detailPanel.setLayout(null);
		detailPanel.add(recipeLabel);
		detailPanel.add(choosePic);
		detailPanel.add(recipeNameTextField);
		detailPanel.add(picLabel);
		detailPanel.add(realPicLabel);
		detailPanel.add(serveLabel);
		detailPanel.add(serveTextField);
		detailPanel.add(preTimeLabel);
		detailPanel.add(cookTimeLabel);
		detailPanel.add(preTimeTextField);
		detailPanel.add(unitLabel);
		detailPanel.add(cookTimeTextField);
		detailPanel.add(unitLabel2);
		detailPanel.add(ingredientsLabel);
		detailPanel.add(confirmButton);
		detailPanel.add(instructionsLabel);
		detailPanel.add(amountLabel);
		detailPanel.add(unitNameLabel);
		detailPanel.add(materialLabel);
	}

	/**
	 * set layout and initialize attributes of instruction field.
	 * @param clickedRecipe
	 */
	private void setInstruction(Recipe clickedRecipe) {
		instructionsLabel = new JLabel("Instructions");
		instructionsLabel.setFont(new Font("",Font.BOLD,15));
		instructionsLabel.setBounds(15, 520, 108, 21);
		instructionTextArea = new JTextArea();
		JScrollPane scrollPane_1 = new JScrollPane();  
		scrollPane_1.setBounds(15, 555, 595, 210);  
		detailPanel.add(scrollPane_1);  
		instructionTextArea.setEditable(false);
		instructionTextArea.setBounds(15, 555, 595, 210);
		instructionTextArea.setText(clickedRecipe.getInstructions());
		 scrollPane_1.setViewportView(instructionTextArea);
		instructionTextArea.setLineWrap(true);   
	}

	/**
	 * set layout and initialize attributes of ingredients field.
	 * @param clickedRecipe
	 */
	private void setIngredients(Recipe clickedRecipe) {
		ingredientsLabel = new JLabel("Ingredients");
		ingredientsLabel.setFont(new Font("",Font.BOLD,15));
		ingredientsLabel.setBounds(15, 278, 99, 21);
		amountLabel = new JLabel("Amount");
		amountLabel.setBounds(17, 300, 70, 25);
		unitNameLabel = new JLabel("Unit");
		unitNameLabel.setBounds(100, 300, 70, 25);
		materialLabel = new JLabel("Material");
		materialLabel.setBounds(183, 300, 70, 25);
		ingredientField = new JTextField[9][3];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 3; j++) {
				ingredientField[i][j] = new JTextField();
				detailPanel.add(ingredientField[i][j]);
				ingredientField[i][j].setBounds(17 + j * 83, 326 + 20 * i, 80, 20);
				if ( j == 2 ) {
					ingredientField[i][j].setBounds(17 + j * 83, 326 + 20 * i, 150, 20);
				}
				
				ingredientField[i][j].setEditable(false);
			}
		}
		for (int i = 0; i < clickedRecipe.getIngredients().size(); i++) {
			ingredientField[i][0].setText(clickedRecipe.getIngredients().get(i).getAmount() + "");
			ingredientField[i][1].setText(clickedRecipe.getIngredients().get(i).getUnit());
			ingredientField[i][2].setText(clickedRecipe.getIngredients().get(i).getMaterialName());
		}
	}

	/**
	 * set layout and initialize attributes of time field.
	 * @param clickedRecipe
	 */
	private void setRecipeTime(Recipe clickedRecipe) {
		preTimeLabel = new JLabel("PreTime");
		preTimeLabel.setFont(new Font("",Font.BOLD,15));
		preTimeLabel.setBounds(15, 219, 200, 21);
		preTimeTextField = new JTextField();
		preTimeTextField.setEditable(false);
		preTimeTextField.setBounds(15, 248, 114, 27);
		preTimeTextField.setText("Input Integer.");
		preTimeTextField.setColumns(10);
		preTimeTextField.setText(clickedRecipe.getPrepTime() + "");
		unitLabel = new JLabel("mins");
		unitLabel.setBounds(143, 251, 36, 21);
		cookTimeLabel = new JLabel("Cook Time");
		cookTimeLabel.setFont(new Font("",Font.BOLD,15));
		cookTimeLabel.setBounds(270, 219, 200, 21);
		unitLabel2 = new JLabel("mins");
		unitLabel2.setBounds(386, 251, 36, 21);
		cookTimeTextField = new JTextField();
		cookTimeTextField.setEditable(false);
		cookTimeTextField.setBounds(270, 248, 96, 27);
		cookTimeTextField.setText("Input Integer.");
		cookTimeTextField.setColumns(10);
		cookTimeTextField.setText(clickedRecipe.getCookTime() + "");
	}

	/**
	 * set layout and initialize attributes of serve field.
	 * @param clickedRecipe
	 * @return
	 */
	private JLabel setServe(Recipe clickedRecipe) {
		serveLabel = new JLabel("Serve");
		serveLabel.setFont(new Font("",Font.BOLD,15));
		serveLabel.setBounds(15, 142, 200, 21);
		serveTextField = new JTextField();
		serveTextField.setBounds(15, 172, 595, 32);
		serveTextField.setText("Please input an Integer...");
		serveTextField.setText(clickedRecipe.getServe() + "");
		serveTextField.setColumns(10);
		confirmButton = new JButton("Confirm");
		confirmButton.setBounds(70, 142, 90, 30);
		return serveLabel;
	}

	/**
	 * set layout and initialize attributes of picture field.
	 * @param clickedRecipe
	 * @return
	 */
	private JLabel setPic(Recipe clickedRecipe) {
		picLabel = new JLabel("Picture");
		picLabel.setFont(new Font("",Font.BOLD,15));
		choosePic = new JButton("upload photos");
		choosePic.setBounds(90, 72, 170, 20);
		int width = 50;
		int height = 27;
		ImageIcon image = new ImageIcon(clickedRecipe.getRecipeImage());
		image.setImage(image.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
		realPicLabel = new JLabel();
		realPicLabel.setIcon(image);
		picLabel.setBounds(15, 72, 63, 21);
		realPicLabel.setBounds(15, 108, 50, 27);
		return picLabel;
	}

	/**
	 * set layout and initialize attributes of recipe name field.
	 * @param clickedRecipe
	 * @return
	 */
	private JLabel setName(Recipe clickedRecipe) {
		recipeLabel = new JLabel("Recipe Name");
		recipeLabel.setFont(new Font("",Font.BOLD,15));
		recipeLabel.setBounds(15, 9, 200, 21);
		recipeNameTextField = new JTextField();
		recipeNameTextField.setEditable(false);
		recipeNameTextField.setBounds(15, 39, 595, 32);
		recipeNameTextField.setColumns(10);
		recipeNameTextField.setText(clickedRecipe.getRecipeName());
		return recipeLabel;
	}

	/**
	 * set layout of dialog field.
	 */
	private void setDialog() {
		setBounds(100, 100, 641, 900);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setResizable(false);
		setLocationRelativeTo(null);
	}

	/**
	 * add all action listener of detail dialog
	 */
	private void setDialogListener() {
		// TODO Auto-generated method stub
		choosePic.addActionListener(controller);
		confirmButton.addActionListener(controller);
		favButton.addActionListener(controller);
		confirmRate.addActionListener(controller);
		editButton.addActionListener(controller);
		editDoneButton.addActionListener(controller);
		deleteButton.addActionListener(controller);
		serveTextField.addMouseListener(controller);
		preTimeTextField.addMouseListener(controller);
		cookTimeTextField.addMouseListener(controller);
		for(int i = 0; i < 9; i++) {
			ingredientField[i][0].addKeyListener(controller);			
		}
		preTimeTextField.addKeyListener(controller);
		serveTextField.addKeyListener(controller);
		cookTimeTextField.addKeyListener(controller);
	}

	/**
	 * realize the funtion of choose photo
	 * @return the path of choosed photo
	 */
	public String choosePicture(){
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG Images", "jpg", "png");
		File f = null;
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(this);		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			f = chooser.getSelectedFile();
			path="./src/Pics/"+f.getName();
			FileChannel inputChannel = null;   
		    FileChannel outputChannel = null;
		    Path sourcePath = Paths.get(f.getAbsolutePath());
		    Path destinationPath = Paths.get(path);
		    //do not choose file from source folder!!!
		    try { 
			    inputChannel = new FileInputStream(f).getChannel(); 
			    outputChannel = new FileOutputStream(new File(path)).getChannel(); 
			    outputChannel.transferFrom(inputChannel, 0, inputChannel.size()); 
			  } catch (IOException e) {
				JOptionPane.showMessageDialog(this,"Picture failed to save.");
			  } finally { 
			    try {
					inputChannel.close();
					outputChannel.close(); 
				} catch (IOException e) {
					JOptionPane.showMessageDialog(this,"Channel failed to close.");
				} 
			 }
			ImageIcon i = new ImageIcon(path);
			i.setImage(i.getImage().getScaledInstance(50, 27, Image.SCALE_DEFAULT));
			realPicLabel.setIcon(i);
		    }
		return path;

	}

	/**
	 * change ingredient amount according to serve amount
	 * @return
	 */
	public List<Ingredient> updateIngredients() {
		serveAmount = new Integer(serveTextField.getText());
		for (int i = 0; i < clickedRecipe.getIngredients().size(); i++) {
			double amount = Double.valueOf(clickedRecipe.getIngredients().get(i).getAmount() / clickedRecipe.getServe() * serveAmount + "");
			amount = (double) Math.round(amount * 100) / 100;
			ingredientField[i][0].setText(amount+"");
			ingredientField[i][1].setText(clickedRecipe.getIngredients().get(i).getUnit());
			ingredientField[i][2].setText(clickedRecipe.getIngredients().get(i).getMaterialName());
		}
		ingredients = new ArrayList<Ingredient>();
		for (int i = 0; i < 9; i++) {
			if (ingredientField[i][0].getText().isEmpty() == false) {
				Ingredient getIngredient = new Ingredient();
				getIngredient.setAmount(Double.valueOf(ingredientField[i][0].getText()));
				getIngredient.setUnit(ingredientField[i][1].getText());
				getIngredient.setMaterialName(ingredientField[i][2].getText());
				ingredients.add(getIngredient);
			}
		}
		return ingredients;
	}

	/**
	 * get the newest serve amount which the user input.
	 * @return
	 */
	public int updateServeAmount() {
		serveAmount = new Integer(serveTextField.getText());
		return serveAmount;
	}

	/**
	 * change favButton when it is clicked.
	 * @return
	 */
	public int updateFav() {
		if (favButton.getText() == " "+"Not Favourite"+" ") {
			favButton.setText("  "+"Favourited"+"   ");
			favButton.setForeground(Color.BLACK);
			favButton.setBorder(BorderFactory.createRaisedBevelBorder());
			isFav = 0;

		} else {
			favButton.setText(" "+"Not Favourite"+" ");
			favButton.setBackground(null);
			favButton.setForeground(Color.GRAY);
			favButton.setBorder(BorderFactory.createLoweredBevelBorder());
			isFav = 1;

		}
		return isFav;
	}

	/**
	 * change the detail dialog view when clicked edit button.
	 */
	public void updateRecipe() {
		buttonPanel.setVisible(false);
		editButton.setVisible(false);
		editDoneButton.setVisible(true);
		confirmButton.setVisible(false);
		deleteButton.setVisible(false);
		favButton.setVisible(false);
		ratingSpinner.setVisible(false);
		confirmRate.setVisible(false);
		ratingLabel.setVisible(false);
		recipeNameTextField.setEditable(true);
		preTimeTextField.setEditable(true);
		cookTimeTextField.setEditable(true);
		serveTextField.setEditable(true);
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 3; j++) {
				ingredientField[i][j].setEditable(true);
			}
		}
		instructionTextArea.setEditable(true);
	}

	/**
	 * put all the content of ingredient text field to a list.
	 * @return
	 */
	public List<Ingredient> setIngredientList() {
		ingredients = new ArrayList<Ingredient>();
		for (int i = 0; i < 9; i++) {
			if (ingredientField[i][0].getText().isEmpty() == false) {
				Ingredient getIngredient = new Ingredient();
				getIngredient.setAmount(Double.valueOf(ingredientField[i][0].getText()));
				getIngredient.setUnit(ingredientField[i][1].getText());
				getIngredient.setMaterialName(ingredientField[i][2].getText());
				ingredients.add(getIngredient);
			}
		}
		return ingredients;
	}

	/**
	 * when clicked edit done button, the detail dialog view changed.
	 */
	public void updateSubmit() {
		favButton.setVisible(true);
		ratingLabel.setVisible(true);
		ratingSpinner.setVisible(true);
		confirmRate.setVisible(true);
		deleteButton.setVisible(true);
	}
}
