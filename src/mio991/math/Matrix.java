package mio991.math;

import java.io.*;
import java.util.Locale;
import java.util.Scanner;

/**
 * A Matrix of variable dimensions.
 * 
 * All the basic operations are implemented, including the Gauss Algorithm.
 * @author mio991
 *
 */
public class Matrix implements Cloneable {
	private double[][] m_Values;

	/**
	 * Default Matrix Constructor
	 * 
	 * @param n
	 *            Height of the Matrix
	 * @param m
	 *            Width of the Matrix
	 */
	public Matrix(int n, int m) {
		if(n <= 0)
		{
			throw new IllegalArgumentException("n has to be biger then 0!");
		}
		if(m <= 0)
		{
			throw new IllegalArgumentException("m has to be biger then 0!");
		}

		m_Values = new double[n][m];
	}

	/**
	 * Creates square matrix of dimension n.
	 * 
	 * @param n
	 *            Dimension of the new nxn-Matrix.
	 */
	public Matrix(int n) {
		this(n, n);
	}

	/**
	 * Copy Constructor
	 * 
	 * @param m
	 *            Original to copy
	 */
	public Matrix(Matrix m) {
		m_Values = m.m_Values.clone();
	}

	/**
	 * Constructs the Matrix from a two-dimensional double array
	 * 
	 * @param val
	 *            the source array;
	 */
	public Matrix(double[][] val) {
		int width = val[0].length;
		for (double[] ds : val) {
			if(ds.length != width)
			{
				throw new IllegalArgumentException("Every row has to have the same length!");
			}
		}

		m_Values = val;
	}
	
	/**
	 * Reads a Matrix from an InputStream.
	 * It expects first the height then the width as integers, 
	 * and the should follow height * width decimal numbers.
	 * Every number should be separated by a whitespace character like ' ' or '\n'.
	 * 
	 * @param input the InputStream to read from.
	 */
	public Matrix(InputStream input)
	{
		Scanner scanner = new Scanner(input);
		scanner.useLocale(Locale.ENGLISH);
		
		if(!scanner.hasNextInt())
		{
			scanner.close();
			throw new IllegalArgumentException("Corrupted File: Missing Data? Non numerical Symbols?");
		}
		int n = scanner.nextInt();
		
		if(!scanner.hasNextInt())
		{
			scanner.close();
			throw new IllegalArgumentException("Corrupted File: Missing Data? Non numerical Symbols?");
		}
		int m = scanner.nextInt();
		
		m_Values = new double[n][m];
		
		for(int k = 0; k < n; k++)
		{
			for(int l = 0; l < m; l++)
			{
				if(!scanner.hasNextDouble())
				{
					scanner.close();
					throw new IllegalArgumentException("Corrupted File: Missing Data? Non numerical Symbols?");
				}
					
				this.set(k, l, scanner.nextDouble());
			}
		}
		
		scanner.close();
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new Matrix(this);
	}

	/**
	 * Writes the Matrix to the OutputStream.
	 * @param output the target to write to. 
	 */
	public void save(OutputStream output)
	{
		PrintWriter writer = new PrintWriter(output);
		
		writer.print(this.getHeight());
		writer.print(" ");
		writer.print(this.getWidth());
		writer.print(" ");
		
		for(int k = 0; k < this.getHeight(); k++)
		{
			for(int l = 0; l < this.getWidth(); l++)
			{
				writer.print(this.get(k, l));
				writer.print(" ");
			}
		}
		
		writer.close();
	}
	
	/**
	 * Returns the height of the Matrix
	 * 
	 * @return the height of the Matrix also the row count.
	 */
	public int getHeight() {
		return m_Values.length;
	}

	/**
	 * Returns the width of the Matrix
	 * 
	 * @return the width of the Matrix also the column count.
	 */
	public int getWidth() {
		return m_Values[0].length;
	}

	/**
	 * Returns the Value of the specified Cell
	 * 
	 * @param k
	 *            row number
	 * @param l
	 *            column number
	 * @return contained value
	 */
	public double get(int k, int l) {
		return m_Values[k][l];
	}

	/**
	 * Sets the specified cell to the Value v
	 * 
	 * @param k
	 *            row number
	 * @param l
	 *            column number
	 * @param v
	 *            Value
	 */
	public void set(int k, int l, double v) {
		m_Values[k][l] = v;
	}

	/**
	 * p-Norm extended for matrices
	 * 
	 * @param p
	 *            exponent to use in the p-Norm
	 * @return the p-Norm
	 */
	public double norm(double p) {
		double res = 0;
		for (double[] ds : m_Values) {
			for (double d : ds) {
				res += Math.pow(d, p);
			}
		}

		return Math.pow(res, 1.0d / p);
	}

	/**
	 * Default 2-Norm
	 * 
	 * @return 2-Norm of this Matrix
	 */
	public double norm() {
		return norm(2);
	}

	/**
	 * Transposes the Matrix and returns it. Does not change the original
	 * 
	 * @return the Transpose of the Matrix.
	 */
	public Matrix transpose() {
		Matrix res = new Matrix(this.getWidth(), this.getHeight());
		for (int k = 0; k < res.getHeight(); k++) {
			for (int l = 0; l < res.getWidth(); l++) {
				res.set(k, l, this.get(l, k));
			}
		}
		return res;
	}
	
	/**
	 * Returns sub matrix
	 * @param k left out row
	 * @param l left out column
	 * @return the specified {@link Matrix} with the row and column left out.
	 */
	public Matrix sub(int k, int l)
	{
		if(this.getHeight() <= 1 || this.getWidth() <= 1)
		{
			throw new IllegalArgumentException("The Matrix has to have at least the size 2 in both directions!");
		}
		
		Matrix res = new Matrix(this.getHeight()-1, this.getWidth()-1);
		
		int ri = 0;
		
		for (int i = 0; i < this.getHeight(); i++) {
			if(i != k)
			{
				int rj = 0;
				for(int j = 0; j < this.getWidth(); j++)
				{
					if(j != l)
					{
						res.set(ri, rj, this.get(i, j));
						rj++;
					}
				}
				ri++;
			}
		}
		
		return res;
	}
	
	/**
	 * Adds a row to the Matrix. To add a column use {@link Matrix}.transpose().
	 * @param row Row to add to the Matrix.
	 * @return returns the new Matrix.
	 */
	public Matrix addRow(double[] row)
	{
		if(row.length != this.getWidth())
		{
			throw new IllegalArgumentException("The new row has to have the same length as the existing ones!");
		}
		
		double[][] dat = new double [this.getHeight()+1][];
		
		int i = 0;
		
		for(i = 0; i < this.getHeight(); i++)
		{
			dat[i] = m_Values[i].clone();
		}
		
		dat[i] = row;
		
		return new Matrix(dat);
	}
	
	/**
	 * Adds a Column to the Matrix
	 * @param column the Column to add
	 * @return the new Matrix
	 */
	public Matrix addColumn(double[] column)
	{
		if(column.length != this.getHeight())
		{
			throw new IllegalArgumentException("The new column has to have the same length as the existing ones!");
		}
		
		return this.transpose().addRow(column).transpose();
	}
	
	/**
	 * Removes the last row of the Matrix.
	 * @return the modified Matrix
	 */
	public Matrix removeLastRow() {
		double[][] vals = new double[getHeight()-1][];
		for (int i = 0; i < vals.length; i++) {
			vals[i] = m_Values[i];
		}
		return new Matrix(vals);
	}
	
	/**
	 * Removes the last column of the Matrix.
	 * @return the modified Matrix
	 */
	public Matrix removeLastColumn() {
		return transpose().removeLastRow().transpose();
	}
	
	/**
	 * Calculates Det by Lapal development
	 * @return det of Matrix
	 */
	public double det()
	{
		if(this.getHeight() != this.getWidth())
		{
			throw new IllegalArgumentException("Can't compute det of a non square Matrix!");
		}
		
		double sum = 0;
		
		if(this.getHeight() > 1)
		{
			for(int i = 0; i < this.getHeight(); i++)
			{
				sum += Math.pow(-1, i)*this.get(0, i)*this.sub(0, i).det();
			}
		}
		else
		{
			sum = this.get(0, 0);
		}
		return sum;
	}
	
	/**
	 * Returns the inverse of this Matrix
	 * @return the inverse of this Matrix.
	 */
	public Matrix inverse() {
		if(this.getHeight() != this.getWidth())
		{
			throw new IllegalArgumentException("The height and with of the Matrix are not equal!");
		}
		if(this.det() == 0)
		{
			throw new IllegalArgumentException("The det of this Matrix is 0!");
		}
		
		return gauss(this, unit(this.getHeight()));
	}

	@Override
	public String toString() {
		String res = "";

		for (double[] ds : m_Values) {
			res += "(";
			for (double d : ds) {
				res += d + " ";
			}
			res += ")\n";
		}

		return res;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Matrix)) {
			return false;
		}

		Matrix t = (Matrix) obj;

		if (t.getHeight() != this.getHeight() || t.getWidth() != this.getWidth()) {
			return false;
		}

		for (int k = 0; k < this.getHeight(); k++) {
			for (int l = 0; l < this.getWidth(); l++) {
				if (t.get(k, l) != this.get(k, l)) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Adds two matrices and returns the result, keeps originals intact.
	 * 
	 * @param lhs
	 *            left hand side
	 * @param rhs
	 *            right hand side
	 */
	public static Matrix add(Matrix lhs, Matrix rhs) {
		if(lhs.getWidth() != rhs.getWidth() || lhs.getHeight() != rhs.getHeight())
		{
			throw new IllegalArgumentException("The two matricies must have the same dimensions!");
		}

		Matrix result = new Matrix(lhs.getHeight(), lhs.getWidth());

		for (int i = 0; i < result.getHeight(); i++) {
			for (int j = 0; j < result.getWidth(); j++) {
				result.set(i, j, lhs.get(i, j) + rhs.get(i, j));
			}
		}

		return result;
	}

	/**
	 * Multiplies two matrices together, leaving the originals intact.
	 * 
	 * @param lhs
	 *            left hand side
	 * @param rhs
	 *            right hand side
	 */
	public static Matrix multiply(Matrix lhs, Matrix rhs) {
		if(lhs.getWidth() != rhs.getHeight())
		{
			throw new IllegalArgumentException("The width of the left hand side has to be equal to the height of the right hand side!");
		}
		
		Matrix result = new Matrix(lhs.getHeight(), rhs.getWidth());

		for (int k = 0; k < result.getHeight(); k++) {
			for (int l = 0; l < result.getWidth(); l++) {
				double sum = 0;

				for (int i = 0; i < lhs.getWidth(); i++) {
					sum += lhs.get(k, i) * rhs.get(i, l);
				}

				result.set(k, l, sum);
			}
		}

		return result;
	}

	/**
	 * Scalar multiplication
	 * 
	 * Multiplication of a matrix by a scalar.
	 * 
	 * @param lhs
	 *            scalar
	 * @param rhs
	 *            Matrix
	 */
	public static Matrix multiply(double lhs, Matrix rhs) {
		Matrix result = new Matrix(rhs);

		for (int k = 0; k < result.getHeight(); k++) {
			for (int l = 0; l < result.getWidth(); l++) {
				result.set(k, l, lhs * result.get(k, l));
			}
		}
		return result;
	}
	
	/**
	 * Takes tow matrices of the same height and applies the gauss-algorithm
	 * @param lhs the left hand side to be transformed into a unit matrix
	 * @param rhs the right hand side to be transformed equally to the left hand side
	 * @return returns the transformed right hand side.
	 */
	public static Matrix gauss(Matrix lhs, Matrix rhs) {
		
		if(lhs.getHeight() != rhs.getHeight())
		{
			throw new IllegalArgumentException("The left hand side height and right hand side height are not equal!");
		}
		
		if(lhs.getHeight() > lhs.getWidth())
		{
			throw new IllegalArgumentException("The left hand side height is biger then left hand side width!");
		}
		
		Matrix copy = new Matrix(lhs);
		Matrix res = new Matrix(rhs);
		
		for(int k = 0; k < copy.getHeight(); k++)
		{
			int max = k;
			for(int l = k + 1; l < copy.getHeight(); l++)
			{
				if(copy.get(max, k) < copy.get(l, k))
				{
					max = l;
				}
			}
			
			{
				Matrix C = e(copy.getHeight(), k, max);
				copy = multiply(C, copy);
				res = multiply(C, res);
			}
			
			if((k < copy.getWidth()) && (copy.get(k, k) != 0))
			{
				Matrix C = e(copy.getHeight(), k, 1.0 / copy.get(k, k));
				copy = multiply(C, copy);
				res = multiply(C, res);
			}
			
			for(int l = k+1; l < copy.getHeight(); l++)
			{
				Matrix C = e(copy.getHeight(), l, k, -copy.get(l, k));
				copy = multiply(C, copy);
				res = multiply(C, res);
			}
		}
		
		for(int k = copy.getWidth()-1; k >= 0; k--)
		{			
			for(int l = k-1; l >= 0; l--)
			{
					Matrix C = e(copy.getHeight(), l, k, -copy.get(l, k));
					copy = multiply(C, copy);
					res = multiply(C, res);
			}
		}
		
		System.out.println(copy.toString());
		
		return res;
	}
	
	/**
	 * Returns the unit Matrix
	 * @param n size of the requires Matrix
	 * @return
	 */
	public static Matrix unit(int n)
	{
		Matrix res = new Matrix(n);
		for (int i = 0; i < n; i++) {
			res.set(i, i, 1);
		}
		return res;
	}
	
	/**
	 * Creates an E(k, l)-Matrix of size n
	 * @param n size of the Matrix
	 * @param k Row 1
	 * @param l Row 2
	 * @return
	 */
	public static Matrix e(int n, int k, int l)
	{
		Matrix res = unit(n);
		res.set(k, k, 0);
		res.set(l, l, 0);
		res.set(k, l, 1);
		res.set(l, k, 1);
		return res;
	}
	
	/**
	 * Creates an E(k, lambda)-Matrix of size n
	 * @param n size of the Matrix
	 * @param k Row
	 * @param lambda Factor
	 * @return
	 */
	public static Matrix e(int n, int k, double lambda)
	{
		Matrix res = unit(n);
		res.set(k, k, lambda);
		return res;
	}
	
	/**
	 * Creates an E(k, l, lambda)-Matrix of size n
	 * @param n size of the Matrix
	 * @param k Row
	 * @param l Column
	 * @param lambda Factor
	 * @return
	 */
	public static Matrix e(int n, int k, int l, double lambda)
	{
		Matrix res = unit(n);
		res.set(k, l, lambda);
		return res;
	}
}
