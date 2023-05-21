package entity.unit;

import game.GameLogic;
import game.Terrain;
import utils.GameConfig;

public class MountainSwordMan extends SwordMan implements Attackable, Movable{

	
	
	public MountainSwordMan(int people, int attackRange, int moveRange, float attackMultiplier) {
		super(people, attackRange, moveRange, attackMultiplier);
	}
	
	
	public MountainSwordMan() {
		super();
	}
	
	public void buffByTerrain(Terrain T) {
		if (GameLogic.getOurUnitTerrain(this) == Terrain.MOUNTAIN) {
			this.setAttackMultiplier(GameConfig.MOUNTAINSWORDMAN_ATTACK_MULTIPLIER);
			this.setMoveRange(GameConfig.MOUNTAINSWORDMAN_MOVE_RANGE);
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

