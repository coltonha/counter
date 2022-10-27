package EdgeDetector

import chisel3._

class EdgeDetector(width: Int = 1, bothEdges: Boolean = false) extends Module {
  val io = IO(new Bundle {
    val in = Input(Vec(Bool(), width))
    val out = Output(Vec(Bool(), width))
  })

  if (!bothEdges) {
    val history = Reg(Bool())
    history := io.in

    when (!history && io.in) {
      io.out := true.B
    }.otherwise {
      io.out := false.B
    }
  } else {
    // TODO: modify this such that when bothEdges is true the output should be a 1 cycle wide pulse when the input has a rising and falling edge

  }

  when (io.in =/= register) {
    register:=true.B
  }
  .otherwise {
    register:=false.B
  }
  io.out:=register
}
