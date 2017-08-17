/**
 * 
 */
package syntax;

import java.util.Arrays;
import java.util.List;
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
public abstract class Expr implements Comparable<Expr> {
	public abstract Tuple<Subst, Type> inferTuple(TypeEnv env) throws Exception;
	
	public Type infer() throws Exception
	{
		return this.infer(new TypeEnv(), new TreeSet<Tuple<Type, Type>>());
	}
	
	public Type infer(Set<Tuple<Type, Type>> emit) throws Exception
	{
		return this.infer(new TypeEnv(), emit);
	}
	
	protected abstract Type infer(TypeEnv env, Set<Tuple<Type, Type>> emit) throws Exception;
	
	private static List<Class<? extends Expr>> ordering = Arrays.asList(Var.class,
																		App.class,
																		Lam.class,
																		Let.class,
																		Lit.class,
																		If.class,
																		Fix.class,
																		Op.class);
	
	public int compareTo(Expr o) {
		return (int) Math.signum(ordering.indexOf(this.getClass()) - ordering.indexOf(o.getClass()));
	}
}
