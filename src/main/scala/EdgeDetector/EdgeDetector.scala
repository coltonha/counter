package EdgeDetector

import chisel3._

class EdgeDetector(width: Int = 1, bothEdges: Boolean = false) extends Module {
  val io = IO(new Bundle {
    val in = Input(UInt(width.W))
    val out = Output(UInt(width.W))
  })
  val history = Reg(UInt(width.W))
  

    history := io.in
    
    if (!bothEdges) {
      io.out := ~history &io.in
    } else {
    // TODO: modify this such that when bothEdges is true the output should be a 1 cycle wide pulse when the input has a rising and falling edge
      io.out := history ^ io.in
    }
  


  // when (io.in =/= register) {
  //   register:=true.B
  // }
  // .otherwise {
  //   register:=false.B
  // }
  // io.out:=register
}
