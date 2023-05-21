package entity.unit;

import game.GameLogic;
import game.Position;
import game.Terrain;
import utils.GameConfig;

public class FieldSwordMan extends SwordMan implements Attackable, Movable{

	
	public FieldSwordMan(int people, int attackRange, int moveRange, float attackMultiplier) {
		super(people, attackRange, moveRange, attackMultiplier);
	}
	
	public FieldSwordMan() {
		super();
	}
	
	public void buffByTerrain(Terrain T) {
		if (GameLogic.getOurUnitTerrain(this) == Terrain.PLAIN) {
			this.setAttackMultiplier(GameConfig.FIELDSWORDMAN_ATTACK_MULTIPLIER);
			this.setMoveRange(GameConfig.FIELDSWORDMAN_MOVE_RANGE);
		}
		else {
			this.setAttackMultiplier(GameConfig.SWORDMAN_ATTACK_MULTIPLIER);
			this.setMoveRange(GameConfig.SWORDMAN_MOVE_RANGE);
		}
	}

//	public void attack(BaseUnit to) {
//		Terrain terrain = GameLogic.getOurUnitTerrain(this);
//		if (terrain == Terrain.PLAIN) {
//			System.out.println(this.getClass().getSimpleName() + " is buff 'cause it's in " + terrain + "  ;-)");
//			this.setAttackMultiplier(GameConfig.FIELDSWORDMAN_ATTACK_MULTIPLIER);
//			super.attack(to);
//			this.setAttackMultiplier(GameConfig.SWORDMAN_ATTACK_MULTIPLIER);
//		}
//		else {
//			super.attack(to);
//		}
//	}
//	
//	public void move(Position destination) {
//		Terrain terrain = GameLogic.getOurUnitTerrain(this);
//		if (terrain == Terrain.PLAIN) {
//			this.setMoveRange(GameConfig.FIELDSWORDMAN_MOVE_RANGE);
//			super.move(destination);
//			this.setMoveRange(GameConfig.SWORDMAN_MOVE_RANGE);
//		}
//		else {
//			super.move(destination);
//		}
//	}
}
