/*
 * This file is part of ZenCode, licensed under the MIT License (MIT).
 * 
 * Copyright (c) 2014 openzen.org <http://zencode.openzen.org>
 */
package org.openzen.zencode.parser.type;

import org.openzen.zencode.symbolic.expression.IPartialExpression;
import org.openzen.zencode.symbolic.scope.IModuleScope;
import org.openzen.zencode.symbolic.type.IGenericType;

/**
 * Parsed array type (valuetype[]).
 * 
 * @author Stan Hebben
 */
public class ParsedTypeArray implements IParsedType
{
	private final IParsedType baseType;

	public ParsedTypeArray(IParsedType baseType)
	{
		this.baseType = baseType;
	}

	@Override
	public <E extends IPartialExpression<E>>
		 IGenericType<E> compile(IModuleScope<E> scope)
	{
		return scope.getTypeCompiler().getArray(baseType.compile(scope));
	}
	
	@Override
	public String toString()
	{
		return baseType.toString() + "[]";
	}
}
