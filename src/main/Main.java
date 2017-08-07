/**
 * 
 */
package main;

import types.VarName;

/**
 * @author r.SKRABAL
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for(int i = 0; i < ('z' - 'a')*5; i++) {
			System.out.println(VarName.next());
		}
	}

}
