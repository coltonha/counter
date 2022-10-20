package gcd

import chiseltest.ChiselScalatestTester
import org.scalatest.freespec.AnyFreeSpec
import chiseltest._
import chisel3._

class CounterSpec extends AnyFreeSpec with ChiselScalatestTester {
  "counter should count" in {
    test(new Counter).withAnnotations(Seq(WriteVcdAnnotation)) { c =>
      c.io.out.expect(0.U)
      c.clock.step(1)
      c.io.out.expect(1.U)
      c.clock.step(5)
      c.io.out.expect(6.U)
      /*
      for (i <- 0 until 100) {
        //c.io.in.poke(i.U)
        c.clock.step(1)
        c.io.out.expect((i + 1).U)
      }

       */
    }
  }
}
