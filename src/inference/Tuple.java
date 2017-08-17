/**
 * 
 */
package inference;

/**
 * @author r.SKRABAL
 *
 */
public class Tuple<X extends Comparable<X>, Y extends Comparable<Y>> implements Comparable<Tuple<X, Y>> { 
	public final X x; 
	public final Y y; 
	public Tuple(X x, Y y) { 
		this.x = x; 
		this.y = y; 
	} 
	
	public String toString() {
		return "[ " + x.toString() + ", " + y.toString() + " ]";
	}

	@Override
	public int compareTo(Tuple<X, Y> o) {
		int n = o.x.compareTo(this.x);
		
		return n != 0 ? n : o.y.compareTo(this.y);
	}
} 
