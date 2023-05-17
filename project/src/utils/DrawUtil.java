package utils;

import java.nio.IntBuffer;

import controller.InterruptController;
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
import javafx.scene.paint.Color;
import game.Cell;
import game.Direction;
import game.GameLogic;
import scene.GameScene;

/**
 * The utility class that provide sprite drawing method and PixelReader for
 * sprite.
 *
 */
public class DrawUtil {

	/**
	 * Cell sprites.
	 */
	private static PixelReader cellSprites;

	/**
	 * Item sprites.
	 */
	private static PixelReader itemSprites;

	/**
	 * Ladder sprites.
	 */
	private static PixelReader ladderSprites;

	/**
	 * Entity sprites.
	 */
	private static PixelReader entitySprites;

	/**
	 * Attack mouse icon.
	 */
	private static Image attackMouseIcon;

	/**
	 * Loads resources.
	 */
	static {
		cellSprites = getImagePixelReader("sprites/cell.png");
		entitySprites = getImagePixelReader("sprites/entity.png");
		itemSprites = getImagePixelReader("sprites/item.png");
		ladderSprites = getImagePixelReader("sprites/ladder.png");
		attackMouseIcon = getAttackMouseIcon();
	}

	/**
	 * Renders individual {@link Cell cell} sprite at the specified position on the
	 * game scene.
	 * 
	 * @param y    Position in the Y-axis
	 * @param x    Position in the X-axis
	 * @param cell The cell to be rendered
	 */
	public static void drawCell(int y, int x, Cell cell) {
		GraphicsContext gc = GameScene.getGraphicsContext();
		if (cell.getType() != Cell.VOID) {
			WritableImage img = new WritableImage(cellSprites, cell.getSymbol() * 32, 0, 32, 40);
			gc.drawImage(scaleUp(img, GameConfig.getScale()), x, y - 8 * GameConfig.getScale());
		}
	}


	/**
	 * Renders {@link Entity entity} at the specified position on the game scene.
	 * 
	 * @param y      Position in the Y-axis
	 * @param x      Position in the X-axis
	 * @param entity The entity to be rendered
	 * @param frame  The current animation frame
	 */
	public static void drawEntity(int y, int x, BaseUnit unit, int frame) {
		if (unit == null) {
			return;
		}

		GraphicsContext gc = GameScene.getGraphicsContext();
		int directionIndex = Direction.getSpriteIndex(entity.getDirection());
		int walkIndex = (frame / 8 + 1) % 4;
		if (walkIndex == 3 || !entity.isMoving()) {
			walkIndex = 1;
		}

		WritableImage img = new WritableImage(entitySprites, (96 * entity.getSymbol()) + 32 * walkIndex,
				directionIndex * 32, 32, 32);
		img = scaleUp(img, GameConfig.getScale());
		if (unit.isAttacked()) {
			img = changeColor(img);
		}

		gc.drawImage(img, x, y);
	}

	/**
	 * Renders health point bar of the {@link Entity entity} at the specified
	 * position on the game scene.
	 * 
	 * @param y      Position in the Y-axis
	 * @param x      Position in the X-axis
	 * @param entity The entity to be rendered
	 */
	public static void drawHPBar(int y, int x, Entity entity) {
		if (!(entity instanceof Monster)) {
			return;
		}

		GraphicsContext gc = GameScene.getGraphicsContext();
		gc.setFill(Color.BLACK);
		gc.fillRect(x + 4 * GameConfig.getScale(), y - 4 * GameConfig.getScale(), 25 * GameConfig.getScale(),
				2 * GameConfig.getScale());
		gc.setFill(Color.RED);
		gc.fillRect(x + 4 * GameConfig.getScale(), y - 4 * GameConfig.getScale(),
				Math.ceil((double) entity.getHealth() / (double) entity.getMaxHealth() * 25.0 * GameConfig.getScale()),
				2 * GameConfig.getScale());
	}

	/**
	 * Renders {@link Item item} on the graphic context on the specified position
	 * 
	 * @param gc   Graphic context to be rendered on
	 * @param y    Position in the Y-axis
	 * @param x    Position in the X-axis
	 * @param item The item to be rendered
	 */
	public static void drawItem(GraphicsContext gc, int y, int x, Item item) {
		if (item == null) {
			return;
		}

		int index = item.getSpriteIndex();
		WritableImage img = new WritableImage(itemSprites, index * 32, item.getSymbol() * 32, 32, 32);
		gc.drawImage(scaleUp(img, GameConfig.getScale()), y, x);
	}

	/**
	 * Adds an invisible button on the entity at the specified position so the
	 * player can click to attack the entity.
	 * 
	 * @param y      Position in the Y-axis
	 * @param x      Position in the X-axis
	 * @param entity The entity to adds the button on
	 */
	public static void addEntityButton(int y, int x, Entity entity) {
		if (entity == null || !(entity instanceof Monster)) {
			return;
		}

		Canvas canvas = new Canvas(GameConfig.SPRITE_SIZE * GameConfig.getScale(),
				GameConfig.SPRITE_SIZE * GameConfig.getScale());
		canvas.setOnMouseClicked((event) -> {
			if (!InterruptController.isInterruptPlayerMovingInput()) {
				GameLogic.gameUpdate(DispatchAction.ATTACK, (Monster) entity);
			}
		});
		addCursorHover(canvas, true);
		AnchorPane.setTopAnchor(canvas, (double) (y/* - 8 */));
		AnchorPane.setLeftAnchor(canvas, (double) x);
		GameScene.getButtonPane().getChildren().add(canvas);
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
	 * Getter for attack icon.
	 * 
	 * @return Attack icon
	 */
	private static Image getAttackMouseIcon() {
		return new WritableImage(itemSprites, 0, 0, 32, 32);
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