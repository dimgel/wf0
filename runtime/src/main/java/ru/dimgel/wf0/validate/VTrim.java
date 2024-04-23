package ru.dimgel.wf0.validate;

/**
 * Never fails. Trims input if it's not null.
 */
public final class VTrim extends Validator<String, String> {

	protected VTrim() {
		super(null);
	}

	@Override
	public VResult<String> validate(String s) {
		return success(s != null ? s.trim() : null);
	}
}
