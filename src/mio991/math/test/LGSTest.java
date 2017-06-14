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

}
