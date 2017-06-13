package mio991.lgs.gui;

import mio991.math.Matrix;

public class CLI {

	public static void main(String[] args) {
		Matrix m = new Matrix(new double[][]{{2, 3}, {2, 1}});
		Matrix i = m.inverse();
		
		System.out.println(i);
		
		System.out.println(Matrix.multiply(m, i));
	}

}
