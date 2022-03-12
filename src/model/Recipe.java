package model;

import java.util.List;

/**
 * The recipe class
 * @author Mingzhe Gu
 *
 */
public class Recipe implements Cloneable{
	private int recipeId;
	private String recipeName;
	private String recipeImage; 
	private int prepTime;
	private int cookTime;
	private int serve;
	private List<Ingredient> ingredients;
	private String instructions;
	private int score;
	private int isFavourite;
	
	public Recipe() {
		
	}

	/**
	 * @param recipeId
	 * @param recipeName
	 * @param recipeImage
	 * @param prepTime
	 * @param cookTime
	 * @param serve
	 * @param ingredients
	 * @param instructions
	 * @param score
	 * @param isFavourite
	 */
	public Recipe(int recipeId, String recipeName, String recipeImage, int prepTime, int cookTime, int serve,
			List<Ingredient> ingredients, String instructions, int score,int isFavourite) {
		this.recipeId = recipeId;
		this.recipeName = recipeName;
		this.recipeImage = recipeImage;
		this.prepTime = prepTime;
		this.cookTime = cookTime;
		this.serve = serve;
		this.ingredients = ingredients;
		this.instructions = instructions;
		this.score = score;
		this.isFavourite = isFavourite;
	}
	

	public Recipe(String recipeName, String recipeImage, int prepTime, int cookTime, int serve,
			List<Ingredient> ingredients, String instructions, int score, int isFavourite) {
		super();
		this.recipeName = recipeName;
		this.recipeImage = recipeImage;
		this.prepTime = prepTime;
		this.cookTime = cookTime;
		this.serve = serve;
		this.ingredients = ingredients;
		this.instructions = instructions;
		this.score = score;
		this.isFavourite = isFavourite;
	}

	public int getServe() {
		return serve;
	}

	public void setServe(int serve) {
		this.serve = serve;
	}

	/**
	 * @param recipeId
	 * @param recipeName
	 * @param recipeImage
	 * @param prepTime
	 * @param cookTime
	 */
	public Recipe(int recipeId, String recipeName, String recipeImage, int prepTime, int cookTime) {
		this.recipeId = recipeId;
		this.recipeName = recipeName;
		this.recipeImage = recipeImage;
		this.prepTime = prepTime;
		this.cookTime = cookTime;
	}

	public int getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(int recipeId) {
		this.recipeId = recipeId;
	}

	public String getRecipeName() {
		return recipeName;
	}

	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}

	public String getRecipeImage() {
		return recipeImage;
	}

	public void setRecipeImage(String recipeImage) {
		this.recipeImage = recipeImage;
	}

	public int getPrepTime() {
		return prepTime;
	}

	public void setPrepTime(int prepTime) {
		this.prepTime = prepTime;
	}

	public int getCookTime() {
		return cookTime;
	}

	public void setCookTime(int cookTime) {
		this.cookTime = cookTime;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getIsFavourite() {
		return isFavourite;
	}

	public void setIsFavourite(int isFavourite) {
		this.isFavourite = isFavourite;
	}
//	
	/*
	 * public String ingredientsToString() { String ingredientsContent = ""; for
	 * (Ingredient ingredient : ingredients) {
	 * ingredientsContent=ingredientsContent+ingredient.ingredientToString()+"\n"; }
	 * return ingredientsContent+"\n"; }
	 */
	
//	public String toString() {
//		return ingredientsToString();
//		
//	}
		public String toString() {
		return "recipeId:"+recipeId+"\n"+"recipeName:"+recipeName+"\n"+"recipeImage:"+recipeImage+"\n"+"prepTime:"+prepTime+"\n"+"cookTime:"+cookTime+"\n"+"serve:"+serve+"\n"+"ingredients: "+"\n"+ingredients.toString()+"instructions:"+instructions+"\n"+"score:"+score+"\n"+"isFavourite:"+isFavourite;
		
    }

		
		@Override
		public Object clone() throws CloneNotSupportedException {
			return super.clone();
		}
	
		
	
}
