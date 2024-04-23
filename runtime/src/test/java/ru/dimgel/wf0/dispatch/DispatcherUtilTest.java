package ru.dimgel.wf0.dispatch;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class DispatcherUtilTest {

	@Test
	public void splitRequestPath() {
		assertEquals(List.of(), DispatcherUtil.splitRequestPath(""));
		assertEquals(List.of(), DispatcherUtil.splitRequestPath("?"));
		assertEquals(List.of(), DispatcherUtil.splitRequestPath("?a=1"));
		assertEquals(List.of(), DispatcherUtil.splitRequestPath("/"));
		assertEquals(List.of(), DispatcherUtil.splitRequestPath("/?"));
		assertEquals(List.of(), DispatcherUtil.splitRequestPath("/?a=1"));
		assertEquals(List.of("hello", "world"), DispatcherUtil.splitRequestPath("hello/world"));
		assertEquals(List.of("hello", "world"), DispatcherUtil.splitRequestPath("hello/world?"));
		assertEquals(List.of("hello", "world"), DispatcherUtil.splitRequestPath("hello/world?a=1"));
		assertEquals(List.of("hello", "world?"), DispatcherUtil.splitRequestPath("///hello///world%3F///"));
		assertEquals(List.of("hello", "world?"), DispatcherUtil.splitRequestPath("///hello///world%3F///?"));
		assertEquals(List.of("hello", "world?"), DispatcherUtil.splitRequestPath("///hello///world%3F///?a=1"));
	}
}
