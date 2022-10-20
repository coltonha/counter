package EdgeDetector

import chisel3._

class EdgeDetector extends Module {
  val io = IO(new Bundle {
    val in = Input(Bool())
    val out = Output(Bool())
  })
  io.out := 0.B
}
