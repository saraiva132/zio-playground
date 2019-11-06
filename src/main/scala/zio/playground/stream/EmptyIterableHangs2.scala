package zio.playground.stream

import zio.stream.ZStream
import zio.{ UIO, ZIO }

object EmptyIterableHangs2 extends zio.App {

  def stream =
    ZStream[Int]()
      .partitionEither(i => if (i % 2 == 0) UIO.succeed(Left(i)) else UIO.succeed(Right(i)))
      .map { case (evens, odds) => evens.mergeEither(odds) }
      .use(_.runCollect)

  override def run(args: List[String]) =
    ZIO
      .sequence(
        Range(0, 1000).toList.map(
          i =>
            for {
              res <- stream
              _   <- ZIO.effectTotal(println(i, res))
            } yield ()
        )
      )
      .map(_ => 0)
}
