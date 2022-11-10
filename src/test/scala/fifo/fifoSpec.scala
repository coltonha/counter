package fifo

import chiseltest.ChiselScalatestTester
import org.scalatest.freespec.AnyFreeSpec
import chiseltest._
import chisel3._

class FIFOSpec extends AnyFreeSpec with ChiselScalatestTester {
    "FIFO should be first in first out" in {
        test(new fifo(8, 2)).withAnnotations(Seq(WriteVcdAnnotation)) { c =>
            
            //adds 10 to queue, not valid
            c.io.din.poke(10.U)
            c.io.din_valid.poke(0.B)
            c.io.dout_ready.poke(0.B)
            c.clock.step(1)
            c.io.din_ready.expect(1.B) // not full so should be ready to take in input
            c.io.dout_valid.expect(0.B) // empty so should not be ready to output

            // adds 6 to queue, valid 
            c.io.din.poke(6.U)
            c.io.din_valid.poke(1.B)
            c.io.dout_ready.poke(0.B)
            c.clock.step(1)
            c.io.din_ready.expect(1.B) // not full so should be ready to take in input
            c.io.dout_valid.expect(1.B) // not empty so should be ready to output
            c.io.dout.expect(6.U)

            // takes first value out of queue
            c.io.din.poke(15.U)
            c.io.din_valid.poke(0.B)
            c.io.dout_ready.poke(1.B)
            c.clock.step(1)
            c.io.din_ready.expect(1.B) // not full so should be read to take in input
            c.io.dout_valid.expect(0.B) // should be empty so should be ready to output 
            c.io.dout.expect(0.U)
            

            // adds 15 to queue, valid 
            c.io.din.poke(15.U)
            c.io.din_valid.poke(1.B)
            c.io.dout_ready.poke(0.B)
            c.clock.step(1)
            c.io.din_ready.expect(1.B) //not full so should be ready to take in input
            c.io.dout_valid.expect(1.B) // not empty so should be ready to output
            c.io.dout.expect(15.U)

            // add 3 to queue, valid 
            c.io.din.poke(3.U)
            c.io.din_valid.poke(1.B)
            c.io.dout_ready.poke(0.B)
            c.clock.step(1)
            c.io.din_ready.expect(0.B) // full so should not be ready to take in input
            c.io.dout_valid.expect(1.B) // not empty so should be ready to output
            c.io.dout.expect(15.U)

            // takes first value out of queue
            c.io.din_valid.poke(0.B)
            c.io.dout_ready.poke(1.B)
            c.clock.step(1)
            c.io.din_ready.expect(1.B) // not full anymore so should be read to take in input
            c.io.dout_valid.expect(1.B) // not empty (still has 3) so should be ready to output 
            c.io.dout.expect(3.U) 

            // adds a value to the queue and take one out at the same time
            c.io.din.poke(8.U)
            c.io.din_valid.poke(1.B)
            c.io.dout_ready.poke(1.B)
            c.clock.step(1)
            c.io.din_ready.expect(1.B) // not full anymore so should be read to take in input
            c.io.dout_valid.expect(1.B) // not empty (still has 8) so should be ready to output 
            c.io.dout.expect(8.U) 

            // take last value out of the queue
            c.io.din.poke(20.U)
            c.io.din_valid.poke(0.B)
            c.io.dout_ready.poke(1.B)
            c.clock.step(1)
            c.io.din_ready.expect(1.B) // not full so should be read to take in input
            c.io.dout_valid.expect(0.B) // should be empty 
            c.io.dout.expect(0.U) 

        }
    }
        "fifo full enque deque" in {
        test(new fifo(8, 3)).withAnnotations(Seq(WriteVcdAnnotation)) { c =>

            // adds 6 to queue, valid 
            c.io.din.poke(6.U)
            c.io.din_valid.poke(1.B)
            c.io.dout_ready.poke(0.B)
            c.clock.step(1)
            c.io.din_ready.expect(1.B) // not full so should be ready to take in input
            c.io.dout_valid.expect(1.B) // not empty so should be ready to output
            c.io.dout.expect(6.U)

            // adds 15 to queue, valid 
            c.io.din.poke(15.U)
            c.io.din_valid.poke(1.B)
            c.io.dout_ready.poke(0.B)
            c.clock.step(1)
            c.io.din_ready.expect(1.B) //not full so should be ready to take in input
            c.io.dout_valid.expect(1.B) // not empty so should be ready to output
            c.io.dout.expect(6.U)

            // add 3 to queue, valid 
            c.io.din.poke(3.U)
            c.io.din_valid.poke(1.B)
            c.io.dout_ready.poke(0.B)
            c.clock.step(1)
            c.io.din_ready.expect(0.B) // full so should not be ready to take in input
            c.io.dout_valid.expect(1.B) // not empty so should be ready to output
            c.io.dout.expect(6.U)

            // try to add 4 to queue, invalid
            c.io.din.poke(4.U)
            c.io.din_valid.poke(1.B)
            c.io.dout_ready.poke(0.B)
            c.clock.step(1)
            c.io.din_ready.expect(0.B) // full so should not be ready to take in input
            c.io.dout_valid.expect(1.B) // not empty so should be ready to output
            c.io.dout.expect(6.U) //since it was invalid, nothing changed

            // // takes first value out of queue
            // c.io.din.poke(15.U)
            // c.io.din_valid.poke(0.B)
            // c.io.dout_ready.poke(1.B)
            // c.clock.step(1)
            // c.io.din_ready.expect(1.B) // not full so should be read to take in input
            // c.io.dout_valid.expect(0.B) // should be empty so should be ready to output 
            // c.io.dout.expect(0.U)

            // // takes first value out of queue
            // c.io.din_valid.poke(0.B)
            // c.io.dout_ready.poke(1.B)
            // c.clock.step(1)
            // c.io.din_ready.expect(1.B) // not full anymore so should be read to take in input
            // c.io.dout_valid.expect(1.B) // not empty (still has 3) so should be ready to output 
            // c.io.dout.expect(3.U) 

            // // adds a value to the queue and take one out at the same time
            // c.io.din.poke(8.U)
            // c.io.din_valid.poke(1.B)
            // c.io.dout_ready.poke(1.B)
            // c.clock.step(1)
            // c.io.din_ready.expect(1.B) // not full anymore so should be read to take in input
            // c.io.dout_valid.expect(1.B) // not empty (still has 8) so should be ready to output 
            // c.io.dout.expect(8.U) 

            // // take last value out of the queue
            // c.io.din.poke(20.U)
            // c.io.din_valid.poke(0.B)
            // c.io.dout_ready.poke(1.B)
            // c.clock.step(1)
            // c.io.din_ready.expect(1.B) // not full so should be read to take in input
            // c.io.dout_valid.expect(0.B) // should be empty 
            // c.io.dout.expect(0.U) 

        }
    }
}