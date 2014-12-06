/*
 * This file is part of MineTweaker API, licensed under the MIT License (MIT).
 * 
 * Copyright (c) 2014 MineTweaker <http://minetweaker3.powerofbytes.com>
 */
package org.openzen.zencode.symbolic.symbols;

import org.openzen.zencode.symbolic.expression.partial.PartialStaticMember;
import org.openzen.zencode.symbolic.scope.IScopeMethod;
import org.openzen.zencode.symbolic.expression.IPartialExpression;
import org.openzen.zencode.symbolic.type.IZenType;
import org.openzen.zencode.util.CodePosition;

/**
 *
 * @author Stan
 * @param <E>
 * @param <T>
 */
public class MemberStaticSymbol<E extends IPartialExpression<E, T>, T extends IZenType<E, T>>
		implements IZenSymbol<E, T>
{
	private final PartialStaticMember<E, T> member;

	public MemberStaticSymbol(PartialStaticMember<E, T> member)
	{
		this.member = member;
	}

	@Override
	public IPartialExpression<E, T> instance(CodePosition position, IScopeMethod<E, T> environment)
	{
		return member.makeVariant(position, environment);
	}
}