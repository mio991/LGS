package mio991.math;

import static mio991.math.Matrix.*;

public class LGS {
	Matrix m_Coefficients;
	Matrix m_Constants;
	
	public LGS(Matrix coefficients, Matrix constants) {
		assert coefficients.getWidth() == constants.getHeight();
		assert coefficients.getHeight() == coefficients.getWidth();
		assert coefficients.det() != 0.0;
		
		m_Coefficients = coefficients;
		m_Constants = constants;
	}
	
	public Matrix calcSollution()
	{
		return multiply(m_Coefficients.inverse(), m_Constants);
	}

}
