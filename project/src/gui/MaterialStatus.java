package gui;

import game.GameLogic;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class MaterialStatus extends HBox {
	
	private Text food, wood, stone, iron, ironOre, money;
	
	public MaterialStatus() {
		food = new Text(Integer.toString(GameLogic.getFood()));
		wood = new Text(Integer.toString(GameLogic.getWood()));
		stone = new Text(Integer.toString(GameLogic.getStone()));
		iron = new Text(Integer.toString(GameLogic.getIron()));
		ironOre = new Text(Integer.toString(GameLogic.getIronOre()));
		money = new Text(Integer.toString(GameLogic.getMoney()));
		
		this.getChildren().addAll(food, wood, stone, iron, ironOre, money);
	}
	
	public void update() {
		this.setFood(GameLogic.getFood());
		this.setWood(GameLogic.getWood());
		this.setStone(GameLogic.getStone());
		this.setIron(GameLogic.getIron());
		this.setIronOre(GameLogic.getIronOre());
		this.setMoney(GameLogic.getMoney());
	}

	public void setFood(int food) {
		this.food.setText(Integer.toString(food));
	}

	public void setWood(int wood) {
		this.wood.setText(Integer.toString(wood));
	}

	public void setStone(int stone) {
		this.stone.setText(Integer.toString(stone));
	}

	public void setIron(int iron) {
		this.iron.setText(Integer.toString(iron));
	}

	public void setIronOre(int ironOre) {
		this.ironOre.setText(Integer.toString(ironOre));
	}

	public void setMoney(int money) {
		this.money.setText(Integer.toString(money));
	}
	
	
}
