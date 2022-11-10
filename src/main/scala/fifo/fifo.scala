package fifo

import chisel3._
import chisel3.util._

class fifo(width: Int, depth: Int) extends Module {
  val io = IO(new Bundle {
    //fifo write interface
    val din = Input(UInt(width.W))
    val din_valid = Input(Bool()) //writeEn
    val din_ready = Output(Bool()) //!full

    //fifo read interface
    val dout = Output(UInt(width.W))
    val dout_valid = Output(Bool()) //!empty
    val dout_ready = Input(Bool()) //readEn
  })
  val fifo= Reg(Vec(depth, UInt(width.W)))
  val wrCounter = RegInit(0.U(log2Ceil(depth).W))
  val rdCounter = RegInit(0.U(log2Ceil(depth).W))
  val elemCounter= RegInit(0.U(log2Ceil(depth+1).W)) //possible element number :0~depth=>depth+1

  io.din_ready:= elemCounter<depth.U //not ready(fifo is full) when depth=elemcounter
  io.dout_valid:= elemCounter> 0.U //can dequeue from fifo when # of elements are bigger than 0

  //circle fifo
  when (wrCounter === (depth).U) {
    wrCounter := 0.U
  }
  when (rdCounter === (depth).U) {
    rdCounter := 0.U
  }
  

  io.dout:=fifo(rdCounter)
  when (io.din_valid && io.dout_ready && io.din_ready && io.dout_valid) {
    //write first (enqueue)
    fifo(wrCounter) := io.din
    wrCounter := wrCounter + 1.U
    //read (dequeue)
    // io.dout := fifo(rdCounter)
    rdCounter := rdCounter + 1.U
    //elemCounter doesnt change bc it stays the same
  }.elsewhen(io.din_valid && io.din_ready) {
    fifo(wrCounter) := io.din
    wrCounter := wrCounter + 1.U
    elemCounter := elemCounter + 1.U
    //elem added, wrCounter+1
  }.elsewhen (io.dout_ready && io.dout_valid) {
    // io.dout := fifo(rdCounter)
    rdCounter := rdCounter + 1.U
    elemCounter := elemCounter - 1.U
    //elem removed, rdCounter+1
  }
  
  when (elemCounter===0.U) {
    io.dout:=0.U
  }
}
