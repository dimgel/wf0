package ru.dimgel.wf0.dispatch;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static ru.dimgel.wf0.validate.ValidatorFactory.*;


// TODO Separate tests for each validator (with default & custom error messages), and few tests for pipes (including VOptional.Pipe).
public class ValidatorsTest {

	@Test
	public void sample() {
		var v = vOne().pipe(vRequired().pipe(vInt().pipe(vRange(1, 100))));

		assertTrue(v.validate(new String[]{"1"}).ok());
		assertTrue(v.validate(new String[]{"100"}).ok());

		assertFalse(v.validate(new String[]{"1", "1"}).ok());
		assertFalse(v.validate(new String[]{"1", ""}).ok());
		assertFalse(v.validate(new String[]{""}).ok());
		assertFalse(v.validate(new String[]{"aaa"}).ok());
		assertFalse(v.validate(new String[]{"0"}).ok());
		assertFalse(v.validate(new String[]{"101"}).ok());
	}
}
