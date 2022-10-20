package Counter

import chisel3._

class Counter extends Module {
  val io = IO(new Bundle {
    //val in  = Input(UInt(32.W))
    val out = Output(UInt(32.W))
  })
  
  val register = RegInit(UInt(32.W), 0.U)
  register := register + 1.U
  io.out := register
}