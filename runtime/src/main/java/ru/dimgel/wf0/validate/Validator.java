package ru.dimgel.wf0.validate;


public abstract class Validator<In, Out> {

	protected final VErrorMessageProducer<In> err;

	protected Validator(VErrorMessageProducer<In> err) {
		this.err = err;
	}


	public abstract VResult<Out> validate(In in);


	/**
	 * Helper for subclasses.
	 */
	protected final VResult<Out> success(Out out) {
		return new VResult<>(out, null);
	}

	/**
	 * Helper for subclasses.
	 */
	protected final VResult<Out> failure(In in) {
		return new VResult<>(null, err.createMessage(in));
	}


	private final class Pipe<In2 /*super Out*/, Out2> extends Validator<In, Out2> {
		private final Validator<In2, Out2> v2;

		Pipe(Validator<In2, Out2> v2) {
			super(null);
			this.v2 = v2;
		}

		@SuppressWarnings("unchecked")
		@Override
		public VResult<Out2> validate(In in) {
			var r1 = Validator.this.validate(in);

			return r1.ok()
					? v2.validate((In2)r1.out())
					: new VResult<>(null, r1.errorMessage());
		}
	}

	/**
	 * <p><b>NOTE</b>: I cannot write {@code <In2 super Out, Out2>} because someone decided
	 * that no-one will ever need {@code super} in type parameters list -- and forbade it.
	 * TODO Create analyze-only maven plugin -- should check all cases where I have "super ..." commented out (e.g. {@link VOptional#pipe(Validator)}).</p>
	 */
	public <In2 /*super Out*/, Out2> Validator<In, Out2> pipe(Validator<In2, Out2> v2) {
		return new Pipe<>(v2);
	}
}
