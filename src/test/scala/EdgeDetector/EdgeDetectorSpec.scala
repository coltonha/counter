package EdgeDetector

import chiseltest.ChiselScalatestTester
import org.scalatest.freespec.AnyFreeSpec
import chiseltest._
import chisel3._

class EdgeDetectorSpec extends AnyFreeSpec with ChiselScalatestTester {

  "normal edge detector " in {
    test(new EdgeDetector(1,false)).withAnnotations(Seq(/*VerilatorBackendAnnotation,*/ WriteVcdAnnotation)) { c =>
      c.io.in.poke(0.U)
      c.clock.step(1)
      c.io.in.poke(0.U)
      c.io.out.expect(0.U)   //0->0  :0
      
      c.clock.step(1)
      c.io.in.poke(1.U)
      c.io.out.expect(1.U)   //0->1 :1

      c.clock.step(1)
      c.io.in.poke(1.U)
      c.io.out.expect(0.U) //1->1 : 0

      c.clock.step(1)
      c.io.in.poke(0.U)
      c.io.out.expect(0.U)   //1->0  :0

      c.clock.step(1)
      // c.io.out.expect(0.U)
    }
  }
    "both edge detector " in {
    test(new EdgeDetector(1,true)).withAnnotations(Seq(/*VerilatorBackendAnnotation,*/ WriteVcdAnnotation)) { c =>
      c.io.in.poke(0.U)
      c.clock.step(1)
      c.io.in.poke(0.U)
      c.io.out.expect(0.U)   //0->0  :0
      
      c.clock.step(1)
      c.io.in.poke(1.U)
      c.io.out.expect(1.U)   //0->1 :1

      c.clock.step(1)
      c.io.in.poke(1.U)
      c.io.out.expect(0.U) //1->1 : 0

      c.clock.step(1)
      c.io.in.poke(0.U)
      c.io.out.expect(1.U)   //1->0  :0

      c.clock.step(5)
    }
  }

}
