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

/**
 * The MaterialStatus class represents a graphical component that displays the current material status of the game.
 * It shows the quantities of food, wood, stone, iron, iron ore, and money.
 */
public class MaterialStatus extends HBox {
	
	private Text food, wood, stone, iron, ironOre, money;
	
	/**
     * Constructs a new MaterialStatus component.
     * It sets up the layout and initializes the text elements for each material.
     */
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
	
	/**
     * Updates the material quantities displayed in the component.
     * It retrieves the current material quantities from the GameLogic class and sets them in the corresponding text elements.
     */
	public void update() {
		this.setFood(GameLogic.getFood());
		this.setWood(GameLogic.getWood());
		this.setStone(GameLogic.getStone());
		this.setIron(GameLogic.getIron());
		this.setIronOre(GameLogic.getIronOre());
		this.setMoney(GameLogic.getMoney());
	}

	/**
     * Sets the quantity of food displayed in the component.
     *
     * @param food The quantity of food to set.
     */
	public void setFood(int food) {
		this.food.setText(Integer.toString(food));
	}

	/**
     * Sets the quantity of wood displayed in the component.
     *
     * @param wood The quantity of wood to set.
     */
	public void setWood(int wood) {
		this.wood.setText(Integer.toString(wood));
	}

	/**
     * Sets the quantity of stone displayed in the component.
     *
     * @param stone The quantity of stone to set.
     */
	public void setStone(int stone) {
		this.stone.setText(Integer.toString(stone));
	}

	/**
     * Sets the quantity of iron displayed in the component.
     *
     * @param iron The quantity of iron to set.
     */
	public void setIron(int iron) {
		this.iron.setText(Integer.toString(iron));
	}

	/**
     * Sets the quantity of iron ore displayed in the component.
     *
     * @param ironOre The quantity of iron ore to set.
     */
	public void setIronOre(int ironOre) {
		this.ironOre.setText(Integer.toString(ironOre));
	}

	/**
     * Sets the amount of money displayed in the component.
     *
     * @param money The amount of money to set.
     */
	public void setMoney(int money) {
		this.money.setText(Integer.toString(money));
	}
	
	
}
