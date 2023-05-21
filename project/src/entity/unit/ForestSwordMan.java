package entity.unit;

import game.GameLogic;
import game.Terrain;
import utils.GameConfig;

public class ForestSwordMan extends SwordMan implements Attackable, Movable{
	
	public ForestSwordMan() {
		super();
	}
	
	public ForestSwordMan(int people, int attackRange, int moveRange, float attackMultiplier) {
		super(people, attackRange, moveRange, attackMultiplier);
	}
	
	public void buffByTerrain(Terrain T) {
		if (GameLogic.getOurUnitTerrain(this) == Terrain.PLAIN) {
			this.setAttackMultiplier(GameConfig.FORESTSWORDMAN_ATTACK_MULTIPLIER);
			this.setMoveRange(GameConfig.FORESTSWORDMAN_MOVE_RANGE);
		}
		else {
			this.setAttackMultiplier(GameConfig.SWORDMAN_ATTACK_MULTIPLIER);
			this.setMoveRange(GameConfig.SWORDMAN_MOVE_RANGE);
		}
	}

	public void attack() {
		// some logic to check terrain
		// if(GameLogic.)
	}
	
	public void move() {
		// some logic to check terrain
		// if(GameLogic.)
	}
}
