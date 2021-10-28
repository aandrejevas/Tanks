import utils.MapBackBuilder;
import utils.ArenaMap;
import utils.Builder;
import utils.Utils;

public class MapFrontBuilder extends MapBackBuilder implements Builder {

	private final ArenaMap map;

	public MapFrontBuilder(final ArenaMap map) {
		super(map);
		this.map = map;
	}

	@Override
	public ArenaMap getBuildable() {
		return this.map;
	}

	@Override
	public Builder build() {
		return this.makeBackground().makeLava().makeWater().makeBorders().makeMaze();
	}



	public MapFrontBuilder makeBackground() {
		for (int i = 0; i < this.map.edge; i++) {
			for (int j = 0; j < this.map.edge; j++) {
				this.map.background[i][j].value = Utils.MAP_EMPTY;
			}
		}
		return this;
	}
}
