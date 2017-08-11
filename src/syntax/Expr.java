/**
 * 
 */
package syntax;

import java.util.Map;
import java.util.TreeMap;

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
		return this.infer(new TypeEnv(), new TreeMap<Type, Type>());
	}
	
	protected abstract Type infer(TypeEnv env, Map<Type, Type> emit) throws Exception;
}
