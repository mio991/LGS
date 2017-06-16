package mio991.math.test;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.*;

import mio991.math.*;

public class LGSTest {

	@Test
	public void calcSollution() {
		
		Matrix A = new Matrix(new double[][]{{1, 2}, {0, 1}});
		Matrix b = new Matrix(new double[][]{{2}, {3}});
		
		LinearSystem linearSystem = new LinearSystem(A, b);
		
		Matrix s = new Matrix(new double[][]{{-4}, {3}});
		
		assertEquals(s, linearSystem.calcSollution());
		
		A = new Matrix(new double[][]{{1, 0, 0}, {0, 0, 1}, {0, 1, 0}});
		b = new Matrix(new double[][]{{2}, {8}, {4}});
		
		linearSystem = new LinearSystem(A, b);
		
		s = new Matrix(new double[][]{{2}, {4}, {8}});
		
		assertEquals(s, linearSystem.calcSollution());
	}
	
	@Test
	public void calcDifficultSollution()
	{
		Matrix A = new Matrix(new double[][]{{1, 2, 3}, {4, 5, 6}, {7, 0, 9}});
		Matrix b = new Matrix(new double[][]{{2}, {8}, {4}});
		
		LinearSystem linearSystem = new LinearSystem(A, b);
		
		Matrix s = new Matrix(new double[][]{{1.375}, {1.25}, {-0.625}});
		double err = Matrix.add(linearSystem.calcSollution(),Matrix.multiply(-1.0, s)).norm();
		
		//System.out.printf("LGS Error = %s\n", err);
		
		assertEquals(0, err, 1.0E-10);
	}
	
	@Test
	public void fileOp() throws IOException
	{
		InputStream input = MatrixTests.class.getResourceAsStream("test.lgs");
		
		LinearSystem linearSystem = new LinearSystem(input);
		
		double err = Matrix.add(linearSystem.calcSollution(), 
				Matrix.multiply(-1.0, new Matrix(new double[][]{{0}, {-4}, {3}}))).norm();
		
		assertEquals(0, err, 1.0E-10);
	}
	
	@Test
	public void saveTest() throws IOException
	{
		Matrix A = new Matrix(new double[][]{{1, 2}, {0, 1}});
		Matrix b = new Matrix(new double[][]{{2}, {3}});
		
		LinearSystem linearSystem = new LinearSystem(A, b);
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		linearSystem.save(out);
		
		System.out.println(out.toString());
		
		assertEquals(linearSystem, new LinearSystem(new ByteArrayInputStream(out.toByteArray())));
	}
}
