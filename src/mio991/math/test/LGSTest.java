package mio991.math.test;

import static org.junit.Assert.*;
import org.junit.*;

import mio991.math.*;

public class LGSTest {

	@Test
	public void calcSollution() {
		
		Matrix A = new Matrix(new double[][]{{1, 2}, {0, 1}});
		Matrix b = new Matrix(new double[][]{{2}, {3}});
		
		LGS lgs = new LGS(A, b);
		
		Matrix s = new Matrix(new double[][]{{-4}, {3}});
		
		assertEquals(s, lgs.calcSollution());
		
		A = new Matrix(new double[][]{{1, 0, 0}, {0, 0, 1}, {0, 1, 0}});
		b = new Matrix(new double[][]{{2}, {8}, {4}});
		
		lgs = new LGS(A, b);
		
		s = new Matrix(new double[][]{{2}, {4}, {8}});
		
		assertEquals(s, lgs.calcSollution());
	}
	
	@Test
	public void calcDifficultSollution()
	{
		Matrix A = new Matrix(new double[][]{{1, 2, 3}, {4, 5, 6}, {7, 0, 9}});
		Matrix b = new Matrix(new double[][]{{2}, {8}, {4}});
		
		LGS lgs = new LGS(A, b);
		
		Matrix s = new Matrix(new double[][]{{1.375}, {1.25}, {-0.625}});
		double err = Matrix.add(lgs.calcSollution(),Matrix.multiply(-1.0, s)).norm();
		
		System.out.println(err);
		
		assertEquals(0, err, 0.01);
	}

}
