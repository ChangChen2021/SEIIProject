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
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
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
import controller.CreateDialogController;

import model.Ingredient;
import model.Recipe;

import java.awt.Color;

/**
 * Create Create dialog view to write a new recipe for user.
 * 
 * @author Rui Yang
 *
 */

public class CreateDialog extends JDialog {

	
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	public JTextField recipeNameTextField;
	public JButton returnButton;
	public JTextField serveTextField;
	public JTextField preTimeTextField;
	public JTextField cookTimeTextField;
	public JButton favButton;
	public JTextArea instructionTextArea;
	public int isFav;
	public List<Ingredient> ingredients;
	public JButton confirmButton;
	public JButton choosePic;
	private JLabel realPicLabel;
	private String path;
	public JSpinner ratingSpinner;
	private JPanel detailPanel;
	public JTextField[][] ingredientField;
	private List<Double> serveAmountList = new ArrayList<Double>();;
	public JButton saveButton;
	private CreateDialogController controller;
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
	public JLabel ratingLabel;
	public Recipe clickedRecipe;
	public Recipe re;
	public JButton editButton;
	public JButton editDoneButton;
	public JButton confirmRate;
	public JButton deleteButton;
    private Recipe model;
	public CreateDialog() {
		
		controller = new CreateDialogController(this,model);
		JOptionPane.showMessageDialog(this, "Please input the ingredients amount according to your serve amount");
		//set Dialog size
		setDialog();
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		// set button panel layout
		GroupLayout gl_contentPanel = setDetailPanel(buttonPanel);
		// set recipe field layout
		recipeLabel = setRecipeName();
		// set picture choose field layout
		setPic();
		// set serve  field layout
		setServe();
		//set prep time and cook time field layout
		setTime();
		// set ingredient field layout
		setIngredients();
		// set instruction field layout
		setInstruction();
		//add components to detail panel
		addDetailPanel();
		// add components to button panel
		addButtonPanel(buttonPanel);
		//set panel layout of return to gallery panel
		setDownPanel(gl_contentPanel);
		//set all action listener
		setActionListener();
	}

	/**
	 * set layout of return to gallery panel
	 * @param gl_contentPanel
	 */
	private void setDownPanel(GroupLayout gl_contentPanel) {
		contentPanel.setLayout(gl_contentPanel);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				returnButton = new JButton("return to gallery");
				// when click ok button,show message to inform user editing successfully.

				buttonPane.add(returnButton);
				getRootPane().setDefaultButton(returnButton);
			}

		}
	}

	/**
	 * set layout and initialize attributes of favorite button, rating spinner and save button.
	 * @param buttonPanel
	 */
	private void addButtonPanel(JPanel buttonPanel) {
		favButton = new JButton(" "+"Not Favourite"+" ");
		favButton.setBorder(BorderFactory.createLoweredBevelBorder());
		favButton.setForeground(Color.GRAY);
		isFav = 1;
		favButton.setHorizontalAlignment(SwingConstants.LEFT);
		buttonPanel.add(favButton);
		JLabel ratingLabel = new JLabel("Rating\uFF1A");
		buttonPanel.add(ratingLabel);
		ratingLabel.setVisible(false);
		ratingSpinner = new JSpinner();
		ratingSpinner.setModel(new SpinnerNumberModel(0, 0, 5, 1));
		buttonPanel.add(ratingSpinner);
//		ratingSpinner.setVisible(false);
		saveButton = new JButton("Save recipe");
		buttonPanel.add(saveButton);
	}

	/**
	 * add all components in detail panel to this panel.
	 */
	private void addDetailPanel() {
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
//		detailPanel.add(instructionTextArea);
		detailPanel.add(amountLabel);
		detailPanel.add(unitNameLabel);
		detailPanel.add(materialLabel);
	}

	/**
	 * Initialize attributes of ingredients field components and set layout of it.
	 */
	private void setIngredients() {
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
				ingredientField[i][j].setEditable(true);
			}
		}
	}

	/**
	 * Initialize attributes of instruction field components and set layout of it.
	 */
	private void setInstruction() {
		instructionsLabel = new JLabel("Instructions");
		instructionsLabel.setFont(new Font("",Font.BOLD,15));
		instructionsLabel.setBounds(15, 520, 108, 21);
		instructionTextArea = new JTextArea();
		JScrollPane scrollPane_1 = new JScrollPane();  
		scrollPane_1.setBounds(15, 555, 595, 210);  
		detailPanel.add(scrollPane_1);  
		instructionTextArea.setBounds(15, 555, 595, 210);
		instructionTextArea.setText("");
		 scrollPane_1.setViewportView(instructionTextArea);
		instructionTextArea.setLineWrap(true);   

	}

	/**
	 * Initialize attributes of prep and cook time field components and set layout of them.
	 */
	private void setTime() {
		preTimeLabel = new JLabel("PreTime");
		preTimeLabel.setFont(new Font("",Font.BOLD,15));
		preTimeLabel.setBounds(15, 219, 200, 21);
		preTimeTextField = new JTextField();
		preTimeTextField.setEditable(true);
		preTimeTextField.setBounds(15, 248, 114, 27);
		preTimeTextField.setColumns(10);
		preTimeTextField.setText("Input Integer.");
		preTimeTextField.setForeground(Color.GRAY);
		unitLabel = new JLabel("mins");
		unitLabel.setBounds(143, 251, 36, 21);
		cookTimeLabel = new JLabel("Cook Time");
		cookTimeLabel.setFont(new Font("",Font.BOLD,15));
		cookTimeLabel.setBounds(270, 219, 200, 21);
		unitLabel2 = new JLabel("mins");
		unitLabel2.setBounds(386, 251, 36, 21);
		cookTimeTextField = new JTextField();
		cookTimeTextField.setEditable(true);
		cookTimeTextField.setBounds(270, 248, 96, 27);
		cookTimeTextField.setColumns(10);
		cookTimeTextField.setText("Input Integer.");
		cookTimeTextField.setForeground(Color.GRAY);
	}

	/**
	 * Initialize attributes of serve field components and set layout of it.
	 */
	private void setServe() {
		serveLabel = new JLabel("Serve");
		serveLabel.setFont(new Font("",Font.BOLD,15));
		serveLabel.setBounds(15, 142, 200, 21);
		serveTextField = new JTextField();
		serveTextField.setBounds(15, 172, 595, 32);
		serveTextField.setText("Please input an Integer...");
		serveTextField.setForeground(Color.GRAY);
		serveTextField.setColumns(10);
		confirmButton = new JButton("Confirm");
		confirmButton.setBounds(70, 142, 90, 30);
	}
	/**
	 * Initialize attributes of picture field components and set layout of it.
	 */
	private void setPic() {
		picLabel = new JLabel("Picture");
		picLabel.setFont(new Font("",Font.BOLD,15));
		choosePic = new JButton("upload photos");
		choosePic.setBounds(90, 72, 170, 20);
		int width = 50;
		int height = 27;
		ImageIcon image = new ImageIcon("");
		image.setImage(image.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
		realPicLabel = new JLabel();
		realPicLabel.setIcon(image);
		picLabel.setBounds(15, 72, 63, 21);
		realPicLabel.setBounds(15, 108, 50, 27);
	}

	/**
	 * Initialize attributes of recipe name field components and set layout of it.
	 * @return
	 */
	private JLabel setRecipeName() {
		JLabel recipeLabel = new JLabel("Recipe Name");
		recipeLabel.setFont(new Font("",Font.BOLD,15));
		recipeLabel.setBounds(15, 9, 200, 21);
		recipeNameTextField = new JTextField();
		recipeNameTextField.setEditable(true);
		recipeNameTextField.setBounds(15, 39, 595, 32);
		recipeNameTextField.setColumns(10);
		recipeNameTextField.setText("");
		return recipeLabel;
	}

	/**
	 * set button panel layout
	 * @param buttonPanel
	 * @return
	 */
	private GroupLayout setDetailPanel(JPanel buttonPanel) {
		detailPanel = new JPanel();
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
	 * set create dialog layout and size.
	 */
	private void setDialog() {
		setBounds(100, 100, 641, 900);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setResizable(false);
	}

	/**
	 * realize the photo choose function
	 * @return path of the choosed photo.
	 */
	public String choosePic() {
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
	 * realize the function of change ingredient amount according to the serve amount.
	 */
	public void updateIngredientAmount() {
		serveAmountList.add(new Double(serveTextField.getText()));
		for (int i = 0; i < 9; i++) {
			if (ingredientField[i][0].getText().isEmpty() == false) {
				Double amount = Double.valueOf(ingredientField[i][0].getText());
				if (serveAmountList.size() > 1)
					amount = Double.valueOf(Double.valueOf(ingredientField[i][0].getText())/ serveAmountList.get(serveAmountList.size() - 2)* serveAmountList.get(serveAmountList.size() - 1) + "");
				amount = (double) Math.round(amount * 100) / 100;
				
				ingredientField[i][0].setText(amount+"");
			

			}
		}
	}

	/**
	 * change the favButton state when it is clicked.
	 */
	public void updateFav() {
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
	}

	/**
	 * add the content of ingredient text field to the ingredient list
	 */
	public void updateIngredients() {
		ingredients = new ArrayList<Ingredient>();
		for (int i = 0; i < 9; i++) {
			if (!ingredientField[i][0].getText().isEmpty()) {
				ingredients.add(new Ingredient(Double.parseDouble(ingredientField[i][0].getText()),
						ingredientField[i][1].getText(), ingredientField[i][2].getText()));
			}
		}
	}

	public void closeDialog() {
		dispose();
	}

	/**
	 * set all action listener
	 */
	private void setActionListener() {
		confirmButton.addActionListener(controller);
		returnButton.addActionListener(controller);
		saveButton.addActionListener(controller);
		favButton.addActionListener(controller);
		choosePic.addActionListener(controller);
		serveTextField.addMouseListener(controller);
		preTimeTextField.addMouseListener(controller);
		cookTimeTextField.addMouseListener(controller);
		preTimeTextField.addKeyListener(controller);
		serveTextField.addKeyListener(controller);
		cookTimeTextField.addKeyListener(controller);
		for(int i = 0 ; i < 9 ; i++) {
			ingredientField[i][0].addKeyListener(controller);
		}
	}
}
