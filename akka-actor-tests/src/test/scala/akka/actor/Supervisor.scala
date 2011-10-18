/**
 * Copyright (C) 2009-2011 Typesafe Inc. <http://www.typesafe.com>
 */
package akka.actor

class Supervisor extends Actor {
  def receive = {
    case x: Props ⇒ reply(context.actorOf(x))
  }
}
