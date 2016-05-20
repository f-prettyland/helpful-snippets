import java.util.ArrayList;
import java.util.List;

public class Tuple<X> {
	public  X x;
	public  X y;
	public Tuple(X x, X y) {
		this.x = x;
		this.y = y;
	}

	public Tuple<X> deepClone(){
		return new Tuple<X>(x,y);
	}

	@Override
	public boolean equals(Object comp){
		if(comp instanceof Tuple){
			if(this.x.equals(((Tuple<?>)comp).x) && this.y.equals(((Tuple<?>)comp).y)){
				return true;
			}
		}

		return false;
	}
}