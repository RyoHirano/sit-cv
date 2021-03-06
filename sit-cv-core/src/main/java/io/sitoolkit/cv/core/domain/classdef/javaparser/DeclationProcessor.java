package io.sitoolkit.cv.core.domain.classdef.javaparser;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.CatchClause;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.TryStmt;
import com.github.javaparser.resolution.declarations.ResolvedMethodDeclaration;
import io.sitoolkit.cv.core.domain.classdef.BranchStatement;
import io.sitoolkit.cv.core.domain.classdef.CatchStatement;
import io.sitoolkit.cv.core.domain.classdef.ConditionalStatement;
import io.sitoolkit.cv.core.domain.classdef.FinallyStatement;
import io.sitoolkit.cv.core.domain.classdef.LoopStatement;
import io.sitoolkit.cv.core.domain.classdef.MethodCallDef;
import io.sitoolkit.cv.core.domain.classdef.TryStatement;
import io.sitoolkit.cv.core.domain.classdef.TypeDef;
import java.util.Optional;
import java.util.stream.Collectors;

public class DeclationProcessor {

  public static LoopStatement createLoopStatement(Node n, String scope) {
    LoopStatement statement = new LoopStatement();
    statement.setBody(n.toString());
    statement.setScope(scope);
    return statement;
  }

  public static BranchStatement createBranchStatement(IfStmt n) {
    BranchStatement statement = new BranchStatement();
    return statement;
  }

  public static ConditionalStatement createConditionalStatement(
      Statement n, String condition, boolean isFirst) {
    ConditionalStatement statement = new ConditionalStatement();
    statement.setBody(n.toString());
    statement.setCondition(condition);
    statement.setFirst(isFirst);
    return statement;
  }

  public static TryStatement createTryStatement(TryStmt n) {
    TryStatement statement = new TryStatement();
    statement.setBody(n.toString());
    return statement;
  }

  public static CatchStatement createCatchStatement(CatchClause n, String parameter) {
    CatchStatement statement = new CatchStatement();
    statement.setBody(n.toString());
    statement.setParameter(parameter);
    return statement;
  }

  public static FinallyStatement createFinallyStatement(Statement n) {
    FinallyStatement statement = new FinallyStatement();
    statement.setBody(n.toString());
    return statement;
  }

  public static MethodCallDef createMethodCall(
      ResolvedMethodDeclaration rmd, Optional<Node> parentNode, NodeList<Expression> args) {
    MethodCallDef methodCall = new MethodCallDef();
    methodCall.setSignature(rmd.getSignature());
    methodCall.setQualifiedSignature(rmd.getQualifiedSignature());
    methodCall.setName(rmd.getName());
    methodCall.setClassName(rmd.getClassName());
    methodCall.setPackageName(rmd.getPackageName());
    TypeDef returnType = TypeProcessor.createTypeDef(rmd.getReturnType());
    if (parentNode.isPresent() && parentNode.get() instanceof VariableDeclarator) {
      String variable = ((VariableDeclarator) parentNode.get()).getNameAsString();
      returnType.setVariable(variable);
    }
    methodCall.setReturnType(returnType);
    methodCall.setParamTypes(TypeProcessor.collectParamTypes(rmd));
    methodCall.setArgs(args.stream().map(Expression::toString).collect(Collectors.toList()));
    return methodCall;
  }
}
