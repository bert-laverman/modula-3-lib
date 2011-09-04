/**
 * Copyright (c) 2008 B. Laverman. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: bertl
 */

// $Id$


package org.m3.m3lib.reflect.visitor;

/**
 * 
 * @author LavermB
 *
 */
public interface Visitor
{

  // void            startVisit(TargetClass obj[,
  //                            SomeStateHolder stateFromParent])
  // void            beforeChildVisit(TargetClass1 obj[,
  //                                  TargetClass2 child][,
  //                                  SomeStateHolder currentState])
  // void            afterChildVisit(TargetClass1 obj[,
  //                                 TargetClass2 child][,
  //                                 SomeStateHolder currentState])
  // void            betweenChildVisits(TargetClass obj,
  //                                    [SomeStateHolder currentState])
  // void            endVisit(TargetClass obj[,
  //                          SomeStateHolder currentState])

  // SomeStateHolder getInitialState(TargetClass obj[,
  //                                 SomeStateHolder stateFromParent]);
  // SomeStateHolder getStateForChild(TargetClass1 obj[,
  //                                  TargetClass2 child][,
  //                                  SomeStateHolder currentState]);
  // SomeStateHolder processStateFromChild(TargetClass1 obj[,
  //                                       TargetClass2 child][,
  //                                       SomeStateHolder stateFromChild[,
  //                                       SomeStateHolder currentState]])
  // SomeStateHolder getReturnState(TargetClass obj[,
  //                                SomeStateHolder currentState]);

  // List            getChildren(TargetClass obj)

}

/*
 * $Log$
 */
