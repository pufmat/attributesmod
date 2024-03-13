package net.puffish.attributesmod.util;

public enum Sign {
	POSITIVE,
	NEGATIVE;

	public <T> Signed<T> wrap(T value) {
		return new Signed<>(this, value);
	}
}
