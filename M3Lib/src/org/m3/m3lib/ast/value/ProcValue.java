/**
 * Copyright (c) 2008 B. Laverman. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: bertl
 */

// $Id$

package org.m3.m3lib.ast.value;

import java.io.Serializable;
import java.util.List;

import org.m3.m3lib.ast.Binding;
import org.m3.m3lib.ast.Context;
import org.m3.m3lib.ast.IncompatibleTypeException;
import org.m3.m3lib.ast.M3RuntimeException;
import org.m3.m3lib.ast.SimpleContext;
import org.m3.m3lib.ast.stat.BlockStatement;
import org.m3.m3lib.ast.stat.ReturnException;
import org.m3.m3lib.ast.type.ProcedureType;
import org.m3.m3lib.ast.type.Type;
import org.m3.m3lib.walkers.run.StatementRunner;

/**
 * 
 * @author LavermB
 * 
 */
public class ProcValue
    implements Value, Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = -2564327106932332042L;

  private ProcedureType     type_;

  private BlockStatement    body_;

  /**
   * 
   */
  public ProcValue(ProcedureType type, BlockStatement body)
  {
    super();

    this.type_ = type;
    this.body_ = body;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.Value#getType()
   */
  public Type getType()
  {
    return this.type_;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.Value#getValue()
   */
  public Value getValue()
  {
    return this;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.Value#toObject()
   */
  public Object toObject()
  {
    return this;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.Value#toNumber()
   */
  public Number toNumber()
      throws IncompatibleTypeException
  {
    throw new IncompatibleTypeException("A PROCEDURE is not a number");
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.Value#toLong()
   */
  public long toLong()
      throws IncompatibleTypeException
  {
    throw new IncompatibleTypeException("A PROCEDURE is not a number");
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.Value#toLongOrdinal()
   */
  public long toLongOrdinal()
      throws IncompatibleTypeException
  {
    throw new IncompatibleTypeException("A PROCEDURE is not an ordinal");
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.Value#toDouble()
   */
  public double toDouble()
      throws IncompatibleTypeException
  {
    throw new IncompatibleTypeException("A PROCEDURE is not a number");
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.syllogic.m3.ast.value.Value#toBoolean()
   */
  public boolean toBoolean()
      throws IncompatibleTypeException
  {
    throw new IncompatibleTypeException("A PROCEDURE is not a boolean");
  }

  /**
   * @return Returns the body.
   */
  public BlockStatement getBody()
  {
    return this.body_;
  }

  public static Value runProcedure(Context ctx, ProcValue proc,
      Value... actuals)
      throws M3RuntimeException
  {
    try {
      ProcedureType type = proc.type_;
      List<ProcedureType.FormalArgument> formals = type.getArgs();

      if (formals.size() != actuals.length) { throw new IncompatibleTypeException(
          "Number of actuals doesn't match number of formals"); }
      SimpleContext inner = new SimpleContext(ctx);
      for (int i = 0; i < actuals.length; i++) {
        final ProcedureType.FormalArgument formal = formals.get(i);
        final Value actual = actuals[i];
        if (!formal.getType().isAssignableFrom(actual.getType())) { throw new IncompatibleTypeException(
            "Actual for parameter \"" + formal.getName() + "\" type mismatch",
            formal.getType(), actual); }
        if (formal.getKind().equals(ProcedureType.ArgumentKind.AK_VAR)
            && !(actual instanceof LValue)) { throw new IncompatibleTypeException(
            "Actual for parameter \"" + formal.getName()
                + "\" is not an LValue"); }
        Binding parm = null;
        if (formal.getKind().equals(ProcedureType.ArgumentKind.AK_VAR)) {

        }
        else {
          parm = new Binding(formal.getName(), formal.getType(), actual
              .getValue());
        }
        parm.setConst(formal.getKind().equals(
            ProcedureType.ArgumentKind.AK_CONST));

        inner.add(parm);
      }
      StatementRunner.run(proc.getBody(), inner);
    }
    catch (ReturnException e) {
      Value result = e.getResult();

      if (result == null) {
        if (((ProcedureType)proc.getType()).getReturnType() != null) {
          throw new M3RuntimeException("Function failed to return a value");
        }
      }
      else {
        if (((ProcedureType)proc.getType()).getReturnType() == null) {
          throw new M3RuntimeException("Procedure returned a value");
        }
      }
      return result;
    }
    return ObjectValue.UNDEFINED;
  }

  public static Value runProcedure(Context ctx, ProcValue proc,
      List<Value> actuals)
      throws M3RuntimeException
  {
    Value[] arr = new Value[actuals.size()];

    for (int i = 0; i < actuals.size(); i++) {
      arr[i] = actuals.get(i);
    }

    return runProcedure(ctx, proc, arr);
  }
}

/*
 * $Log$
 */
