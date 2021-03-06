/**
 * 
 */
package main;

import syntax.Expr;
import syntax.Lam;
import syntax.Op;

import java.util.Set;
import java.util.TreeSet;

import inference.Inference;
import inference.Subst;
import inference.Tuple;
import syntax.App;
import syntax.Binop;
import syntax.Var;
import types.Type;
import types.TypeEnv;

/**
 * @author r.SKRABAL
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// \x y z -> x + y + z
		Expr e = new Lam("x", new Lam("y", new Lam("z", new Op(Binop.Add, new Var("x"), new Op(Binop.Add, new Var("y"), new Var("z"))))));
		System.out.println(e);
		try {
			Tuple<Subst, Type> rslt = e.inferTuple(new TypeEnv());
			System.out.println(rslt);
			
			Set<Tuple<Type, Type>> emit = new TreeSet<Tuple<Type, Type>>();
			Type t = e.infer(emit);
			Subst s = Inference.solveConstraints(emit);
			System.out.println(t);
			System.out.println(s);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// \f g h -> f (g h)
		Expr f = new Lam("f", new Lam("g", new Lam("h", new App(new Var("f"), new App(new Var("g"), new Var("h"))))));
		System.out.println(f);
		try {
			Tuple<Subst, Type> rslt = f.inferTuple(new TypeEnv());
			System.out.println(rslt);
			
			Set<Tuple<Type, Type>> emit = new TreeSet<Tuple<Type, Type>>();
			Type t = f.infer(emit);
			Subst s = Inference.solveConstraints(emit);
			System.out.println(t);
			System.out.println(s);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
