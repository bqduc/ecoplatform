/**
 * ************************************************************************
 * * The contents of this file are subject to the MRPL 1.2
 * * (the  "License"),  being   the  Mozilla   Public  License
 * * Version 1.1  with a permitted attribution clause; you may not  use this
 * * file except in compliance with the License. You  may  obtain  a copy of
 * * the License at http://www.floreantpos.org/license.html
 * * Software distributed under the License  is  distributed  on  an "AS IS"
 * * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * * License for the specific  language  governing  rights  and  limitations
 * * under the License.
 * * The Original Code is FLOREANT POS.
 * * The Initial Developer of the Original Code is OROCUBE LLC
 * * All portions are Copyright (C) 2015 OROCUBE LLC
 * * All Rights Reserved.
 * ************************************************************************
 */
package net.brilliance.exceptions;

public class VpxRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2491422323987100381L;

	public VpxRuntimeException() {
		super();
	}

	public VpxRuntimeException(String arg0) {
		super(arg0);
	}

	public VpxRuntimeException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public VpxRuntimeException(Throwable arg0) {
		super(arg0);
	}

}
