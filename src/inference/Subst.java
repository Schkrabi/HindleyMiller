package inference;

import java.util.TreeMap;

import types.TVar;
import types.Type;

public class Subst extends TreeMap<TVar, Type> implements Comparable<Subst> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4574499966941663011L;
	
	private static int globalNumbering = 0;
	public final int number;
	
	public static final Subst nullSubst(){
		return new Subst();
	}
	
	public Subst() {
		super();
		number = globalNumbering;
		globalNumbering++;
	}
	
	public Subst compose(Subst other){
		Subst s = new Subst();
		s.putAll(this);
		s.putAll(other);
		return s;
	}

	@Override
	public int compareTo(Subst o) {
		//Dirty, dirty solution...
		if(this.equals(o)){
			return 0;
		}
		return (int)Math.signum(this.number - o.number);
	}
}
