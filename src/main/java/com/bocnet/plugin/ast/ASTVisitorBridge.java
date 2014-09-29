/**
 * !(#) ASTVisitorBridge.java
 * Copyright (c) 2014 Bank of China Co. Ltd.
 * All rights reserved.
 *
 * Create by manbaum.
 * On Sep 29, 2014.
 */
package com.bocnet.plugin.ast;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeMemberDeclaration;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.AssertStatement;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BlockComment;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.BreakStatement;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.ContinueStatement;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EmptyStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.InstanceofExpression;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.LabeledStatement;
import org.eclipse.jdt.core.dom.LineComment;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MemberRef;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.MethodRef;
import org.eclipse.jdt.core.dom.MethodRefParameter;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.QualifiedType;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.core.dom.SuperFieldAccess;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.SynchronizedStatement;
import org.eclipse.jdt.core.dom.TagElement;
import org.eclipse.jdt.core.dom.TextElement;
import org.eclipse.jdt.core.dom.ThisExpression;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclarationStatement;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.dom.TypeParameter;
import org.eclipse.jdt.core.dom.UnionType;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;
import org.eclipse.jdt.core.dom.WildcardType;

/**
 * Class/Interface ASTVisitorBridge.
 * 
 * @author manbaum
 * @since Sep 29, 2014
 * 
 */
public final class ASTVisitorBridge extends ASTVisitor {

	private final VisitorDelegator delegator;

	/**
	 * Constructor of ASTVisitorBridge.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param delegator
	 */
	public ASTVisitorBridge(VisitorDelegator delegator) {
		this.delegator = delegator;
	}

	@Override
	public boolean preVisit2(ASTNode node) {
		return delegator.preVisit(node);
	}

	@Override
	public void postVisit(ASTNode node) {
		delegator.postVisit(node);
	}

	private <T extends ASTNode> boolean visit(Class<T> type, T node) {
		return delegator.visit(type, node);
	}

	private <T extends ASTNode> void endVisit(Class<T> type, T node) {
		delegator.endVisit(type, node);
	}

	@Override
	public boolean visit(AnnotationTypeDeclaration node) {
		return visit(AnnotationTypeDeclaration.class, node);
	}

	@Override
	public boolean visit(AnnotationTypeMemberDeclaration node) {
		return visit(AnnotationTypeMemberDeclaration.class, node);
	}

	@Override
	public boolean visit(AnonymousClassDeclaration node) {
		return visit(AnonymousClassDeclaration.class, node);
	}

	@Override
	public boolean visit(ArrayAccess node) {
		return visit(ArrayAccess.class, node);
	}

	@Override
	public boolean visit(ArrayCreation node) {
		return visit(ArrayCreation.class, node);
	}

	@Override
	public boolean visit(ArrayInitializer node) {
		return visit(ArrayInitializer.class, node);
	}

	@Override
	public boolean visit(ArrayType node) {
		return visit(ArrayType.class, node);
	}

	@Override
	public boolean visit(AssertStatement node) {
		return visit(AssertStatement.class, node);
	}

	@Override
	public boolean visit(Assignment node) {
		return visit(Assignment.class, node);
	}

	@Override
	public boolean visit(Block node) {
		return visit(Block.class, node);
	}

	@Override
	public boolean visit(BlockComment node) {
		return visit(BlockComment.class, node);
	}

	@Override
	public boolean visit(BooleanLiteral node) {
		return visit(BooleanLiteral.class, node);
	}

	@Override
	public boolean visit(BreakStatement node) {
		return visit(BreakStatement.class, node);
	}

	@Override
	public boolean visit(CastExpression node) {
		return visit(CastExpression.class, node);
	}

	@Override
	public boolean visit(CatchClause node) {
		return visit(CatchClause.class, node);
	}

	@Override
	public boolean visit(CharacterLiteral node) {
		return visit(CharacterLiteral.class, node);
	}

	@Override
	public boolean visit(ClassInstanceCreation node) {
		return visit(ClassInstanceCreation.class, node);
	}

	@Override
	public boolean visit(CompilationUnit node) {
		return visit(CompilationUnit.class, node);
	}

	@Override
	public boolean visit(ConditionalExpression node) {
		return visit(ConditionalExpression.class, node);
	}

	@Override
	public boolean visit(ConstructorInvocation node) {
		return visit(ConstructorInvocation.class, node);
	}

	@Override
	public boolean visit(ContinueStatement node) {
		return visit(ContinueStatement.class, node);
	}

	@Override
	public boolean visit(DoStatement node) {
		return visit(DoStatement.class, node);
	}

	@Override
	public boolean visit(EmptyStatement node) {
		return visit(EmptyStatement.class, node);
	}

	@Override
	public boolean visit(EnhancedForStatement node) {
		return visit(EnhancedForStatement.class, node);
	}

	@Override
	public boolean visit(EnumConstantDeclaration node) {
		return visit(EnumConstantDeclaration.class, node);
	}

	@Override
	public boolean visit(EnumDeclaration node) {
		return visit(EnumDeclaration.class, node);
	}

	@Override
	public boolean visit(ExpressionStatement node) {
		return visit(ExpressionStatement.class, node);
	}

	@Override
	public boolean visit(FieldAccess node) {
		return visit(FieldAccess.class, node);
	}

	@Override
	public boolean visit(FieldDeclaration node) {
		return visit(FieldDeclaration.class, node);
	}

	@Override
	public boolean visit(ForStatement node) {
		return visit(ForStatement.class, node);
	}

	@Override
	public boolean visit(IfStatement node) {
		return visit(IfStatement.class, node);
	}

	@Override
	public boolean visit(ImportDeclaration node) {
		return visit(ImportDeclaration.class, node);
	}

	@Override
	public boolean visit(InfixExpression node) {
		return visit(InfixExpression.class, node);
	}

	@Override
	public boolean visit(InstanceofExpression node) {
		return visit(InstanceofExpression.class, node);
	}

	@Override
	public boolean visit(Initializer node) {
		return visit(Initializer.class, node);
	}

	@Override
	public boolean visit(Javadoc node) {
		return visit(Javadoc.class, node);
	}

	@Override
	public boolean visit(LabeledStatement node) {
		return visit(LabeledStatement.class, node);
	}

	@Override
	public boolean visit(LineComment node) {
		return visit(LineComment.class, node);
	}

	@Override
	public boolean visit(MarkerAnnotation node) {
		return visit(MarkerAnnotation.class, node);
	}

	@Override
	public boolean visit(MemberRef node) {
		return visit(MemberRef.class, node);
	}

	@Override
	public boolean visit(MemberValuePair node) {
		return visit(MemberValuePair.class, node);
	}

	@Override
	public boolean visit(MethodRef node) {
		return visit(MethodRef.class, node);
	}

	@Override
	public boolean visit(MethodRefParameter node) {
		return visit(MethodRefParameter.class, node);
	}

	@Override
	public boolean visit(MethodDeclaration node) {
		return visit(MethodDeclaration.class, node);
	}

	@Override
	public boolean visit(MethodInvocation node) {
		return visit(MethodInvocation.class, node);
	}

	@Override
	public boolean visit(Modifier node) {
		return visit(Modifier.class, node);
	}

	@Override
	public boolean visit(NormalAnnotation node) {
		return visit(NormalAnnotation.class, node);
	}

	@Override
	public boolean visit(NullLiteral node) {
		return visit(NullLiteral.class, node);
	}

	@Override
	public boolean visit(NumberLiteral node) {
		return visit(NumberLiteral.class, node);
	}

	@Override
	public boolean visit(PackageDeclaration node) {
		return visit(PackageDeclaration.class, node);
	}

	@Override
	public boolean visit(ParameterizedType node) {
		return visit(ParameterizedType.class, node);
	}

	@Override
	public boolean visit(ParenthesizedExpression node) {
		return visit(ParenthesizedExpression.class, node);
	}

	@Override
	public boolean visit(PostfixExpression node) {
		return visit(PostfixExpression.class, node);
	}

	@Override
	public boolean visit(PrefixExpression node) {
		return visit(PrefixExpression.class, node);
	}

	@Override
	public boolean visit(PrimitiveType node) {
		return visit(PrimitiveType.class, node);
	}

	@Override
	public boolean visit(QualifiedName node) {
		return visit(QualifiedName.class, node);
	}

	@Override
	public boolean visit(QualifiedType node) {
		return visit(QualifiedType.class, node);
	}

	@Override
	public boolean visit(ReturnStatement node) {
		return visit(ReturnStatement.class, node);
	}

	@Override
	public boolean visit(SimpleName node) {
		return visit(SimpleName.class, node);
	}

	@Override
	public boolean visit(SimpleType node) {
		return visit(SimpleType.class, node);
	}

	@Override
	public boolean visit(SingleMemberAnnotation node) {
		return visit(SingleMemberAnnotation.class, node);
	}

	@Override
	public boolean visit(SingleVariableDeclaration node) {
		return visit(SingleVariableDeclaration.class, node);
	}

	@Override
	public boolean visit(StringLiteral node) {
		return visit(StringLiteral.class, node);
	}

	@Override
	public boolean visit(SuperConstructorInvocation node) {
		return visit(SuperConstructorInvocation.class, node);
	}

	@Override
	public boolean visit(SuperFieldAccess node) {
		return visit(SuperFieldAccess.class, node);
	}

	@Override
	public boolean visit(SuperMethodInvocation node) {
		return visit(SuperMethodInvocation.class, node);
	}

	@Override
	public boolean visit(SwitchCase node) {
		return visit(SwitchCase.class, node);
	}

	@Override
	public boolean visit(SwitchStatement node) {
		return visit(SwitchStatement.class, node);
	}

	@Override
	public boolean visit(SynchronizedStatement node) {
		return visit(SynchronizedStatement.class, node);
	}

	@Override
	public boolean visit(TagElement node) {
		return visit(TagElement.class, node);
	}

	@Override
	public boolean visit(TextElement node) {
		return visit(TextElement.class, node);
	}

	@Override
	public boolean visit(ThisExpression node) {
		return visit(ThisExpression.class, node);
	}

	@Override
	public boolean visit(ThrowStatement node) {
		return visit(ThrowStatement.class, node);
	}

	@Override
	public boolean visit(TryStatement node) {
		return visit(TryStatement.class, node);
	}

	@Override
	public boolean visit(TypeDeclaration node) {
		return visit(TypeDeclaration.class, node);
	}

	@Override
	public boolean visit(TypeDeclarationStatement node) {
		return visit(TypeDeclarationStatement.class, node);
	}

	@Override
	public boolean visit(TypeLiteral node) {
		return visit(TypeLiteral.class, node);
	}

	@Override
	public boolean visit(TypeParameter node) {
		return visit(TypeParameter.class, node);
	}

	@Override
	public boolean visit(UnionType node) {
		return visit(UnionType.class, node);
	}

	@Override
	public boolean visit(VariableDeclarationExpression node) {
		return visit(VariableDeclarationExpression.class, node);
	}

	@Override
	public boolean visit(VariableDeclarationStatement node) {
		return visit(VariableDeclarationStatement.class, node);
	}

	@Override
	public boolean visit(VariableDeclarationFragment node) {
		return visit(VariableDeclarationFragment.class, node);
	}

	@Override
	public boolean visit(WhileStatement node) {
		return visit(WhileStatement.class, node);
	}

	@Override
	public boolean visit(WildcardType node) {
		return visit(WildcardType.class, node);
	}

	@Override
	public void endVisit(AnnotationTypeDeclaration node) {
		endVisit(AnnotationTypeDeclaration.class, node);
	}

	@Override
	public void endVisit(AnnotationTypeMemberDeclaration node) {
		endVisit(AnnotationTypeMemberDeclaration.class, node);
	}

	@Override
	public void endVisit(AnonymousClassDeclaration node) {
		endVisit(AnonymousClassDeclaration.class, node);
	}

	@Override
	public void endVisit(ArrayAccess node) {
		endVisit(ArrayAccess.class, node);
	}

	@Override
	public void endVisit(ArrayCreation node) {
		endVisit(ArrayCreation.class, node);
	}

	@Override
	public void endVisit(ArrayInitializer node) {
		endVisit(ArrayInitializer.class, node);
	}

	@Override
	public void endVisit(ArrayType node) {
		endVisit(ArrayType.class, node);
	}

	@Override
	public void endVisit(AssertStatement node) {
		endVisit(AssertStatement.class, node);
	}

	@Override
	public void endVisit(Assignment node) {
		endVisit(Assignment.class, node);
	}

	@Override
	public void endVisit(Block node) {
		endVisit(Block.class, node);
	}

	@Override
	public void endVisit(BlockComment node) {
		endVisit(BlockComment.class, node);
	}

	@Override
	public void endVisit(BooleanLiteral node) {
		endVisit(BooleanLiteral.class, node);
	}

	@Override
	public void endVisit(BreakStatement node) {
		endVisit(BreakStatement.class, node);
	}

	@Override
	public void endVisit(CastExpression node) {
		endVisit(CastExpression.class, node);
	}

	@Override
	public void endVisit(CatchClause node) {
		endVisit(CatchClause.class, node);
	}

	@Override
	public void endVisit(CharacterLiteral node) {
		endVisit(CharacterLiteral.class, node);
	}

	@Override
	public void endVisit(ClassInstanceCreation node) {
		endVisit(ClassInstanceCreation.class, node);
	}

	@Override
	public void endVisit(CompilationUnit node) {
		endVisit(CompilationUnit.class, node);
	}

	@Override
	public void endVisit(ConditionalExpression node) {
		endVisit(ConditionalExpression.class, node);
	}

	@Override
	public void endVisit(ConstructorInvocation node) {
		endVisit(ConstructorInvocation.class, node);
	}

	@Override
	public void endVisit(ContinueStatement node) {
		endVisit(ContinueStatement.class, node);
	}

	@Override
	public void endVisit(DoStatement node) {
		endVisit(DoStatement.class, node);
	}

	@Override
	public void endVisit(EmptyStatement node) {
		endVisit(EmptyStatement.class, node);
	}

	@Override
	public void endVisit(EnhancedForStatement node) {
		endVisit(EnhancedForStatement.class, node);
	}

	@Override
	public void endVisit(EnumConstantDeclaration node) {
		endVisit(EnumConstantDeclaration.class, node);
	}

	@Override
	public void endVisit(EnumDeclaration node) {
		endVisit(EnumDeclaration.class, node);
	}

	@Override
	public void endVisit(ExpressionStatement node) {
		endVisit(ExpressionStatement.class, node);
	}

	@Override
	public void endVisit(FieldAccess node) {
		endVisit(FieldAccess.class, node);
	}

	@Override
	public void endVisit(FieldDeclaration node) {
		endVisit(FieldDeclaration.class, node);
	}

	@Override
	public void endVisit(ForStatement node) {
		endVisit(ForStatement.class, node);
	}

	@Override
	public void endVisit(IfStatement node) {
		endVisit(IfStatement.class, node);
	}

	@Override
	public void endVisit(ImportDeclaration node) {
		endVisit(ImportDeclaration.class, node);
	}

	@Override
	public void endVisit(InfixExpression node) {
		endVisit(InfixExpression.class, node);
	}

	@Override
	public void endVisit(InstanceofExpression node) {
		endVisit(InstanceofExpression.class, node);
	}

	@Override
	public void endVisit(Initializer node) {
		endVisit(Initializer.class, node);
	}

	@Override
	public void endVisit(Javadoc node) {
		endVisit(Javadoc.class, node);
	}

	@Override
	public void endVisit(LabeledStatement node) {
		endVisit(LabeledStatement.class, node);
	}

	@Override
	public void endVisit(LineComment node) {
		endVisit(LineComment.class, node);
	}

	@Override
	public void endVisit(MarkerAnnotation node) {
		endVisit(MarkerAnnotation.class, node);
	}

	@Override
	public void endVisit(MemberRef node) {
		endVisit(MemberRef.class, node);
	}

	@Override
	public void endVisit(MemberValuePair node) {
		endVisit(MemberValuePair.class, node);
	}

	@Override
	public void endVisit(MethodRef node) {
		endVisit(MethodRef.class, node);
	}

	@Override
	public void endVisit(MethodRefParameter node) {
		endVisit(MethodRefParameter.class, node);
	}

	@Override
	public void endVisit(MethodDeclaration node) {
		endVisit(MethodDeclaration.class, node);
	}

	@Override
	public void endVisit(MethodInvocation node) {
		endVisit(MethodInvocation.class, node);
	}

	@Override
	public void endVisit(Modifier node) {
		endVisit(Modifier.class, node);
	}

	@Override
	public void endVisit(NormalAnnotation node) {
		endVisit(NormalAnnotation.class, node);
	}

	@Override
	public void endVisit(NullLiteral node) {
		endVisit(NullLiteral.class, node);
	}

	@Override
	public void endVisit(NumberLiteral node) {
		endVisit(NumberLiteral.class, node);
	}

	@Override
	public void endVisit(PackageDeclaration node) {
		endVisit(PackageDeclaration.class, node);
	}

	@Override
	public void endVisit(ParameterizedType node) {
		endVisit(ParameterizedType.class, node);
	}

	@Override
	public void endVisit(ParenthesizedExpression node) {
		endVisit(ParenthesizedExpression.class, node);
	}

	@Override
	public void endVisit(PostfixExpression node) {
		endVisit(PostfixExpression.class, node);
	}

	@Override
	public void endVisit(PrefixExpression node) {
		endVisit(PrefixExpression.class, node);
	}

	@Override
	public void endVisit(PrimitiveType node) {
		endVisit(PrimitiveType.class, node);
	}

	@Override
	public void endVisit(QualifiedName node) {
		endVisit(QualifiedName.class, node);
	}

	@Override
	public void endVisit(QualifiedType node) {
		endVisit(QualifiedType.class, node);
	}

	@Override
	public void endVisit(ReturnStatement node) {
		endVisit(ReturnStatement.class, node);
	}

	@Override
	public void endVisit(SimpleName node) {
		endVisit(SimpleName.class, node);
	}

	@Override
	public void endVisit(SimpleType node) {
		endVisit(SimpleType.class, node);
	}

	@Override
	public void endVisit(SingleMemberAnnotation node) {
		endVisit(SingleMemberAnnotation.class, node);
	}

	@Override
	public void endVisit(SingleVariableDeclaration node) {
		endVisit(SingleVariableDeclaration.class, node);
	}

	@Override
	public void endVisit(StringLiteral node) {
		endVisit(StringLiteral.class, node);
	}

	@Override
	public void endVisit(SuperConstructorInvocation node) {
		endVisit(SuperConstructorInvocation.class, node);
	}

	@Override
	public void endVisit(SuperFieldAccess node) {
		endVisit(SuperFieldAccess.class, node);
	}

	@Override
	public void endVisit(SuperMethodInvocation node) {
		endVisit(SuperMethodInvocation.class, node);
	}

	@Override
	public void endVisit(SwitchCase node) {
		endVisit(SwitchCase.class, node);
	}

	@Override
	public void endVisit(SwitchStatement node) {
		endVisit(SwitchStatement.class, node);
	}

	@Override
	public void endVisit(SynchronizedStatement node) {
		endVisit(SynchronizedStatement.class, node);
	}

	@Override
	public void endVisit(TagElement node) {
		endVisit(TagElement.class, node);
	}

	@Override
	public void endVisit(TextElement node) {
		endVisit(TextElement.class, node);
	}

	@Override
	public void endVisit(ThisExpression node) {
		endVisit(ThisExpression.class, node);
	}

	@Override
	public void endVisit(ThrowStatement node) {
		endVisit(ThrowStatement.class, node);
	}

	@Override
	public void endVisit(TryStatement node) {
		endVisit(TryStatement.class, node);
	}

	@Override
	public void endVisit(TypeDeclaration node) {
		endVisit(TypeDeclaration.class, node);
	}

	@Override
	public void endVisit(TypeDeclarationStatement node) {
		endVisit(TypeDeclarationStatement.class, node);
	}

	@Override
	public void endVisit(TypeLiteral node) {
		endVisit(TypeLiteral.class, node);
	}

	@Override
	public void endVisit(TypeParameter node) {
		endVisit(TypeParameter.class, node);
	}

	@Override
	public void endVisit(UnionType node) {
		endVisit(UnionType.class, node);
	}

	@Override
	public void endVisit(VariableDeclarationExpression node) {
		endVisit(VariableDeclarationExpression.class, node);
	}

	@Override
	public void endVisit(VariableDeclarationStatement node) {
		endVisit(VariableDeclarationStatement.class, node);
	}

	@Override
	public void endVisit(VariableDeclarationFragment node) {
		endVisit(VariableDeclarationFragment.class, node);
	}

	@Override
	public void endVisit(WhileStatement node) {
		endVisit(WhileStatement.class, node);
	}

	@Override
	public void endVisit(WildcardType node) {
		endVisit(WildcardType.class, node);
	}
}
