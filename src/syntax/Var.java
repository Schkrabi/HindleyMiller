/**
 * 
 */
package syntax;

import java.util.Set;

import inference.Inference;
import inference.Subst;
import inference.Tuple;
import types.Type;
import types.TypeEnv;

/**
 * @author schkrabi
 *
 */
public class Var extends Expr {
	private String name;
	
	public Var(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public String toString(){
		return name;
	}

	@Override
	public int compareTo(Expr o) {
		if(o instanceof Var)
			return this.name.compareTo(((Var)o).name);
		return super.compareTo(o);
	}
	
	@Override
	public Tuple<Subst, Type> inferTuple(TypeEnv env) throws Exception{
		return Inference.lookupEnv(env, this);
	}

	@Override
	public Type infer(TypeEnv env, Set<Tuple<Type, Type>> emit) throws Exception {
		if(!env.containsKey(this)){
			throw new Exception("Varibale not found");
		}
		return Inference.instantiate(env.get(this));
	}
}
