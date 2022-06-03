package controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.*;

/**
 * Database needs to be connected to the GUI, this is our next move.
 * @author Mingde Huang
 *
 */
public class DatabaseController {

	private Connection con;
	
	public DatabaseController() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cookbook?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", "root", "12345");
	}
	
	public List<Recipe> searchRecipe(String string) throws Exception {
		Statement stmt = con.createStatement();
		List<Recipe> recipes = new ArrayList<Recipe>();
		Recipe result;
		ResultSet rset = stmt.executeQuery("SELECT * FROM recipe WHERE recipeName LIKE '%" + string + "%'");
		while(rset.next()) {
		    result = new Recipe(rset.getInt("recipeId"),rset.getString("recipeName"),rset.getString("recipeImage"),rset.getInt("preptime"),rset.getInt("cookTime"));
		    recipes.add(result);
		}
		return recipes;
	}

	public boolean createRecipe(Recipe recipe) {
		//System.out.println("ingre:------------" + recipe.getIngredients());
		PreparedStatement stmt;
	
		try {
			stmt = con.prepareStatement("INSERT INTO recipe (recipeName, recipeImage, prepTime, cookTime, serve, instructions, score, isFavourite) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
		
		stmt.setString(1, recipe.getRecipeName());
		stmt.setString(2, recipe.getRecipeImage());
		stmt.setInt(3, recipe.getPrepTime());
		stmt.setInt(4, recipe.getCookTime());
		stmt.setInt(5, recipe.getServe());
		stmt.setString(6, recipe.getInstructions());
		stmt.setInt(7, recipe.getScore());
		stmt.setInt(8, recipe.getIsFavourite());
		 stmt.execute();
	
		String maxId = "SELECT MAX(recipeId) FROM recipe";
	
		ResultSet rs = stmt.executeQuery(maxId);
		
		int currentId = 0;
		while( rs.next()) {
			currentId = rs.getInt(1); 
			
		}
		for (Ingredient map : recipe.getIngredients()) {

			
			String sql = "INSERT INTO ingredients (recipeId, materialName, unit, amount) VALUES ("
					+ Integer.toString(currentId) + ", '" + map.getMaterialName() + "', '" + map.getUnit() + "', " + Double.toString(map.getAmount()) +  ")";
			stmt.execute(sql);
		}
		stmt.close();
		return true;
		} catch (SQLException e) {
			return  false;
		}
	}
	
	public boolean updateRecipe(Recipe changedRecipe) {
		int re;
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement("UPDATE recipe SET recipeName=?, recipeImage=?, prepTime=?, cookTime=?, serve=?, instructions=?, score=?, isFavourite=? WHERE recipeId =?");
			stmt.setString(1, changedRecipe.getRecipeName());
			stmt.setString(2, changedRecipe.getRecipeImage());
			stmt.setInt(3, changedRecipe.getPrepTime());
			stmt.setInt(4, changedRecipe.getCookTime());
			stmt.setInt(5, changedRecipe.getServe());
			stmt.setString(6, changedRecipe.getInstructions());
			stmt.setInt(7, changedRecipe.getScore());
			stmt.setInt(8, changedRecipe.getIsFavourite());
			stmt.setInt(9, changedRecipe.getRecipeId());
			re = stmt.executeUpdate();
			String delete = "DELETE FROM ingredients WHERE recipeId=" + changedRecipe.getRecipeId();
			stmt = con.prepareStatement(delete);
		    stmt.executeUpdate();
			
			for (Ingredient map : changedRecipe.getIngredients()) {
				String sql = "INSERT IGNORE INTO ingredients (recipeId, materialName, unit, amount) VALUES ("
						+ Integer.toString(changedRecipe.getRecipeId()) + ", '" + map.getMaterialName() + "', '" + map.getUnit() + "', " + Double.toString(map.getAmount()) + ")";
				stmt = con.prepareStatement(sql);
				stmt.executeUpdate();
			}
			
			stmt.close();
			return true;
			
		} catch (SQLException e) {
			return false;
		}
	}
	
	public int deleteRecipe(int recipeId) {
		Statement stmt;
		try {
			stmt = con.createStatement();
			String deleteIngredients = "DELETE FROM ingredients WHERE recipeId = " + recipeId ;
			stmt.executeUpdate(deleteIngredients);
			String deleteRecipe = "DELETE FROM recipe WHERE recipeId = " + recipeId ;
			int i = stmt.executeUpdate(deleteRecipe);
		    System.out.println(i);
			stmt.close();
			return i;
		} catch (SQLException e) {
			return 0;
		}
		
		
	}
	
	public Recipe detailedRecipe(int recipeId) throws Exception {
		Statement stmt = con.createStatement();
		
		ResultSet ingredientSet = stmt.executeQuery("SELECT * FROM ingredients WHERE recipeId = " + recipeId);
		Recipe targetRecipe = new Recipe();
		List<Ingredient> targetIngredients = new ArrayList<>();
		Ingredient material;
		while(ingredientSet.next()) {
			material = new Ingredient(ingredientSet.getDouble("amount"), ingredientSet.getString("unit"), ingredientSet.getString("materialName"));
			targetIngredients.add(material);
		}
		ResultSet recipeSet = stmt.executeQuery("SELECT * FROM recipe WHERE recipeId = " + recipeId );
		while(recipeSet.next()) {
			targetRecipe.setRecipeId(recipeSet.getInt("recipeId"));
			//System.out.println(targetRecipe);
			targetRecipe.setRecipeName(recipeSet.getString("recipeName"));
			targetRecipe.setRecipeImage(recipeSet.getString("recipeImage"));
			targetRecipe.setPrepTime(recipeSet.getInt("prepTime"));
			targetRecipe.setCookTime(recipeSet.getInt("cookTime"));
			targetRecipe.setServe(recipeSet.getInt("serve"));
			targetRecipe.setInstructions(recipeSet.getString("instructions"));
			targetRecipe.setScore(recipeSet.getInt("score"));
			targetRecipe.setIsFavourite(recipeSet.getInt("isFavourite"));
			targetRecipe.setIngredients(targetIngredients);
		}
		
			
		
		return targetRecipe;
	}
	public List<Recipe> FavouriteRecipe() throws Exception {
		Statement stmt = con.createStatement();
		List<Recipe> recipes = new ArrayList<Recipe>();
		Recipe result;
		ResultSet rset = stmt.executeQuery("SELECT * FROM recipe WHERE isFavourite = 0");
		while(rset.next()) {
		    result = new Recipe(rset.getInt("recipeId"),rset.getString("recipeName"),rset.getString("recipeImage"),rset.getInt("preptime"),rset.getInt("cookTime"));
		    recipes.add(result);
		}
		return recipes;
	}
}
