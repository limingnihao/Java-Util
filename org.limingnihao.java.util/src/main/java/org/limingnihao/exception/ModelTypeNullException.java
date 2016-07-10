package org.limingnihao.exception;

public class ModelTypeNullException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public ModelTypeNullException() {
		super("枚举类型不存在");
	}
}
