/*
 * This file is part of ZenCode, licensed under the MIT License (MIT).
 * 
 * Copyright (c) 2014 openzen.org <http://zencode.openzen.org>
 */
package org.openzen.zencode.java.expression;

import java.util.List;
import org.openzen.zencode.java.method.IJavaMethod;
import org.openzen.zencode.symbolic.scope.IMethodScope;
import org.openzen.zencode.util.CodePosition;
import org.openzen.zencode.java.util.MethodOutput;
import org.openzen.zencode.runtime.IAny;
import org.openzen.zencode.symbolic.type.IGenericType;

/**
 *
 * @author Stan
 */
public class JavaCallVirtual extends AbstractJavaExpression
{
	private final IJavaMethod method;
	private final IJavaExpression target;
	private final List<IJavaExpression> arguments;
	
	public JavaCallVirtual(CodePosition position, IMethodScope<IJavaExpression> scope, IJavaMethod method, IJavaExpression target, List<IJavaExpression> arguments)
	{
		super(position, scope);
		
		this.method = method;
		this.target = target;
		this.arguments = arguments;
	}

	@Override
	public void compile(boolean result, MethodOutput output)
	{
		target.compile(true, output);
		
		for (IJavaExpression argument : arguments)
		{
			argument.compile(true, output);
		}
		
		output.invokeVirtual(
					method.getDeclaringClass(),
					method.getMethodName(),
					method.getMethodSignature());
		
		if (!result && method.getReturnType() != getScope().getTypeCompiler().void_)
			output.pop(method.getReturnType());
	}

	@Override
	public IGenericType<IJavaExpression> getType()
	{
		return method.getReturnType();
	}

	@Override
	public IAny getCompileTimeValue()
	{
		return null;
	}

	@Override
	public void validate()
	{
		
	}
}
