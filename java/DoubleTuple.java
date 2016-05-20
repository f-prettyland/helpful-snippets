package shared;

public class Tuple<X, Y> { 
	public  X x; 
	public  Y y; 
	public Tuple(X x, Y y) { 
		this.x = x; 
		this.y = y; 
	} 
	public boolean equals(Tuple<X,Y> comp){
		if(this.x == comp.x && this.y == comp.y){
			return true;
		}
		return false;
	}
} 