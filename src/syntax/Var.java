/**
 * 
 */
package syntax;

import java.util.Map;

import inference.Inference;
import inference.Subst;
import inference.Tuple;
import types.Type;
import types.TypeEnv;

/**
 * @author schkrabi
 *
 */
public class Var extends Expr implements Comparable<Var> {
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
	public int compareTo(Var o) {
		return this.name.compareTo(o.name);
	}
	
	@Override
	public Tuple<Subst, Type> inferTuple(TypeEnv env) throws Exception{
		return Inference.lookupEnv(env, this);
	}

	@Override
	public Type infer(TypeEnv env, Map<Type, Type> emit) throws Exception {
		if(!env.containsKey(this)){
			throw new Exception("Varibale not found");
		}
		return Inference.instantiate(env.get(this));
	}
}
