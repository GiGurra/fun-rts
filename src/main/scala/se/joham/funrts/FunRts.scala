package se.joham.funrts

import org.fusesource.jansi.AnsiConsole
import org.fusesource.jansi.Ansi._
import org.fusesource.jansi.Ansi.Color._

object FunRts {

  def main(args: Array[String]): Unit = try {

    AnsiConsole.systemInstall()

    for (i <- 0 until 100) {
      println(ansi().eraseScreen())
      println(ansi().fg(YELLOW).a("---------------------------------------------------"))
      println(ansi().bg(WHITE).fg(RED).a("Hello").bg(BLACK).fg(GREEN).a("World"))
      println(ansi().bg(WHITE).fg(RED).a("Hello").bg(BLACK).fg(GREEN).a("World"))
      println(ansi().bg(WHITE).fg(RED).a("Hello").bg(BLACK).fg(GREEN).a("World"))
      println(ansi().bg(WHITE).fg(RED).a("Hello").bg(BLACK).fg(GREEN).a("World"))
      println(ansi().bg(WHITE).fg(RED).a("Hello").bg(BLACK).fg(GREEN).a("World"))
      println(ansi().bg(WHITE).fg(RED).a("Hello").bg(BLACK).fg(GREEN).a("World"))
      println(ansi().bg(WHITE).fg(RED).a("Hello").bg(BLACK).fg(GREEN).a("World"))
      println(ansi().bg(WHITE).fg(RED).a("Hello").bg(BLACK).fg(GREEN).a("World"))
      println(ansi().bg(WHITE).fg(RED).a("Hello").bg(BLACK).fg(GREEN).a("World"))
      println(ansi().bg(WHITE).fg(RED).a("Hello").bg(BLACK).fg(GREEN).a("World"))
      println(ansi().bg(WHITE).fg(RED).a("Hello").bg(BLACK).fg(GREEN).a("World"))
      println(ansi().bg(WHITE).fg(RED).a("Hello").bg(BLACK).fg(GREEN).a("World"))
      println(ansi().bg(WHITE).fg(RED).a("Hello").bg(BLACK).fg(GREEN).a("World"))
      println(ansi().bg(WHITE).fg(RED).a("Hello").bg(BLACK).fg(GREEN).a("World"))
      println(ansi().bg(WHITE).fg(RED).a("Hello").bg(BLACK).fg(GREEN).a("World"))
      println(ansi().bg(WHITE).fg(RED).a("Hello").bg(BLACK).fg(GREEN).a("World"))
      println(ansi().bg(WHITE).fg(RED).a("Hello").bg(BLACK).fg(GREEN).a("World"))
      println(ansi().bg(WHITE).fg(RED).a("Hello").bg(BLACK).fg(GREEN).a("World"))
      println(ansi().bg(WHITE).fg(RED).a("Hello").bg(BLACK).fg(GREEN).a("World"))
      println(ansi().bg(WHITE).fg(RED).a("Hello").bg(BLACK).fg(GREEN).a("World"))
      println(ansi().bg(WHITE).fg(RED).a("Hello").bg(BLACK).fg(GREEN).a("World"))
      println(ansi().bg(WHITE).fg(RED).a("Hello").bg(BLACK).fg(GREEN).a("World"))
      println(ansi().bg(WHITE).fg(RED).a("Hello").bg(BLACK).fg(GREEN).a("World"))
      println(ansi().bg(WHITE).fg(RED).a("Hello").bg(BLACK).fg(GREEN).a("World"))
      println(ansi().fg(YELLOW).a("---------------------------------------------------"))
      println(ansi().reset())
      Thread.sleep(1500)
    }

  } finally {
    AnsiConsole.systemUninstall()
  }
}
