package ru.dimgel.wf0.validate;


/**
 * <ul>
 * <li>Never fails;</li>
 * <li>succeeds immediately (i.e. without calling following piped validators) with {@code out = null} if input is {@code null} or empty string;</li>
 * <li>succeeds with {@code out = in} otherwise.</li>
 * </ul>
 *
 * <p>You might want to insert {@link VTrim} between {@link VOne} and {@code VOptional}.</p>
 */
public final class VOptional extends Validator<String, String> {

	public VOptional() {
		super(null);
	}

	@Override
	public VResult<String> validate(String in) {
		return success(in != null && in.isEmpty() ? null : in);
	}


	private final class Pipe<In2, Out2> extends Validator<String, Out2> {
		private final Validator<In2, Out2> v2;

		Pipe(Validator<In2, Out2> v2) {
			super(null);
			this.v2 = v2;
		}

		@SuppressWarnings("unchecked")
		@Override
		public VResult<Out2> validate(String in) {
			var r1 = VOptional.this.validate(in);

			return r1.out() != null
					? v2.validate((In2)r1.out())
					: new VResult<>(null, null);
		}
	}

	@Override
	public <In2 /*super String*/, Out2> Validator<String, Out2> pipe(Validator<In2, Out2> v2) {
		return new Pipe<>(v2);
	}
}
