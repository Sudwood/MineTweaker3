/*
 * This file is part of MineTweaker API, licensed under the MIT License (MIT).
 * 
 * Copyright (c) 2014 MineTweaker <http://minetweaker3.powerofbytes.com>
 */
package org.openzen.zencode.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.openzen.zencode.lexer.Token;
import org.openzen.zencode.lexer.ZenLexer;
import static org.openzen.zencode.lexer.ZenLexer.*;
import org.openzen.zencode.parser.expression.ParsedCallArguments;
import org.openzen.zencode.parser.expression.ParsedCallArguments.MatchedArguments;
import org.openzen.zencode.parser.type.IParsedType;
import org.openzen.zencode.parser.type.TypeParser;
import org.openzen.zencode.symbolic.annotations.SymbolicAnnotation;
import org.openzen.zencode.symbolic.expression.IPartialExpression;
import org.openzen.zencode.symbolic.scope.IModuleScope;
import org.openzen.zencode.symbolic.type.IGenericType;
import org.openzen.zencode.util.CodePosition;

/**
 *
 * @author Stan
 */
public class ParsedAnnotation
{
	public static List<ParsedAnnotation> parseAll(ZenLexer lexer)
	{
		if (lexer.peek().getType() != T_SQBROPEN)
			return Collections.emptyList();
		
		List<ParsedAnnotation> annotations = new ArrayList<>();
		
		while (lexer.peek().getType() == T_SQBROPEN) {
			annotations.add(parse(lexer));
		}
		
		return annotations;
	}
	
	public static ParsedAnnotation parse(ZenLexer lexer)
	{
		Token opening = lexer.required(T_SQBROPEN, "[ expected");
		
		IParsedType annotationType = TypeParser.parse(lexer);
		ParsedCallArguments arguments = null;
		
		if (lexer.peek().getType() == T_BROPEN)
			arguments = ParsedCallArguments.parse(lexer);
		
		lexer.required(T_SQBRCLOSE, "] expected");
		return new ParsedAnnotation(opening.getPosition(), annotationType, arguments);
	}
	
	private final CodePosition position;
	private final IParsedType annotationType;
	private final ParsedCallArguments arguments;
	
	private ParsedAnnotation(CodePosition position, IParsedType annotationType, ParsedCallArguments arguments)
	{
		this.position = position;
		this.annotationType = annotationType;
		this.arguments = arguments;
	}
	
	public <E extends IPartialExpression<E>>
		SymbolicAnnotation<E> compile(IModuleScope<E> scope)
	{
		IGenericType<E> type = annotationType.compile(scope);
		MatchedArguments<E> compiledArguments = arguments.compile(type.getConstructors(scope), scope.getConstantScope());
		if (compiledArguments == null) {
			if (type.getConstructors(scope).isEmpty()) {
				scope.getErrorLogger().errorNoConstructorsForType(position, type);
			} else {
				scope.getErrorLogger().errorNoMatchingMethod(position, type.getConstructors(scope), arguments);
			}
			
			return null;
		}
		return new SymbolicAnnotation<E>(position, type, compiledArguments.method, compiledArguments.arguments);
	}
}
