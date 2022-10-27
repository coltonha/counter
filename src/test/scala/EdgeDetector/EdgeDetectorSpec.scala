package EdgeDetector

import chiseltest.ChiselScalatestTester
import org.scalatest.freespec.AnyFreeSpec
import chiseltest._
import chisel3._

class EdgeDetectorSpec extends AnyFreeSpec with ChiselScalatestTester {
  "edge detector should detect rising edges" in {
    test(new EdgeDetector(false)).withAnnotations(Seq(VerilatorBackendAnnotation, WriteVcdAnnotation)) { c =>
      c.io.in.poke(0.U)
      c.clock.step(1)
      c.io.out.expect(0.U)
      c.io.in.poke(1.U)
      c.clock.step(1)
      c.io.out.expect(1.U)
      c.clock.step(1)
      c.io.out.expect(0.U)

      c.io.in.poke(0.U)
      c.clock.step(1)
      c.io.out.expect(0.U)
      c.io.in.poke(1.U)
      c.clock.step(1)
      c.io.out.expect(1.U)
      c.clock.step(1)
      c.io.out.expect(0.U)
    }
  }
  // TODO: new test case for the falling / rising edge case
}
