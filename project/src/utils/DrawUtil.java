package utils;

import java.nio.IntBuffer;

import controller.InterruptController;
import entity.building.BaseBuilding;
import entity.building.Field;
import entity.building.House;
import entity.building.MilitaryCamp;
import entity.building.Mine;
import entity.building.Sawmill;
import entity.building.Smelter;
import entity.unit.BaseUnit;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.image.WritablePixelFormat;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import game.Cell;
import game.Position;
import game.Terrain;
import scene.GameScene;

/**
 * The utility class that provide sprite drawing method and PixelReader for
 * sprite.
 *
 */
public class DrawUtil {

	/**
	 * Plan sprites.
	 */
	private static PixelReader plainSprites;
	
	/**
	 * Plan sprites.
	 */
	private static PixelReader forestSprites;
	
	/**
	 * Plan sprites.
	 */
	private static PixelReader mountainSprites;
	
	/**
	 * Plan sprites.
	 */
	private static PixelReader waterSprites;
	
	/**
	 * Barn sprites.
	 */
	private static PixelReader barnSprites;
	
	/**
	 * Mine sprites.
	 */
	private static PixelReader mineSprites;
	
	/**
	 * Sawmill sprites.
	 */
	private static PixelReader sawmillSprites;
	
	/**
	 * Smelter sprites.
	 */
	private static PixelReader smelterSprites;
	
	/**
	 * House sprites.
	 */
	private static PixelReader houseSprites;
	
	/**
	 * Military camp sprites.
	 */
	private static PixelReader militaryCampSprites; 
	
	/**
	 * Swordman sprites.
	 */
	private static PixelReader SwordManSprites;

	/**
	 * Attack mouse icon.
	 */
	private static Image attackMouseIcon;
	
	/**
	 * Attack mouse icon.
	 */
	private static PixelReader debugSprites;

	/**
	 * Loads resources.
	 */
	static {
		plainSprites = getImagePixelReader("terrain/Plain.png");
		forestSprites = getImagePixelReader("terrain/Forrest.png");
		mountainSprites = getImagePixelReader("terrain/Mountain.png");
		waterSprites = getImagePixelReader("terrain/Water.png");
		
		barnSprites = getImagePixelReader("building/Barn.png");
		mineSprites = getImagePixelReader("building/Mine.png");
		sawmillSprites = getImagePixelReader("building/Sawmill.png");
		smelterSprites = getImagePixelReader("building/Smelter.png");
		houseSprites = getImagePixelReader("building/House.png");
		militaryCampSprites = getImagePixelReader("building/MilitaryCamp.png");
		
		SwordManSprites = getImagePixelReader("unit/SwordMan.png");
		
		debugSprites = getImagePixelReader("unit/SwordMan.png");
	}

	/**
	 * Renders individual {@link Cell cell} sprite at the specified position on the
	 * game scene.
	 * 
	 * @param y    Position in the Y-axis
	 * @param x    Position in the X-axis
	 * @param cell The cell to be rendered
	 */
	public static void drawTerrain(int y, int x, Terrain terrain) {
		GraphicsContext gc = GameScene.getGraphicsContext();
			
		PixelReader drawSprites;
			switch(terrain) {
				case PLAIN:
					drawSprites = plainSprites;
					break;
				case FOREST:
					drawSprites = forestSprites;
					break;
				case MOUNTAIN:
					drawSprites = mountainSprites;
					break;
				case WATER:
					drawSprites = waterSprites;
					break;
				default:
					System.out.println("Invalid terrain for drawTerrain");
					drawSprites = debugSprites;
			}
			WritableImage img = new WritableImage(drawSprites, 32, 32);
			gc.drawImage(scaleUp(img, GameConfig.getScale()), x, y - 8 * GameConfig.getScale());
	}


	/**
	 * Renders health point bar of the {@link Entity entity} at the specified
	 * position on the game scene.
	 * 
	 * @param y      Position in the Y-axis
	 * @param x      Position in the X-axis
	 * @param entity The entity to be rendered
	 */
	public static void drawUnitPeopleBar(int y, int x, BaseUnit unit) {

		GraphicsContext gc = GameScene.getGraphicsContext();
		gc.setFill(Color.BLACK);
		gc.fillRect(x + 4 * GameConfig.getScale(), y - 4 * GameConfig.getScale(), 25 * GameConfig.getScale(),
				2 * GameConfig.getScale());
		gc.setFill(Color.RED);
		gc.fillRect(x + 4 * GameConfig.getScale(), y - 4 * GameConfig.getScale(),
				Math.ceil((double) unit.getPeople() / (double) GameConfig.MILITARY_SIZE * 25.0 * GameConfig.getScale()),
				2 * GameConfig.getScale());
	}
	
	/**
	 * Renders health point bar of the {@link Entity entity} at the specified
	 * position on the game scene.
	 * 
	 * @param y      Position in the Y-axis
	 * @param x      Position in the X-axis
	 * @param entity The entity to be rendered
	 */
	public static void drawBuildingDurabilityBar(int y, int x, BaseBuilding building) {

		GraphicsContext gc = GameScene.getGraphicsContext();
		gc.setFill(Color.BLACK);
		gc.fillRect(x + 4 * GameConfig.getScale(), y - 4 * GameConfig.getScale(), 25 * GameConfig.getScale(),
				2 * GameConfig.getScale());
		gc.setFill(Color.RED);
		gc.fillRect(x + 4 * GameConfig.getScale(), y - 4 * GameConfig.getScale(),
				Math.ceil((double) building.getDurability() / (double) GameConfig.FIELD_DURABILITY * 25.0 * GameConfig.getScale()),
				2 * GameConfig.getScale());
	}
	
	/**
	 * Renders {@link Entity entity} at the specified position on the game scene.
	 * 
	 * @param y      Position in the Y-axis
	 * @param x      Position in the X-axis
	 * @param entity The entity to be rendered
	 */
	public static void drawBuilding(int y, int x, BaseBuilding building) {
		if (building == null) {
			return;
		}

		GraphicsContext gc = GameScene.getGraphicsContext();

		WritableImage img = new WritableImage(barnSprites, 32, 32);
		if (building instanceof Mine)
			img = new WritableImage(mineSprites, 32, 32);
		else if (building instanceof Sawmill)
			img = new WritableImage(sawmillSprites, 32, 32);
		else if (building instanceof Smelter)
			img = new WritableImage(smelterSprites, 32, 32);
		else if (building instanceof House)
			img = new WritableImage(houseSprites, 32, 32);
		else if (building instanceof MilitaryCamp)
			img = new WritableImage(militaryCampSprites, 32, 32);
		
		
		img = scaleUp(img, GameConfig.getScale());
		if (building.isAttacked()) {
			img = changeColor(img);
		}

		gc.drawImage(img, x, y - 20);
	}

	/**
	 * Renders {@link Entity entity} at the specified position on the game scene.
	 * 
	 * @param y      Position in the Y-axis
	 * @param x      Position in the X-axis
	 * @param entity The entity to be rendered
	 * @param frame  The current animation frame
	 */
	public static void drawUnit(int y, int x, BaseUnit unit, int frame) {
		if (unit == null) {
			return;
		}

		GraphicsContext gc = GameScene.getGraphicsContext();

		WritableImage img = new WritableImage(SwordManSprites, 32, 32);
		img = scaleUp(img, GameConfig.getScale());
		if (unit.isAttacked()) {
			img = changeColor(img);
		}

		gc.drawImage(img, x, y);
	}

	/**
	 * Adds an invisible button on the entity at the specified position so the
	 * player can click to attack the entity.
	 * 
	 * @param y      Position in the Y-axis
	 * @param x      Position in the X-axis
	 * @param entity The entity to adds the button on
	 */
	public static void addUnitButton(int y, int x, BaseUnit unit) {
		// TODO: add logic for checking ours or enemy
		if (unit == null) {
			return;
		}

		Canvas canvas = new Canvas(GameConfig.SPRITE_SIZE * GameConfig.getScale(),
				GameConfig.SPRITE_SIZE * GameConfig.getScale());
		canvas.setOnMouseClicked((event) -> {
			if (!InterruptController.isInterruptPlayerMovingInput()) {
//				GameLogic.gameUpdate(DispatchAction.ATTACK, (Monster) entity);
				System.out.println("Clicked! " + unit.getClass().getSimpleName());
			}
		});
		addCursorHover(canvas, true);
		AnchorPane.setTopAnchor(canvas, (double) (y/* - 8 */));
		AnchorPane.setLeftAnchor(canvas, (double) x);
		GameScene.getButtonPane().getChildren().add(canvas);
		
//		VBox holder = new VBox();
//		holder.setPrefWidth(GameConfig.SPRITE_SIZE * GameConfig.getScale());
//		holder.setPrefHeight(GameConfig.SPRITE_SIZE * GameConfig.getScale());
//		holder.setBackground(new Background(new BackgroundFill(Color.BLUE, null, null)));
//		AnchorPane.setTopAnchor(holder, (double) (y/* - 8 */));
//		AnchorPane.setLeftAnchor(holder, (double) x);
//		GameScene.getButtonPane().getChildren().add(holder);
		
		System.out.println("Build unit button");
	}
	
	/**
	 * Adds an invisible button on the entity at the specified position so the
	 * player can click to get building info.
	 * 
	 * @param y      Position in the Y-axis
	 * @param x      Position in the X-axis
	 * @param entity The entity to adds the button on
	 */
	public static void addBuildingButton(int y, int x, BaseBuilding building) {
		if (building == null) {
			return;
		}

		Canvas canvas = new Canvas(GameConfig.SPRITE_SIZE * GameConfig.getScale(),
				GameConfig.SPRITE_SIZE * GameConfig.getScale());
		
		VBox holder = new VBox();
		holder.setPrefWidth(GameConfig.SPRITE_SIZE * GameConfig.getScale());
		holder.setPrefHeight(GameConfig.SPRITE_SIZE * GameConfig.getScale());
		holder.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
//		AnchorPane.setTopAnchor(holder, (double) (y - 8 * GameConfig.getScale()));
//		AnchorPane.setLeftAnchor(holder, (double) (x + 37 * GameConfig.getScale()));
		
		holder.setOnMouseClicked((event) -> {
			if (!InterruptController.isInterruptPlayerMovingInput()) {
				GameScene.getResourceStatus().update(building);
				System.out.println("Clicked! " + building.getClass().getSimpleName());
			}
		});
		addCursorHover(canvas, true);
//		AnchorPane.setTopAnchor(canvas, (double) (y - 8 * GameConfig.getScale()));
//		AnchorPane.setLeftAnchor(canvas, (double) (x + 37 * GameConfig.getScale()));
//		GameScene.getButtonPane().getChildren().add(canvas);
		GameScene.getButtonPane().getChildren().add(holder);
	}

	/**
	 * Adds mouse hover event to change cursor image to the specified node.
	 * 
	 * @param node     The node to add the event
	 * @param isEntity The type of the node. If this node is an entity, hover on it
	 *                 will change the cursor to an attack icon. otherwise; change
	 *                 to a hand icon.
	 */
	public static void addCursorHover(Node node, boolean isEntity) {
		node.setOnMouseEntered((event) -> {
			if (isEntity) {
				GameScene.getScene().setCursor(new ImageCursor(attackMouseIcon));
			} else {
				GameScene.getScene().setCursor(Cursor.HAND);
			}
		});

		node.setOnMouseExited((event) -> {
			GameScene.getScene().setCursor(null);
		});
	}

	/**
	 * Get {@link Image} from the file path.
	 * 
	 * @param filePath The file path to image
	 * @return Image from the file path.
	 */
	private static Image getImage(String filePath) {
		return new Image(ClassLoader.getSystemResource(filePath).toString());
	}

	/**
	 * Get {@link PixelReader ImagePixelReader} from the file path.
	 * 
	 * @param filePath The file path to image
	 * @return Pixel reader of the image.
	 */
	public static PixelReader getImagePixelReader(String filePath) {
		return getImage(filePath).getPixelReader();
	}

	/**
	 * Get {@link WritableImage} from the file path.
	 * 
	 * @param filePath The file path to image
	 * @return Writable image from the file path.
	 */
	public static WritableImage getWritableImage(String filePath) {
		Image img = getImage(filePath);
		PixelReader pixelReader = getImagePixelReader(filePath);
		return new WritableImage(pixelReader, 0, 0, (int) img.getWidth(), (int) img.getHeight());
	}

	/**
	 * Scales up the image.
	 * 
	 * @param image The image to be scaled up
	 * @param scale The scale of the output image.
	 * @return The scaled-up image.
	 */
	public static WritableImage scaleUp(WritableImage image, int scale) {
		int width = (int) image.getWidth();
		int height = (int) image.getHeight();

		IntBuffer src = IntBuffer.allocate(width * height);
		WritablePixelFormat<IntBuffer> pf = PixelFormat.getIntArgbInstance();
		image.getPixelReader().getPixels(0, 0, width, height, pf, src, width);
		int newWidth = width * scale;
		int newHeight = height * scale;
		int[] dst = new int[newWidth * newHeight];
		int index = 0;
		for (int y = 0; y < height; y++) {
			index = y * newWidth * scale;
			for (int x = 0; x < width; x++) {
				int pixel = src.get();
				for (int i = 0; i < scale; i++) {
					for (int j = 0; j < scale; j++) {
						dst[index + i + (newWidth * j)] = pixel;
					}
				}
				index += scale;
			}
		}
		WritableImage bigImage = new WritableImage(newWidth, newHeight);
		bigImage.getPixelWriter().setPixels(0, 0, newWidth, newHeight, pf, dst, 0, newWidth);
		return bigImage;
	}

	/**
	 * Change color of the image to be red.
	 * 
	 * @param img The image to be changed
	 * @return Changed color image
	 */
	private static WritableImage changeColor(WritableImage img) {

		int width = (int) img.getWidth();
		int height = (int) img.getHeight();
		WritableImage newImg = new WritableImage(width, height);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Color color = img.getPixelReader().getColor(x, y);
				newImg.getPixelWriter().setColor(x, y, color);

				double hue = 0;
				double saturation = 0.7;
				double brightness = color.getBrightness();
				double opacity = color.getOpacity();

				Color newColor = Color.hsb(hue, saturation, brightness, opacity);
				newImg.getPixelWriter().setColor(x, y, newColor);

			}
		}
		return newImg;
	}

}