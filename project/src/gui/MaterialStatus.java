package gui;

import game.GameLogic;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import utils.FontUtil;
import utils.GameConfig;

public class MaterialStatus extends HBox {
	
	private Text food, wood, stone, iron, ironOre, money;
	
	public MaterialStatus() {
		
		this.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));
		this.setPrefSize(150 * GameConfig.getScale(), 20 * GameConfig.getScale());
		this.setSpacing(35);
		this.setAlignment(Pos.CENTER);
//		this.setPadding(new Insets(9 * GameConfig.getScale(), 0, 0, 14 * GameConfig.getScale()));
		
		food = new Text(Integer.toString(GameLogic.getFood()));
		wood = new Text(Integer.toString(GameLogic.getWood()));
		stone = new Text(Integer.toString(GameLogic.getStone()));
		iron = new Text(Integer.toString(GameLogic.getIron()));
		ironOre = new Text(Integer.toString(GameLogic.getIronOre()));
		money = new Text(Integer.toString(GameLogic.getMoney()));
		
		food.setFont(FontUtil.getFont("extraSmall"));
		Label food_label = new Label("Food");
		food_label.setFont(FontUtil.getFont("extraSmall"));
		VBox food_box = new VBox();
		food_box.getChildren().addAll(food_label, food);
		food_box.setAlignment(Pos.CENTER);
		
		wood.setFont(FontUtil.getFont("extraSmall"));
		Label wood_label = new Label("Wood");
		wood_label.setFont(FontUtil.getFont("extraSmall"));
		VBox wood_box = new VBox();
		wood_box.getChildren().addAll(wood_label, wood);
		wood_box.setAlignment(Pos.CENTER);
		
		stone.setFont(FontUtil.getFont("extraSmall"));
		Label stone_label = new Label("Stone");
		stone_label.setFont(FontUtil.getFont("extraSmall"));
		VBox stone_box = new VBox();
		stone_box.getChildren().addAll(stone_label, stone);
		stone_box.setAlignment(Pos.CENTER);
		
		iron.setFont(FontUtil.getFont("extraSmall"));
		Label iron_label = new Label("Iron");
		iron_label.setFont(FontUtil.getFont("extraSmall"));
		VBox iron_box = new VBox();
		iron_box.getChildren().addAll(iron_label, iron);
		iron_box.setAlignment(Pos.CENTER);
		
		ironOre.setFont(FontUtil.getFont("extraSmall"));
		Label ironOre_label = new Label("ironOre");
		ironOre_label.setFont(FontUtil.getFont("extraSmall"));
		VBox ironOre_box = new VBox();
		ironOre_box.getChildren().addAll(ironOre_label, ironOre);
		ironOre_box.setAlignment(Pos.CENTER);
		
		money.setFont(FontUtil.getFont("extraSmall"));
		Label money_label = new Label("Money");
		money_label.setFont(FontUtil.getFont("extraSmall"));
		VBox money_box = new VBox();
		money_box.getChildren().addAll(money_label, money);
		money_box.setAlignment(Pos.CENTER);
		
		this.getChildren().addAll(food_box, wood_box, stone_box, iron_box, ironOre_box, money_box);
	
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
