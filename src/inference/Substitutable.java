/**
 * 
 */
package inference;

import java.util.Set;

import types.TVar;

/**
 * @author schkrabi
 *
 */
public interface Substitutable<T> {
	public T apply(Subst subst);
	public Set<TVar> ftv();	
}
