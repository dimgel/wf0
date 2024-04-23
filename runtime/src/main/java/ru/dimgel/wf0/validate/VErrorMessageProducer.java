package ru.dimgel.wf0.validate;

public interface VErrorMessageProducer<In> {
	String createMessage(In in);
}
