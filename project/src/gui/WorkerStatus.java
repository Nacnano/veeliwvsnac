package gui;

import game.GameLogic;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import utils.FontUtil;
import utils.GameConfig;

/**
 * The WorkerStatus class represents a graphical component that displays the number of workers
 * assigned to different work areas.
 */
public class WorkerStatus extends VBox {
	private Text field, mine, sawmill, smelter;
	
	/**
     * Constructs a new WorkerStatus object.
     * Initializes the visual appearance and text components.
     */
	public WorkerStatus() {
		this.setBackground(new Background(new BackgroundFill(Color.DARKORANGE, CornerRadii.EMPTY, Insets.EMPTY)));
		this.setPrefSize(50 * GameConfig.getScale(), 50 * GameConfig.getScale());
		this.setSpacing(10);
		this.setAlignment(Pos.CENTER);
		
		field = new Text("Field Workers: " + Integer.toString(GameLogic.getNumberOfWorkers("Field")));
		field.setFont(FontUtil.getFont("extraSmall"));
		
		mine = new Text("Mine Workers: " + Integer.toString(GameLogic.getNumberOfWorkers("Mine")));
		mine.setFont(FontUtil.getFont("extraSmall"));
		
		sawmill = new Text("Sawmill Workers: " + Integer.toString(GameLogic.getNumberOfWorkers("Sawmill")));
		sawmill.setFont(FontUtil.getFont("extraSmall"));
		
		smelter = new Text("Smelter Workers: " + Integer.toString(GameLogic.getNumberOfWorkers("Smelter")));
		smelter.setFont(FontUtil.getFont("extraSmall"));
		
		this.getChildren().addAll(field, mine, sawmill, smelter);
	}
	
	/**
     * Updates the displayed worker counts by fetching the current values from the GameLogic.
     */
	public void update() {
		this.setField(GameLogic.getNumberOfWorkers("Field"));
		this.setMine(GameLogic.getNumberOfWorkers("Mine"));
		this.setSawmill(GameLogic.getNumberOfWorkers("Sawmill"));
		this.setSmelter(GameLogic.getNumberOfWorkers("Smelter"));
	}
	
	/**
     * Sets the number of field workers and updates the corresponding text.
     *
     * @param field the number of field workers
     */
	public void setField(int field) {
		this.field.setText("Field Workers: " + Integer.toString(field));
	}
	
	/**
     * Sets the number of mine workers and updates the corresponding text.
     *
     * @param mine the number of mine workers
     */
	public void setMine(int mine) {
		this.mine.setText("Mine Workers: " + Integer.toString(mine));
	}
	
	/**
     * Sets the number of sawmill workers and updates the corresponding text.
     *
     * @param sawmill the number of sawmill workers
     */
	public void setSawmill(int sawmill) {
		this.sawmill.setText("Sawmill Workers: " + Integer.toString(sawmill));
	}
	
	/**
     * Sets the number of smelter workers and updates the corresponding text.
     *
     * @param smelter the number of smelter workers
     */
	public void setSmelter(int smelter) {
		this.smelter.setText("Smelter Workers: " + Integer.toString(smelter));
	}
	
}
