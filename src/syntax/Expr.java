/**
 * 
 */
package syntax;

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
	
	public abstract Type infer() throws Exception;
}
