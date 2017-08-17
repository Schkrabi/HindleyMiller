package inference;

import java.util.TreeSet;

import types.Type;

public class ConstraintSet extends TreeSet<Tuple<Type, Type>> implements Comparable<ConstraintSet> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2856007460139917397L;
	
	private static int globalNumbering = 0;
	public final int number;
	
	public ConstraintSet() {
		super();
		this.number = globalNumbering;
		globalNumbering++;
	}

	@Override
	public int compareTo(ConstraintSet o) {
		//Dirty, dirty solution...
		if(this.equals(o)){
			return 0;
		}
		return (int)Math.signum(this.number - o.number);
	}

}
