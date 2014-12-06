/*
 * This file is part of ZenCode, licensed under the MIT License (MIT).
 * 
 * Copyright (c) 2014 openzen.org <http://zencode.openzen.org>
 */
package org.openzen.zencode.java.expression;

import org.openzen.zencode.java.field.IJavaField;
import org.openzen.zencode.java.type.IJavaType;
import org.openzen.zencode.symbolic.scope.IScopeMethod;
import org.openzen.zencode.util.CodePosition;
import org.openzen.zencode.java.util.MethodOutput;

/**
 *
 * @author Stan
 */
public class JavaGetInstanceField extends AbstractJavaExpression
{
	private final IJavaField field;
	private final IJavaExpression instance;
	
	public JavaGetInstanceField(CodePosition position, IScopeMethod<IJavaExpression, IJavaType> scope, IJavaField field, IJavaExpression instance)
	{
		super(position, scope);
		
		this.field = field;
		this.instance = instance;
	}

	@Override
	public void compile(boolean pushResult, MethodOutput method)
	{
		instance.compile(pushResult, method);
		
		if (pushResult)
			method.putField(field.getInternalClassName(), field.getFieldName(), field.getType().getSignature());
	}

	@Override
	public IJavaType getType()
	{
		return field.getType();
	}
}