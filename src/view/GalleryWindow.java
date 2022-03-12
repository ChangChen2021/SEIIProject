package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.GalleryWindowController;
import model.Recipe;
import model.Recipes;

import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

/**
 * the gallery window 
 * @author Chang Chen
 *
 */
public class GalleryWindow extends JFrame {

	private Recipes model = new Recipes();
	private GalleryWindowController controller = new GalleryWindowController(this, model);
	public Map<JLabel,Recipe> galleryImagesMap = new HashMap<JLabel,Recipe>();
	
	private JPanel contentPanel;
	public JTextField txtSearch;
	
	JPanel operationPanel = new JPanel();
	JPanel displayPanel = new JPanel();
	JLabel coverUpImage = new JLabel();
	
	public int curImageIndex = 0;
	
	JLabel galleryImage1 = new JLabel();
	JLabel galleryInfo1 = new JLabel("");
	JLabel galleryDetailInfo1 = new JLabel("");
	JLabel galleryImage2 = new JLabel();
	JLabel galleryInfo2 = new JLabel("");
	JLabel galleryDetailInfo2 = new JLabel("");
	JLabel galleryImage3 = new JLabel();
	JLabel galleryInfo3 = new JLabel("");
	JLabel galleryDetailInfo3 = new JLabel("");
	JLabel galleryImage4 = new JLabel();
	JLabel galleryInfo4 = new JLabel("");
	JLabel galleryDetailInfo4 = new JLabel("");
	private JLabel[] galleryInfos = {galleryInfo1,galleryInfo2,galleryInfo3,galleryInfo4};
	private JLabel[] galleryDetailInfos = {galleryDetailInfo1,galleryDetailInfo2,galleryDetailInfo3,galleryDetailInfo4};
	public JLabel[] galleryImages = {galleryImage1,galleryImage2,galleryImage3,galleryImage4};
	
	public JButton btnCreate = new JButton("Create");
	public JButton btnDisplay = new JButton("Display");
	public JButton btnFavoriteButton = new JButton("Favorites");
	public JButton btnPrevious = new JButton("Previous");
	public JButton btnNext = new JButton("Next");
	public JButton btnShowAll = new JButton("ShowAll");
	  
	/**
	 * Create the frame.
	 */
	public GalleryWindow() {
		//setup the window
		setWindow();
		//setup the content panel
		setContentPanel();
		//setup the operation panel in the head
		setOperationPanel();
		//setup the coverup image
		setCoverUp();
		//text field for searching
		setSearchField();
		//setup the create button
		btnCreate.setBounds(646, 5, 123, 27);
		operationPanel.add(btnCreate);
		//setup the display button
		btnDisplay.setBounds(207, 5, 123, 27);
		operationPanel.add(btnDisplay);
		//setup the showAll button
		btnShowAll.setBounds(357, 5, 123, 29);
		operationPanel.add(btnShowAll);
		//setup the favorite button
		btnFavoriteButton.setBounds(508, 5, 123, 29);
		operationPanel.add(btnFavoriteButton);
		//setup previous button
		btnPrevious.setBounds(15, 504, 123, 29);
		contentPanel.add(btnPrevious);
		btnPrevious.setVisible(false);
		//setup next button
		btnNext.setBounds(662, 504, 123, 29);
		contentPanel.add(btnNext);
		btnNext.setVisible(false);
		//setup the display panel 
		setDisplayPanel();
		//setup galleryImages and galleryInfos
		setGallery();
		//setup action listener
		setListener();
	}
	private void setCoverUp() {
		coverUpImage.setBounds(15, 62, 780, 430);
		ImageIcon icon = new ImageIcon("./src/coverup.jpg");
		icon.setImage(icon.getImage().getScaledInstance(coverUpImage.getWidth(), coverUpImage.getHeight(), Image.SCALE_DEFAULT));
		coverUpImage.setIcon(icon);
		coverUpImage.grabFocus();
		contentPanel.add(coverUpImage);
	}
	private void setGallery() {
		for(int i = 0; i < 4; i++) {
			displayPanel.add(galleryImages[i]);
			displayPanel.add(galleryInfos[i]);
			displayPanel.add(galleryDetailInfos[i]);
			galleryImages[i].setBorder(BorderFactory.createRaisedSoftBevelBorder());
		}
	}
	/**
	 * update the information displayed in the gallery
	 */
	public void updateGallery() {
		//display the test recipe image, name of recipes, link the galleryImage with recipe
		displayPanel.setVisible(true);
		coverUpImage.setVisible(false);
		btnPrevious.setVisible(true);
		btnNext.setVisible(true);
		for(int i = 0; i < 4; i++) {
			if((i + 4 * curImageIndex) < model.recipes.size()) {
				ImageIcon icon = new ImageIcon( model.recipes.get(i+4*curImageIndex).getRecipeImage());
				icon.setImage(icon.getImage().getScaledInstance(galleryImages[i].getWidth(), galleryImages[i].getHeight(), Image.SCALE_DEFAULT));
				galleryImages[i].setIcon(icon);
				galleryInfos[i].setText(model.recipes.get(i+4*curImageIndex).getRecipeName());
				galleryInfos[i].setFont(new Font("Senif",Font.BOLD,18));
				galleryInfos[i].setHorizontalAlignment(SwingConstants.CENTER);
				galleryInfos[i].setToolTipText(model.recipes.get(i+4*curImageIndex).getRecipeName());
				galleryDetailInfos[i].setHorizontalAlignment(SwingConstants.CENTER);
				galleryDetailInfos[i].setText("<html><body><p align=\"center\">Cook Time:"+model.recipes.get(i+4*curImageIndex).getCookTime()
						+"<br/>Preparation Time: "+model.recipes.get(i+4*curImageIndex).getPrepTime()+"</p></body></html>");
				galleryDetailInfos[i].setFont(new Font("Senif",Font.PLAIN,12));
				galleryImagesMap.put(galleryImages[i],model.recipes.get(i+4*curImageIndex));
				
			}else {
				galleryImages[i].setIcon(null);
				galleryInfos[i].setText(null);
				galleryDetailInfos[i].setText(null);
				galleryImagesMap.put(galleryImages[i],null);
			}
		}
	}
	private void setDisplayPanel() {
		displayPanel.setBounds(15, 62, 780, 430);
		displayPanel.setBorder(BorderFactory.createTitledBorder("Gallery"));
		contentPanel.add(displayPanel);
		displayPanel.setLayout(new GridLayout(4, 1, 0, 0));
		displayPanel.setVisible(false);
	}
	private void setSearchField() {
		txtSearch = new JTextField("Searching...");
		txtSearch.setBounds(15, 6, 186, 27);
		txtSearch.setHorizontalAlignment(SwingConstants.LEFT);
		operationPanel.add(txtSearch);
		txtSearch.setColumns(20);
	}
	private void setOperationPanel() {
		operationPanel.setBounds(10, 5, 780, 50);
		contentPanel.add(operationPanel);
		operationPanel.setLayout(null);
	}
	private void setContentPanel() {
		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPanel);
		contentPanel.setLayout(null);
	}
	private void setWindow() {
		setResizable(false);
		setTitle("Cookbook Gallery");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 815, 600);
	}
	private void setListener() {
		btnCreate.addActionListener(controller);
		btnDisplay.addActionListener(controller);
		btnFavoriteButton.addActionListener(controller);
		btnPrevious.addActionListener(controller);
		btnNext.addActionListener(controller);
		btnShowAll.addActionListener(controller);

		//when click on the image the detail dialog will pop up
		for (int i = 0; i < 4; i++) {
			galleryImages[i].addMouseListener(controller);
		}
		coverUpImage.addMouseListener(controller);
		txtSearch.addMouseListener(controller);
		txtSearch.addKeyListener(controller);
	}
}
