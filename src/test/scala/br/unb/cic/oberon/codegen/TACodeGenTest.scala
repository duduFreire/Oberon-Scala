package br.unb.cic.oberon.codegen

import br.unb.cic.oberon.ir.ast.{Constant => ASTConstant, _}
import br.unb.cic.oberon.ir.tac._
import org.scalatest.funsuite.AnyFunSuite

class TACodeTest extends AnyFunSuite {

  test("Generate add between constants") {

    Temporary.reset()
    val expr = AddExpression(IntValue(1), IntValue(2))
    val (t, list) = TACodeGenerator.generateExpression(expr, List())

    Temporary.reset()
    val t0 = new Temporary(IntegerType, 0, true)
    val ops = List(AddOp(Constant("1", IntegerType), Constant("2", IntegerType), t0, ""))

    assert(list == ops)
  }

  test("Generate add between add and constant") {
    
    Temporary.reset()
    val expr = AddExpression(AddExpression(IntValue(1), IntValue(2)), IntValue(3))
    val (t, list) = TACodeGenerator.generateExpression(expr, List())

    Temporary.reset()
    val t0 = new Temporary(IntegerType, 0, true)
    val t1 = new Temporary(IntegerType, 1, true)
    val ops = List(
      AddOp(Constant("1", IntegerType), Constant("2", IntegerType), t0, ""),
      AddOp(t0, Constant("3", IntegerType), t1, "")
    )

    assert(list == ops)
  }

}
