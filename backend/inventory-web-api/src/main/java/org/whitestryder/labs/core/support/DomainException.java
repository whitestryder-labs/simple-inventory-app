package org.whitestryder.labs.core.support;


/**
 * Encapsulates an exception coming from the core domain.
 */
public class DomainException extends Exception {

	/** generated. */
	private static final long serialVersionUID = 4188502393518964082L;

	/**
	 * Instantiates a new domain exception.
	 */
	public DomainException() {
		super();
	}

	/**
	 * Instantiates a new domain exception.
	 *
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param arg2 the arg 2
	 * @param arg3 the arg 3
	 */
	public DomainException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	/**
	 * Instantiates a new domain exception.
	 *
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 */
	public DomainException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * Instantiates a new domain exception.
	 *
	 * @param arg0 the arg 0
	 */
	public DomainException(String arg0) {
		super(arg0);
	}

	/**
	 * Instantiates a new domain exception.
	 *
	 * @param arg0 the arg 0
	 */
	public DomainException(Throwable arg0) {
		super(arg0);
	}

	
	
}
