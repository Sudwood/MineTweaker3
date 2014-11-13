/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openzen.zencode.parser.expression;

import org.openzen.zencode.IZenCompileEnvironment;
import org.openzen.zencode.symbolic.scope.IScopeMethod;
import stanhebben.zenscript.expression.ExpressionConditional;
import org.openzen.zencode.symbolic.expression.IPartialExpression;
import stanhebben.zenscript.type.ZenType;
import org.openzen.zencode.runtime.IAny;
import org.openzen.zencode.util.CodePosition;

/**
 *
 * @author Stanneke
 */
public class ParsedExpressionConditional extends ParsedExpression {
	private final ParsedExpression condition;
	private final ParsedExpression ifThen;
	private final ParsedExpression ifElse;
	
	public ParsedExpressionConditional(CodePosition position, ParsedExpression condition, ParsedExpression ifThen, ParsedExpression ifElse) {
		super(position);
		
		this.condition = condition;
		this.ifThen = ifThen;
		this.ifElse = ifElse;
	}

	@Override
	public IPartialExpression compilePartial(IScopeMethod environment, ZenType predictedType) {
		return new ExpressionConditional(
				getPosition(),
				environment,
				condition.compile(environment, environment.getTypes().BOOL).cast(getPosition(), environment.getTypes().BOOL),
				ifThen.compile(environment, predictedType),
				ifElse.compile(environment, predictedType));
	}

	@Override
	public IAny eval(IZenCompileEnvironment environment) {
		IAny conditionValue = condition.eval(environment);
		if (conditionValue == null)
			return null;
		
		boolean conditionBool = conditionValue.asBool();
		if (conditionBool) {
			return ifThen.eval(environment);
		} else {
			return ifElse.eval(environment);
		}
	}
}