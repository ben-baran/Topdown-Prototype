package com.barantschik.game.util;

public interface Listener<Sender, Message>
{
	public void inform(Sender s, Message m);
}