package mio991.lgs.gui;

import mio991.math.Matrix;

public class CLI {

	public static void main(String[] args) {
		Matrix m = new Matrix(new double[][]{{2, 3, 4}, {2, 1, 2}});
		m.save(System.out);
		
		Matrix i = m.inverse();
		
		i.save(System.out);
	}

}
