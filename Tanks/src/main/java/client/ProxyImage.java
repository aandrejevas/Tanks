package client;

import processing.core.PImage;

public class ProxyImage extends PImage {
	private final String filename;
	private PImage image;

	public ProxyImage(final String name) {
		filename = name;
	}

	@Override
	public PImage get() {
		if (image == null) {
			image = Main.self.loadImage(filename);
		}
		return image;
	}
}
