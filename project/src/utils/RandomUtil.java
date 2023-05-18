package utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;


/**
 * The RandomUtil is the class that provide the random method and random the
 * status of {@link Potion}, {@link Armor} and {@link Weapon} from pool.
 *
 */
public class RandomUtil {

	/**
	 * Represent the {@link Random} instance.
	 */
	private static Random rand = new Random();

	/**
	 * Random the integer in range st and ed (inclusive).
	 * 
	 * @param st the starting of range
	 * @param ed the ending of range
	 * @return the random integer in range
	 */
	public static int random(int st, int ed) {
		if (st > ed)
			return 0;
		return st + rand.nextInt(ed - st + 1);
	}

	/**
	 * The utility method that shuffle the {@link Integer} array.
	 * 
	 * @param intArray the integer array that will be shuffled
	 */
	public static void shuffle(Integer[] intArray) {
		List<Integer> intList = Arrays.asList(intArray);
		Collections.shuffle(intList);

		intList.toArray(intArray);
	}

	/**
	 * The utility method that shuffle the two-dimensional integer array.
	 * 
	 * @param array the two-dimensional integer array that will be shuffled
	 */
	public static void shuffle(int[][] array) {
		List<int[]> list = Arrays.asList(array);
		Collections.shuffle(list);
		list.toArray(array);
	}

}