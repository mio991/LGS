package mio991.math.test;

import mio991.math.Matrix;
import org.junit.*;
import static org.junit.Assert.*;

import java.io.*;
import java.util.Random;

public class MatrixTests {
	private Matrix m1;
	private Matrix m2;
	private Matrix r1;
	private Matrix r2;
	private Matrix r3;
	private Matrix r4;
	private Matrix testFile;
	private Matrix elem;
	
	private Random rand = new Random(5);
	

	public MatrixTests() {
		m1 = new Matrix(new double[][] { { 1, 2, 3 }, { 4, 5, 6 } });
		m2 = new Matrix(new double[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } });
		r1 = new Matrix(new double[][] { { 2, 4, 6 }, { 8, 10, 12 } });
		r2 = new Matrix(new double[][] { { 30, 36, 42 }, { 66, 81, 96 } });
		r3 = new Matrix(new double[][] { { 1, 4 }, { 2, 5 }, { 3, 6 } });
		r4 = new Matrix(new double[][] { {5, 6} });
		elem = new Matrix(new double[][]{{0, 1}, {1, 0}});
		testFile = new Matrix(new double[][] { { 1, 2, 3 }, { 0, 1, 2 }, { 0, 0, 1 } });
	}
	
	@Test
	public void testDimensions()
	{
		assertEquals(2, m1.getHeight());
		assertEquals(3, m1.getWidth());
	}

	@Test
	public void equals() {
		assertTrue(m1.equals(m1));
		assertFalse(m1.equals(m2));
		assertFalse(m1.equals(r2));
		assertFalse(m1.equals(r3));
		assertFalse(m1.equals(m1.transpose()));
	}

	@Test
	public void adding() {
		assertEquals(r1, Matrix.add(m1, m1));
	}

	@Test
	public void skalarMul() {
		assertEquals(r1, Matrix.multiply(2, m1));
	}

	@Test
	public void multiply() {
		assertEquals(r2, Matrix.multiply(m1, m2));
	}

	@Test(expected = AssertionError.class)
	public void multiplyWrongOrder() {
		Matrix.multiply(m2, m1);
	}

	@Test
	public void norm() {
		assertEquals(9.508, m1.norm(), 0.1); // TODO: Find a siutable epsilon
	}

	@Test
	public void transpose() {
		assertEquals(r3, m1.transpose());
		for(int i = 1; i < 10; i++)
		{
			assertEquals(Matrix.unit(i), Matrix.unit(i).transpose());
		}
	}
	
	@Test
	public void subTest() {
		assertEquals(r4, m1.sub(0, 0));
	}
	
	@Test
	public void det() {
		for(int i = 1; i < 10; i++)
		{
			assertEquals(1, Matrix.unit(i).det(), 0);
			double r = rand.nextDouble();
			if(i>=2)
			{
				assertEquals(1, Matrix.e(i, i-1, i-2, r).det(), 0);
				assertEquals(r, Matrix.e(i, i-2, r).det(), 0);
			}
		}
	}
	
	@Test
	public void elem() {
		assertEquals(elem, Matrix.e(2, 0, 1));
	}
	
	@Test
	public void inverse(){
		assertEquals(elem, elem.inverse());
		assertEquals(Matrix.unit(5), Matrix.unit(5).inverse());
		assertEquals(Matrix.e(5, 3, 1.0/3.0), Matrix.e(5, 3, 3.0).inverse());
		assertEquals(Matrix.e(3, 1, 2), Matrix.e(3, 1, 2).inverse());
	}
	
	@Test(expected=AssertionError.class)
	public void faultyInvers()
	{
		m1.inverse();
	}
	
	@Test
	public void fileRead() throws IOException {
		InputStream input = MatrixTests.class.getResourceAsStream("test.mat");
		
		Matrix t = new Matrix(input);
		assertEquals(testFile, t);
		
		input.close();
	}
	
	@Test
	public void loadSaveTest() throws IOException {
		File file = File.createTempFile("test1", "mat");
		file.deleteOnExit();
		
		OutputStream out = new FileOutputStream(file);
		m1.save(out);
		out.close();
		
		InputStream in = new FileInputStream(file);
		Matrix test = new Matrix(in);
		in.close();
		
		assertEquals(m1, test);
		
		
	}
}
