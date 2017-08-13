/**
 * 
 */
package syntax;

import java.util.Set;
import java.util.TreeSet;

import inference.Subst;
import inference.Tuple;
import types.Type;
import types.TypeEnv;

/**
 * @author schkrabi
 *
 */
public abstract class Expr {
	public abstract Tuple<Subst, Type> inferTuple(TypeEnv env) throws Exception;
	
	public Type infer() throws Exception
	{
		return this.infer(new TypeEnv(), new TreeSet<Tuple<Type, Type>>());
	}
	
	protected abstract Type infer(TypeEnv env, Set<Tuple<Type, Type>> emit) throws Exception;
}
