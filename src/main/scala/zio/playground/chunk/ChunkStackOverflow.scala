package zio.playground.chunk

import zio.console.putStrLn
import zio.{ Chunk, UIO, ZIO }

object ChunkStackOverflow extends zio.App {

  override def run(args: List[String]): ZIO[zio.ZEnv, Nothing, Int] = {

    val chunk = (1 to 10000).foldLeft(Chunk.single(0))(_ + _)

    for {
      chunks    <- UIO.succeed(chunk)
      printable <- UIO(chunks.fold(0)(_ + _))
      _         <- putStrLn(s"$printable")
    } yield 0

  }
}
