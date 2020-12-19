package domain.entities;

import java.util.Arrays;

public enum TaskPriority {
	MOST_IMPORTANT,
	SECONDARY,
	ADDITIONAL,
	NOT_SET;

	public static TaskPriority findMatch(String key) {
		return Arrays.stream(values())
				.filter(p -> p.toString().equals(key))
				.findFirst()
				.orElse(NOT_SET);
	}
}
