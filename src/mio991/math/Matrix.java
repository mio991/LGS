package mio991.math;

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
		assert n > 0;
		assert m > 0;

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
			assert ds.length == width;
		}

		m_Values = val;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new Matrix(this);
	}

	/**
	 * Returns the height of the Matrix
	 * 
	 * @return the height of the Matrix also the row count.
	 */
	public int height() {
		return m_Values.length;
	}

	/**
	 * Returns the width of the Matrix
	 * 
	 * @return the width of the Matrix also the column count.
	 */
	public int width() {
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
	 * @return the Result
	 */
	public double norm(double p) {
		double res = 0;
		for (double[] ds : m_Values) {
			for (double d : ds) {
				res += Math.pow(d, p);
			}
		}

		return Math.pow(res, 1.0 / p);
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
		Matrix res = new Matrix(this.width(), this.height());
		for (int k = 0; k < res.height(); k++) {
			for (int l = 0; l < res.width(); l++) {
				res.set(k, l, this.get(l, k));
			}
		}
		return res;
	}
	
	/**
	 * Returns sub matrix
	 * @param k left out row
	 * @param l left out column
	 * @return
	 */
	public Matrix sub(int k, int l)
	{
		assert this.height() > 1;
		assert this.width() > 1;
		
		Matrix res = new Matrix(this.height()-1, this.width()-1);
		
		int ri = 0;
		
		for (int i = 0; i < this.height(); i++) {
			if(i != k)
			{
				int rj = 0;
				for(int j = 0; j < this.width(); j++)
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
	 * Calculates Det by Lapal development
	 * @return det of Matrix
	 */
	public double det()
	{
		assert this.height() == this.width();
		
		double sum = 0;
		
		if(this.height() > 1)
		{
			for(int i = 0; i < this.height(); i++)
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
	
	public Matrix inverse() {
		assert this.height() == this.width();
		assert this.det() != 0;
		
		Matrix copy = new Matrix(this);		
		Matrix res = unit(this.height());
		
		
		Matrix C;
		
		for(int l = 0; l < copy.width(); l++)
		{
			int t = l;
			
			while(t < copy.height() && copy.get(t, l) == 0 )
			{
				t++;
			}
			
			if(t < copy.height())
			{
				C = e(this.height(), l, t);
				
				copy = multiply(copy, C);
				res = multiply(res, C);
			}
			
			if(copy.get(l, l) != 0.0)
			{
				C = e(this.height(), l, 1.0 / copy.get(l, l));
			
				copy = multiply(copy, C);
				res = multiply(res, C);
			}
			
			for(int k = l+1; k < copy.height(); k++)
			{
				C = e(this.height(), k, l , -1.0 * copy.get(k, l));
				
				copy = multiply(C, copy);
				res = multiply(C, res);
			}
		}
		
		for(int l = copy.width()-1; l >= 0; l--)
		{
			
			for(int k = l-1; k >= 0; k--)
			{
				
				C = e(this.height(), k, l , -1.0 * copy.get(k, l));
				
				copy = multiply(C, copy);
				res = multiply(C, res);
			}
		}
		
		return res;
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

		if (t.height() != this.height() || t.width() != this.width()) {
			return false;
		}

		for (int k = 0; k < this.height(); k++) {
			for (int l = 0; l < this.width(); l++) {
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
	 * @return result
	 */
	public static Matrix add(Matrix lhs, Matrix rhs) {
		assert lhs.width() == rhs.width();
		assert lhs.height() == rhs.height();

		Matrix result = new Matrix(lhs.height(), lhs.width());

		for (int i = 0; i < result.height(); i++) {
			for (int j = 0; j < result.width(); j++) {
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
	 * @return the result
	 */
	public static Matrix multiply(Matrix lhs, Matrix rhs) {
		assert lhs.width() == rhs.height();

		Matrix result = new Matrix(lhs.height(), rhs.width());

		for (int k = 0; k < result.height(); k++) {
			for (int l = 0; l < result.width(); l++) {
				double sum = 0;

				for (int i = 0; i < lhs.width(); i++) {
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
	 * @param lhs
	 *            scalar
	 * @param rhs
	 *            Matrix
	 * @return result
	 */
	public static Matrix multiply(double lhs, Matrix rhs) {
		Matrix result = new Matrix(rhs);

		for (int k = 0; k < result.height(); k++) {
			for (int l = 0; l < result.width(); l++) {
				result.set(k, l, lhs * result.get(k, l));
			}
		}
		return result;
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
	
	public static Matrix e(int n, int k, int l)
	{
		Matrix res = unit(n);
		res.set(k, k, 0);
		res.set(l, l, 0);
		res.set(k, l, 1);
		res.set(l, k, 1);
		return res;
	}
	
	public static Matrix e(int n, int k, double lambda)
	{
		Matrix res = unit(n);
		res.set(k, k, lambda);
		return res;
	}
	
	public static Matrix e(int n, int k, int l, double lambda)
	{
		Matrix res = unit(n);
		res.set(k, l, lambda);
		return res;
	}
}
