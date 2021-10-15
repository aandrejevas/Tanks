package utils;

import processing.core.PApplet;
import processing.net.Client;
import processing.net.Server;

// https://github.com/processing/processing/blob/master/java/libraries/net/src/processing/net/Server.java
// https://en.wikipedia.org/wiki/Observer_pattern
public class TServer extends Server implements TWritable {

	public TServer(final PApplet applet, final int port) {
		super(applet, port);
	}

	@Override
	public void addClient(final Client client) {
		synchronized (clientsLock) {
			if (clientCount == clients.length) {
				clients = (Client[])PApplet.expand(clients);
			}
			clients[clientCount++] = client;
			client.output = new TOutputStream(client);
		}
	}

	@Override
	public void disconnect(final Client client) {
		synchronized (clientsLock) {
			final int index = clientIndex(client);
			if (index != -1) {
				client.stop();
				removeIndex(index);
			}
		}
	}

	@Override
	public void write(final byte[] data, final int off, final int len) {
		synchronized (clientsLock) {
			if (0 != clientCount) {
				int index = 0;
				do {
					final Client client = clients[index];
					if (client.active()) {
						((TOutputStream)client.output).write(data, off, len);
						++index;
					} else {
						removeIndex(index);
					}
				} while (index != clientCount);
			}
		}
	}

	@Override
	public void write(final String data) {
		TWritable.super.write(data);
	}

	@Override
	public void write(final byte[] data) {
		synchronized (clientsLock) {
			if (0 != clientCount) {
				int index = 0;
				do {
					final Client client = clients[index];
					if (client.active()) {
						((TOutputStream)client.output).write(data);
						++index;
					} else {
						removeIndex(index);
					}
				} while (index != clientCount);
			}
		}
	}

	@Override
	public void write(final int data) {
		synchronized (clientsLock) {
			if (0 != clientCount) {
				int index = 0;
				do {
					final Client client = clients[index];
					if (client.active()) {
						((TOutputStream)client.output).write(data);
						++index;
					} else {
						removeIndex(index);
					}
				} while (index != clientCount);
			}
		}
	}
}
