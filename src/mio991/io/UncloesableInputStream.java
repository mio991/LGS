package mio991.io;

import java.io.IOException;
import java.io.InputStream;

public class UncloesableInputStream extends InputStream {

	InputStream m_Input;
	
	public UncloesableInputStream(InputStream in) {
		m_Input = in;
	}
	
	@Override
	public int read() throws IOException {
		return m_Input.read();
	}

	@Override
	public void close() throws IOException {
		// Do nothing
	}
}
