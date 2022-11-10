package memory


import chisel3._

class Memory(size: Int =10,width: Int = 8) extends Module {
    val io = IO(new Bundle {
        val rdAddr = Input(UInt(size.W))
        val rdData = Output(UInt(width.W))
        val wrAddr = Input(UInt(size.W))
        val wrData = Input(UInt(width.W))
        val wrEna = Input(Bool())
    })

    val mem = SyncReadMem(scala.math.pow(2,size).asInstanceOf[Int] , UInt (width.W))
    io.rdData := mem.read(io.rdAddr)
    when(io.wrEna) {
        mem.write(io.wrAddr , io.wrData)
    }
}