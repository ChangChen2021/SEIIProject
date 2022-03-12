package model;


/**
 * the Ingredient class
 * @author Mingzhe Gu
 *
 */
public class Ingredient {
	private double amount;
	private String unit;
	private String materialName;
	
	public Ingredient() {
		
	}
	
	public double getAmount() {
		return amount;
	}


	public void setAmount(double Amount) {
		this.amount = Amount;
	}


	public String getUnit() {
		return unit;
	}


	public void setUnit(String unit) {
		this.unit = unit;
	}


	public String getMaterialName() {
		return materialName;
	}


	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}


	public Ingredient(double myAmount, String unit, String materialName) {
		super();
		this.amount = myAmount;
		this.unit = unit;
		this.materialName = materialName;
	}
	public String toString() {
		return amount+"-"+unit+"-"+materialName;
		
	}


	

	



	
	 
}
