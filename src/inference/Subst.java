package inference;

import java.util.TreeMap;

import types.TVar;
import types.Type;

public class Subst extends TreeMap<TVar, Type> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4574499966941663011L;
	
	public static final Subst nullSubst(){
		return new Subst();
	}
	
	public Subst compose(Subst other){
		Subst s = new Subst();
		s.putAll(this);
		s.putAll(other);
		return s;
	}
}
