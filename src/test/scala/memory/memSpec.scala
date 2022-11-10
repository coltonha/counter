package memory

import chiseltest.ChiselScalatestTester
import org.scalatest.freespec.AnyFreeSpec
import chiseltest._
import chisel3._

class MemSpec extends AnyFreeSpec with ChiselScalatestTester {
    "memory of size 64KiB with 1Byte wide data" in {
        test(new Memory(16,8)).withAnnotations(Seq(VerilatorBackendAnnotation,WriteVcdAnnotation)) { c =>

            c.io.wrAddr.poke("hdead".U)
            c.io.wrEna.poke(1)
            c.io.wrData.poke("hef".U)
            c.clock.step(1) //testwriteenable
            
            c.io.wrEna.poke(0)
            c.io.wrAddr.poke("hdead".U)
            c.io.wrData.poke("h00".U)
            c.clock.step(1) //testwriteenable false
            
            c.io.rdAddr.poke("hdead".U)
            c.clock.step(1)
            c.io.rdData.expect("hef".U)  
            
        }
    }
}