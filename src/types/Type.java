/**
 * 
 */
package types;

import java.util.Arrays;
import java.util.List;

import inference.Substitutable;

/**
 * @author schkrabi
 *
 */
public abstract class Type implements Substitutable<Type>, Comparable<Type> {
	
	private List<Class<? extends Type>> ordering = Arrays.asList(	TVar.class,
																	TCon.class,
																	TArr.class);
	
	public int compareTo(Type o) {
		return (int) Math.signum(ordering.indexOf(this.getClass()) - ordering.indexOf(o.getClass()));
	}
}
