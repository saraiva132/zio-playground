package zio.playground.stream

import zio.console.putStrLn
import zio.stream.ZStream
import zio.{ UIO, ZIO }

object EmptyIterableHangs extends zio.App {

  def stream1 =
    ZStream
      .range(0, 1000)
      .partitionEither(i => if (i % 2 == 0) UIO.succeed(Left(i)) else UIO.succeed(Right(i)))
      .map { case (evens, odds) => evens.mergeEither(odds) }
      .use(_.runCollect)

  def stream2 =
    ZStream
      .fromIterable[Int](List.empty)
      .partitionEither(i => if (i % 2 == 0) UIO.succeed(Left(i)) else UIO.succeed(Right(i)))
      .map { case (evens, odds) => evens.mergeEither(odds) }
      .use(_.runCollect)

  def stream3 =
    ZStream
      .fromIterable[Int](Seq.empty)
      .runCollect

  override def run(args: List[String]) =
    ZIO
      .sequence(
        Range(0, 10000).toList.map(
          i =>
            for {
              res <- stream2
              _   <- putStrLn(s"$i $res")
            } yield ()
        )
      )
      .map(_ => 0)
}
