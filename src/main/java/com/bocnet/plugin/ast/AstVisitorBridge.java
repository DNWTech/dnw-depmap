/**
 * !(#) AstVisitorBridge.java
 * Copyright (c) 2014 Bank of China Co. Ltd.
 * All rights reserved.
 *
 * Create by manbaum.
 * On Sep 29, 2014.
 */
package com.bocnet.plugin.ast;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
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
import org.eclipse.jdt.core.dom.Statement;
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
 * Class/Interface AstVisitorBridge.
 * 
 * @author manbaum
 * @since Sep 29, 2014
 * 
 */
public class AstVisitorBridge extends ASTVisitor {

	private final VisitorRegistry registry;

	/**
	 * Constructor of AstVisitorBridge.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param registry
	 */
	public AstVisitorBridge(VisitorRegistry registry) {
		this.registry = registry;
	}

	/**
	 * Method doVisit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param clazz
	 *            the type of the node to visit
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 */
	private <T extends ASTNode> boolean doVisit(Class<T> clazz, T node) {
		AstVisitor<T> visitor = registry.lookup(clazz);
		return visitor != null ? visitor.visit(node, null) : false;
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.AnnotationTypeDeclaration)
	 */
	@Override
	public boolean visit(AnnotationTypeDeclaration node) {
		return doVisit(AnnotationTypeDeclaration.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.AnnotationTypeMemberDeclaration)
	 */
	@Override
	public boolean visit(AnnotationTypeMemberDeclaration node) {
		return doVisit(AnnotationTypeMemberDeclaration.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.AnonymousClassDeclaration)
	 */
	@Override
	public boolean visit(AnonymousClassDeclaration node) {
		return doVisit(AnonymousClassDeclaration.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.ArrayAccess)
	 */
	@Override
	public boolean visit(ArrayAccess node) {
		return doVisit(ArrayAccess.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.ArrayCreation)
	 */
	@Override
	public boolean visit(ArrayCreation node) {
		return doVisit(ArrayCreation.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.ArrayInitializer)
	 */
	@Override
	public boolean visit(ArrayInitializer node) {
		return doVisit(ArrayInitializer.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.ArrayType)
	 */
	@Override
	public boolean visit(ArrayType node) {
		return doVisit(ArrayType.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.AssertStatement)
	 */
	@Override
	public boolean visit(AssertStatement node) {
		return doVisit(AssertStatement.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.Assignment)
	 */
	@Override
	public boolean visit(Assignment node) {
		return doVisit(Assignment.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.Block)
	 */
	@Override
	public boolean visit(Block node) {
		if (doVisit(Block.class, node)) {
			@SuppressWarnings("unchecked")
			List<Statement> statements = node.statements();
			for (Statement statement : statements) {
				if (statement instanceof Block) {
					visit((Block) statement);
				} else if (statement instanceof ExpressionStatement) {
					visit((ExpressionStatement) statement);
				}
			}
		}
		return false;
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.BlockComment)
	 */
	@Override
	public boolean visit(BlockComment node) {
		return doVisit(BlockComment.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.BooleanLiteral)
	 */
	@Override
	public boolean visit(BooleanLiteral node) {
		return doVisit(BooleanLiteral.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.BreakStatement)
	 */
	@Override
	public boolean visit(BreakStatement node) {
		return doVisit(BreakStatement.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.CastExpression)
	 */
	@Override
	public boolean visit(CastExpression node) {
		return doVisit(CastExpression.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.CatchClause)
	 */
	@Override
	public boolean visit(CatchClause node) {
		return doVisit(CatchClause.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.CharacterLiteral)
	 */
	@Override
	public boolean visit(CharacterLiteral node) {
		return doVisit(CharacterLiteral.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.ClassInstanceCreation)
	 */
	@Override
	public boolean visit(ClassInstanceCreation node) {
		return doVisit(ClassInstanceCreation.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.CompilationUnit)
	 */
	@Override
	public boolean visit(CompilationUnit node) {
		if (doVisit(CompilationUnit.class, node)) {
			// visit package declaration.
			visit(node.getPackage());
			// visit import declaration.
			@SuppressWarnings("unchecked")
			List<ImportDeclaration> imports = node.imports();
			for (ImportDeclaration i : imports) {
				visit(i);
			}
			// visit type declaration.
			// including type declaration, enum declaration, annotation type
			// declaration.
			@SuppressWarnings("unchecked")
			List<AbstractTypeDeclaration> types = node.types();
			for (AbstractTypeDeclaration type : types) {
				if (type instanceof TypeDeclaration) {
					visit((TypeDeclaration) type);
				} else if (type instanceof EnumDeclaration) {
					visit((EnumDeclaration) type);
				} else if (type instanceof AnnotationTypeDeclaration) {
					visit((AnnotationTypeDeclaration) type);
				} else
					throw new UnknownAstNodeException(type, "unknown.ast.node");
			}
		}
		return false;
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.ConditionalExpression)
	 */
	@Override
	public boolean visit(ConditionalExpression node) {
		return doVisit(ConditionalExpression.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.ConstructorInvocation)
	 */
	@Override
	public boolean visit(ConstructorInvocation node) {
		return doVisit(ConstructorInvocation.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.ContinueStatement)
	 */
	@Override
	public boolean visit(ContinueStatement node) {
		return doVisit(ContinueStatement.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.DoStatement)
	 */
	@Override
	public boolean visit(DoStatement node) {
		return doVisit(DoStatement.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.EmptyStatement)
	 */
	@Override
	public boolean visit(EmptyStatement node) {
		return doVisit(EmptyStatement.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.EnhancedForStatement)
	 */
	@Override
	public boolean visit(EnhancedForStatement node) {
		return doVisit(EnhancedForStatement.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.EnumConstantDeclaration)
	 */
	@Override
	public boolean visit(EnumConstantDeclaration node) {
		return doVisit(EnumConstantDeclaration.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.EnumDeclaration)
	 */
	@Override
	public boolean visit(EnumDeclaration node) {
		return doVisit(EnumDeclaration.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.ExpressionStatement)
	 */
	@Override
	public boolean visit(ExpressionStatement node) {
		return doVisit(ExpressionStatement.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.FieldAccess)
	 */
	@Override
	public boolean visit(FieldAccess node) {
		return doVisit(FieldAccess.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.FieldDeclaration)
	 */
	@Override
	public boolean visit(FieldDeclaration node) {
		return doVisit(FieldDeclaration.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.ForStatement)
	 */
	@Override
	public boolean visit(ForStatement node) {
		return doVisit(ForStatement.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.IfStatement)
	 */
	@Override
	public boolean visit(IfStatement node) {
		return doVisit(IfStatement.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.ImportDeclaration)
	 */
	@Override
	public boolean visit(ImportDeclaration node) {
		return doVisit(ImportDeclaration.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.InfixExpression)
	 */
	@Override
	public boolean visit(InfixExpression node) {
		return doVisit(InfixExpression.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.Initializer)
	 */
	@Override
	public boolean visit(Initializer node) {
		return doVisit(Initializer.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.InstanceofExpression)
	 */
	@Override
	public boolean visit(InstanceofExpression node) {
		return doVisit(InstanceofExpression.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.Javadoc)
	 */
	@Override
	public boolean visit(Javadoc node) {
		return doVisit(Javadoc.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.LabeledStatement)
	 */
	@Override
	public boolean visit(LabeledStatement node) {
		return doVisit(LabeledStatement.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.LineComment)
	 */
	@Override
	public boolean visit(LineComment node) {
		return doVisit(LineComment.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.MarkerAnnotation)
	 */
	@Override
	public boolean visit(MarkerAnnotation node) {
		return doVisit(MarkerAnnotation.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.MemberRef)
	 */
	@Override
	public boolean visit(MemberRef node) {
		return doVisit(MemberRef.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.MemberValuePair)
	 */
	@Override
	public boolean visit(MemberValuePair node) {
		return doVisit(MemberValuePair.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.MethodDeclaration)
	 */
	@Override
	public boolean visit(MethodDeclaration node) {
		if (doVisit(MethodDeclaration.class, node)) {
			visit(node.getBody());
		}
		return false;
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.MethodInvocation)
	 */
	@Override
	public boolean visit(MethodInvocation node) {
		return doVisit(MethodInvocation.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.MethodRef)
	 */
	@Override
	public boolean visit(MethodRef node) {
		return doVisit(MethodRef.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.MethodRefParameter)
	 */
	@Override
	public boolean visit(MethodRefParameter node) {
		return doVisit(MethodRefParameter.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.Modifier)
	 */
	@Override
	public boolean visit(Modifier node) {
		return doVisit(Modifier.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.NormalAnnotation)
	 */
	@Override
	public boolean visit(NormalAnnotation node) {
		return doVisit(NormalAnnotation.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.NullLiteral)
	 */
	@Override
	public boolean visit(NullLiteral node) {
		return doVisit(NullLiteral.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.NumberLiteral)
	 */
	@Override
	public boolean visit(NumberLiteral node) {
		return doVisit(NumberLiteral.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.PackageDeclaration)
	 */
	@Override
	public boolean visit(PackageDeclaration node) {
		return doVisit(PackageDeclaration.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.ParameterizedType)
	 */
	@Override
	public boolean visit(ParameterizedType node) {
		return doVisit(ParameterizedType.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.ParenthesizedExpression)
	 */
	@Override
	public boolean visit(ParenthesizedExpression node) {
		return doVisit(ParenthesizedExpression.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.PostfixExpression)
	 */
	@Override
	public boolean visit(PostfixExpression node) {
		return doVisit(PostfixExpression.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.PrefixExpression)
	 */
	@Override
	public boolean visit(PrefixExpression node) {
		return doVisit(PrefixExpression.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.PrimitiveType)
	 */
	@Override
	public boolean visit(PrimitiveType node) {
		return doVisit(PrimitiveType.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.QualifiedName)
	 */
	@Override
	public boolean visit(QualifiedName node) {
		return doVisit(QualifiedName.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.QualifiedType)
	 */
	@Override
	public boolean visit(QualifiedType node) {
		return doVisit(QualifiedType.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.ReturnStatement)
	 */
	@Override
	public boolean visit(ReturnStatement node) {
		return doVisit(ReturnStatement.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.SimpleName)
	 */
	@Override
	public boolean visit(SimpleName node) {
		return doVisit(SimpleName.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.SimpleType)
	 */
	@Override
	public boolean visit(SimpleType node) {
		return doVisit(SimpleType.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.SingleMemberAnnotation)
	 */
	@Override
	public boolean visit(SingleMemberAnnotation node) {
		return doVisit(SingleMemberAnnotation.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.SingleVariableDeclaration)
	 */
	@Override
	public boolean visit(SingleVariableDeclaration node) {
		return doVisit(SingleVariableDeclaration.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.StringLiteral)
	 */
	@Override
	public boolean visit(StringLiteral node) {
		return doVisit(StringLiteral.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.SuperConstructorInvocation)
	 */
	@Override
	public boolean visit(SuperConstructorInvocation node) {
		return doVisit(SuperConstructorInvocation.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.SuperFieldAccess)
	 */
	@Override
	public boolean visit(SuperFieldAccess node) {
		return doVisit(SuperFieldAccess.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.SuperMethodInvocation)
	 */
	@Override
	public boolean visit(SuperMethodInvocation node) {
		return doVisit(SuperMethodInvocation.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.SwitchCase)
	 */
	@Override
	public boolean visit(SwitchCase node) {
		return doVisit(SwitchCase.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.SwitchStatement)
	 */
	@Override
	public boolean visit(SwitchStatement node) {
		return doVisit(SwitchStatement.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.SynchronizedStatement)
	 */
	@Override
	public boolean visit(SynchronizedStatement node) {
		return doVisit(SynchronizedStatement.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.TagElement)
	 */
	@Override
	public boolean visit(TagElement node) {
		return doVisit(TagElement.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.TextElement)
	 */
	@Override
	public boolean visit(TextElement node) {
		return doVisit(TextElement.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.ThisExpression)
	 */
	@Override
	public boolean visit(ThisExpression node) {
		return doVisit(ThisExpression.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.ThrowStatement)
	 */
	@Override
	public boolean visit(ThrowStatement node) {
		return doVisit(ThrowStatement.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.TryStatement)
	 */
	@Override
	public boolean visit(TryStatement node) {
		return doVisit(TryStatement.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.TypeDeclaration)
	 */
	@Override
	public boolean visit(TypeDeclaration node) {
		if (doVisit(TypeDeclaration.class, node)) {
			// visit java doc.
			visit(node.getJavadoc());
			// visit modifier.
			@SuppressWarnings("unchecked")
			List<Modifier> modifiers = node.modifiers();
			for (Modifier modifier : modifiers) {
				visit(modifier);
			}
			// visit field declaration.
			FieldDeclaration[] fields = node.getFields();
			for (FieldDeclaration field : fields) {
				visit(field);
			}
			// visit method declaration.
			MethodDeclaration[] methods = node.getMethods();
			for (MethodDeclaration method : methods) {
				visit(method);
			}
		}
		return false;
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.TypeDeclarationStatement)
	 */
	@Override
	public boolean visit(TypeDeclarationStatement node) {
		return doVisit(TypeDeclarationStatement.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.TypeLiteral)
	 */
	@Override
	public boolean visit(TypeLiteral node) {
		return doVisit(TypeLiteral.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.TypeParameter)
	 */
	@Override
	public boolean visit(TypeParameter node) {
		return doVisit(TypeParameter.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.UnionType)
	 */
	@Override
	public boolean visit(UnionType node) {
		return doVisit(UnionType.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.VariableDeclarationExpression)
	 */
	@Override
	public boolean visit(VariableDeclarationExpression node) {
		return doVisit(VariableDeclarationExpression.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.VariableDeclarationFragment)
	 */
	@Override
	public boolean visit(VariableDeclarationFragment node) {
		return doVisit(VariableDeclarationFragment.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.VariableDeclarationStatement)
	 */
	@Override
	public boolean visit(VariableDeclarationStatement node) {
		return doVisit(VariableDeclarationStatement.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.WhileStatement)
	 */
	@Override
	public boolean visit(WhileStatement node) {
		return doVisit(WhileStatement.class, node);
	}

	/**
	 * Overrider method visit.
	 * 
	 * @author manbaum
	 * @since Sep 29, 2014
	 * 
	 * @param node
	 *            the node to visit
	 * @return <code>true</code> if the children of this node should be visited,
	 *         and <code>false</code> if the children of this node should be
	 *         skipped.
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.WildcardType)
	 */
	@Override
	public boolean visit(WildcardType node) {
		return doVisit(WildcardType.class, node);
	}
}
