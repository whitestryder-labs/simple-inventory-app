package org.whitestryder.labs.api.support;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;



/**
 * Updates an HTTP Servlet response matching "fromStatusCode" and changes it "toStatusCode"
 */
public class StatusCodeChangingHttpServletResponseWrapper extends HttpServletResponseWrapper {

	private int fromStatusCode;
	private int toStatusCode;
	
	/**
	 * Instantiates a new custom http servlet response wrapper.
	 *
	 * @param response the response
	 * @param fromStatusCode the from status code
	 * @param toStatusCode the to status code
	 */
	public StatusCodeChangingHttpServletResponseWrapper(HttpServletResponse response, int fromStatusCode, int toStatusCode) {
		super(response);
		this.fromStatusCode = fromStatusCode;
		this.toStatusCode = toStatusCode;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServletResponseWrapper#setStatus(int)
	 */
	@Override
	public void setStatus(int sc) {
		if (sc == fromStatusCode){
			super.setStatus(toStatusCode);
		} else {
			super.setStatus(sc);
		}
	}


}
