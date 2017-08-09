/**
 * 
 */
package syntax;

import inference.Subst;
import inference.Tuple;
import types.TCon;
import types.Type;
import types.TypeEnv;

/**
 * @author schkrabi
 *
 */
public class LBool extends Lit {
	public final boolean value;
	
	public LBool(boolean value){
		this.value = value;
	}
	
	public String toString(){
		return Boolean.toString(this.value);
	}

	@Override
	public Tuple<Subst, Type> inferTuple(TypeEnv env) throws Exception {
		return new Tuple<Subst, Type>(Subst.nullSubst(), TCon.typeBool);
	}

	@Override
	public Type infer() {
		return TCon.typeBool;
	}
}
