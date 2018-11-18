package net.brilliance.exceptions;

/**
 * The class {@code ExecutionContextException} and its subclasses are a form of
 * {@code Throwable} that indicates conditions that a reasonable
 * application might want to catch.
 *
 * <p>The class {@code EcosysException} and any subclasses that are not also
 * subclasses of {@link RuntimeException} are <em>checked
 * exceptions</em>.  Checked exceptions need to be declared in a
 * method or constructor's {@code throws} clause if they can be thrown
 * by the execution of the method or constructor and propagate outside
 * the method or constructor boundary.
 *
 * @author ducbq
 */
public class ExecutionContextException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1137280691994978800L;

	/**
   * Constructs a new exception with {@code null} as its detail message.
   * The cause is not initialized, and may subsequently be initialized by a
   * call to {@link #initCause}.
   */
  public ExecutionContextException() {
      super();
  }

  /**
   * Constructs a new exception with the specified detail message.  The
   * cause is not initialized, and may subsequently be initialized by
   * a call to {@link #initCause}.
   *
   * @param   message   the detail message. The detail message is saved for
   *          later retrieval by the {@link #getMessage()} method.
   */
  public ExecutionContextException(String message) {
      super(message);
  }

  /**
   * Constructs a new exception with the specified detail message and
   * cause.  <p>Note that the detail message associated with
   * {@code cause} is <i>not</i> automatically incorporated in
   * this exception's detail message.
   *
   * @param  message the detail message (which is saved for later retrieval
   *         by the {@link #getMessage()} method).
   * @param  cause the cause (which is saved for later retrieval by the
   *         {@link #getCause()} method).  (A <tt>null</tt> value is
   *         permitted, and indicates that the cause is nonexistent or
   *         unknown.)
   * @since  1.4
   */
  public ExecutionContextException(String message, Throwable cause) {
      super(message, cause);
  }

  /**
   * Constructs a new exception with the specified cause and a detail
   * message of <tt>(cause==null ? null : cause.toString())</tt> (which
   * typically contains the class and detail message of <tt>cause</tt>).
   * This constructor is useful for exceptions that are little more than
   * wrappers for other throwables (for example, {@link
   * java.security.PrivilegedActionException}).
   *
   * @param  cause the cause (which is saved for later retrieval by the
   *         {@link #getCause()} method).  (A <tt>null</tt> value is
   *         permitted, and indicates that the cause is nonexistent or
   *         unknown.)
   * @since  1.4
   */
  public ExecutionContextException(Throwable cause) {
      super(cause);
  }

  /**
   * Constructs a new exception with the specified detail message,
   * cause, suppression enabled or disabled, and writable stack
   * trace enabled or disabled.
   *
   * @param  message the detail message.
   * @param cause the cause.  (A {@code null} value is permitted,
   * and indicates that the cause is nonexistent or unknown.)
   * @param enableSuppression whether or not suppression is enabled
   *                          or disabled
   * @param writableStackTrace whether or not the stack trace should
   *                           be writable
   * @since 1.7
   */
  protected ExecutionContextException(String message, Throwable cause,
                      boolean enableSuppression,
                      boolean writableStackTrace) {
      super(message, cause, enableSuppression, writableStackTrace);
  }
}
