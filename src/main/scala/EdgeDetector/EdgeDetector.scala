package EdgeDetector

import chisel3._

class EdgeDetector extends Module {
  val io = IO(new Bundle {
    val in = Input(Bool())
    val out = Output(Bool())
  })
  val register=Reg(Bool())
  when (io.in =/= register) {
    register:=true.B
  }
  .otherwise {
    register:=false.B
  }
  io.out:=register
}
