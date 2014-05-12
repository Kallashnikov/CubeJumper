package com.me.cubejumper.utilities;

import com.badlogic.gdx.scenes.scene2d.Actor;

import aurelienribon.tweenengine.TweenAccessor;

/**
 * Use to tween certain values such as, RGB, ALPHA, X, Y.<p>
 * 
 * @param RGB
 * @param ALPHA
 * @param X
 * @param Y <p>
 * 
 * @author Jacob
 */
public class ActorAccessor implements TweenAccessor<Actor> 
{
	public static final int RGB = 0, ALPHA = 1, X = 2, Y = 3;
	
	@Override
	public int getValues(Actor target, int tweenType, float[] returnValues) {
		switch(tweenType) {
		case RGB:
			returnValues[0] = target.getColor().r;
			returnValues[1] = target.getColor().g;
			returnValues[2] = target.getColor().b;
			return 3;
		case ALPHA:
			returnValues[0] = target.getColor().a;
			return 1;
		case X:
			returnValues[0] = target.getX();
			return 1;
		case Y:
			returnValues[0] = target.getY();
			return 1;
		default:
			assert false;
			return -1;
		}
	}

	@Override
	public void setValues(Actor target, int tweenType, float[] newValues) {
		switch(tweenType) {
		case RGB:
			target.setColor(newValues[0], newValues[1], newValues[2], target.getColor().a);
		case ALPHA:
			target.setColor(target.getColor().r, target.getColor().g, target.getColor().b, newValues[0]);
			break;
		case X:
			target.setX(newValues[0]);
			break;
		case Y:
			target.setY(newValues[0]);
			break;
		default:
			assert false;
		}
	}

}