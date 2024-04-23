package ru.dimgel.wf0.validate;


/**
 * Converts input to Integer. Fails if {@link Integer#parseInt(String)} throws.
 */
public final class VInt extends Validator<String, Integer> {

	public VInt() {
		super(in -> "not an integer");
	}

	public VInt(VErrorMessageProducer<String> err) {
		super(err);
	}

	@Override
	public VResult<Integer> validate(String in) {
		try {
			return success(Integer.parseInt(in));
		} catch (Throwable _) {
			return failure(in);
		}
	}
}
