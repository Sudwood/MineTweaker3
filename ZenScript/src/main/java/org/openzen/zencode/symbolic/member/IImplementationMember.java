/*
 * This file is part of MineTweaker API, licensed under the MIT License (MIT).
 * 
 * Copyright (c) 2014 MineTweaker <http://minetweaker3.powerofbytes.com>
 */
package org.openzen.zencode.symbolic.member;

import org.openzen.zencode.symbolic.expression.IPartialExpression;
import org.openzen.zencode.symbolic.type.IGenericType;

/**
 *
 * @author Stan
 */
public interface IImplementationMember<E extends IPartialExpression<E>> extends IMember<E>
{
	public IGenericType<E> getType();
}