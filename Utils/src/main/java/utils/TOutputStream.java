package utils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;
import processing.net.Client;

// https://github.com/processing/processing/blob/master/java/libraries/net/src/processing/net/Client.java
// https://docs.oracle.com/en/java/javase/15/docs/api/java.base/java/io/OutputStream.html
public class TOutputStream extends OutputStream implements TWritable {

	public final Client client;
	public final OutputStream output;

	public TOutputStream(final Client c) {
		client = Objects.requireNonNull(c);
		output = client.output;
	}

	@Override
	public void close() throws IOException {
		output.close();
	}

	@Override
	public void flush() throws IOException {
		output.flush();
	}

	@Override
	public void write(final int b) {
		try {
			output.write(b);
			output.flush();
		} catch (final IOException e) {
			e.printStackTrace();
			client.stop();
		}
	}

	@Override
	public void write(final byte[] b) {
		try {
			output.write(b);
			output.flush();
		} catch (final IOException e) {
			e.printStackTrace();
			client.stop();
		}
	}

	@Override
	public void write(final byte[] b, final int off, final int len) {
		try {
			output.write(b, off, len);
			output.flush();
		} catch (final IOException e) {
			e.printStackTrace();
			client.stop();
		}
	}
}
