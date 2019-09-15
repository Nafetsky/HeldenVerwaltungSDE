package api;

import lombok.Data;

@Data
public class Disadvantage implements Vantage{

	private final String name;
	private final int cost;
}
